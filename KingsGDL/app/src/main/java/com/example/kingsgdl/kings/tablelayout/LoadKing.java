package com.example.kingsgdl.kings.tablelayout;

import android.content.Context;

import com.example.kingsgdl.kings.King;
import com.example.kingsgdl.kings.KingFull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadKing {

    public static List<King> kingsList(Context ctx, int resId) {
        List<King> kingsList = new ArrayList<>();
        String allKings = readRaw(ctx, resId);
        List<String> kingsStringList = kingsListWithAdd(allKings);
        for (String s : kingsStringList) {
            String id = kingId(s);
            String kingName = kingName(s);
            String kingDateLife = kingDateLife(s);
            String kingOccupation= kingOccupation(s);
            String link = kingLink(s);
            String kingPhotoUnit = kingPhotoUnit(s);
            kingsList.add(new King(id, kingName, kingDateLife, kingOccupation, link , kingPhotoUnit));
        }
        return kingsList;
    }

    private static String kingLink(String kingUnit) {
        String kingLink = null;
        String start = "<kingLink>";
        String finish = "</kingLink>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingLink = matcher1.group(1).trim();
        }
        return kingLink;
    }

    private static String kingOccupation(String kingUnit) {
        String kingOccupation = null;
        String start = "<kingOccupation>";
        String finish = "</kingOccupation>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingOccupation = matcher1.group(1).trim();
        }
        return kingOccupation;
    }


        public static KingFull kingsListWithAdd(Context ctx, int resId) {

        String KingFull = readRaw(ctx, resId);
        String s = kingUnit(KingFull);
            String id = kingId(s);
            String kingName = kingName(s);
            String kingDateLife = kingDateLife(s);
            String kingDateReign = kingDateReign(s);
            String kingShortHist = kingShortHist(s);
            String kingLongHist = kingLongHist(s);
            List<String> kingPhotoUnitList = kingPhotoUnitList(s);
            List<String> kingPhotoUnitListDesc = kingPhotoUnitListDesc(s);
            KingFull kingFull = new KingFull(id, kingName, kingDateLife, kingDateReign, kingShortHist, kingLongHist, kingPhotoUnitList, kingPhotoUnitListDesc);

        return kingFull;
    }


//    public static List<KingFull> kingsListWithAdd(Context ctx, int resId) {
//        List<KingFull> kingsList = new ArrayList<>();
//        String allKings = readRaw(ctx, resId);
//        List<String> kingsStringList = kingsListWithAdd(allKings);
//        for (String s : kingsStringList) {
//            String id = kingId(s);
//            String kingName = kingName(s);
//            String kingDateLife = kingDateLife(s);
//            String kingDateReign = kingDateReign(s);
//            String kingShortHist = kingShortHist(s);
//            String kingLongHist = kingLongHist(s);
//            List<String> kingPhotoUnitList = kingPhotoUnitList(s);
//            List<String> kingPhotoUnitListDesc = kingPhotoUnitListDesc(s);
//            kingsList.add(new KingFull(id, kingName, kingDateLife, kingDateReign, kingShortHist, kingLongHist, kingPhotoUnitList, kingPhotoUnitListDesc));
//        }
//
//        return kingsList;
//    }


    private static String kingId(String kingUnit) {
        String id = null;
        String start = "<id>";
        String finish = "</id>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            id = matcher1.group(1);
        }
        return id;
    }

    private static String kingDateLife(String kingUnit) {
        String kingDateLife = null;
        String start = "<kingDateLife>";
        String finish = "</kingDateLife>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingDateLife = matcher1.group(1).trim();
        }
        return kingDateLife;
    }

    private static String kingDateReign(String kingUnit) {
        String kingDateReign = null;
        String start = "<kingDateReign>";
        String finish = "</kingDateReign>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingDateReign = matcher1.group(1).trim();
        }
        return kingDateReign;
    }

    private static String kingShortHist(String kingUnit) {
        String kingShortHist = null;
        String start = "<kingShortHist>";
        String finish = "</kingShortHist>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingShortHist = matcher1.group(1).replaceAll("\n", "").trim().replaceAll("\\s+", " ");
        }
        return kingShortHist;
    }

    private static String kingLongHist(String kingUnit) {
        String kingLongHist = null;
        String start = "<kingLongHist>";
        String finish = "</kingLongHist>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingLongHist = matcher1.group(1).replaceAll("\n", "").trim().replaceAll("\\s+", " ");
        }
        return kingLongHist;
    }




    private static List<String> kingPhotoUnitList(String s) {
        String kingPhotos = null;
        String start = "<kingPhotos>";
        String finish = "</kingPhotos>";
        Pattern pattern = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            kingPhotos = matcher.group(1);
        }






        List<String> kingPhotoUnitList = new ArrayList<>();
        String start1 = "<kingPhotoUnit>";
        String finish1 = "</kingPhotoUnit>";
        Pattern pattern1 = Pattern.compile(start1 + "(.*?)" + finish1, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingPhotos);
        while (matcher1.find()) {
            kingPhotoUnitList.add(matcher1.group(1).trim().replaceAll("\\s+", " "));
        }
        return kingPhotoUnitList;
    }

    private static String kingPhotoUnit(String s) {
        String kingPhoto = null;
        String start = "<kingPhotoUnit>";
        String finish = "</kingPhotoUnit>";
        Pattern pattern = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            kingPhoto = matcher.group(1);
        }
        return kingPhoto;
    }

    private static List<String> kingPhotoUnitListDesc(String s) {
        String kingPhotos = null;
        String start = "<kingPhotos>";
        String finish = "</kingPhotos>";
        Pattern pattern = Pattern.compile(start + "(.*?)" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            kingPhotos = matcher.group(1);
        }
        List<String> kingPhotoUnitListDesc = new ArrayList<>();
        String start1 = "<kingPhotoUnitDesc>";
        String finish1 = "</kingPhotoUnitDesc>";
        Pattern pattern1 = Pattern.compile(start1 + "(.*?)" + finish1, Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(kingPhotos);
        while (matcher1.find()) {
            kingPhotoUnitListDesc.add(matcher1.group(1).trim());
        }
        return kingPhotoUnitListDesc;
    }


    private static String kingName(String kingUnit) {
        String kingName = null;
        String start = "<kingName>";
        String finish = "</kingName>";
        Pattern pattern1 = Pattern.compile(start + "(.*?)" + finish);
        Matcher matcher1 = pattern1.matcher(kingUnit);
        while (matcher1.find()) {
            kingName = matcher1.group(1).replaceAll("\n", "").trim();
        }
        return kingName;
    }


    //для спісу князёў
    private static List<String> kingsListWithAdd(String allKings) {
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



    //для 1 князя
    private static String kingUnit(String allKings) {
        String kingUnit = null;
        String start = "<king>";
        String finish = "</king>";
        Pattern pattern = Pattern.compile(start + "\\s*(.*?)\\s*" + finish, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(allKings);
        while (matcher.find()) {
            kingUnit = matcher.group(1);
        }
        return kingUnit;
    }

    public static String readRaw(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
//        InputStream inputStream = MainActivityTable.getAppContext().getResources().openRawResource(resId);
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