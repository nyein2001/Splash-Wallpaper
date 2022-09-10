package com.wallpaper.splash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<DataModel> arrayList;

    public DataAdapter(Context context, ArrayList<DataModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_wallpaper_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataModel data = arrayList.get(position);
        String imgUrl = data.getImageUrl();
        String name = data.getImageName();
        ImageView wallpaper_image = holder.img;
        if (imgUrl != null && !imgUrl.isEmpty()){
            Glide.with(mContext).load(imgUrl).into(wallpaper_image);

        }
        if (name != null && !name.isEmpty()) {
            holder.title.setText(data.getImageName());
        }

        wallpaper_image.setOnClickListener(view -> {
            Intent intent = new Intent(mContext.getApplicationContext(),SetWallpaper.class);
            intent.putExtra("WALLPAPER",imgUrl);
            intent.putExtra("NAME",name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.home_image_view);
            this.title = itemView.findViewById(R.id.home_title);
        }
    }
}
