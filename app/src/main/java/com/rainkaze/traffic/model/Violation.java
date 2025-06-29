package com.rainkaze.traffic.model;

import java.util.Date;

public class Violation {
    private Long id;
    private String plateNumber;
    private String violationType;
    private String location;
    private Date violationTime;
    private String status; // e.g., "未处理", "已处理"
    private String imageUrl;

    // Getters and Setters
    public Long getId() { return id; }
    public String getPlateNumber() { return plateNumber; }
    public String getViolationType() { return violationType; }
    public String getLocation() { return location; }
    public Date getViolationTime() { return violationTime; }
    public String getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
}