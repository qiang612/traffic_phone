package com.rainkaze.traffic.model;

public class Device {
    private Long id;
    private String deviceName;
    private String deviceType; // e.g., "Camera", "Sensor"
    private String location;
    private String status; // e.g., "Online", "Offline"

    // Getters and Setters
    public Long getId() { return id; }
    public String getDeviceName() { return deviceName; }
    public String getDeviceType() { return deviceType; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
}
