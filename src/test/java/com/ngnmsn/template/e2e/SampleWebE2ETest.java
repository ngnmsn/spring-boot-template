package com.ngnmsn.template.e2e;

import com.ngnmsn.template.presentation.request.SampleCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
class SampleWebE2ETest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    private WebDriver driver;
    
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Test
    @Order(1)
    void shouldDisplaySampleSearchPage() {
        var baseUrl = "http://localhost:" + port;
        
        driver.get(baseUrl + "/samples");
        
        assertThat(driver.getTitle()).contains("サンプル一覧");
        assertThat(driver.findElement(By.tagName("h1")).getText())
            .contains("サンプル一覧");
        
        assertThat(driver.findElement(By.name("displayId"))).isNotNull();
        assertThat(driver.findElement(By.name("text1"))).isNotNull();
        
        var createButton = driver.findElement(By.linkText("新規作成"));
        assertThat(createButton).isNotNull();
    }
    
    @Test
    @Order(2)
    void shouldCreateSampleThroughWebUI() {
        var baseUrl = "http://localhost:" + port;
        var testText = "E2Eテストサンプル";
        var testNumber = "123";
        
        driver.get(baseUrl + "/samples");
        driver.findElement(By.linkText("新規作成")).click();
        
        assertThat(driver.getCurrentUrl()).contains("/samples/create");
        assertThat(driver.getTitle()).contains("サンプル作成");
        
        driver.findElement(By.name("text1")).sendKeys(testText);
        driver.findElement(By.name("num1")).sendKeys(testNumber);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlMatches(".*/samples/\\d+"));
        
        assertThat(driver.findElement(By.className("sample-text")).getText())
            .contains(testText);
        assertThat(driver.findElement(By.className("sample-number")).getText())
            .contains(testNumber);
        
        var successMessage = driver.findElement(By.className("alert-success"));
        assertThat(successMessage.getText()).contains("作成しました");
    }
    
    @Test
    @Order(3)
    void shouldSearchSamplesThroughWebUI() {
        createTestSample("検索テスト1", 100);
        createTestSample("検索テスト2", 200);
        
        var baseUrl = "http://localhost:" + port;
        
        driver.get(baseUrl + "/samples");
        driver.findElement(By.name("text1")).sendKeys("検索テスト");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        var resultRows = driver.findElements(By.cssSelector("table.sample-list tbody tr"));
        assertThat(resultRows).hasSizeGreaterThanOrEqualTo(2);
        
        var pageText = driver.getPageSource();
        assertThat(pageText).contains("検索テスト1");
        assertThat(pageText).contains("検索テスト2");
    }
    
    @Test
    @Order(4)
    void shouldUpdateSampleThroughWebUI() {
        var sampleId = createTestSample("更新前テキスト", 999);
        var baseUrl = "http://localhost:" + port;
        
        driver.get(baseUrl + "/samples/" + sampleId);
        driver.findElement(By.linkText("編集")).click();
        
        assertThat(driver.getCurrentUrl()).contains("/samples/" + sampleId + "/edit");
        
        var textField = driver.findElement(By.name("text1"));
        textField.clear();
        textField.sendKeys("更新後テキスト");
        
        var numberField = driver.findElement(By.name("num1"));
        numberField.clear();
        numberField.sendKeys("777");
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/samples/" + sampleId));
        
        assertThat(driver.findElement(By.className("sample-text")).getText())
            .contains("更新後テキスト");
        assertThat(driver.findElement(By.className("sample-number")).getText())
            .contains("777");
    }
    
    @Test
    @Order(5)
    void shouldDeleteSampleThroughWebUI() {
        var sampleId = createTestSample("削除テストサンプル", 555);
        var baseUrl = "http://localhost:" + port;
        
        driver.get(baseUrl + "/samples/" + sampleId);
        
        driver.findElement(By.cssSelector("button.delete-button")).click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).contains("削除");
        alert.accept();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/samples"));
        
        var successMessage = driver.findElement(By.className("alert-success"));
        assertThat(successMessage.getText()).contains("削除しました");
        
        driver.get(baseUrl + "/samples/" + sampleId);
        assertThat(driver.findElement(By.className("alert-danger")).getText())
            .contains("見つかりません");
    }
    
    @Test
    @Order(6)
    void shouldHandleValidationErrorsOnWebUI() {
        var baseUrl = "http://localhost:" + port;
        
        driver.get(baseUrl + "/samples/create");
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        assertThat(driver.getCurrentUrl()).contains("/samples/create");
        
        var errorMessages = driver.findElements(By.className("field-error"));
        assertThat(errorMessages).isNotEmpty();
        
        driver.findElement(By.name("text1")).sendKeys("a".repeat(101));
        driver.findElement(By.name("num1")).sendKeys("10000");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        var textError = driver.findElement(By.cssSelector(".text1-error"));
        assertThat(textError.getText()).contains("100文字以内");
        
        var numberError = driver.findElement(By.cssSelector(".num1-error"));
        assertThat(numberError.getText()).contains("9999以下");
    }
    
    private Long createTestSample(String text, Integer number) {
        var createUrl = "http://localhost:" + port + "/api/samples";
        var request = new SampleCreateRequest(text, number);
        
        ResponseEntity<Map> response = restTemplate.postForEntity(createUrl, request, Map.class);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return ((Number) data.get("id")).longValue();
    }
}