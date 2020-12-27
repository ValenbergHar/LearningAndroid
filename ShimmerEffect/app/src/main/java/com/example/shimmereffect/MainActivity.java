package com.example.shimmereffect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {
    ShimmerFrameLayout layout;
    ImageView imageView;
    TextView textView1, textView2;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.shimmer);
        imageView = findViewById(R.id.image_view);
        textView1 = findViewById(R.id.title);
        textView2 = findViewById(R.id.subtitle);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.stopShimmer();
                layout.hideShimmer();
                layout.setVisibility(View.GONE);

                imageView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
            }
        },3000);
    }
}