package by.carkva_gazeta.malitounik;

import androidx.annotation.NonNull;

/**
 * Created by oleg on 23.12.16
 */

class prazdniki implements Comparable<prazdniki> {

    final int data;
    final String opisanie;
    final String opisanieData;

    prazdniki(int data, String opisanie, String opisanieData) {
        this.data = data;
        this.opisanie = opisanie;
        this.opisanieData = opisanieData;
    }

    @Override
    public int compareTo(@NonNull prazdniki tmp) {
        if (this.data < tmp.data) {
            return -1;
        } else if (this.data > tmp.data) {
            return 1;
        }
        return 0;
    }
}
