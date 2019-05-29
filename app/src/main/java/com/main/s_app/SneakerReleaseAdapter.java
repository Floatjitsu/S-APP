package com.main.s_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.s_app.com.main.s_app.firebase.Sneaker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SneakerReleaseAdapter extends RecyclerView.Adapter<SneakerReleaseAdapter.ViewHolder> {

    private ArrayList<Sneaker> sneakers;
    private Context context;

    public SneakerReleaseAdapter(ArrayList<Sneaker> sneakers, Context context) {
        this.sneakers = sneakers;
        this.context = context;
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
        viewHolder.bind(sneakers.get(i));
        /*viewHolder.sneakerBrand.setText(sneakers.get(i).getBrandName());
        viewHolder.sneakerModelName.setText(sneakers.get(i).getModelName());
        StorageReference modelImageRef = FirebaseStorage.getInstance().getReference().child("/images/" + sneakers.get(i).getImageUri());
        GlideApp.with(context).load(modelImageRef).into(viewHolder.sneakerModelImage); */
    }

    @Override
    public int getItemCount() {
        return sneakers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sneakerBrand, sneakerModelName;
        ImageView sneakerModelImage;
        ImageButton buttonAddReleaseDateToCalendar;
        long timeStamp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sneakerBrand = itemView.findViewById(R.id.sneaker_brand);
            sneakerModelName = itemView.findViewById(R.id.sneaker_model_name);
            sneakerModelImage = itemView.findViewById(R.id.sneaker_model_image);
            buttonAddReleaseDateToCalendar = itemView.findViewById(R.id.button_add_to_calendar);
        }

        void bind(Sneaker s) {
            sneakerBrand.setText(s.getBrandName());
            sneakerModelName.setText(s.getModelName());
            StorageReference modelImageRef = FirebaseStorage.getInstance().getReference().child("/images/" + s.getImageUri());
            GlideApp.with(context).load(modelImageRef).into(sneakerModelImage);
/*
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.YYYY");
            try {
                Date d = dateFormat.parse(s.getReleaseDate());
                timeStamp = d.getTime();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } */
        }

        View.OnClickListener buttonListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");

                }
            };
        }
    }
}
