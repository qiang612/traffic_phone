package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class Violation {

    @SerializedName("id")
    private Long id;

    // [FIXED] 从 "plateNumber" 改为 "plate"
    @SerializedName("plate")
    private String plateNumber;

    // [FIXED] 从 "violationType" 改为 "type"
    @SerializedName("type")
    private String violationType;

    @SerializedName("location")
    private String location;

    // [FIXED] 从 "violationTime" 改为 "time"
    @SerializedName("time")
    private Date violationTime;

    @SerializedName("status")
    private String status;

    // 注意：根据日志，服务器并未返回这个字段，所以它会是null
    @SerializedName("evidenceImageUrls")
    private List<String> evidenceImageUrls;

    @SerializedName("device")
    private String device;

    @SerializedName("district")
    private String district;

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

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

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