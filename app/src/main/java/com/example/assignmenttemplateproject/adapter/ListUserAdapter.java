package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.UserDAO;
import com.example.assignmenttemplateproject.fragment.Login;
import com.example.assignmenttemplateproject.model.User;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserViewHolder> {

    private List<User> users;
    private LayoutInflater inflater;
    private UserDAO userDAO;

    public ListUserAdapter(Context context, UserDAO userDAO) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.userDAO = userDAO;
        this.users = this.userDAO.getAllUser();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_list_user, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        final User user = users.get(position);

        if (position % 3 == 0)
            holder.ivAvatarUser.setImageResource(R.drawable.emthree);
        else if (position % 3 == 1)
            holder.ivAvatarUser.setImageResource(R.drawable.emtwo);
        else
            holder.ivAvatarUser.setImageResource(R.drawable.emone);

        holder.tvFullName.setText(user.getFullName());
        holder.tvPhone.setText(user.getPhone());
        holder.ivDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Login.getUSER().equals(user.getUserName())) {
                    if (userDAO.deleteUser(user)) {
                        Toast.makeText(holder.itemView.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        users.remove(user);
                        notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Người dùng hiện đang đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username_key", user.getUserName());
                bundle.putString("password_key", user.getUserPass());
                bundle.putString("phone_key", user.getPhone());
                bundle.putString("fullName_key", user.getFullName());

                Navigation.findNavController(v).navigate(R.id.action_listUser2_to_updateUserInfo2, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivAvatarUser, ivDeleteUser;
        public TextView tvFullName, tvPhone;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatarUser = itemView.findViewById(R.id.ivAvatarUser);
            ivDeleteUser = itemView.findViewById(R.id.ivDeleteUser);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
