package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("deviceId")
    private Long id;

    private String deviceName;
    private String deviceType;

    @SerializedName("address")
    private String location;

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