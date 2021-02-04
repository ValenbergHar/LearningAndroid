package by.carkva_gazeta.malitounik;

import androidx.annotation.NonNull;

import java.util.GregorianCalendar;

/**
 * Created by oleg on 10.5.17
 */

class Padzeia implements Comparable<Padzeia> {
    final String padz;
    final String dat;
    final String tim;
    final long paznic;
    final int vybtime;
    final String sec;
    final String datK;
    final String timK;
    final int repit;
    final String file;
    final String count;
    final int color;

    Padzeia(String padzea, String data, String time, long paznica, int vyb, String secund, String dataK, String timeK, int rep, String repCount, String filename, int color) {
        padz = padzea;
        dat = data;
        tim = time;
        paznic = paznica;
        vybtime = vyb;
        sec = secund;
        datK = dataK;
        timK = timeK;
        repit = rep;
        file = filename;
        count = repCount;
        this.color = color;
    }

    @Override
    public int compareTo(@NonNull Padzeia tmp) {
        String[] days = this.dat.split("[.]");
        String[] tims = this.tim.split(":");
        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(days[2]), Integer.parseInt(days[1]) - 1, Integer.parseInt(days[0]), Integer.parseInt(tims[0]), Integer.parseInt(tims[1]), 0);
        String[] days2 = tmp.dat.split("[.]");
        String[] tims2 = tmp.tim.split(":");
        GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(days2[2]), Integer.parseInt(days2[1]) - 1, Integer.parseInt(days2[0]), Integer.parseInt(tims2[0]), Integer.parseInt(tims2[1]), 0);
        long kon = gc2.getTimeInMillis();
        long result = gc.getTimeInMillis();
        if (result < kon) {
            return -1;
        } else if (result > kon) {
            return 1;
        }
        return 0;
    }
}
