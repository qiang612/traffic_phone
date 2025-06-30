package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PageResult<T> {

    // [FIXED] 将JSON中的 "items" 字段映射到 "content" 字段
    @SerializedName("items")
    private List<T> content;

    @SerializedName("totalPages")
    private int totalPages;

    // [FIXED] 将JSON中的 "totalItems" 字段映射到 "totalElements" 字段
    @SerializedName("totalItems")
    private long totalElements;

    // Getters
    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }
}