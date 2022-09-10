package com.wallpaper.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CategoryDetailActivity extends AppCompatActivity {

    CircularProgressIndicator indicator;
    RecyclerView recyclerView;
    CategoryDetailAdapter adapter;
    DatabaseReference dataRef;

    private ArrayList<DataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        String category = getIntent().getStringExtra("CATEGORY");

        recyclerView = findViewById(R.id.category_detail_recyclerview);
        indicator = findViewById(R.id.category_detail_progress_circular);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        arrayList = new ArrayList<>();

        dataRef = FirebaseDatabase.getInstance().getReference().child("Images");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                    String CAT = dataModel.getCategory();
                   if (Objects.equals(CAT, category)) {

                       arrayList.add(dataModel);
                   }


                }
                Collections.reverse(arrayList);
                adapter = new CategoryDetailAdapter(getApplicationContext(), arrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                indicator.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoryDetailActivity.this,"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class CategoryDetailAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context mContext;
        ArrayList<DataModel> arrayList;

        public CategoryDetailAdapter(Context context, ArrayList<DataModel> arrayList) {
            this.mContext = context;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detail_view,parent,false);
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
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.category_detail_image_view);
            this.title = itemView.findViewById(R.id.category_detail_title);
        }
    }
}