package com.nearme.util;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PageResponse<T> of(Page<T> pageData) {
        PageResponse<T> response = new PageResponse<>();
        response.content = pageData.getContent();
        response.page = pageData.getNumber();
        response.size = pageData.getSize();
        response.totalElements = pageData.getTotalElements();
        response.totalPages = pageData.getTotalPages();
        return response;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}
