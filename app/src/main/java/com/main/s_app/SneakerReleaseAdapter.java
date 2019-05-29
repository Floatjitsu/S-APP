package com.main.s_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.s_app.com.main.s_app.firebase.Sneaker;

import java.util.ArrayList;

public class SneakerReleaseAdapter extends RecyclerView.Adapter<SneakerReleaseAdapter.ViewHolder> {

    private ArrayList<Sneaker> sneakers;

    public SneakerReleaseAdapter(ArrayList<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    @NonNull
    @Override
    public SneakerReleaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.sneaker_release_item, viewGroup, false);
        return new SneakerReleaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SneakerReleaseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.sneakerBrand.setText(sneakers.get(i).getBrandName());
        viewHolder.sneakerModelName.setText(sneakers.get(i).getModelName());
    }

    @Override
    public int getItemCount() {
        return sneakers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sneakerBrand, sneakerModelName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sneakerBrand = itemView.findViewById(R.id.sneaker_brand);
            sneakerModelName = itemView.findViewById(R.id.sneaker_model_name);
        }
    }
}
