package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Comparator;

class my_natatki_files_sort implements Comparator<my_natatki_files> {

    private final SharedPreferences chin;

    my_natatki_files_sort(Context context) {
        chin = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
    }

    @Override
    public int compare(my_natatki_files o1, my_natatki_files o2) {
        int sort = chin.getInt("natatki_sort", 0);
        if (sort == 1)
            return o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
        if (sort == 0) {
            if (o1.lastModified < o2.lastModified) {
                return 1;
            } else if (o1.lastModified > o2.lastModified) {
                return -1;
            }
        }
        return 0;
    }
}
