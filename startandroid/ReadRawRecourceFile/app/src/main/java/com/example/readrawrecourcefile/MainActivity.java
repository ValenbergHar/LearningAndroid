package com.example.readrawrecourcefile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView txtRawResource;
    private List<String> kingsStringList;
    private List<King> kingsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtRawResource = findViewById(R.id.txtRawResource);
        kingsList = new ArrayList<>();


        String allKings = readRawTextFile(this, R.raw.kings);
        String allKings1 = readRawTextFile(this, R.raw.kings);
        kingsStringList = kingsList(allKings);

        for (String s : kingsStringList) {
            Log.i("qqq", String.valueOf(kingId(s)));
//            Pattern pattern1 = Pattern.compile("<id>[0-9]+</id>");
//            Matcher matcher1 = pattern1.matcher(s);
//            while (matcher1.find()) {
//
//                Log.i("qqq", String.valueOf(matcher1));
//            }
        }


    }

    private int kingId(String kingUnit) {
        int id = 0;
        Pattern pattern1 = Pattern.compile("<id>[0-9]+</id>");
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            id= Integer.parseInt(String.valueOf(matcher1));
        }
        return id;
    }


//    private int kingId(String kingUnit) {
//        int id = 0;
//        String start = "<id>";
//        String finish = "</id>";
//        Pattern pattern = Pattern.compile(start + "[0-9]+" + finish);
//        Matcher matcher = pattern.matcher(kingUnit);
//        while (matcher.find()) {
//           id = Integer.parseInt(String.valueOf(matcher));
//        }
//        return id;
//    }


    private List<String> kingsList(String allKings) {
        List<String> kings = new ArrayList<>();
        String start = "<king>";
        String finish = "</king>";
        Pattern pattern = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(allKings);
        while (matcher.find()) {
            kings.add(String.valueOf(matcher));
        }
        return kings;
    }

    public static String readRawTextFile(Context ctx, int resId) {
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


}