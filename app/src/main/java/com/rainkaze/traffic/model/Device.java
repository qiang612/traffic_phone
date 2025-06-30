package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("deviceId")
    private Long id;

    @SerializedName("deviceName")
    private String deviceName;
    @SerializedName("deviceType")
    private String deviceType;

    @SerializedName("location") // 假设后端返回 "location"
    private String location;

    @SerializedName("status")
    private String status;

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}