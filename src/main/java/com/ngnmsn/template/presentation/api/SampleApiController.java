package com.ngnmsn.template.presentation.api;

import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.service.SampleApplicationService;
import com.ngnmsn.template.presentation.request.BulkDeleteRequest;
import com.ngnmsn.template.presentation.request.SampleCreateRequest;
import com.ngnmsn.template.presentation.request.SampleUpdateRequest;
import com.ngnmsn.template.presentation.response.ApiResponse;
import com.ngnmsn.template.presentation.response.PagedResponse;
import com.ngnmsn.template.presentation.response.SampleResponse;
import com.ngnmsn.template.presentation.response.SampleStatisticsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/samples")
public class SampleApiController {
    private final SampleApplicationService sampleApplicationService;
    
    public SampleApiController(SampleApplicationService sampleApplicationService) {
        this.sampleApplicationService = Objects.requireNonNull(sampleApplicationService);
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<SampleResponse>>> search(
            @RequestParam(required = false) String displayId,
            @RequestParam(required = false) String text1,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        var query = new SampleSearchQuery(displayId, text1, page, size);
        var results = sampleApplicationService.search(query);
        var pagedResponse = PagedResponse.from(results);
        
        return ResponseEntity.ok(ApiResponse.success(pagedResponse));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SampleResponse>> getById(@PathVariable Long id) {
        var sampleDetail = sampleApplicationService.findById(id);
        var response = SampleResponse.from(sampleDetail);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<SampleResponse>> create(@Valid @RequestBody SampleCreateRequest request) {
        var command = new SampleCreateCommand(request.getText1(), request.getNum1());
        var createdSample = sampleApplicationService.createSample(command);
        var response = SampleResponse.from(createdSample);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("サンプルが作成されました", response));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SampleResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody SampleUpdateRequest request) {
        
        var command = new SampleUpdateCommand(request.getText1(), request.getNum1());
        var updatedSample = sampleApplicationService.updateSample(id, command);
        var response = SampleResponse.from(updatedSample);
        
        return ResponseEntity.ok(ApiResponse.success("サンプルが更新されました", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        sampleApplicationService.deleteSample(id);
        
        return ResponseEntity.ok(ApiResponse.successMessage("サンプルが削除されました"));
    }
    
    @PostMapping("/bulk-delete")
    public ResponseEntity<ApiResponse<Void>> bulkDelete(@Valid @RequestBody BulkDeleteRequest request) {
        sampleApplicationService.bulkDeleteSamples(request.getIds());
        
        return ResponseEntity.ok(ApiResponse.successMessage(request.getIds().size() + "件のサンプルが削除されました"));
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<SampleStatisticsResponse>> getStatistics(
            @RequestParam(required = false) String displayId,
            @RequestParam(required = false) String text1) {
        
        var query = new SampleSearchQuery(displayId, text1, 1, Integer.MAX_VALUE);
        var statistics = sampleApplicationService.getStatistics(query);
        var response = SampleStatisticsResponse.from(statistics);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}