package com.rainkaze.traffic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.rainkaze.traffic.R;
import com.rainkaze.traffic.adapter.DeviceAdapter;
import com.rainkaze.traffic.api.ApiClient;
import com.rainkaze.traffic.api.ApiService;
import com.rainkaze.traffic.model.Device;
import com.rainkaze.traffic.model.PageResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private ProgressBar progressBar;
    private TextView emptyView;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_management, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        progressBar = view.findViewById(R.id.progressBar);
        emptyView = view.findViewById(R.id.textViewEmpty);
        emptyView.setText("没有设备数据");

        apiService = ApiClient.getClient(getContext()).create(ApiService.class);

        setupRecyclerView();
        fetchDevices();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceAdapter = new DeviceAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(deviceAdapter);
    }

    private void fetchDevices() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        apiService.getDevices(0, 20).enqueue(new Callback<PageResult<Device>>() {
            @Override
            public void onResponse(@NonNull Call<PageResult<Device>> call, @NonNull Response<PageResult<Device>> response) {
                // *** 安全检查 ***
                if (!isAdded() || getContext() == null) {
                    return;
                }

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getContent() != null) {
                    if (response.body().getContent().isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        deviceAdapter.setDevices(response.body().getContent());
                    }
                } else {
                    Toast.makeText(getContext(), "加载设备列表失败", Toast.LENGTH_SHORT).show();
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PageResult<Device>> call, @NonNull Throwable t) {
                // *** 安全检查 ***
                if (!isAdded() || getContext() == null) {
                    return;
                }

                progressBar.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}