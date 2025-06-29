package com.rainkaze.traffic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.rainkaze.traffic.R;
import com.rainkaze.traffic.model.Violation;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder> {

    private List<Violation> violationList;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public ViolationAdapter(Context context, List<Violation> violationList) {
        this.context = context;
        this.violationList = violationList;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_violation, parent, false);
        return new ViolationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        Violation violation = violationList.get(position);
        holder.plateNumber.setText(violation.getPlateNumber());
        holder.violationType.setText("类型: " + violation.getViolationType());
        holder.location.setText("地点: " + violation.getLocation());
        holder.status.setText(violation.getStatus());

        if ("已处理".equals(violation.getStatus())) {
            holder.status.setTextColor(Color.parseColor("#4CAF50")); // Green
        } else {
            holder.status.setTextColor(Color.parseColor("#F44336")); // Red
        }

        Glide.with(context)
                .load(violation.getImageUrl())
                .placeholder(R.drawable.ic_image_placeholder) // Create a placeholder drawable
                .error(R.drawable.ic_image_placeholder)
                .into(holder.violationImage);
    }

    @Override
    public int getItemCount() {
        return violationList.size();
    }

    public void setViolations(List<Violation> violations) {
        this.violationList = violations;
        notifyDataSetChanged();
    }

    static class ViolationViewHolder extends RecyclerView.ViewHolder {
        ImageView violationImage;
        TextView plateNumber, violationType, location, status;

        public ViolationViewHolder(@NonNull View itemView) {
            super(itemView);
            violationImage = itemView.findViewById(R.id.imageViewViolation);
            plateNumber = itemView.findViewById(R.id.textViewPlateNumber);
            violationType = itemView.findViewById(R.id.textViewViolationType);
            location = itemView.findViewById(R.id.textViewLocation);
            status = itemView.findViewById(R.id.textViewStatus);
        }
    }
}