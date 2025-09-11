package com.ngnmsn.template.presentation.response;

import com.ngnmsn.template.domain.model.SampleSearchResults;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagedResponse<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final boolean first;
    private final boolean last;
    
    public PagedResponse(List<T> content, int page, int size, long totalElements) {
        this.content = content != null ? new ArrayList<>(content) : new ArrayList<>();
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        this.first = page == 1;
        this.last = page >= totalPages;
    }
    
    public static PagedResponse<SampleResponse> from(SampleSearchResults results) {
        List<SampleResponse> sampleResponses = results.getResults().stream()
            .map(SampleResponse::from)
            .collect(Collectors.toList());
            
        return new PagedResponse<>(
            sampleResponses,
            results.getPage(),
            results.getMaxNumPerPage(),
            results.getTotalCount()
        );
    }
    
    public List<T> getContent() { return new ArrayList<>(content); }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public int getTotalPages() { return totalPages; }
    public long getTotalElements() { return totalElements; }
    public boolean isFirst() { return first; }
    public boolean isLast() { return last; }
    public boolean hasNext() { return !last; }
    public boolean hasPrevious() { return !first; }
}