package com.rainkaze.traffic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.rainkaze.traffic.R;
// [MODIFIED] 导入新的Adapter
import com.rainkaze.traffic.adapter.WarningAdapter;
import com.rainkaze.traffic.adapter.ViolationAdapter; // 复用
import com.rainkaze.traffic.api.ApiClient;
import com.rainkaze.traffic.api.ApiService;
import com.rainkaze.traffic.model.DashboardDataDto;
import com.rainkaze.traffic.model.Violation; // 导入 Violation 模型

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardDebug";

    private TextView textTotalViolations, textPendingViolations;
    private BarChart barChart;
    // [MODIFIED] 声明新的 RecyclerView 和 Adapter
    private RecyclerView recyclerViewWarnings, recyclerViewRecentViolations;
    private WarningAdapter warningAdapter;
    private ViolationAdapter recentViolationAdapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textTotalViolations = view.findViewById(R.id.textTotalViolations);
        textPendingViolations = view.findViewById(R.id.textPendingViolations);
        barChart = view.findViewById(R.id.barChart);
        // [MODIFIED] 初始化新的RecyclerView
        recyclerViewWarnings = view.findViewById(R.id.recyclerViewWarnings);
        recyclerViewRecentViolations = view.findViewById(R.id.recyclerViewRecentViolations);

        apiService = ApiClient.getClient(getContext()).create(ApiService.class);

        setupChart();
        setupRecyclerViews();
        fetchDashboardData();

        return view;
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
    }

    private void setupRecyclerViews() {
        // 设置实时预警列表
        recyclerViewWarnings.setLayoutManager(new LinearLayoutManager(getContext()));
        warningAdapter = new WarningAdapter(getContext(), new ArrayList<>());
        recyclerViewWarnings.setAdapter(warningAdapter);

        // 设置最近违法记录列表
        recyclerViewRecentViolations.setLayoutManager(new LinearLayoutManager(getContext()));
        recentViolationAdapter = new ViolationAdapter(getContext(), new ArrayList<>()); // 复用 ViolationAdapter
        recyclerViewRecentViolations.setAdapter(recentViolationAdapter);
    }

    private void fetchDashboardData() {
        Log.d(TAG, "开始获取仪表盘数据...");
        apiService.getDashboardData("month").enqueue(new Callback<DashboardDataDto>() {
            @Override
            public void onResponse(@NonNull Call<DashboardDataDto> call, @NonNull Response<DashboardDataDto> response) {
                if (!isAdded() || getContext() == null) return;

                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API 响应成功，数据体存在。");
                    DashboardDataDto data = response.body();

                    // 更新统计数据
                    if (data.getStats() != null) {
                        textTotalViolations.setText("今日违法总数: " + data.getStats().getTotalToday());
                        textPendingViolations.setText("待处理: " + data.getStats().getPendingToday());
                    }

                    // 更新柱状图
                    updateBarChart(data.getViolationTypeDistribution());

                    // [MODIFIED] 更新新的列表
                    updateWarningsList(data.getRealtimeWarnings());
                    updateRecentViolationsList(data.getRecentViolations());

                } else {
                    Log.e(TAG, "加载仪表盘数据失败，响应码: " + response.code());
                    Toast.makeText(getContext(), "加载仪表盘数据失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardDataDto> call, @NonNull Throwable t) {
                if (!isAdded() || getContext() == null) return;
                Log.e(TAG, "网络错误", t);
                Toast.makeText(getContext(), "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBarChart(DashboardDataDto.ViolationTypeDistribution distribution) {
        if (distribution == null || distribution.getData() == null || distribution.getLabels() == null) {
            Log.w(TAG, "updateBarChart: 柱状图数据不完整或为null。");
            barChart.clear();
            barChart.invalidate();
            return;
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        List<Integer> data = distribution.getData();
        for (int i = 0; i < data.size(); i++) {
            entries.add(new BarEntry(i, data.get(i)));
        }

        if (entries.isEmpty()) {
            Log.d(TAG, "updateBarChart: 条目为空, 清空图表。");
            barChart.clear();
            barChart.invalidate();
            return;
        }

        ArrayList<String> labels = new ArrayList<>(distribution.getLabels());
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(labels.size());

        BarDataSet dataSet = new BarDataSet(entries, "违法类型分布");
        dataSet.setColor(Color.rgb(63, 81, 181));
        dataSet.setValueTextSize(12f); // 调整数值文本大小，使其更清晰

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate();
        Log.d(TAG, "updateBarChart: 柱状图已更新，包含 " + entries.size() + " 个条目。");
    }

    // [NEW] 更新实时预警列表的方法
    private void updateWarningsList(List<DashboardDataDto.RealtimeWarning> warnings) {
        if (warnings != null && !warnings.isEmpty()) {
            Log.d(TAG, "更新实时预警列表，数量: " + warnings.size());
            warningAdapter.setWarnings(warnings);
        } else {
            Log.w(TAG, "实时预警数据为null或空。");
        }
    }

    // [NEW] 更新最近违法记录列表的方法
    private void updateRecentViolationsList(List<DashboardDataDto.RecentViolation> recentViolations) {
        if (recentViolations != null && !recentViolations.isEmpty()) {
            Log.d(TAG, "更新最近违法记录列表，数量: " + recentViolations.size());
            // --- 数据转换: 从 RecentViolation 转换为 Violation ---
            List<Violation> violationList = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            for (DashboardDataDto.RecentViolation recent : recentViolations) {
                Violation v = new Violation();
                v.setPlateNumber(recent.getPlateNumber());
                v.setViolationType(recent.getViolationType());
                v.setLocation(recent.getLocation());
                v.setStatus(recent.getStatus());
                // ImageUrl 是空的，但Adapter有占位图，所以没问题
                try {
                    v.setViolationTime(sdf.parse(recent.getTime()));
                } catch (ParseException e) {
                    Log.e(TAG, "日期解析失败", e);
                }
                violationList.add(v);
            }
            recentViolationAdapter.setViolations(violationList);
        } else {
            Log.w(TAG, "最近违法记录数据为null或空。");
        }
    }
}