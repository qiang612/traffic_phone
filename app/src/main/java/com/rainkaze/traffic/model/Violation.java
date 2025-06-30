package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

public class Violation {

    @SerializedName("id")
    private Long id;

    @SerializedName("plate")
    private String plateNumber;

    @SerializedName("type")
    private String violationType;

    @SerializedName("location")
    private String location;

    @SerializedName("time")
    private Date violationTime;

    @SerializedName("status")
    private String status;

    // 后端返回的可能是图片URL列表，所以我们用List<String>
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

    // 返回图片列表中的第一个URL，或者在没有图片时返回null
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