package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PageResult<T> {

    // 使用 @SerializedName 将JSON中的 "items" 字段映射到这个 "content" 字段
    @SerializedName("items")
    private List<T> content;

    // 使用 @SerializedName 将JSON中的 "totalPages" 字段映射到这个 "totalPages" 字段
    @SerializedName("totalPages")
    private int totalPages;

    // 使用 @SerializedName 将JSON中的 "totalItems" 字段映射到这个 "totalElements" 字段
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