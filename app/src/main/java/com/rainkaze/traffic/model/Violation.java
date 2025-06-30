package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class Violation {

    @SerializedName("id")
    private Long id;

    @SerializedName("plateNumber") // 假设后端返回 "plateNumber"
    private String plateNumber;

    @SerializedName("violationType") // 假设后端返回 "violationType"
    private String violationType;

    @SerializedName("location")
    private String location;

    @SerializedName("violationTime") // 假设后端返回 "violationTime"
    private Date violationTime;

    @SerializedName("status")
    private String status;

    @SerializedName("evidenceImageUrls")
    private List<String> evidenceImageUrls;


    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getViolationType() { return violationType; }
    public void setViolationType(String violationType) { this.violationType = violationType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getViolationTime() { return violationTime; }
    public void setViolationTime(Date violationTime) { this.violationTime = violationTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getEvidenceImageUrls() {
        return evidenceImageUrls;
    }

    public String getImageUrl() {
        if (evidenceImageUrls != null && !evidenceImageUrls.isEmpty()) {
            return evidenceImageUrls.get(0);
        }
        return null;
    }

    public void setEvidenceImageUrls(List<String> evidenceImageUrls) {
        this.evidenceImageUrls = evidenceImageUrls;
    }
}