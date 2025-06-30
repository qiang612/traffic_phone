package com.rainkaze.traffic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat; // 引入ContextCompat
import androidx.recyclerview.widget.RecyclerView;
import com.rainkaze.traffic.R;
import com.rainkaze.traffic.model.DashboardDataDto;
import java.util.List;

public class WarningAdapter extends RecyclerView.Adapter<WarningAdapter.WarningViewHolder> {

    private Context context;
    private List<DashboardDataDto.RealtimeWarning> warningList;

    public WarningAdapter(Context context, List<DashboardDataDto.RealtimeWarning> warningList) {
        this.context = context;
        this.warningList = warningList;
    }

    @NonNull
    @Override
    public WarningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_warning, parent, false);
        return new WarningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WarningViewHolder holder, int position) {
        DashboardDataDto.RealtimeWarning warning = warningList.get(position);
        holder.plateNumber.setText(warning.getPlateNumber());
        holder.violationType.setText(warning.getViolationType());
        holder.location.setText(warning.getLocation());
        // *** 核心修改点 3 ***
        // holder.timeAgo.setText(warning.getTimeAgo()); // 此行已被删除

        // 根据告警级别设置文本和颜色
        if (warning.getWarningLevel() == 1) {
            holder.warningLevel.setText("高危");
            // 使用我们预定义的颜色，而非硬编码
            holder.warningLevel.setTextColor(ContextCompat.getColor(context, R.color.status_danger));
        } else {
            holder.warningLevel.setText("普通");
            // 使用我们预定义的颜色
            holder.warningLevel.setTextColor(ContextCompat.getColor(context, R.color.status_warning));
        }
    }

    @Override
    public int getItemCount() {
        return warningList == null ? 0 : warningList.size();
    }

    public void setWarnings(List<DashboardDataDto.RealtimeWarning> warnings) {
        this.warningList = warnings;
        notifyDataSetChanged();
    }

    static class WarningViewHolder extends RecyclerView.ViewHolder {
        // *** 核心修改点 1 ***
        // 移除了 timeAgo 的声明
        TextView plateNumber, violationType, location, warningLevel;

        public WarningViewHolder(@NonNull View itemView) {
            super(itemView);
            plateNumber = itemView.findViewById(R.id.textViewPlateNumber);
            violationType = itemView.findViewById(R.id.textViewViolationType);
            location = itemView.findViewById(R.id.textViewLocation);
            // *** 核心修改点 2 ***
            // 移除了 findViewById 对 R.id.textViewTimeAgo 的调用
            warningLevel = itemView.findViewById(R.id.textViewWarningLevel);
        }
    }
}