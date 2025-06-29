package com.rainkaze.traffic.model;

import java.util.Date;

public class User {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String policeId;
    private String role;
    private Date createdAt;

    // Getters and Setters for all fields
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