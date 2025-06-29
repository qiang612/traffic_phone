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
import com.rainkaze.traffic.adapter.ViolationAdapter;
import com.rainkaze.traffic.api.ApiClient;
import com.rainkaze.traffic.api.ApiService;
import com.rainkaze.traffic.model.PageResult;
import com.rainkaze.traffic.model.Violation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViolationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViolationAdapter violationAdapter;
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
        emptyView.setText("没有违法记录");

        // 在 onCreateView 或 onAttach 中获取 context 是安全的
        apiService = ApiClient.getClient(getContext()).create(ApiService.class);

        setupRecyclerView();
        fetchViolations();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        violationAdapter = new ViolationAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(violationAdapter);
    }

    private void fetchViolations() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        apiService.getViolations(0, 20).enqueue(new Callback<PageResult<Violation>>() {
            @Override
            public void onResponse(@NonNull Call<PageResult<Violation>> call, @NonNull Response<PageResult<Violation>> response) {
                // =======================
                //  *** 这里是修改点 ***
                //  在处理回调前，检查Fragment是否还存活
                // =======================
                if (!isAdded() || getContext() == null) {
                    return; // Fragment已经不存在了，直接返回，不做任何UI操作
                }

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getContent() != null) {
                    if (response.body().getContent().isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        violationAdapter.setViolations(response.body().getContent());
                    }
                } else {
                    Toast.makeText(getContext(), "加载违法记录失败", Toast.LENGTH_SHORT).show();
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PageResult<Violation>> call, @NonNull Throwable t) {
                // =======================
                //  *** 这里是修改点 ***
                //  在处理回调前，检查Fragment是否还存活
                // =======================
                if (!isAdded() || getContext() == null) {
                    return; // Fragment已经不存在了，直接返回，不做任何UI操作
                }

                progressBar.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                // 现在调用 getContext() 是安全的
                Toast.makeText(getContext(), "网络错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}