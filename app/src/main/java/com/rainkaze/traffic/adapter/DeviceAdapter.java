package com.rainkaze.traffic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.rainkaze.traffic.R;
import com.rainkaze.traffic.model.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> deviceList;
    private Context context;

    public DeviceAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.deviceName.setText(device.getDeviceName());
        holder.deviceLocation.setText("位置: " + device.getLocation());
        holder.deviceStatus.setText(device.getStatus());

        // 根据设备类型设置图标 (可选的优化)
        if ("Camera".equalsIgnoreCase(device.getDeviceType())) {
            holder.deviceIcon.setImageResource(R.drawable.ic_camera); // 准备一个相机图标
        } else {
            holder.deviceIcon.setImageResource(R.drawable.ic_devices); // 默认设备图标
        }

        // 根据设备状态设置背景和文字颜色
        if ("Online".equalsIgnoreCase(device.getStatus()) || "在线".equals(device.getStatus())) {
            holder.deviceStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.status_background_online));
        } else {
            holder.deviceStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.status_background_offline));
        }
    }

    @Override
    public int getItemCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    public void setDevices(List<Device> devices) {
        this.deviceList = devices;
        notifyDataSetChanged();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView deviceIcon;
        TextView deviceName, deviceLocation, deviceStatus;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceIcon = itemView.findViewById(R.id.imageViewDeviceIcon);
            deviceName = itemView.findViewById(R.id.textViewDeviceName);
            deviceLocation = itemView.findViewById(R.id.textViewDeviceLocation);
            deviceStatus = itemView.findViewById(R.id.textViewDeviceStatus);
        }
    }
}