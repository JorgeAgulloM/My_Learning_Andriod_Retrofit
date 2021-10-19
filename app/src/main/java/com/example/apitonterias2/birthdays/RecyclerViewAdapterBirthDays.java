package com.example.apitonterias2.birthdays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apitonterias2.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapterBirthDays  extends RecyclerView.Adapter<RecyclerViewAdapterBirthDays.MyViewHolder> {

    private LayoutInflater inflater;
    DataResponseApiBirthDays data;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageBirthDayList;
        MaterialAutoCompleteTextView txtNameBirthDayList, txtDateBirthDayList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageBirthDayList = itemView.findViewById(R.id.imageBirthDayList);
            txtNameBirthDayList = itemView.findViewById(R.id.txtNameBirthDayList);
            txtDateBirthDayList = itemView.findViewById(R.id.txtDateBirthDayList);

        }
    }

    public RecyclerViewAdapterBirthDays(Context context, @NonNull DataResponseApiBirthDays data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterBirthDays.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.birthdays_activity_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterBirthDays.MyViewHolder holder, int position) {
        String imageTxt = data.getBirthDayItems().get(position).image;
        Picasso.get().load(imageTxt).into(holder.imageBirthDayList);
        holder.txtNameBirthDayList.setText(data.birthDayItems.get(position).name);
        holder.txtDateBirthDayList.setText(data.birthDayItems.get(position).date);
    }

    @Override
    public int getItemCount() {
        return data.birthDayItems.size();
    }

}
