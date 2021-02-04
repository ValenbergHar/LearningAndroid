package by.carkva_gazeta.malitounik;

import java.util.ArrayList;

public class MaranAta_Global_List {

    private static int ListPosition = 0;
    private static int mListGlava = 0;
    private static ArrayList<String> bible;
    private static boolean mPedakVisable = false;
    private static ArrayList<ArrayList<Integer>> Vydelenie;
    private static ArrayList<String> zakladkiSemuxa;
    private static ArrayList<String> zakladkiSinodal;
    private static ArrayList<ArrayList<String>> natatkiSemuxa;
    private static ArrayList<ArrayList<String>> natatkiSinodal;
    //private static ArrayList<String> zakladkiPsalterNadsana;
    //private static ArrayList<ArrayList<String>> natatkiPsalterNadsana;

    public static ArrayList<ArrayList<String>> getNatatkiSinodal() {
        return natatkiSinodal;
    }

    static void setNatatkiSinodal(ArrayList<ArrayList<String>> natatkiSinodal) {
        MaranAta_Global_List.natatkiSinodal = natatkiSinodal;
    }

    public static ArrayList<ArrayList<String>> getNatatkiSemuxa() {
        return natatkiSemuxa;
    }

    static void setNatatkiSemuxa(ArrayList<ArrayList<String>> zametkiSemuxa) {
        MaranAta_Global_List.natatkiSemuxa = zametkiSemuxa;
    }

    public static ArrayList<String> getZakladkiSinodal() {
        return zakladkiSinodal;
    }

    static void setZakladkiSinodal(ArrayList<String> zakladkiSinodal) {
        MaranAta_Global_List.zakladkiSinodal = zakladkiSinodal;
    }

    public static ArrayList<String> getZakladkiSemuxa() {
        return zakladkiSemuxa;
    }

    static void setZakladkiSemuxa(ArrayList<String> zakladkiSemuxa) {
        MaranAta_Global_List.zakladkiSemuxa = zakladkiSemuxa;
    }

    /*static ArrayList<ArrayList<String>> getNatatkiPsalterNadsana() {
        return natatkiPsalterNadsana;
    }

    static ArrayList<String> getZakladkiPsalterNadsana() {
        return zakladkiPsalterNadsana;
    }

    static void setZakladkiPsalterNadsana(ArrayList<String> zakladkiPsalterNadsana) {
        MaranAta_Global_List.zakladkiPsalterNadsana = zakladkiPsalterNadsana;
    }

    static void setNatatkiPsalterNadsana(ArrayList<ArrayList<String>> natatkiPsalterNadsana) {
        MaranAta_Global_List.natatkiPsalterNadsana = natatkiPsalterNadsana;
    }*/

    public static ArrayList<ArrayList<Integer>> getVydelenie() {
        return Vydelenie;
    }

    public static void setVydelenie(ArrayList<ArrayList<Integer>> vydelenie) {
        Vydelenie = vydelenie;
    }

    public static int getmListGlava() {
        return mListGlava;
    }

    public static void setmListGlava(int mListGlava) {
        MaranAta_Global_List.mListGlava = mListGlava;
    }

    public static void setBible(ArrayList<String> bible) {
        MaranAta_Global_List.bible = bible;
    }

    public static void setmPedakVisable(boolean redartor) {
        MaranAta_Global_List.mPedakVisable = redartor;
    }

    public static boolean getmPedakVisable() {
        return mPedakVisable;
    }

    public static ArrayList<String> getBible() {
        return bible;
    }

    public static int getListPosition() {
        return ListPosition;
    }

    public static void setListPosition(int mListPosition) {
        MaranAta_Global_List.ListPosition = mListPosition;
    }

    public static int checkPosition(int glava, int position) {
        for (int i = 0; i < Vydelenie.size(); i++) {
            if (Vydelenie.get(i).get(0) == glava && Vydelenie.get(i).get(1) == position) {
                return i;
            }
        }
        return -1;
    }
}
