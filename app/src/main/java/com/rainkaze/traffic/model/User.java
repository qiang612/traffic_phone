package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class User {

    @SerializedName("userId")
    private Integer userId;
    @SerializedName("username")
    private String username;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("policeId")
    private String policeId;

    @SerializedName("rank") // 后端JSON中是 "rank"，映射到App的 "role" 字段
    private String role;

    @SerializedName("createdAt")
    private Date createdAt;

    // --- Getters and Setters ---

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPoliceId() { return policeId; }
    public void setPoliceId(String policeId) { this.policeId = policeId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}