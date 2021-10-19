package com.example.apitonterias2.bands;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apitonterias2.R;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapterBands extends RecyclerView.Adapter<RecyclerViewAdapterBands.MyViewHoler> {
    private LayoutInflater inflater;
    DataResponseApiBands data;

    public class MyViewHoler extends RecyclerView.ViewHolder {

        MaterialTextView nameTV, descriptionTV;
        ImageView imageBand;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.itemBand_TV_name);
            descriptionTV = itemView.findViewById(R.id.itemBand_TV_descrip);
            imageBand = itemView.findViewById(R.id.itemBand_Image);
        }
    }


    public RecyclerViewAdapterBands(Context context, @NonNull DataResponseApiBands data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterBands.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bands_activity_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.nameTV.setText(data.getGrupos().get(position).getNombre()); //holder.nameTV.setText(data.get(position).nombre);
        holder.descriptionTV.setText(data.getGrupos().get(position).getDescripcion());
        String url = data.getGrupos().get(position).getImagen();
        Picasso.get().load(url).into(holder.imageBand);
    }

    @Override
    public int getItemCount() {
        return data.getGrupos().size();
    }

}
