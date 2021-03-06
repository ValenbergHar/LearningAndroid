package com.example.gdlkingslist;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application {
    private static List<King> kingsList = new ArrayList<>();
    private static int nextId = 13;

    public static List<King> getKingsList() {
        return kingsList;
    }

    public static void setKingsList(List<King> kingsList) {
        MyApplication.kingsList = kingsList;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        MyApplication.nextId = nextId;
    }


    public MyApplication() {
        fillKingsList();
    }

    private void fillKingsList() {
        King p1 = new King(1, "Міндоўг", 1253, "https://upload.wikimedia.org/wikipedia/commons/2/24/Mendog_1578.png");
        King p2 = new King(2, "Транята", 1263, "https://upload.wikimedia.org/wikipedia/commons/2/2e/Treniota.jpg");
        King p3 = new King(3, "Войшалк", 1264, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Vojshalk.png/230px-Vojshalk.png");
        King p4 = new King(4, "Шварн", 1267, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p5 = new King(5, "Тройдзень", 1269, "https://upload.wikimedia.org/wikipedia/commons/b/bd/Lithuanian_Grand_Duke_Traidenis.JPG");
        King p6 = new King(6, "Даўмонт", 1282, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p7 = new King(7, "Будзікід", 1285, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p8 = new King(8, "Будзівід", 1290, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p9 = new King(9, "Віцень", 1295, "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Witenes.PNG/274px-Witenes.PNG");
        King p10 = new King(10, "Гедымін (Гедзімін)", 1316, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Gedimin_grav_xvii.jpg/274px-Gedimin_grav_xvii.jpg");
        King p11 = new King(11, "Яўнут", 1341, "https://i.c97.org/gi/44659/229844-1541254501-big.jpg");
        King p12 = new King(12, "Альгерд", 1345, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg/274px-Algierd._Альгерд_%28A._Guagnini%2C_1578%29.jpg");
        King p13 = new King(13, "Ягайла", 1377, "https://upload.wikimedia.org/wikipedia/commons/c/ca/Jagiello.jpg");

        kingsList.addAll(Arrays.asList(new King[]{p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13}));

    }
}
