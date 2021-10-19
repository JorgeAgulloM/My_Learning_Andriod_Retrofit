package com.example.apitonterias2.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apitonterias2.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecyclerViewAdapterUserList extends RecyclerView.Adapter<RecyclerViewAdapterUserList.MyViewHoler> {
    private LayoutInflater inflater;
    DataResponseApiUsers data;

    public class MyViewHoler extends RecyclerView.ViewHolder {

        MaterialTextView nameUser, passUser;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.txtName);
            passUser = itemView.findViewById(R.id.txtPass);
        }
    }

    public RecyclerViewAdapterUserList(Context context, @NonNull DataResponseApiUsers data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterUserList.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_activity_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.nameUser.setText(data.user.get(position));
        holder.passUser.setText("*******");
    }

    @Override
    public int getItemCount() {
        return data.user.size();
    }

}
