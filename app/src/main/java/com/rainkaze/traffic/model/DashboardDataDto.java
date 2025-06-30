package com.rainkaze.traffic.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class DashboardDataDto {

    // 确保这里的字段名 (stats, violationTrends, etc.) 与后端JSON的key完全一致
    // 使用 @SerializedName 可以确保即使变量名不同也能正确解析
    @SerializedName("stats")
    private Stats stats;

    @SerializedName("violationTrends")
    private List<ViolationTrend> violationTrends;

    @SerializedName("violationTypeDistribution")
    private ViolationTypeDistribution violationTypeDistribution;

    @SerializedName("highFrequencyLocations")
    private List<LocationCount> highFrequencyLocations;

    // --- Getters ---
    public Stats getStats() {
        return stats;
    }

    public List<ViolationTrend> getViolationTrends() {
        return violationTrends;
    }

    public ViolationTypeDistribution getViolationTypeDistribution() {
        return violationTypeDistribution;
    }
    public static class ViolationTypeDistribution {
        private List<String> labels;
        private List<Integer> data;

        public List<String> getLabels() { return labels; }
        public List<Integer> getData() { return data; }
    }
    public List<LocationCount> getHighFrequencyLocations() {
        return highFrequencyLocations;
    }

    // --- Inner Classes for nested JSON objects ---

    public static class Stats {
        private long totalToday;
        private double totalChange;
        private long pendingToday;
        private double pendingChange;
        private long processedToday;
        private double processedChange;

        // Getters for Stats
        public long getTotalToday() { return totalToday; }
        public double getTotalChange() { return totalChange; }
        public long getPendingToday() { return pendingToday; }
        public double getPendingChange() { return pendingChange; }
        public long getProcessedToday() { return processedToday; }
        public double getProcessedChange() { return processedChange; }
    }

    public static class ViolationTrend {
        private String date;
        private int count;

        // Getters for ViolationTrend
        public String getDate() { return date; }
        public int getCount() { return count; }
    }

    public static class LocationCount {
        private String location;
        private int count;

        // Getters for LocationCount
        public String getLocation() { return location; }
        public int getCount() { return count; }
    }
}
