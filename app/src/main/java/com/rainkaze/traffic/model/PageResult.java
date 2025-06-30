package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PageResult<T> {

    // 将JSON中的 "content" 字段映射到 "content" 字段
    @SerializedName("item")
    private List<T> content;

    // 将JSON中的 "totalPages" 字段映射到 "totalPages" 字段
    @SerializedName("totalPages")
    private int totalPages;

    // 将JSON中的 "totalElements" 字段映射到 "totalElements" 字段
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