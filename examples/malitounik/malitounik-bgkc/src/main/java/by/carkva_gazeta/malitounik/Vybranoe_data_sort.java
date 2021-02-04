package by.carkva_gazeta.malitounik;

import java.util.Comparator;

class Vybranoe_data_sort implements Comparator<Vybranoe_data> {

    @Override
    public int compare(Vybranoe_data o1, Vybranoe_data o2) {
        return o1.data.toLowerCase().compareTo(o2.data.toLowerCase());
    }
}
