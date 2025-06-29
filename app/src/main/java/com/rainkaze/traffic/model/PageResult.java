package com.rainkaze.traffic.model;

import java.util.List;

public class PageResult<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    // Getters
    public List<T> getContent() { return content; }
    public int getTotalPages() { return totalPages; }
}