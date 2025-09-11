package com.ngnmsn.template.application.query;

/**
 * Query for searching samples
 */
public class SampleSearchQuery {
    private final String displayId;
    private final String text1;
    private final Integer page;
    private final Integer maxNumPerPage;
    
    public SampleSearchQuery(String displayId, String text1, Integer page, Integer maxNumPerPage) {
        this.displayId = displayId;
        this.text1 = text1;
        this.page = page != null ? page : 1;
        this.maxNumPerPage = maxNumPerPage != null ? maxNumPerPage : 20;
    }
    
    public String getDisplayId() { 
        return displayId; 
    }
    
    public String getText1() { 
        return text1; 
    }
    
    public Integer getPage() { 
        return page; 
    }
    
    public Integer getMaxNumPerPage() { 
        return maxNumPerPage; 
    }
}