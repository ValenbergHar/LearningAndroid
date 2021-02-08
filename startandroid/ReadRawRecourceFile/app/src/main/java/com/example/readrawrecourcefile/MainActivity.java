package com.example.readrawrecourcefile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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
    private RecyclerView photoRecView;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<King> kingList = Util.kingsList(this, R.raw.kings);
        King king  =kingList.get(0);





        adapter= new PhotoAdapter(this, king.getKingPhotos(), king.getKingPhotosDesc());
        photoRecView = findViewById(R.id.photoRecView);
        photoRecView.setAdapter(adapter);
        photoRecView.setLayoutManager(new GridLayoutManager(this,2));



//        txtRawResource.setText(String.valueOf(Util.kingsList(this, R.raw.kings).get(0)));


//        kingsList = new ArrayList<>();
//
//
//        String allKings = readRawTextFile(this, R.raw.kings);
//        kingsStringList = kingsList(allKings);
//
//        for (String s : kingsStringList) {
//            Log.i("qqq", kingId(s));
//            Log.i("qqq", kingName(s));
//            Log.i("qqq", kingDateLife(s));
//            Log.i("qqq", kingDateReign(s));
//            Log.i("qqq", kingShortHist(s));
//            txtRawResource.setText(kingShortHist(s));
//            Log.i("qqq", kingLongHist(s));
////            Log.i("qqq", kingPhotos(s));
//            kingPhotoUnitList = kingPhotoUnitList(s);
//            for (String ph : kingPhotoUnitList) {
//                Log.i("qqq", ph);
//            }

//        }


    }

//    private static List<King> kingsList(Context ctx, int resId) {
//        List<King> kingsList = new ArrayList<>();
//        String allKings = readRaw(ctx, resId);
//        List<String> kingsStringList = kingsList(allKings);
//        for (String s : kingsStringList) {
//            String id = kingId(allKings);
//            String kingName = kingName(s);
//            String kingDateLife = kingDateLife(s);
//            String kingDateReign = kingDateReign(s);
//            String kingShortHist = kingShortHist(s);
//            String kingLongHist = kingLongHist(s);
//            List<String> kingPhotoUnitList = kingPhotoUnitList(s);
//            kingsList.add(new King(id, kingName, kingDateLife, kingDateReign, kingShortHist, kingLongHist, kingPhotoUnitList));
//        }
//
//        return kingsList;
//    }
//
//
//    private static String kingId(String kingUnit) {
//        String id = null;
//        String start = "<id>";
//        String finish = "</id>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            id = matcher1.group(1);
//        }
//        return id;
//    }
//
//    private static String kingDateLife(String kingUnit) {
//        String kingDateLife = null;
//        String start = "<kingDateLife>";
//        String finish = "</kingDateLife>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            kingDateLife = matcher1.group(1).trim();
//        }
//        return kingDateLife;
//    }
//
//    private static String kingDateReign(String kingUnit) {
//        String kingDateReign = null;
//        String start = "<kingDateReign>";
//        String finish = "</kingDateReign>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            kingDateReign = matcher1.group(1).trim();
//        }
//        return kingDateReign;
//    }
//
//    private static String kingShortHist(String kingUnit) {
//        String kingShortHist = null;
//        String start = "<kingShortHist>";
//        String finish = "</kingShortHist>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            kingShortHist = matcher1.group(1).trim();
//        }
//        return kingShortHist;
//    }
//
//    private static String kingLongHist(String kingUnit) {
//        String kingLongHist = null;
//        String start = "<kingLongHist>";
//        String finish = "</kingLongHist>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            kingLongHist = matcher1.group(1).trim();
//        }
//        return kingLongHist;
//    }
//
//
////    private String kingPhotos(String kingUnit) {
////        String kingPhotos = null;
////        String start = "<kingPhotos>";
////        String finish = "</kingPhotos>";
////        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
////        Matcher matcher1 = pattern1.matcher(kingUnit);
////        while (matcher1.find()) {
////            kingPhotos = matcher1.group(1);
////        }
////        return kingPhotos;
////    }
//
////    private List<String> kingPhotoUnitList(String kingPhotos) {
////        List<String> kingPhotoUnitList = new ArrayList<>();
////
////        String start = "<kingPhotoUnit>";
////        String finish = "</kingPhotoUnit>";
////        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
////        Matcher matcher1 = pattern1.matcher(kingPhotos);
////        while (matcher1.find()) {
////            kingPhotoUnitList.add(matcher1.group(1));
////        }
////        return kingPhotoUnitList;
////    }
//
//    private static List<String> kingPhotoUnitList(String s) {
//        String kingPhotos = null;
//        String start = "<kingPhotos>";
//        String finish = "</kingPhotos>";
//        Pattern pattern = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(s);
//        while (matcher.find()) {
//            kingPhotos = matcher.group(1);
//        }
//        List<String> kingPhotoUnitList = new ArrayList<>();
//        String start1 = "<kingPhotoUnit>";
//        String finish1 = "</kingPhotoUnit>";
//        Pattern pattern1 = Pattern.compile(start1 + "(.*?)" + finish1, Pattern.DOTALL);
//        Matcher matcher1 = pattern1.matcher(kingPhotos);
//        while (matcher1.find()) {
//            kingPhotoUnitList.add(matcher1.group(1).trim());
//        }
//        return kingPhotoUnitList;
//    }
//
//
//    private static String kingName(String kingUnit) {
//        String kingName = null;
//        String start = "<kingName>";
//        String finish = "</kingName>";
//        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
//        Matcher matcher1 = pattern1.matcher(kingUnit);
//        while (matcher1.find()) {
//            kingName = matcher1.group(1).trim();
//        }
//        return kingName;
//    }
//
//
//    private static List<String> kingsList(String allKings) {
//        List<String> kings = new ArrayList<>();
//        String start = "<king>";
//        String finish = "</king>";
//        Pattern pattern = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(allKings);
//        while (matcher.find()) {
//            kings.add(matcher.group(1));
//        }
//        return kings;
//    }
//
//    public static String readRaw(Context ctx, int resId) {
//        InputStream inputStream = ctx.getResources().openRawResource(resId);
//        BufferedReader buffreader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        StringBuilder text = new StringBuilder();
//
//        try {
//            while ((line = buffreader.readLine()) != null) {
//                text.append(line);
//                text.append('\n');
//            }
//        } catch (IOException e) {
//            return null;
//        }
//        return text.toString();
//    }


}