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
public class Menu_malitvy extends ListFragment {

    private long mLastClickTime = 0;
    //String[] by.carkva_gazeta.malitounikApp.data = {"Ранішняя малітвы", "Вячэрнія малітвы", "Прынагодныя малітвы", "няма Прынагодныя – у псальмах"};
    private final String[] data = {"Ранішняя малітвы", "Вячэрнія малітвы", "Прынагодныя малітвы"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Menu_ListAdaprer adapter = new Menu_ListAdaprer(Objects.requireNonNull(getActivity()), data);
        setListAdapter(adapter);
        getListView().setVerticalScrollBarEnabled(false);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (position == 2) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.Malitvy_prynagodnyia"));
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        } else {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.bogashlugbovya"));
                    intent.putExtra("bogashlugbovya", position);
                    intent.putExtra("menu", 2);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
    }
}
