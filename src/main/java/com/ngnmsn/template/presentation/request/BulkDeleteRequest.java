package com.ngnmsn.template.presentation.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class BulkDeleteRequest {
    
    @NotEmpty(message = "削除対象のIDを指定してください")
    private List<Long> ids;
    
    public BulkDeleteRequest() {
        this.ids = new ArrayList<>();
    }
    
    public BulkDeleteRequest(List<Long> ids) {
        this.ids = ids != null ? new ArrayList<>(ids) : new ArrayList<>();
    }
    
    public List<Long> getIds() { return new ArrayList<>(ids); }
    public void setIds(List<Long> ids) { 
        this.ids = ids != null ? new ArrayList<>(ids) : new ArrayList<>(); 
    }
}