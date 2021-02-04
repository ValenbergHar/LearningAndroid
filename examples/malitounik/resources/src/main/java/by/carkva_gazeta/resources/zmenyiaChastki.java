package by.carkva_gazeta.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;

/**
 * Created by oleg on 25.5.16
 */

class zmenyiaChastki {

    private final ArrayMap<String, Integer> data;
    private final ArrayList<ArrayList<String>> arrayData;
    private final Context context;

    zmenyiaChastki(Context context) {
        data = new ArrayMap<>();
        data.put("Евангельле паводле Мацьвея", R.raw.biblian1);
        data.put("Евангельле паводле Марка", R.raw.biblian2);
        data.put("Евангельле паводле Лукаша", R.raw.biblian3);
        data.put("Евангельле паводле Яна", R.raw.biblian4);
        data.put("Дзеі Апосталаў", R.raw.biblian5);
        data.put("Пасланьне Якуба", R.raw.biblian6);
        data.put("1-е пасланьне Пятра", R.raw.biblian7);
        data.put("2-е пасланьне Пятра", R.raw.biblian8);
        data.put("1-е пасланьне Яна Багаслова", R.raw.biblian9);
        data.put("2-е пасланьне Яна Багаслова", R.raw.biblian10);
        data.put("3-е пасланьне Яна Багаслова", R.raw.biblian11);
        data.put("Пасланьне Юды", R.raw.biblian12);
        data.put("Пасланьне да Рымлянаў", R.raw.biblian13);
        data.put("1-е пасланьне да Карынфянаў", R.raw.biblian14);
        data.put("2-е пасланьне да Карынфянаў", R.raw.biblian15);
        data.put("Пасланьне да Галятаў", R.raw.biblian16);
        data.put("Пасланьне да Эфэсянаў", R.raw.biblian17);
        data.put("Пасланьне да Філіпянаў", R.raw.biblian18);
        data.put("Пасланьне да Каласянаў", R.raw.biblian19);
        data.put("1-е пасланьне да Салунянаў", R.raw.biblian20);
        data.put("2-е пасланьне да Салунянаў", R.raw.biblian21);
        data.put("1-е пасланьне да Цімафея", R.raw.biblian22);
        data.put("2-е пасланьне да Цімафея", R.raw.biblian23);
        data.put("Пасланьне да Ціта", R.raw.biblian24);
        data.put("Пасланьне да Філімона", R.raw.biblian25);
        data.put("Пасланьне да Габрэяў", R.raw.biblian26);
        data.put("Кніга Быцьця", R.raw.biblias1);
        data.put("Кніга Выслоўяў Саламонавых", R.raw.biblias20);
        data.put("Кніга прарока Езэкііля", R.raw.biblias26);
        data.put("Кніга Выхаду", R.raw.biblias2);
        data.put("Кніга Ёва", R.raw.biblias18);
        data.put("Кніга прарока Захарыі", R.raw.biblias38);
        data.put("Кніга прарока Ёіля", R.raw.biblias29);
        data.put("Кніга прарока Сафона", R.raw.biblias36);
        data.put("Кніга прарока Ісаі", R.raw.biblias23);

        this.context = context;
        arrayData = getDate();
    }

    private int getmun() {
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        int position = (SettingsActivity.GET_CALIANDAR_YEAR_MAX - 1 - SettingsActivity.GET_CALIANDAR_YEAR_MIN) * 12 + g.get(Calendar.MONTH);
        int count = (SettingsActivity.GET_CALIANDAR_YEAR_MAX - SettingsActivity.GET_CALIANDAR_YEAR_MIN + 1) * 12;
        for (int i = 0; i < count; i++) {
            if (position == i) {
                return position;
            }
        }
        return position;
    }

