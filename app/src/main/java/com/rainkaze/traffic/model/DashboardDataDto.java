package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DashboardDataDto {

    @SerializedName("stats")
    private Stats stats;

    @SerializedName("violationTypeDistribution")
    private ViolationTypeDistribution violationTypeDistribution;

    // [MODIFIED] 修改字段以匹配后端
    @SerializedName("realtimeWarnings")
    private List<RealtimeWarning> realtimeWarnings;

    @SerializedName("recentViolations")
    private List<RecentViolation> recentViolations;


    // --- Getters ---
    public Stats getStats() { return stats; }
    public ViolationTypeDistribution getViolationTypeDistribution() { return violationTypeDistribution; }
    public List<RealtimeWarning> getRealtimeWarnings() { return realtimeWarnings; }
    public List<RecentViolation> getRecentViolations() { return recentViolations; }

    // --- Inner Classes for nested JSON objects ---

    public static class Stats {
        private long totalToday;
        private double totalChange;
        private long pendingToday;
        private double pendingChange;
        private long processedToday;
        private double processedChange;

        public long getTotalToday() { return totalToday; }
        public double getTotalChange() { return totalChange; }
        public long getPendingToday() { return pendingToday; }
        public double getPendingChange() { return pendingChange; }
        public long getProcessedToday() { return processedToday; }
        public double getProcessedChange() { return processedChange; }
    }

    public static class ViolationTypeDistribution {
        private List<String> labels;
        private List<Integer> data;
        public List<String> getLabels() { return labels; }
        public List<Integer> getData() { return data; }
    }

    // [NEW] 新增内部类以匹配 "realtimeWarnings"
    public static class RealtimeWarning {
        private String plateNumber;
        private String violationType;
        private String location;
        private String timeAgo;
        private int warningLevel;

        public String getPlateNumber() { return plateNumber; }
        public String getViolationType() { return violationType; }
        public String getLocation() { return location; }
        public String getTimeAgo() { return timeAgo; }
        public int getWarningLevel() { return warningLevel; }
    }

    // [NEW] 新增内部类以匹配 "recentViolations"
    public static class RecentViolation {
        private String time;
        private String plateNumber;
        private String violationType;
        private String location;
        private String status;

        public String getTime() { return time; }
        public String getPlateNumber() { return plateNumber; }
        public String getViolationType() { return violationType; }
        public String getLocation() { return location; }
        public String getStatus() { return status; }
    }
}