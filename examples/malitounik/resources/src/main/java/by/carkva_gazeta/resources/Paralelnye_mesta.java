package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 18.10.16
 */

class Paralelnye_mesta {

    private InputStream inputStream;
    static String kniga, nazva, nazvaBel;
    static int nomer;

    @SuppressLint("SetTextI18n")
    ArrayList<TextView_Roboto_Condensed> paralel(Context context, String cytanneSours, String cytanneParalelnye, boolean semuxa) {
        ArrayList<TextView_Roboto_Condensed> arrayList = new ArrayList<>();
        TextView_Roboto_Condensed textViewSours, textViewZag, textViewOpis;
        if (semuxa) {
            if (cytanneSours.contains("Быт")) {
                cytanneSours = cytanneSours.replace("Быт", "Быц");
            }
            if (cytanneSours.equals("Исх")) {
                cytanneSours = cytanneSours.replace("Исх", "Вых");
            }
            if (cytanneSours.contains("Лев")) {
                cytanneSours = cytanneSours.replace("Лев", "Ляв");
            }
            if (cytanneSours.contains("Числа")) {
                cytanneSours = cytanneSours.replace("Числа", "Лікі");
            }
            if (cytanneSours.contains("Втор")) {
                cytanneSours = cytanneSours.replace("Втор", "Дрг");
            }
            if (cytanneSours.contains("Руфь")) {
                cytanneSours = cytanneSours.replace("Руфь", "Рут");
            }
            if (cytanneSours.contains("1 Пар")) {
                cytanneSours = cytanneSours.replace("1 Пар", "1 Лет");
            }
            if (cytanneSours.contains("2 Пар")) {
                cytanneSours = cytanneSours.replace("2 Пар", "2 Лет");
            }
            if (cytanneSours.contains("1 Езд")) {
                cytanneSours = cytanneSours.replace("1 Езд", "1 Эзд");
            }
            if (cytanneSours.contains("Неем")) {
                cytanneSours = cytanneSours.replace("Неем", "Нээм");
            }
            if (cytanneSours.contains("Есф")) {
                cytanneSours = cytanneSours.replace("Есф", "Эст");
            }
            if (cytanneSours.contains("Иов")) {
                cytanneSours = cytanneSours.replace("Иов", "Ёва");
            }
            if (cytanneSours.contains("Притч")) {
                cytanneSours = cytanneSours.replace("Притч", "Высл");
            }
            if (cytanneSours.contains("Еккл")) {
                cytanneSours = cytanneSours.replace("Еккл", "Экл");
            }
            if (cytanneSours.contains("Песн")) {
                cytanneSours = cytanneSours.replace("Песн", "Псн");
            }
            if (cytanneSours.equals("Ис")) {
                cytanneSours = cytanneSours.replace("Ис", "Іс");
            }
            if (cytanneSours.contains("Иер")) {
                cytanneSours = cytanneSours.replace("Иер", "Ер");
            }
            if (cytanneSours.contains("Плач Иер")) {
                cytanneSours = cytanneSours.replace("Плач Иер", "Пасл Ер");
            }
            if (cytanneSours.contains("Иез")) {
                cytanneSours = cytanneSours.replace("Иез", "Езк");
            }
            if (cytanneSours.contains("Ос")) {
                cytanneSours = cytanneSours.replace("Ос", "Ас");
            }
            if (cytanneSours.contains("Иоиль")) {
                cytanneSours = cytanneSours.replace("Иоиль", "Ёіл");
            }
            if (cytanneSours.contains("Авдий")) {
                cytanneSours = cytanneSours.replace("Авдий", "Аўдз");
            }
            if (cytanneSours.contains("Иона")) {
                cytanneSours = cytanneSours.replace("Иона", "Ёны");
            }
            if (cytanneSours.contains("Мих")) {
                cytanneSours = cytanneSours.replace("Мих", "Міх");
            }
            if (cytanneSours.contains("Наум")) {
                cytanneSours = cytanneSours.replace("Наум", "Нвм");
            }
            if (cytanneSours.contains("Аввакум")) {
                cytanneSours = cytanneSours.replace("Аввакум", "Абк");
            }
            if (cytanneSours.contains("Сафония")) {
                cytanneSours = cytanneSours.replace("Сафония", "Саф");
            }
            if (cytanneSours.contains("Аггей")) {
                cytanneSours = cytanneSours.replace("Аггей", "Аг");
            }
            if (cytanneSours.contains("Мф")) {
                cytanneSours = cytanneSours.replace("Мф", "Мц");
            }
            if (cytanneSours.contains("Лк")) {
                cytanneSours = cytanneSours.replace("Лк", "Лук");
            }
            if (cytanneSours.contains("Ин")) {
                cytanneSours = cytanneSours.replace("Ин", "Ян");
            }
            if (cytanneSours.contains("Деян")) {
                cytanneSours = cytanneSours.replace("Деян", "Дз");
            }
            if (cytanneSours.contains("Иак")) {
                cytanneSours = cytanneSours.replace("Иак", "Як");
            }
            if (cytanneSours.contains("1 Пет")) {
                cytanneSours = cytanneSours.replace("1 Пет", "1 Пт");
            }
            if (cytanneSours.contains("2 Пет")) {
                cytanneSours = cytanneSours.replace("2 Пет", "2 Пт");
            }
            if (cytanneSours.contains("1 Ин")) {
                cytanneSours = cytanneSours.replace("1 Ин", "1 Ян");
            }
            if (cytanneSours.contains("2 Ин")) {
                cytanneSours = cytanneSours.replace("2 Ин", "2 Ян");
            }
            if (cytanneSours.contains("3 Ин")) {
                cytanneSours = cytanneSours.replace("3 Ин", "3 Ян");
            }
            if (cytanneSours.contains("Иуд")) {
                cytanneSours = cytanneSours.replace("Иуд", "Юд");
            }
            if (cytanneSours.contains("Рим")) {
                cytanneSours = cytanneSours.replace("Рим", "Рым");
            }
            if (cytanneSours.contains("1 Кор")) {
                cytanneSours = cytanneSours.replace("1 Кор", "1 Кар");
            }
            if (cytanneSours.contains("2 Кор")) {
                cytanneSours = cytanneSours.replace("2 Кор", "2 Кар");
            }
            if (cytanneSours.contains("Еф")) {
                cytanneSours = cytanneSours.replace("Еф", "Эф");
            }
            if (cytanneSours.contains("Флп")) {
                cytanneSours = cytanneSours.replace("Флп", "Плп");
            }
            if (cytanneSours.contains("Кол")) {
                cytanneSours = cytanneSours.replace("Кол", "Клс");
            }
            if (cytanneSours.contains("1 Тим")) {
                cytanneSours = cytanneSours.replace("1 Тим", "1 Цім");
            }
            if (cytanneSours.contains("2 Тим")) {
                cytanneSours = cytanneSours.replace("2 Тим", "2 Цім");
            }
            if (cytanneSours.contains("Тит")) {
                cytanneSours = cytanneSours.replace("Тит", "Ціт");
            }
            if (cytanneSours.contains("Евр")) {
                cytanneSours = cytanneSours.replace("Евр", "Гбр");
            }
            if (cytanneSours.contains("Откр")) {
                cytanneSours = cytanneSours.replace("Откр", "Адкр");
            }
        }

        String[] chten = cytanneParalelnye.split(";");

        textViewSours = new TextView_Roboto_Condensed(context);
        textViewSours.setTextIsSelectable(true);
        textViewSours.setTypeface(null, Typeface.BOLD_ITALIC);
        textViewSours.setPadding(0, 0, 0, 10);
        SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        float fontBiblia = k.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            textViewSours.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
        } else {
            textViewSours.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
        }
        textViewSours.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        textViewSours.setText(context.getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_smoll, cytanneSours));
        arrayList.add(textViewSours);

        for (String aChten : chten) {
            int nomerglavy = 1;
            String fit = aChten.trim(), nachalo, konec;
            String[] bible = biblia(fit);
            kniga = bible[0];
            nazva = bible[1];
            nazvaBel = bible[2];
            nomer = Integer.parseInt(bible[3]);
            // Пс 88:12-13; 135:5; 145:6; Сир 18:1; Ин 1:3; Пс 22
            // Быт 13:15; 15:7, 18, 15-16; 26:3-4; Втор 34:4; 1 Тим 2:13
            String[] split = fit.split(",");
            for (String aSplit : split) {
                String splitres = aSplit.trim();
                int s2 = splitres.indexOf(" ", 2);
                int a1 = splitres.indexOf(".");
                if (s2 != -1) {
                    if (a1 != -1) {
                        nomerglavy = Integer.parseInt(splitres.substring(s2 + 1, a1));
                        int a2 = splitres.indexOf("-");
                        if (a2 != -1) {
                            nachalo = splitres.substring(a1 + 1, a2);
                            konec = splitres.substring(a2 + 1);
                        } else {
                            nachalo = splitres.substring(a1 + 1);
                            konec = nachalo;
                        }
                    } else {
                        nomerglavy = Integer.parseInt(splitres.substring(s2 + 1));
                        nachalo = "1";
                        konec = "+-+";
                    }
                } else {
                    int a2 = splitres.indexOf("-");
                    if (a1 != -1) {
                        nomerglavy = Integer.parseInt(splitres.substring(0, a1));
                        if (a2 != -1) {
                            nachalo = splitres.substring(a1 + 1, a2);
                            konec = splitres.substring(a2 + 1);
                        } else {
                            nachalo = splitres.substring(a1 + 1);
                            konec = nachalo;
                        }
                    } else {
                        if (a2 != -1) {
                            nachalo = splitres.substring(0, a2);
                            konec = splitres.substring(a2 + 1);
                        } else {
                            nachalo = splitres;
                            konec = nachalo;
                        }
                    }
                }
                try {
                    Resources r = context.getResources();
                    if (semuxa) {
                        switch (nomer) {
                            case 1:
                                inputStream = r.openRawResource(R.raw.biblias1);
                                break;
                            case 2:
                                inputStream = r.openRawResource(R.raw.biblias2);
                                break;
                            case 3:
                                inputStream = r.openRawResource(R.raw.biblias3);
                                break;
                            case 4:
                                inputStream = r.openRawResource(R.raw.biblias4);
                                break;
                            case 5:
                                inputStream = r.openRawResource(R.raw.biblias5);
                                break;
                            case 6:
                                inputStream = r.openRawResource(R.raw.biblias6);
                                break;
                            case 7:
                                inputStream = r.openRawResource(R.raw.biblias7);
                                break;
                            case 8:
                                inputStream = r.openRawResource(R.raw.biblias8);
                                break;
                            case 9:
                                inputStream = r.openRawResource(R.raw.biblias9);
                                break;
                            case 10:
                                inputStream = r.openRawResource(R.raw.biblias10);
                                break;
                            case 11:
                                inputStream = r.openRawResource(R.raw.biblias11);
                                break;
                            case 12:
                                inputStream = r.openRawResource(R.raw.biblias12);
                                break;
                            case 13:
                                inputStream = r.openRawResource(R.raw.biblias13);
                                break;
                            case 14:
                                inputStream = r.openRawResource(R.raw.biblias14);
                                break;
                            case 15:
                                inputStream = r.openRawResource(R.raw.biblias15);
                                break;
                            case 16:
                                inputStream = r.openRawResource(R.raw.biblias16);
                                break;
                            case 20:
                                inputStream = r.openRawResource(R.raw.biblias17);
                                break;
                            case 21:
                                inputStream = r.openRawResource(R.raw.biblias18);
                                break;
                            case 22:
                                inputStream = r.openRawResource(R.raw.biblias19);
                                break;
                            case 23:
                                inputStream = r.openRawResource(R.raw.biblias20);
                                break;
                            case 24:
                                inputStream = r.openRawResource(R.raw.biblias21);
                                break;
                            case 25:
                                inputStream = r.openRawResource(R.raw.biblias22);
                                break;
                            case 28:
                                inputStream = r.openRawResource(R.raw.biblias23);
                                break;
                            case 29:
                                inputStream = r.openRawResource(R.raw.biblias24);
                                break;
                            case 30:
                                inputStream = r.openRawResource(R.raw.biblias25);
                                break;
                            case 33:
                                inputStream = r.openRawResource(R.raw.biblias26);
                                break;
                            case 34:
                                inputStream = r.openRawResource(R.raw.biblias27);
                                break;
                            case 35:
                                inputStream = r.openRawResource(R.raw.biblias28);
                                break;
                            case 36:
                                inputStream = r.openRawResource(R.raw.biblias29);
                                break;
                            case 37:
                                inputStream = r.openRawResource(R.raw.biblias30);
                                break;
                            case 38:
                                inputStream = r.openRawResource(R.raw.biblias31);
                                break;
                            case 39:
                                inputStream = r.openRawResource(R.raw.biblias32);
                                break;
                            case 40:
                                inputStream = r.openRawResource(R.raw.biblias33);
                                break;
                            case 41:
                                inputStream = r.openRawResource(R.raw.biblias34);
                                break;
                            case 42:
                                inputStream = r.openRawResource(R.raw.biblias35);
                                break;
                            case 43:
                                inputStream = r.openRawResource(R.raw.biblias36);
                                break;
                            case 44:
                                inputStream = r.openRawResource(R.raw.biblias37);
                                break;
                            case 45:
                                inputStream = r.openRawResource(R.raw.biblias38);
                                break;
                            case 46:
                                inputStream = r.openRawResource(R.raw.biblias39);
                                break;
                            case 51:
                                inputStream = r.openRawResource(R.raw.biblian1);
                                break;
                            case 52:
                                inputStream = r.openRawResource(R.raw.biblian2);
                                break;
                            case 53:
                                inputStream = r.openRawResource(R.raw.biblian3);
                                break;
                            case 54:
                                inputStream = r.openRawResource(R.raw.biblian4);
                                break;
                            case 55:
                                inputStream = r.openRawResource(R.raw.biblian5);
                                break;
                            case 56:
                                inputStream = r.openRawResource(R.raw.biblian6);
                                break;
                            case 57:
                                inputStream = r.openRawResource(R.raw.biblian7);
                                break;
                            case 58:
                                inputStream = r.openRawResource(R.raw.biblian8);
                                break;
                            case 59:
                                inputStream = r.openRawResource(R.raw.biblian9);
                                break;
                            case 60:
                                inputStream = r.openRawResource(R.raw.biblian10);
                                break;
                            case 61:
                                inputStream = r.openRawResource(R.raw.biblian11);
                                break;
                            case 62:
                                inputStream = r.openRawResource(R.raw.biblian12);
                                break;
                            case 63:
                                inputStream = r.openRawResource(R.raw.biblian13);
                                break;
                            case 64:
                                inputStream = r.openRawResource(R.raw.biblian14);
                                break;
                            case 65:
                                inputStream = r.openRawResource(R.raw.biblian15);
                                break;
                            case 66:
                                inputStream = r.openRawResource(R.raw.biblian16);
                                break;
                            case 67:
                                inputStream = r.openRawResource(R.raw.biblian17);
                                break;
                            case 68:
                                inputStream = r.openRawResource(R.raw.biblian18);
                                break;
                            case 69:
                                inputStream = r.openRawResource(R.raw.biblian19);
                                break;
                            case 70:
                                inputStream = r.openRawResource(R.raw.biblian20);
                                break;
                            case 71:
                                inputStream = r.openRawResource(R.raw.biblian21);
                                break;
                            case 72:
                                inputStream = r.openRawResource(R.raw.biblian22);
                                break;
                            case 73:
                                inputStream = r.openRawResource(R.raw.biblian23);
                                break;
                            case 74:
                                inputStream = r.openRawResource(R.raw.biblian24);
                                break;
                            case 75:
                                inputStream = r.openRawResource(R.raw.biblian25);
                                break;
                            case 76:
                                inputStream = r.openRawResource(R.raw.biblian26);
                                break;
                            case 77:
                                inputStream = r.openRawResource(R.raw.biblian27);
                                break;
                            default:
                                inputStream = null;
                                break;
                        }
                    } else {
                        if (nomer == 1) inputStream = r.openRawResource(R.raw.sinaidals1);
                        if (nomer == 2) inputStream = r.openRawResource(R.raw.sinaidals2);
                        if (nomer == 3) inputStream = r.openRawResource(R.raw.sinaidals3);
                        if (nomer == 4) inputStream = r.openRawResource(R.raw.sinaidals4);
                        if (nomer == 5) inputStream = r.openRawResource(R.raw.sinaidals5);
                        if (nomer == 6) inputStream = r.openRawResource(R.raw.sinaidals6);
                        if (nomer == 7) inputStream = r.openRawResource(R.raw.sinaidals7);
                        if (nomer == 8) inputStream = r.openRawResource(R.raw.sinaidals8);
                        if (nomer == 9) inputStream = r.openRawResource(R.raw.sinaidals9);
                        if (nomer == 10) inputStream = r.openRawResource(R.raw.sinaidals10);
                        if (nomer == 11) inputStream = r.openRawResource(R.raw.sinaidals11);
                        if (nomer == 12) inputStream = r.openRawResource(R.raw.sinaidals12);
                        if (nomer == 13) inputStream = r.openRawResource(R.raw.sinaidals13);
                        if (nomer == 14) inputStream = r.openRawResource(R.raw.sinaidals14);
                        if (nomer == 15) inputStream = r.openRawResource(R.raw.sinaidals15);
                        if (nomer == 16) inputStream = r.openRawResource(R.raw.sinaidals16);
                        if (nomer == 17) inputStream = r.openRawResource(R.raw.sinaidals17);
                        if (nomer == 18) inputStream = r.openRawResource(R.raw.sinaidals18);
                        if (nomer == 19) inputStream = r.openRawResource(R.raw.sinaidals19);
                        if (nomer == 20) inputStream = r.openRawResource(R.raw.sinaidals20);
                        if (nomer == 21) inputStream = r.openRawResource(R.raw.sinaidals21);
                        if (nomer == 22) inputStream = r.openRawResource(R.raw.sinaidals22);
                        if (nomer == 23) inputStream = r.openRawResource(R.raw.sinaidals23);
                        if (nomer == 24) inputStream = r.openRawResource(R.raw.sinaidals24);
                        if (nomer == 25) inputStream = r.openRawResource(R.raw.sinaidals25);
                        if (nomer == 26) inputStream = r.openRawResource(R.raw.sinaidals26);
                        if (nomer == 27) inputStream = r.openRawResource(R.raw.sinaidals27);
                        if (nomer == 28) inputStream = r.openRawResource(R.raw.sinaidals28);
                        if (nomer == 29) inputStream = r.openRawResource(R.raw.sinaidals29);
                        if (nomer == 30) inputStream = r.openRawResource(R.raw.sinaidals30);
                        if (nomer == 31) inputStream = r.openRawResource(R.raw.sinaidals31);
                        if (nomer == 32) inputStream = r.openRawResource(R.raw.sinaidals32);
                        if (nomer == 33) inputStream = r.openRawResource(R.raw.sinaidals33);
                        if (nomer == 34) inputStream = r.openRawResource(R.raw.sinaidals34);
                        if (nomer == 35) inputStream = r.openRawResource(R.raw.sinaidals35);
                        if (nomer == 36) inputStream = r.openRawResource(R.raw.sinaidals36);
                        if (nomer == 37) inputStream = r.openRawResource(R.raw.sinaidals37);
                        if (nomer == 38) inputStream = r.openRawResource(R.raw.sinaidals38);
                        if (nomer == 39) inputStream = r.openRawResource(R.raw.sinaidals39);
                        if (nomer == 40) inputStream = r.openRawResource(R.raw.sinaidals40);
                        if (nomer == 41) inputStream = r.openRawResource(R.raw.sinaidals41);
                        if (nomer == 42) inputStream = r.openRawResource(R.raw.sinaidals42);
                        if (nomer == 43) inputStream = r.openRawResource(R.raw.sinaidals43);
                        if (nomer == 44) inputStream = r.openRawResource(R.raw.sinaidals44);
                        if (nomer == 45) inputStream = r.openRawResource(R.raw.sinaidals45);
                        if (nomer == 46) inputStream = r.openRawResource(R.raw.sinaidals46);
                        if (nomer == 47) inputStream = r.openRawResource(R.raw.sinaidals47);
                        if (nomer == 48) inputStream = r.openRawResource(R.raw.sinaidals48);
                        if (nomer == 49) inputStream = r.openRawResource(R.raw.sinaidals49);
                        if (nomer == 50) inputStream = r.openRawResource(R.raw.sinaidals50);
                        if (nomer == 51) inputStream = r.openRawResource(R.raw.sinaidaln1);
                        if (nomer == 52) inputStream = r.openRawResource(R.raw.sinaidaln2);
                        if (nomer == 53) inputStream = r.openRawResource(R.raw.sinaidaln3);
                        if (nomer == 54) inputStream = r.openRawResource(R.raw.sinaidaln4);
                        if (nomer == 55) inputStream = r.openRawResource(R.raw.sinaidaln5);
                        if (nomer == 56) inputStream = r.openRawResource(R.raw.sinaidaln6);
                        if (nomer == 57) inputStream = r.openRawResource(R.raw.sinaidaln7);
                        if (nomer == 58) inputStream = r.openRawResource(R.raw.sinaidaln8);
                        if (nomer == 59) inputStream = r.openRawResource(R.raw.sinaidaln9);
                        if (nomer == 60) inputStream = r.openRawResource(R.raw.sinaidaln10);
                        if (nomer == 61) inputStream = r.openRawResource(R.raw.sinaidaln11);
                        if (nomer == 62) inputStream = r.openRawResource(R.raw.sinaidaln12);
                        if (nomer == 63) inputStream = r.openRawResource(R.raw.sinaidaln13);
                        if (nomer == 64) inputStream = r.openRawResource(R.raw.sinaidaln14);
                        if (nomer == 65) inputStream = r.openRawResource(R.raw.sinaidaln15);
                        if (nomer == 66) inputStream = r.openRawResource(R.raw.sinaidaln16);
                        if (nomer == 67) inputStream = r.openRawResource(R.raw.sinaidaln17);
                        if (nomer == 68) inputStream = r.openRawResource(R.raw.sinaidaln18);
                        if (nomer == 69) inputStream = r.openRawResource(R.raw.sinaidaln19);
                        if (nomer == 70) inputStream = r.openRawResource(R.raw.sinaidaln20);
                        if (nomer == 71) inputStream = r.openRawResource(R.raw.sinaidaln21);
                        if (nomer == 72) inputStream = r.openRawResource(R.raw.sinaidaln22);
                        if (nomer == 73) inputStream = r.openRawResource(R.raw.sinaidaln23);
                        if (nomer == 74) inputStream = r.openRawResource(R.raw.sinaidaln24);
                        if (nomer == 75) inputStream = r.openRawResource(R.raw.sinaidaln25);
                        if (nomer == 76) inputStream = r.openRawResource(R.raw.sinaidaln26);
                        if (nomer == 77) inputStream = r.openRawResource(R.raw.sinaidaln27);
                    }
                    if (inputStream != null) {
                        InputStreamReader isr = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(isr);
                        String line;
                        StringBuilder builder = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("//")) {
                                int t1 = line.indexOf("//");
                                line = line.substring(0, t1).trim();
                                if (!line.equals(""))
                                    builder.append(line).append("\n");
                                continue;
                            }
                            builder.append(line).append("\n");
                        }
                        inputStream.close();
                        String[] split2 = builder.toString().split("===");
                        String r1 = split2[nomerglavy].trim(), r2;
                        int vN = r1.indexOf(nachalo);
                        int vK1 = r1.indexOf(konec);
                        int vK = r1.indexOf("\n", vK1);
                        if (semuxa && nomer == 22) {
                            r1 = r1.replace("\n", "<br>\n");
                            String[] r3 = r1.split("\n");
                            StringBuilder sb = new StringBuilder();
                            for (int w = Integer.parseInt(nachalo); w <= Integer.parseInt(konec); w++) {
                                sb.append(r3[w - 1]);
                            }
                            r2 = sb.toString();
                        } else {
                            if (vK1 != -1) {
                                if (vK != -1) {
                                    r2 = r1.substring(vN, vK);
                                } else {
                                    r2 = r1.substring(vN);
                                }
                            } else {
                                r2 = r1;
                            }
                        }
                        textViewZag = new TextView_Roboto_Condensed(context);
                        textViewOpis = new TextView_Roboto_Condensed(context);
                        textViewZag.setTextIsSelectable(true);
                        textViewOpis.setTextIsSelectable(true);
                        dzenNoch = k.getBoolean("dzen_noch", false);
                        if (dzenNoch) {
                            textViewZag.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
                            textViewOpis.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
                        } else {
                            textViewZag.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                            textViewOpis.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                        }
                        textViewZag.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        textViewZag.setTypeface(null, Typeface.BOLD);
                        String kon;
                        if (semuxa) nazva = nazvaBel;
                        if (nachalo.equals(konec)) {
                            kon = nazva + " " + nomerglavy + "." + nachalo;
                        } else if (konec.contains("+-+")) {
                            kon = nazva + " " + nomerglavy;
                        } else {
                            kon = nazva + " " + nomerglavy + "." + nachalo + "-" + konec;
                        }
                        textViewZag.setText(kon);
                        arrayList.add(textViewZag);
                        textViewOpis.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        if (semuxa && nomer == 22) {
                            //CaseInsensitiveResourcesFontLoader fontLoader = new CaseInsensitiveResourcesFontLoader();
                            textViewOpis.setText(MainActivity.fromHtml(context.getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_opis, r2)));
                        } else
                            textViewOpis.setText(context.getResources().getString(by.carkva_gazeta.malitounik.R.string.paralel_opis, r2));
                    } else {
                        textViewZag = new TextView_Roboto_Condensed(context);
                        textViewOpis = new TextView_Roboto_Condensed(context);
                        textViewZag.setTextIsSelectable(true);
                        textViewOpis.setTextIsSelectable(true);
                        dzenNoch = k.getBoolean("dzen_noch", false);
                        if (dzenNoch) {
                            textViewZag.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
                            textViewOpis.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
                        } else {
                            textViewZag.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                            textViewOpis.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text));
                        }
                        textViewOpis.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        textViewZag.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
                        textViewZag.setTypeface(null, Typeface.BOLD);
                        String kon;
                        if (semuxa) nazva = nazvaBel;
                        if (nachalo.equals(konec)) {
                            kon = nazva + " " + nomerglavy + "." + nachalo;
                        } else if (konec.contains("+-+")) {
                            kon = nazva + " " + nomerglavy;
                        } else {
                            kon = nazva + " " + nomerglavy + "." + nachalo + "-" + konec;
                        }
                        textViewZag.setText(kon);
                        arrayList.add(textViewZag);
                        textViewOpis.setText(context.getResources().getString(by.carkva_gazeta.malitounik.R.string.semuxa_maran_ata_error) + "\n");
                    }
                    arrayList.add(textViewOpis);
                } catch (Throwable ignored) {
                }
            }
        }
        return arrayList;
    }

    @SuppressWarnings("ConstantConditions")
    static String[] biblia(@NonNull String kniga) {
        String[] bible = {Paralelnye_mesta.kniga, nazva, nazvaBel, String.valueOf(nomer)};
        if (kniga.contains("Быт") || kniga.contains("Быц")) {
            bible[0] = "Быт"; // Сокращение по русски
            bible[1] = "Бытие"; // Название по русски
            bible[2] = "Быцьцё"; // Название по Белорусски
            bible[3] = "1";// Номер книги
        }
        if (kniga.contains("Исх") || kniga.contains("Вых")) {
            bible[0] = "Исх";
            bible[1] = "Исход";
            bible[2] = "Выхад";
            bible[3] = "2";
        }
        if (kniga.contains("Лев") || kniga.contains("Ляв")) {
            bible[0] = "Лев";
            bible[1] = "Левит";
            bible[2] = "Лявіт";
            bible[3] = "3";
        }
        if (kniga.contains("Чис") || kniga.contains("Лікі")) {
            bible[0] = "Числа";
            bible[1] = "Числа";
            bible[2] = "Лікі";
            bible[3] = "4";
        }
        if (kniga.contains("Втор") || kniga.contains("Дрг")) {
            bible[0] = "Втор";
            bible[1] = "Второзаконие";
            bible[2] = "Другі Закон";
            bible[3] = "5";
        }
        if (kniga.contains("Нав")) {
            bible[0] = "Нав";
            bible[1] = "Иисуса Навина";
            bible[2] = "Ісуса сына Нава";
            bible[3] = "6";
        }
        if (kniga.contains("Суд")) {
            bible[0] = "Суд";
            bible[1] = "Судей израилевых";
            bible[2] = "Судзьдзяў";
            bible[3] = "7";
        }
        if (kniga.contains("Руфь") || kniga.contains("Рут")) {
            bible[0] = "Руфь";
            bible[1] = "Руфи";
            bible[2] = "Рут";
            bible[3] = "8";
        }
        if (kniga.contains("1 Цар")) {
            bible[0] = "1 Цар";
            bible[1] = "1-я Царств";
            bible[2] = "1-я Царстваў";
            bible[3] = "9";
        }
        if (kniga.contains("2 Цар")) {
            bible[0] = "2 Цар";
            bible[1] = "2-я Царств";
            bible[2] = "2-я Царстваў";
            bible[3] = "10";
        }
        if (kniga.contains("3 Цар")) {
            bible[0] = "3 Цар";
            bible[1] = "3-я Царств";
            bible[2] = "3-я Царстваў";
            bible[3] = "11";
        }
        if (kniga.contains("4 Цар")) {
            bible[0] = "4 Цар";
            bible[1] = "4-я Царств";
            bible[2] = "4-я Царстваў";
            bible[3] = "12";
        }
        if (kniga.contains("1 Пар") || kniga.contains("1 Лет")) {
            bible[0] = "1 Пар";
            bible[1] = "1-я Паралипоменон";
            bible[2] = "1-я Летапісаў";
            bible[3] = "13";
        }
        if (kniga.contains("2 Пар") || kniga.contains("2 Лет")) {
            bible[0] = "2 Пар";
            bible[1] = "2-я Паралипоменон";
            bible[2] = "2-я Летапісаў";
            bible[3] = "14";
        }
        if (kniga.contains("1 Езд") || kniga.contains("1 Эзд")) {
            bible[0] = "1 Езд";
            bible[1] = "1-я Ездры";
            bible[2] = "1-я Эздры";
            bible[3] = "15";
        }
        if (kniga.contains("Неем") || kniga.contains("Нээм")) {
            bible[0] = "Неем";
            bible[1] = "Неемии";
            bible[2] = "Нээміі";
            bible[3] = "16";
        }
        if (kniga.contains("2 Езд") || kniga.contains("2 Эзд")) {
            bible[0] = "2 Езд";
            bible[1] = "2-я Ездры";
            bible[2] = "2-я Эздры";
            bible[3] = "17";
        }
        if (kniga.contains("Тов") || kniga.contains("Тав")) {
            bible[0] = "Тов";
            bible[1] = "Товита";
            bible[2] = "Тавіта";
            bible[3] = "18";
        }
        if (kniga.contains("Иудифь") || kniga.contains("Юдт")) {
            bible[0] = "Иудифь";
            bible[1] = "Иудифи";
            bible[2] = "Юдыты";
            bible[3] = "19";
        }
        if (kniga.contains("Есф") || kniga.contains("Эст")) {
            bible[0] = "Есф";
            bible[1] = "Есфири";
            bible[2] = "Эстэр";
            bible[3] = "20";
        }
        if (kniga.contains("Иов") || kniga.contains("Ёва")) {
            bible[0] = "Иов";
            bible[1] = "Иова";
            bible[2] = "Ёва";
            bible[3] = "21";
        }
        if (kniga.contains("Пс")) {
            bible[0] = "Пс";
            bible[1] = "Псалтирь";
            bible[2] = "Псалтыр";
            bible[3] = "22";
        }
        if (kniga.contains("Притч") || kniga.contains("Высл")) {
            bible[0] = "Притч";
            bible[1] = "Притчи Соломона";
            bible[2] = "Выслоўяў Саламонавых";
            bible[3] = "23";
        }
        if (kniga.contains("Еккл") || kniga.contains("Экл")) {
            bible[0] = "Еккл";
            bible[1] = "Екклезиаста";
            bible[2] = "Эклезіяста";
            bible[3] = "24";
        }
        if (kniga.contains("Песн") || kniga.contains("Псн")) {
            bible[0] = "Песн";
            bible[1] = "Песнь песней Соломона";
            bible[2] = "Найвышэйшая Песьня Саламонава";
            bible[3] = "25";
        }
        if (kniga.contains("Прем") || kniga.contains("Мдр")) {
            bible[0] = "Прем";
            bible[1] = "Премудрости Соломона";
            bible[2] = "Мудрасьці Саламона";
            bible[3] = "26";
        }
        if (kniga.contains("Сир") || kniga.contains("Сір")) {
            bible[0] = "Сир";
            bible[1] = "Премудрости Иисуса, сына Сирахова";
            bible[2] = "Мудрасьці Ісуса, сына Сірахава";
            bible[3] = "27";
        }
        if ((kniga.contains("Ис") && !kniga.contains("Исх")) || kniga.contains("Іс")) {
            bible[0] = "Ис";
            bible[1] = "Исаии";
            bible[2] = "Ісаі";
            bible[3] = "28";
        }
        if (kniga.contains("Иер") || kniga.contains("Ер")) {
            bible[0] = "Иер";
            bible[1] = "Иеремии";
            bible[2] = "Ераміі";
            bible[3] = "29";
        }
        if (kniga.contains("Плач")) {
            bible[0] = "Плач Иер";
            bible[1] = "Плач Иеремии";
            bible[2] = "Ераміін Плач";
            bible[3] = "30";
        }
        if (kniga.contains("Посл Иер") || kniga.contains("Пасл Ер")) {
            bible[0] = "Посл Иеремии";
            bible[1] = "Послание Иеремии";
            bible[2] = "Пасланьне Ераміі";
            bible[3] = "31";
        }
        if (kniga.contains("Вар") || kniga.contains("Бар")) {
            bible[0] = "Вар";
            bible[1] = "Варуха";
            bible[2] = "Баруха";
            bible[3] = "32";
        }
        if (kniga.contains("Иез") || kniga.contains("Езк")) {
            bible[0] = "Иез";
            bible[1] = "Иезекииля";
            bible[2] = "Езэкііля";
            bible[3] = "33";
        }
        if (kniga.contains("Дан")) {
            bible[0] = "Дан";
            bible[1] = "Даниила";
            bible[2] = "Данііла";
            bible[3] = "34";
        }
        if (kniga.contains("Ос") || kniga.contains("Ас")) {
            bible[0] = "Ос";
            bible[1] = "Осии";
            bible[2] = "Асіі";
            bible[3] = "35";
        }
        if (kniga.contains("Иоил") || kniga.contains("Ёіл")) {
            bible[0] = "Иоиль";
            bible[1] = "Иоиля";
            bible[2] = "Ёіля";
            bible[3] = "36";
        }
        if (kniga.contains("Ам")) {
            bible[0] = "Ам";
            bible[1] = "Амоса";
            bible[2] = "Амоса";
            bible[3] = "37";
        }
        if (kniga.contains("Авд") || kniga.contains("Аўдз")) {
            bible[0] = "Авдий";
            bible[1] = "Авдия";
            bible[2] = "Аўдзея";
            bible[3] = "38";
        }
        if (kniga.contains("Иона") || kniga.contains("Ёны")) {
            bible[0] = "Иона";
            bible[1] = "Ионы";
            bible[2] = "Ёны";
            bible[3] = "39";
        }
        if (kniga.contains("Мих") || kniga.contains("Міх")) {
            bible[0] = "Мих";
            bible[1] = "Михея";
            bible[2] = "Міхея";
            bible[3] = "40";
        }
        if (kniga.contains("Наум") || kniga.contains("Нвм")) {
            bible[0] = "Наум";
            bible[1] = "Наума";
            bible[2] = "Навума";
            bible[3] = "41";
        }
        if (kniga.contains("Авв") || kniga.contains("Абк")) {
            bible[0] = "Аввакум";
            bible[1] = "Аввакума";
            bible[2] = "Абакума";
            bible[3] = "42";
        }
        if (kniga.contains("Соф") || kniga.contains("Саф")) {
            bible[0] = "Сафония";
            bible[1] = "Софонии";
            bible[2] = "Сафона";
            bible[3] = "43";
        }
        if (kniga.contains("Агг") || kniga.contains("Аг")) {
            bible[0] = "Аггей";
            bible[1] = "Аггея";
            bible[2] = "Агея";
            bible[3] = "44";
        }
        if (kniga.contains("Зах")) {
            bible[0] = "Зах";
            bible[1] = "Захарии";
            bible[2] = "Захарыі";
            bible[3] = "45";
        }
        if (kniga.contains("Мал")) {
            bible[0] = "Мал";
            bible[1] = "Малахии";
            bible[2] = "Малахіі";
            bible[3] = "46";
        }
        if (kniga.contains("1 Мак")) {
            bible[0] = "1 Мак";
            bible[1] = "1-я Маккавейская";
            bible[2] = "1-я Макабэяў";
            bible[3] = "47";
        }
        if (kniga.contains("2 Мак")) {
            bible[0] = "2 Мак";
            bible[1] = "2-я Маккавейская";
            bible[2] = "2-я Макабэяў";
            bible[3] = "48";
        }
        if (kniga.contains("3 Мак")) {
            bible[0] = "3 Мак";
            bible[1] = "3-я Маккавейская";
            bible[2] = "3-я Макабэяў";
            bible[3] = "49";
        }
        if (kniga.contains("3 Езд") || kniga.contains("3 Эзд")) {
            bible[1] = "3-я Ездры";
            bible[2] = "3-я Эздры";
            bible[3] = "50";
        }
        if (kniga.contains("Мф") || kniga.contains("Мц")) {
            bible[0] = "Мф";
            bible[1] = "От Матфея";
            bible[2] = "Паводле Мацьвея";
            bible[3] = "51";
        }
        if (kniga.contains("Мк")) {
            bible[0] = "Мк";
            bible[1] = "От Марка";
            bible[2] = "Паводле Марка";
            bible[3] = "52";
        }
        if (kniga.contains("Лк")) {
            bible[0] = "Лк";
            bible[1] = "От Луки";
            bible[2] = "Паводле Лукаша";
            bible[3] = "53";
        }
        if (kniga.contains("Ин") || kniga.contains("Ян")) {
            bible[0] = "Ин";
            bible[1] = "От Иоанна";
            bible[2] = "Паводле Яна";
            bible[3] = "54";
        }
        if (kniga.contains("Деян") || kniga.contains("Дз")) {
            bible[0] = "Деян";
            bible[1] = "Деяния святых апостолов";
            bible[2] = "Дзеі Апосталаў";
            bible[3] = "55";
        }
        if (kniga.contains("Иак") || kniga.contains("Як")) {
            bible[0] = "Иак";
            bible[1] = "Иакова";
            bible[2] = "Якава";
            bible[3] = "56";
        }
        if (kniga.contains("1 Пет") || kniga.contains("1 Пт")) {
            bible[0] = "1 Петр";
            bible[1] = "1-е Петра";
            bible[2] = "1-е Пятра";
            bible[3] = "57";
        }
        if (kniga.contains("2 Пет") || kniga.contains("2 Пт")) {
            bible[0] = "2 Петр";
            bible[1] = "2-е Петра";
            bible[2] = "2-е Пятра";
            bible[3] = "58";
        }
        if (kniga.contains("1 Ин") || kniga.contains("1 Ян")) {
            bible[0] = "1 Ин";
            bible[1] = "1-е Иоанна";
            bible[2] = "1-е Яна Багаслова";
            bible[3] = "59";
        }
        if (kniga.contains("2 Ин") || kniga.contains("2 Ян")) {
            bible[0] = "2 Ин";
            bible[1] = "2-е Иоанна";
            bible[2] = "2-е Яна Багаслова";
            bible[3] = "60";
        }
        if (kniga.contains("3 Ин") || kniga.contains("3 Ян")) {
            bible[0] = "3 Ин";
            bible[1] = "3-е Иоанна";
            bible[2] = "3-е Яна Багаслова";
            bible[3] = "61";
        }
        if (kniga.contains("Иуд") || (kniga.contains("Юд") && !kniga.contains("Юдт"))) {
            bible[0] = "Иуд";
            bible[1] = "Иуды";
            bible[2] = "Юды";
            bible[3] = "62";
        }
        if (kniga.contains("Рим") || kniga.contains("Рым")) {
            bible[0] = "Рим";
            bible[1] = "Римлянам";
            bible[2] = "Да Рымлянаў";
            bible[3] = "63";
        }
        if (kniga.contains("1 Кор") || kniga.contains("1 Кар")) {
            bible[0] = "1 Кор";
            bible[1] = "1-е Коринфянам";
            bible[2] = "1-е да Карынфянаў";
            bible[3] = "64";
        }
        if (kniga.contains("2 Кор") || kniga.contains("2 Кар")) {
            bible[0] = "2 Кор";
            bible[1] = "2-е Коринфянам";
            bible[2] = "2-е да Карынфянаў";
            bible[3] = "65";
        }
        if (kniga.contains("Гал")) {
            bible[0] = "Гал";
            bible[1] = "Галатам";
            bible[2] = "Да Галятаў";
            bible[3] = "66";
        }
        if (kniga.contains("Еф") || kniga.contains("Эф")) {
            bible[0] = "Еф";
            bible[1] = "Ефесянам";
            bible[2] = "Да Эфэсянаў";
            bible[3] = "67";
        }
        if (kniga.contains("Флп") || kniga.contains("Плп")) {
            bible[0] = "Флп";
            bible[1] = "Филиппийцам";
            bible[2] = "Да Піліпянаў";
            bible[3] = "68";
        }
        if (kniga.contains("Кол") || kniga.contains("Клс")) {
            bible[0] = "Кол";
            bible[1] = "Колоссянам";
            bible[2] = "Да Каласянаў";
            bible[3] = "69";
        }
        if (kniga.contains("1 Фес")) {
            bible[0] = "1 Фес";
            bible[1] = "1-е Фессалоникийцам (Солунянам)";
            bible[2] = "1-е да Фесаланікійцаў";
            bible[3] = "70";
        }
        if (kniga.contains("2 Фес")) {
            bible[0] = "2 Фес";
            bible[1] = "2-е Фессалоникийцам (Солунянам)";
            bible[2] = "2-е да Фесаланікійцаў";
            bible[3] = "71";
        }
        if (kniga.contains("1 Тим") || kniga.contains("1 Цім")) {
            bible[0] = "1 Тим";
            bible[1] = "1-е Тимофею";
            bible[2] = "1-е да Цімафея";
            bible[3] = "72";
        }
        if (kniga.contains("2 Тим") || kniga.contains("2 Цім")) {
            bible[0] = "2 Тим";
            bible[1] = "2-е Тимофею";
            bible[2] = "2-е да Цімафея";
            bible[3] = "73";
        }
        if (kniga.contains("Тит") || kniga.contains("Ціт")) {
            bible[0] = "Тит";
            bible[1] = "Титу";
            bible[2] = "Да Ціта";
            bible[3] = "74";
        }
        if (kniga.contains("Флм")) {
            bible[0] = "Флм";
            bible[1] = "Филимону";
            bible[2] = "Да Філімона";
            bible[3] = "75";
        }
        if (kniga.contains("Евр") || kniga.contains("Гбр")) {
            bible[0] = "Евр";
            bible[1] = "Евреям";
            bible[2] = "Да Габрэяў";
            bible[3] = "76";
        }
        if (kniga.contains("Откр") || kniga.contains("Адкр")) {
            bible[0] = "Откр";
            bible[1] = "Откровение (Апокалипсис)";
            bible[2] = "Адкрыцьцё (Апакаліпсіс)";
            bible[3] = "77";
        }
        return bible;
    }
}