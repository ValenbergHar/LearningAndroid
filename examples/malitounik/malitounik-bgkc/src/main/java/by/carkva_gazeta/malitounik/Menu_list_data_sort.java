package by.carkva_gazeta.malitounik;

import java.util.Comparator;

public class Menu_list_data_sort implements Comparator<Menu_list_data> {

    @Override
    public int compare(Menu_list_data o1, Menu_list_data o2) {
        return o1.data.toLowerCase().compareTo(o2.data.toLowerCase());
    }
}
