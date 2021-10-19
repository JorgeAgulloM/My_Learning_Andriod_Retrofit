package com.example.apitonterias2.tonterias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apitonterias2.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecyclerViewAdapterTonterias extends RecyclerView.Adapter<RecyclerViewAdapterTonterias.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<TonteriasItem> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView txtName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }

    public RecyclerViewAdapterTonterias(Context context, @NonNull ArrayList<TonteriasItem> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterTonterias.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tonterias_activity_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(data.get(position).quote);
    }

    @Override
    public int getItemCount() {
        try {
            return data.size(); //Pendiente####################################################
        }catch (Exception e){
            return 1;
        }
    }

}
