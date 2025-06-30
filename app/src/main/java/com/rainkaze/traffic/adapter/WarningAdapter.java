package com.rainkaze.traffic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
        holder.timeAgo.setText(warning.getTimeAgo());

        // 根据告警级别设置文本和颜色
        if (warning.getWarningLevel() == 1) {
            holder.warningLevel.setText("高危");
            holder.warningLevel.setTextColor(Color.parseColor("#F44336")); // Red
        } else {
            holder.warningLevel.setText("普通");
            holder.warningLevel.setTextColor(Color.parseColor("#FF9800")); // Orange
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
        TextView plateNumber, violationType, location, timeAgo, warningLevel;

        public WarningViewHolder(@NonNull View itemView) {
            super(itemView);
            plateNumber = itemView.findViewById(R.id.textViewPlateNumber);
            violationType = itemView.findViewById(R.id.textViewViolationType);
            location = itemView.findViewById(R.id.textViewLocation);
            timeAgo = itemView.findViewById(R.id.textViewTimeAgo);
            warningLevel = itemView.findViewById(R.id.textViewWarningLevel);
        }
    }
}