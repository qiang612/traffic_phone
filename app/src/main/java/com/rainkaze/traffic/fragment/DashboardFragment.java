package com.rainkaze.traffic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.rainkaze.traffic.R;
import com.rainkaze.traffic.api.ApiClient;
import com.rainkaze.traffic.api.ApiService;
import com.rainkaze.traffic.model.DashboardDataDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private TextView textTotalViolations;
    // private TextView textPendingViolations; // 移除未使用的变量声明
    // private TextView textProcessedViolations; // 移除未使用的变量声明
    private BarChart barChart;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textTotalViolations = view.findViewById(R.id.textTotalViolations);
        barChart = view.findViewById(R.id.barChart);
        apiService = ApiClient.getClient(getContext()).create(ApiService.class);

        setupChart();
        fetchDashboardData();

        return view;
    }

    private void setupChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
    }

    private void fetchDashboardData() {
        apiService.getDashboardData("month").enqueue(new Callback<DashboardDataDto>() {
            @Override
            public void onResponse(@NonNull Call<DashboardDataDto> call, @NonNull Response<DashboardDataDto> response) {
                // *** 安全检查 ***
                if (!isAdded() || getContext() == null) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    DashboardDataDto data = response.body();
                    DashboardDataDto.Stats stats = data.getStats();
                    if (stats != null) {
                        textTotalViolations.setText("今日违法总数: " + stats.getTotalToday());
                    }

                    if (data.getViolationTypeDistribution() != null) {
                        updateBarChart(data.getViolationTypeDistribution());
                    }

                } else {
                    Toast.makeText(getContext(), "加载仪表盘数据失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardDataDto> call, @NonNull Throwable t) {
                // *** 安全检查 ***
                if (!isAdded() || getContext() == null) {
                    return;
                }
                textTotalViolations.setText("加载数据失败"); // 简化错误信息
                Toast.makeText(getContext(), "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBarChart(DashboardDataDto.ViolationTypeDistribution distribution) {
        if (distribution == null) return;

        ArrayList<BarEntry> entries = new ArrayList<>();
        // 直接使用从JSON获取的data列表
        List<Integer> data = distribution.getData();
        for (int i = 0; i < data.size(); i++) {
            entries.add(new BarEntry(i, data.get(i)));
        }

        // 直接使用从JSON获取的labels列表
        ArrayList<String> labels = new ArrayList<>(distribution.getLabels());

        if (entries.isEmpty()) {
            barChart.clear();
            barChart.invalidate();
            return;
        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setLabelCount(labels.size());

        BarDataSet dataSet = new BarDataSet(entries, "违法类型分布");
        dataSet.setColor(Color.rgb(63, 81, 181));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}