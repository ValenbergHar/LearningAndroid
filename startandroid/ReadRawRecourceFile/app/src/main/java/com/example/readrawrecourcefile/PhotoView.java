package com.example.readrawrecourcefile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PhotoView extends AppCompatActivity {
    private static final String PHOTO_ID_KEY = "photo";
    private SubsamplingScaleImageView imageView;
    private Bitmap photo;



    public static void start(Context context, String s) {
        Intent intent = new Intent(context, PhotoView.class);
        intent.putExtra(PHOTO_ID_KEY, s);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.imageView);

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);

        ImageLoader.getInstance().loadImage(getIntent().getStringExtra(PHOTO_ID_KEY), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (!isFinishing()) {
                    photo = loadedImage;
                    imageView.setImage(ImageSource.cachedBitmap(loadedImage));
                }
            }
        });


//        Glide.with(this).load(photoStringURL).into(new CustomTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                imageView.setMinimumDpi(80);
//                imageView.setImage(ImageSource.bitmap(resource));
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                hillImageView.setMinimumDpi(80);
//                SubsamplingScaleImageView hillImageView = (SubsamplingScaleImageView) view.findViewById(R.id.hill_image);
//                hillImageView.setMinimumDpi(80);
//                hillImageView.setImage(ImageSource.bitmap(resource));
//            }
//        });

    }


}