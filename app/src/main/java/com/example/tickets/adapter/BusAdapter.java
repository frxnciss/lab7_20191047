package com.example.tickets.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tickets.ProfileActivity;
import com.example.tickets.R;
import com.example.tickets.dto.Bus;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {
    private Context context;
    private List<Bus> busList;

    public BusAdapter(Context context, List<Bus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bus, parent, false);
        return new BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);
        holder.tvNombre.setText(bus.getName());
        Glide.with(context)
                .load(bus.getImagen1())
                .into(holder.imgBus);

        holder.imgInf.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("bus", bus);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public static class BusViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        ImageView imgInf, imgBus;

        public BusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNameBus);
            imgBus = itemView.findViewById(R.id.imgUser);
            imgInf = itemView.findViewById(R.id.inf);
        }
    }
}
