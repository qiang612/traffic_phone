package com.rainkaze.traffic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.rainkaze.traffic.R;
import com.rainkaze.traffic.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.fullName.setText(user.getFullName());
        holder.role.setText(user.getRole());
        holder.policeId.setText("警号: " + user.getUserId());
        holder.email.setText("邮箱: " + user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public void setUsers(List<User> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, role, policeId, email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.textViewFullName);
            role = itemView.findViewById(R.id.textViewRole);
            policeId = itemView.findViewById(R.id.textViewPoliceId);
            email = itemView.findViewById(R.id.textViewEmail);
        }
    }
}