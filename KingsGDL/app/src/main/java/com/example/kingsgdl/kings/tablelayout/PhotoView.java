package com.example.kingsgdl.kings.tablelayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.kingsgdl.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhotoView extends AppCompatActivity {
    private static final String PHOTO_ID_KEY = "photo";
    private SubsamplingScaleImageView subsamplingScaleImageView;



    public static void start(Context context, String s) {
        Intent intent = new Intent(context, PhotoView.class);
        intent.putExtra(PHOTO_ID_KEY, s);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_view);

        subsamplingScaleImageView = findViewById(R.id.subsamplingScaleImageView);
        Intent intent = getIntent();
        String photoUrl = intent.getStringExtra(PHOTO_ID_KEY);



//        Picasso.with(this).load(photoUrl).into(new SimpleTarget<File>() {
//                                                   @Override
//                                                   public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                                                       subsamplingScaleImageView.setImage(ImageSource.uri(resource.getAbsolutePath()));
//                                                       subsamplingScaleImageView.setMaxScale(10f);
//                                                   }
//
//                                               });




        Glide.with(this)
                .download(photoUrl)
                .into(new SimpleTarget<File>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.d("load failed", "nothing");
                    }
                    @Override
                    public void onResourceReady(File resource, Transition<? super File> transition) {
                        subsamplingScaleImageView.setImage(ImageSource.uri(resource.getAbsolutePath()));
                        subsamplingScaleImageView.setMaxScale(10f);
                    }
                });
    }
}