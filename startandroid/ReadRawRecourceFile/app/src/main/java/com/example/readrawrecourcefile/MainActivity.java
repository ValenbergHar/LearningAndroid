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
        kingsStringList = kingsList(allKings);

        for (String s : kingsStringList) {
            Log.i("qqq", kingId(s));
            Log.i("qqq", kingName(s));
            Log.i("qqq", kingDateLife(s));
            Log.i("qqq", kingDateReign(s));
            Log.i("qqq", kingShortHist(s));
            Log.i("qqq", kingLongHist(s));
            Log.i("qqq", kingPhotos(s));


//            Pattern pattern1 = Pattern.compile("<id>[0-9]+</id>");
//            Matcher matcher1 = pattern1.matcher(s);
//            while (matcher1.find()) {
//
//                Log.i("qqq", String.valueOf(matcher1));
//            }
        }


    }

    private String kingId(String kingUnit) {
        String id = null;
        Pattern pattern1 = Pattern.compile("<id>[0-9]+</id>");
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            id = matcher1.group();
        }
        return id;
    }

    private String kingDateLife(String kingUnit) {
        String kingDateLife = null;
        String start = "<kingDateLife>";
        String finish = "</kingDateLife>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingDateLife = matcher1.group(1);
        }
        return kingDateLife;
    }

    private String kingDateReign(String kingUnit) {
        String kingDateReign = null;
        String start = "<kingDateReign>";
        String finish = "</kingDateReign>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingDateReign = matcher1.group(1);
        }
        return kingDateReign;
    }

    private String kingShortHist(String kingUnit) {
        String kingShortHist = null;
        String start = "<kingShortHist>";
        String finish = "</kingShortHist>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingShortHist = matcher1.group(1);
        }
        return kingShortHist;
    }

    private String kingLongHist(String kingUnit) {
        String kingLongHist = null;
        String start = "<kingLongHist>";
        String finish = "</kingLongHist>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingLongHist = matcher1.group(1);
        }
        return kingLongHist;
    }


    private String kingPhotos(String kingUnit) {
        String kingPhotos = null;
        String start = "<kingPhotos>";
        String finish = "</kingPhotos>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingPhotos = matcher1.group(1);
        }
        return kingPhotos;
    }

    private List<String> kingPhotoUnitList(String kingPhotos) {
        List<String> kingPhotoUnitList = new ArrayList<>();

        String start = "<kingPhotoUnit>";
        String finish = "</kingPhotoUnit>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingPhotos);
        while (matcher1.find()) {
            kingPhotoUnitList.add(matcher1.group(1));
        }
        return kingPhotoUnitList;
    }







    private String kingName(String kingUnit) {
        String kingName = null;
        String start = "<kingName>";
        String finish = "</kingName>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingName = matcher1.group(1);
        }
        return kingName;
    }


    private List<String> kingsList(String allKings) {
        List<String> kings = new ArrayList<>();
        String start = "<king>";
        String finish = "</king>";
        Pattern pattern = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(allKings);
        while (matcher.find()) {
            kings.add(matcher.group(1));
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