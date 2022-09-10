package com.wallpaper.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    CategoryAdapter adapter;
    private ArrayList<CategoryModel> arrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.category_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        for (int i = 0; i < CategoryData.categoryNameArray.length; i++) {
            arrayList.add(new CategoryModel(
                    CategoryData.categoryNameArray[i],
                    CategoryData.categoryDrawableArray[i]
            ));
        }

        adapter = new CategoryAdapter(view.getContext(),arrayList);
        recyclerView.setAdapter(adapter);
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

        Context context;
        ArrayList<CategoryModel> arrayList;

        public CategoryAdapter(Context context, ArrayList<CategoryModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view,parent,false);
            CategoryAdapter.MyViewHolder myViewHolder = new CategoryAdapter.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ImageView categoryDrawable = holder.categoryDrawable;
            TextView categoryName = holder.categoryName;
            final CategoryModel data = arrayList.get(position);
            Integer imgUrl = data.getCategoryDrawable();
            String name = data.getCategoryName();
            if (imgUrl != 0){
                categoryDrawable.setBackgroundResource(arrayList.get(position).getCategoryDrawable());
            }
            categoryName.setText(data.getCategoryName());

            categoryDrawable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(),CategoryDetailActivity.class);
                    intent.putExtra("CATEGORY",data.getCategoryName().toLowerCase(Locale.ROOT));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView categoryDrawable;
            TextView categoryName;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryDrawable = itemView.findViewById(R.id.category_image_view);
                categoryName = itemView.findViewById(R.id.category_title);
            }
        }
    }

    private class CategoryModel {

         String categoryName;
          Integer categoryDrawable;

        public CategoryModel(String categoryName,Integer categoryDrawable){
            this.categoryName = categoryName;
            this.categoryDrawable = categoryDrawable;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryDrawable(Integer categoryDrawable) {
            this.categoryDrawable = categoryDrawable;
        }

        public Integer getCategoryDrawable() {
            return categoryDrawable;
        }
    }
    private static class CategoryData{
       // public static OptionalDataException categoryNameArray;
     static  String[] categoryNameArray = {"Light", "Background", "Texture", "Wall",
                "Paint", "Miscellanea", "Interior", "Design"};
     static  Integer[] categoryDrawableArray = {R.drawable.light, R.drawable.background, R.drawable.texture,
                R.drawable.wall, R.drawable.paint, R.drawable.miscellanea, R.drawable.interior,
                R.drawable.design};
    }
}
