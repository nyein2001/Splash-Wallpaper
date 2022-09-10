package com.wallpaper.splash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class SetWallpaper extends AppCompatActivity {

    ImageView imageView,back;
    TextView title;
    Button btnApply;
    String name;
    String imageUrl;
    Bitmap wallpaperBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);


        imageView = findViewById(R.id.wallpaper);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        btnApply = findViewById(R.id.apply);

        imageUrl = getIntent().getStringExtra("WALLPAPER");
        name = getIntent().getStringExtra("NAME");


        title.setText(name);

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(imageUrl)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                try {
                                    WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);
                                    Toast.makeText(SetWallpaper.this, "Wallpaper Set ... ", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}