package com.example.readrawrecourcefile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

import java.io.File;

public class PhotoView extends AppCompatActivity {
    private static final String PHOTO_ID_KEY = "photo";
 private SubsamplingScaleImageView imageView;
//    private  ImageView imageView;
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
        Intent intent = getIntent();
        String photoUrl=intent.getStringExtra(PHOTO_ID_KEY);



//
//        ImageLoader.getInstance().loadImage(getIntent().getStringExtra(PHOTO_ID_KEY), new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                if (!isFinishing()) {
//                    photo = loadedImage;
//                    imageView.setImage(ImageSource.cachedBitmap(loadedImage));
//                }
//            }
//        });


        //working
//        Glide.with(this)
//                .asBitmap()
//                .load(photoUrl)
//                .into(imageView);


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
                        imageView.setImage(ImageSource.uri(resource.getAbsolutePath()));
                        imageView.setMaxScale(10f);
                    }
                });









    }


}