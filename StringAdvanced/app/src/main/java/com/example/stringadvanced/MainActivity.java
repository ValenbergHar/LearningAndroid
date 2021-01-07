package com.example.stringadvanced;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String nameString = "sdfgdfg, argraeg, asefas, sdgs, asedfdtsg, asef, df";
//        String[] names = nameString.split(", ");
//        for (String name: names){
//            Log.d("my", name);
//        }

//        String geometry = "Geometry";
//        String meter = geometry.substring(3,7);
//        Log.d("my", meter);

//        String river = "Mississippi";
//        Pattern pattern = Pattern.compile("Mi(.*?)pi");
//        Matcher matcher = pattern.matcher(river);
//        while (matcher.find()) {
//            Log.i("my", matcher.group(1));
//        }


        String s = "<figure class=\"wp-block-image size-large\"><img loading=\"lazy\" width=\"344\" height=\"431\" src=\"https://svenskainfluencers.nu/wp-content/uploads/Skärmavbild-2020-06-28-kl.-22.33.08.png\" alt=\"\" class=\"wp-image-231\" srcset=\"https://svenskainfluencers.nu/wp-content/uploads/Skärmavbild-2020-06-28-kl.-22.33.08.png 344w, https://svenskainfluencers.nu/wp-content/uploads/Skärmavbild-2020-06-28-kl.-22.33.08-239x300.png 239w\" sizes=\"(max-width: 344px) 100vw, 344px\" /><figcaption>Instagram: Marguax Dietz</figcaption></figure>";
        Pattern patternImg = Pattern.compile("src=\"(.*?)\"");
        Pattern patternName = Pattern.compile("<figcaption>Instagram: (.*?)</figcaption>");
        Matcher matcherImg = patternImg.matcher(s);
        Matcher matcherName= patternName.matcher(s);
        while (matcherImg.find()) {
            Log.i("my", matcherImg.group(1));
        }
        while (matcherName.find()) {
            Log.i("my", matcherName.group(1));
        }

    }
}