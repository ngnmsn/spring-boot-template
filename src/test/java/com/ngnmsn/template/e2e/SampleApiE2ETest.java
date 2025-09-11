package com.ngnmsn.template.e2e;

import com.ngnmsn.template.presentation.request.BulkDeleteRequest;
import com.ngnmsn.template.presentation.request.SampleCreateRequest;
import com.ngnmsn.template.presentation.request.SampleUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
@ActiveProfiles("test")
class SampleApiE2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    private String baseUrl;
    
    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/samples";
    }
    
    @Test
    @Order(1)
    void shouldCreateSampleThroughAPI() {
        var request = new SampleCreateRequest("APIテスト", 123);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(baseUrl, request, Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("message")).isEqualTo("サンプルが作成されました");
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        assertThat(data.get("text1")).isEqualTo("APIテスト");
        assertThat(data.get("num1")).isEqualTo(123);
        assertThat(data.get("id")).isNotNull();
    }
    
    @Test
    @Order(2)
    void shouldGetSamplesThroughAPI() {
        createTestSample("検索テスト1", 100);
        createTestSample("検索テスト2", 200);
        
        ResponseEntity<Map> response = restTemplate.getForEntity(
            baseUrl + "?text1=検索テスト&page=1&size=10", Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("content");
        
        assertThat(content).hasSizeGreaterThanOrEqualTo(2);
        assertThat(content.stream().anyMatch(item -> "検索テスト1".equals(item.get("text1")))).isTrue();
        assertThat(content.stream().anyMatch(item -> "検索テスト2".equals(item.get("text1")))).isTrue();
    }
    
    @Test
    @Order(3)
    void shouldUpdateSampleThroughAPI() {
        var sampleId = createTestSample("更新前", 999);
        var updateRequest = new SampleUpdateRequest("更新後", 777);
        
        var requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<Map> response = restTemplate.exchange(
            baseUrl + "/" + sampleId, 
            HttpMethod.PUT, 
            requestEntity, 
            Map.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        assertThat(data.get("text1")).isEqualTo("更新後");
        assertThat(data.get("num1")).isEqualTo(777);
        
        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
            baseUrl + "/" + sampleId, Map.class);
        @SuppressWarnings("unchecked")
        Map<String, Object> getData = (Map<String, Object>) getResponse.getBody().get("data");
        assertThat(getData.get("text1")).isEqualTo("更新後");
    }
    
    @Test
    @Order(4)
    void shouldDeleteSampleThroughAPI() {
        var sampleId = createTestSample("削除テスト", 555);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            baseUrl + "/" + sampleId, 
            HttpMethod.DELETE, 
            null, 
            Map.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("message")).isEqualTo("サンプルが削除されました");
        
        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
            baseUrl + "/" + sampleId, Map.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    @Test
    @Order(5)
    void shouldBulkDeleteSamplesThroughAPI() {
        var id1 = createTestSample("一括削除1", 111);
        var id2 = createTestSample("一括削除2", 222);
        var id3 = createTestSample("一括削除3", 333);
        
        var bulkDeleteRequest = new BulkDeleteRequest(List.of(id1, id2, id3));
        
        var requestEntity = new HttpEntity<>(bulkDeleteRequest);
        ResponseEntity<Map> response = restTemplate.exchange(
            baseUrl + "/bulk-delete", 
            HttpMethod.POST, 
            requestEntity, 
            Map.class
        );
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        assertThat(response.getBody().get("message")).isEqualTo("3件のサンプルが削除されました");
        
        List.of(id1, id2, id3).forEach(id -> {
            ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                baseUrl + "/" + id, Map.class);
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });
    }
    
    @Test
    @Order(6)
    void shouldHandleValidationErrorsThroughAPI() {
        var invalidRequest = new SampleCreateRequest("", null);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(baseUrl, invalidRequest, Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("success")).isEqualTo(false);
        assertThat(response.getBody().get("message")).isEqualTo("入力値に問題があります");
        
        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) response.getBody().get("errors");
        assertThat(errors).isNotEmpty();
        assertThat(errors.stream().anyMatch(error -> error.contains("テキストは必須"))).isTrue();
        assertThat(errors.stream().anyMatch(error -> error.contains("数値は必須"))).isTrue();
    }
    
    @Test
    @Order(7)
    void shouldGetStatisticsThroughAPI() {
        createTestSample("統計テスト長いテキスト".repeat(5), 100);
        createTestSample("短い", 200);
        createTestSample("普通", 101);
        
        ResponseEntity<Map> response = restTemplate.getForEntity(
            baseUrl + "/statistics", Map.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("success")).isEqualTo(true);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        assertThat(data.get("totalCount")).isNotNull();
        assertThat(data.get("averageNumber")).isNotNull();
        assertThat(data.get("longTextCount")).isNotNull();
        assertThat(data.get("evenNumberCount")).isNotNull();
    }
    
    private Long createTestSample(String text, Integer number) {
        var request = new SampleCreateRequest(text, number);
        ResponseEntity<Map> response = restTemplate.postForEntity(baseUrl, request, Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return ((Number) data.get("id")).longValue();
    }
}