    private ArrayList<ArrayList<String>> getDate() {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(MainActivity.caliandar(context, getmun()));
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            isr.close();
        } catch (IOException ignored) {
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
        }.getType();
        return new ArrayList<>(gson.fromJson(builder.toString(), type));
    }

    String sviatyia() {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        return arrayData.get(gc.get(Calendar.DATE) - 1).get(10);
    }

    String sviatyiaView(int apostal) {
        return chtenia(sviatyia(), apostal);
    }

    String zmenya(int apostal) {
        GregorianCalendar kal = (GregorianCalendar) Calendar.getInstance();
        String data = arrayData.get(kal.get(Calendar.DATE) - 1).get(9);
        if (data.contains("Прабачьце, няма дадзеных"))
            return "<em>Прабачьце, няма дадзеных</em>";
        else
            return chtenia(arrayData.get(kal.get(Calendar.DATE) - 1).get(9), apostal);
    }

    @NonNull
    private String chtenia(String w, int apostal) {
        StringBuilder res = new StringBuilder();
        w = MainActivity.removeZnakiAndSlovy(w);

        String[] split = w.split(";");
        //String[] split = {"Гал 1.1-10, 20-2.5"};
        int knigaN, knigaK = 0, zaglnum = 0;
        // Мц 1.1-10, 20-2.5, 10-20, 1.21-2.4, 11;
        int chtenie;
        if (apostal == 1) chtenie = 0;
        else chtenie = 1;
        if (split.length == 3)
            chtenie++;
        String[] zaglavie = split[chtenie].split(",");
        String zagl = "";
        String zaglavieName = "";
        for (int e = 0; e < zaglavie.length; e++) {
            String zaglav = zaglavie[e].trim();
            int zag = zaglav.indexOf(" ", 2);
            int zag1 = zaglav.indexOf(".");
            int zag2 = zaglav.indexOf("-");
            int zag3 = zaglav.indexOf(".", zag1 + 1);
            String zagS;
            if (zag2 != -1) {
                zagS = zaglav.substring(0, zag2);
            } else {
                zagS = zaglav;
            }
            boolean glav = false;
            if (zag1 > zag2 && zag == -1) {
                glav = true;
            } else if (zag != -1) {
                zagl = zaglav.substring(0, zag); // Название книги
                String zaglavieName1 = split[chtenie].trim();
                zaglavieName = " " + zaglavieName1.substring(zag + 1);
                zaglnum = Integer.parseInt(zaglav.substring(zag + 1, zag1)); // Номер главы
            } else if (zag1 != -1) {
                zaglnum = Integer.parseInt(zaglav.substring(0, zag1)); // Номер главы
            }
            if (glav) {
                int zagS1 = zagS.indexOf(".");
                if (zagS1 == -1) {
                    knigaN = Integer.parseInt(zagS); // Начало чтения
                } else {
                    zaglnum = Integer.parseInt(zagS.substring(0, zagS1)); // Номер главы
                    knigaN = Integer.parseInt(zagS.substring(zagS1 + 1)); // Начало чтения
                }
            } else if (zag2 == -1) {
                if (zag1 != -1) {
                    knigaN = Integer.parseInt(zaglav.substring(zag1 + 1)); // Начало чтения
                } else {
                    knigaN = Integer.parseInt(zaglav); // Начало чтения
                }
                knigaK = knigaN; // Конец чтения
            } else {
                knigaN = Integer.parseInt(zaglav.substring(zag1 + 1, zag2)); // Начало чтения
            }
            if (glav) {
                knigaK = Integer.parseInt(zaglav.substring(zag1 + 1)); // Конец чтения
            } else if (zag2 != -1) {
                if (zag3 == -1) {
                    knigaK = Integer.parseInt(zaglav.substring(zag2 + 1)); // Конец чтения
                } else {
                    knigaK = Integer.parseInt(zaglav.substring(zag3 + 1)); // Конец чтения
                }
            }

            int kniga = 0;
            //if (zagl.equals("Ціт")) kniga = 0;
            if (zagl.equals("Езк")) kniga = 1;
            if (zagl.equals("Гбр")) kniga = 2;
            if (zagl.equals("Гал")) kniga = 3;
            if (zagl.equals("Высл")) kniga = 4;
            if (zagl.equals("Плп")) kniga = 5;
            if (zagl.equals("Лк")) kniga = 6;
            if (zagl.equals("Мк")) kniga = 7;
            if (zagl.equals("Юд")) kniga = 8;
            if (zagl.equals("1 Ян")) kniga = 9;
            if (zagl.equals("Мц")) kniga = 10;
            if (zagl.equals("2 Пт")) kniga = 11;
            if (zagl.equals("Ёіл")) kniga = 12;
            if (zagl.equals("Іс")) kniga = 13;
            if (zagl.equals("2 Ян")) kniga = 14;
            if (zagl.equals("Ёў")) kniga = 15;
            if (zagl.equals("Саф")) kniga = 16;
            if (zagl.equals("1 Пт")) kniga = 17;
            if (zagl.equals("Піл")) kniga = 18;
            if (zagl.equals("1 Кар")) kniga = 19;
            if (zagl.equals("Быц")) kniga = 20;
            if (zagl.equals("Зах")) kniga = 21;
            if (zagl.equals("Дз")) kniga = 22;
            if (zagl.equals("Вых")) kniga = 23;
            if (zagl.equals("Эф")) kniga = 24;
            if (zagl.equals("Рым")) kniga = 25;
            if (zagl.equals("Клс")) kniga = 26;
            if (zagl.equals("Ян")) kniga = 27;
            if (zagl.equals("3 Ян")) kniga = 28;
            if (zagl.equals("1 Фес")) kniga = 29;
            if (zagl.equals("2 Фес")) kniga = 30;
            if (zagl.equals("2 Кар")) kniga = 31;
            if (zagl.equals("2 Цім")) kniga = 32;
            if (zagl.equals("Як")) kniga = 33;
            if (zagl.equals("1 Цім")) kniga = 34;

            try {
                Resources r = context.getResources();
                InputStream inputStream = r.openRawResource(data.valueAt(kniga));
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("")) {
                        if (line.contains("//")) {
                            int t1 = line.indexOf("//");
                            line = line.substring(0, t1).trim();
                            if (!line.equals(""))
                                builder.append(line).append("<br>\n");
                            continue;
                        }
                        builder.append(line).append("<br>\n");
                    }
                }
                inputStream.close();
                String[] split2 = builder.toString().split("===");
                String spl;
                int desK1, desN;
                spl = split2[zaglnum].trim();
                desN = spl.indexOf(knigaN + ".");

                if (e == 0) {
                    res.append("<strong>").append(data.keyAt(kniga)).append(zaglavieName).append("</strong><br>");
                } else {
                    res.append("[...]<br>");
                }
                if (knigaN == knigaK) {
                    desK1 = desN;
                } else {
                    desK1 = spl.indexOf(knigaK + ".");
                    if (zag3 != -1 || glav) {
                        String spl1 = split2[zaglnum].trim();
                        String spl2 = split2[zaglnum + 1].trim();
                        int des1 = spl1.length();
                        desN = spl1.indexOf(knigaN + ".");
                        desK1 = spl2.indexOf(knigaK + ".");
                        int desN1 = spl2.indexOf(knigaK + 1 + ".", desK1);
                        if (desN1 == -1) {
                            desN1 = spl1.length();
                        }
                        desK1 = desN1 + des1;
                        spl = spl1 + "\n" + spl2;
                        zaglnum = zaglnum + 1;
                    }
                }
                int desK = spl.indexOf("\n", desK1);
                if (desK == -1) res.append(spl.substring(desN));
                else res.append(spl.substring(desN, desK));
            } catch (Throwable ignored) {
            }
        }
        return res.toString();
    }

    String trapary_i_kandaki_niadzelnyia(int chast) {
        GregorianCalendar kal = (GregorianCalendar) Calendar.getInstance();
        if (!arrayData.get(kal.get(Calendar.DATE) - 1).get(20).equals("")) {
            StringBuilder builder = new StringBuilder();
            Resources r = context.getResources();
            SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            boolean dzenNoch = k.getBoolean("dzen_noch", false);
            InputStream inputStream = r.openRawResource(R.raw.bogashlugbovya9);
            try {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (dzenNoch)
                        line = line.replace("#d00505", "#f44336");
                    builder.append(line).append("\n");
                }
            } catch (Exception ignored) {
            }
            String w = arrayData.get(kal.get(Calendar.DATE) - 1).get(20);

            String result = "";

            if (w.contains("Тон 1")) {
                int sfn = builder.toString().indexOf("<!--ton1n-->");
                int sfk = builder.toString().indexOf("<!--ton1k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 2")) {
                int sfn = builder.toString().indexOf("<!--ton2n-->");
                int sfk = builder.toString().indexOf("<!--ton2k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 3")) {
                int sfn = builder.toString().indexOf("<!--ton3n-->");
                int sfk = builder.toString().indexOf("<!--ton3k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 4")) {
                int sfn = builder.toString().indexOf("<!--ton4n-->");
                int sfk = builder.toString().indexOf("<!--ton4k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 5")) {
                int sfn = builder.toString().indexOf("<!--ton5n-->");
                int sfk = builder.toString().indexOf("<!--ton5k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 6")) {
                int sfn = builder.toString().indexOf("<!--ton6n-->");
                int sfk = builder.toString().indexOf("<!--ton6k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 7")) {
                int sfn = builder.toString().indexOf("<!--ton7n-->");
                int sfk = builder.toString().indexOf("<!--ton7k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (w.contains("Тон 8")) {
                int sfn = builder.toString().indexOf("<!--ton8n-->");
                int sfk = builder.toString().indexOf("<!--ton8k-->");
                String res = builder.toString().substring(sfn, sfk);
                if (chast == 1) {
                    int tfn = res.indexOf("<!--traparn-->");
                    int tfk = res.indexOf("<!--trapark-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 2) {
                    int tfn = res.indexOf("<!--prakimenn-->");
                    int tfk = res.indexOf("<!--prakimenk-->");
                    result = res.substring(tfn, tfk);
                }
                if (chast == 3) {
                    int tfn = res.indexOf("<!--aliluian-->");
                    int tfk = res.indexOf("<!--aliluiak-->");
                    result = res.substring(tfn, tfk);
                }
            }
            if (chast == 4) {
                String res = builder.toString();
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
            return result;
        }
        return "";
    }

    String trapary_i_kandaki_na_kogny_dzen(int day_of_week, int chast) {
        StringBuilder builder = new StringBuilder();
        Resources r = context.getResources();
        InputStream inputStream = r.openRawResource(R.raw.bogashlugbovya10);
        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (Exception ignored) {
        }

        String result = "";
        if (day_of_week == 2) {
            int sfn = builder.toString().indexOf("<!--ton1n-->");
            int sfk = builder.toString().indexOf("<!--ton1k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        if (day_of_week == 3) {
            int sfn = builder.toString().indexOf("<!--ton2n-->");
            int sfk = builder.toString().indexOf("<!--ton2k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        if (day_of_week == 4) {
            int sfn = builder.toString().indexOf("<!--ton3n-->");
            int sfk = builder.toString().indexOf("<!--ton3k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        if (day_of_week == 5) {
            int sfn = builder.toString().indexOf("<!--ton4n-->");
            int sfk = builder.toString().indexOf("<!--ton4k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        if (day_of_week == 6) {
            int sfn = builder.toString().indexOf("<!--ton5n-->");
            int sfk = builder.toString().indexOf("<!--ton5k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        if (day_of_week == 7) {
            int sfn = builder.toString().indexOf("<!--ton6n-->");
            int sfk = builder.toString().indexOf("<!--ton6k-->");
            String res = builder.toString().substring(sfn, sfk);
            if (chast == 1) {
                int tfn = res.indexOf("<!--traparn-->");
                int tfk = res.indexOf("<!--trapark-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 2) {
                int tfn = res.indexOf("<!--prakimenn-->");
                int tfk = res.indexOf("<!--prakimenk-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 3) {
                int tfn = res.indexOf("<!--aliluian-->");
                int tfk = res.indexOf("<!--aliluiak-->");
                result = res.substring(tfn, tfk);
            }
            if (chast == 4) {
                int tfn = res.indexOf("<!--prichasnikn-->");
                int tfk = res.indexOf("<!--prichasnikk-->");
                result = res.substring(tfn, tfk);
            }
        }
        return result;
    }
}
