
package com.example.textviewlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_view);
        String string = readRaw(this, R.raw.text);
        textView.setLinksClickable(true);

//        Linkify.addLinks(textView, Linkify.WEB_URLS);

//        Log.i("qqq", String.valueOf(string.contains("<uline>")));
//        Log.i("qqq", String.valueOf(string.indexOf("<uline>")));
        Log.i("qqq", String.valueOf(linkPlusText(string)));
//        Linkify.addLinks(textView, Linkify.ALL);


        Spanned spanned = HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_MODE_COMPACT);
        textView.setText(spanned);
        SpannableString ss = new SpannableString(string);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/place/32-222+Racławice,+Poland/@50.3257076,20.2282944,15z/data=!3m1!4b1!4m5!3m4!1s0x4717b1871207e977:0xc9353b75c3c85a6c!8m2!3d50.3256947!4d20.2370492?hl=en"));
//                        Uri.parse("https://www.google.com/maps/dir/48.8276261,2.3350114/48.8476794,2.340595/48.8550395,2.300022/48.8417122,2.3028844"));
                startActivity(intent);
                Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=Kjp6J0blNWQ&t=166s&ab_channel=%D0%91%D0%95%D0%9B%D0%A1%D0%90%D0%A2NEWS"));
//                        Uri.parse("https://www.google.com/maps/dir/48.8276261,2.3350114/48.8476794,2.340595/48.8550395,2.300022/48.8417122,2.3028844"));
                startActivity(intent);

                Toast.makeText(MainActivity.this, "Two", Toast.LENGTH_SHORT).show();
            }
        };
        ss.setSpan(clickableSpan1, 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 16, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //textView.setText(ss);
        // textView.setMovementMethod(LinkMovementMethod.getInstance());

    }


    public static String readRaw(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    private static String kingsList(String allKings) {
        List<String> kings = new ArrayList<>();
        String string = null;
        String start = "<text>";
        String finish = "</text>";
        Pattern pattern = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(allKings);
        while (matcher.find()) {
            string = matcher.group(1);
        }
        return string;
    }

    private static char linkPlusText(String c) {
        String start = "<a href=\"";
        String finish = "</a>";
        String middle = "\">";
        String linkPlusText = null;
        int startI = 0;
        int endI = 0;


        startI = c.indexOf(middle) ;
        endI = c.indexOf(finish) ;
        char ch1 = c.charAt(startI);
        char ch2 = c.charAt(endI);
        String[] lpt;
        String link = null;
        String name = null;

        //        String text = "I love you so much";
//        String wordToFind = "love";
//        Pattern word = Pattern.compile(wordToFind);
//        Matcher match = word.matcher(text);
//
//        while (match.find()) {
//            System.out.println("Found love at index "+ match.start() +" - "+ (match.end()-1));
//        }

        List<Integer> integers = new ArrayList<>();
        Pattern pattern = Pattern.compile(middle, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(c);
        while (matcher.find()){
         // integers.add(matcher.end());
        }

        Pattern pattern1 = Pattern.compile(finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(c);
        while (matcher1.find()){
            integers.add(matcher1.start()-1);
        }



       char ch = c.charAt(105);

        Pattern pattern2 = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(c);
//        while (matcher2.find()) {
//            linkPlusText = matcher1.group(0);
//        }
//        lpt = linkPlusText.split(middle);
        return ch;
    }


}