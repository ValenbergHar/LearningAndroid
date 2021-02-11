package com.example.kingsgdl.kings.tablelayout;

import android.content.Context;

import com.bumptech.glide.util.Util;
import com.example.kingsgdl.R;
import com.example.kingsgdl.StaticContextFactory;
import com.example.kingsgdl.kings.King;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class Utils  {


    public static List<King> getKingsPK() {


        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);
//        King p0 = new King(0, "Міндоўг", 1236, "https://upload.wikimedia.org/wikipedia/commons/2/24/Mendog_1578.png");
//        King p1 = new King(1, "Транята", 1263, "https://upload.wikimedia.org/wikipedia/commons/2/2e/Treniota.jpg");
//        King p2 = new King(2, "Войшалк", 1264, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Vojshalk.png/230px-Vojshalk.png");
//        King p3 = new King(3, "Шварн", 1267, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p4 = new King(4, "Тройдзень", 1269, "https://upload.wikimedia.org/wikipedia/commons/b/bd/Lithuanian_Grand_Duke_Traidenis.JPG");
//        King p5 = new King(5, "Даўмонт", 1282, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p6 = new King(6, "Будзікід", 1285, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p7 = new King(7, "Будзівід", 1290, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        kings.addAll(Arrays.asList(new King[]{p0, p1, p2, p3, p4, p5, p6, p7}));
        return kings;
    }


    public static List<King> getKingsVkl() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);
//        King p0 = new King(0, "Міндоўг", 1236, "https://upload.wikimedia.org/wikipedia/commons/2/24/Mendog_1578.png");
//        King p1 = new King(1, "Транята", 1263, "https://upload.wikimedia.org/wikipedia/commons/2/2e/Treniota.jpg");
//        King p2 = new King(2, "Войшалк", 1264, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Vojshalk.png/230px-Vojshalk.png");
//        King p3 = new King(3, "Шварн", 1267, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p4 = new King(4, "Тройдзень", 1269, "https://upload.wikimedia.org/wikipedia/commons/b/bd/Lithuanian_Grand_Duke_Traidenis.JPG");
//        King p5 = new King(5, "Даўмонт", 1282, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p6 = new King(6, "Будзікід", 1285, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p7 = new King(7, "Будзівід", 1290, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p8 = new King(8, "Віцень", 1295, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Witenes.PNG/274px-Witenes.PNG");
//        King p9 = new King(9, "Гедымін (Гедзімін)", 1316, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Gedimin_grav_xvii.jpg/274px-Gedimin_grav_xvii.jpg");
//        King p10 = new King(10, "Яўнут", 1341, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
//        King p11 = new King(11, "Альгерд", 1345, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg/274px-Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg");
//        King p12 = new King(12, "Ягайла", 1377, "https://upload.wikimedia.org/wikipedia/commons/c/ca/Jagiello.jpg");
//        King p13 = new King(13, "Кейстут", 1377, "https://www.calend.ru/img/content_persons/i3/3790.jpg");
//        King p14 = new King(14, "Вітаўт", 1392, "https://s2.15min.lt/images/photos/2010/09/07/original/1283882267vytautas_didysis.jpg");
//        King p15 = new King(15, "Свідрыгайла", 1430, "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Śvidrygajła._Сьвідрыгайла_%28A._Guagnini%2C_1578%29.jpg/280px-Śvidrygajła._Сьвідрыгайла_%28A._Guagnini%2C_1578%29.jpg");
//        King p16 = new King(16, "Жыгімонт Кейстутавіч", 1432, "https://upload.wikimedia.org/wikipedia/commons/b/b4/Žygimont_Kiejstutavič._Жыгімонт_Кейстутавіч_%28J._Aziambłoŭski%2C_1840%29.jpg");
//        King p17 = new King(17, "Казімір", 1440, "https://upload.wikimedia.org/wikipedia/commons/5/5d/Kazimierz_Jagiellonczyk.jpg");
//        King p18 = new King(18, "Аляксандр", 1492, "https://upload.wikimedia.org/wikipedia/commons/9/9c/Aleksander_Jagiellonczyk.jpg");
//        King p19 = new King(19, "Жыгімонт I Стары", 1506, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Lesseur-Zygmunt_I_Stary.jpg/420px-Lesseur-Zygmunt_I_Stary.jpg");
//        King p20 = new King(20, "Жыгімонт II Аўгуст", 1544, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Cranach_the_Younger_Sigismund_II_Augustus.jpg/800px-Cranach_the_Younger_Sigismund_II_Augustus.jpg");
//        kings.addAll(Arrays.asList(new King[]{p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20}));
        return kings;
    }

    public static List<King> getKingsRP() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsRusOcc() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsBNR() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }


    public static List<King> getKingsZahBiel() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsBSSRFirst() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsNacOcc() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsBSSRSecond() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }

    public static List<King> getKingsRB() {
        List<King> kings = LoadKing.kingsList(StaticContextFactory.getAppContext(), R.raw.kingsvkl);

        return kings;
    }
}
