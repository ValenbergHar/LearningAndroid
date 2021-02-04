package by.carkva_gazeta.malitounik;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.Objects;

/**
 * Created by oleg on 30.5.16
 */
public class Menu_ruzanec extends ListFragment {

    private long mLastClickTime = 0;
    private final String[] data = {"Малітвы на вяровіцы", "Молімся на ружанцы", "Разважаньні на Ружанец", "Частка I. Радасныя таямніцы (пн, сб)", "Частка II. Балесныя таямніцы (аўт, пт)", "Частка III. Слаўныя таямніцы (ср, ндз)", "Частка IV. Таямніцы сьвятла (чц)"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Menu_ListAdaprer adapter = new Menu_ListAdaprer(Objects.requireNonNull(getActivity()), data);
        setListAdapter(adapter);
        getListView().setVerticalScrollBarEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (MainActivity.checkModule_resources(getActivity())) {
            try {
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.bogashlugbovya"));
                intent.putExtra("bogashlugbovya", position);
                intent.putExtra("menu", 4);
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }
}
