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
public class Menu_parafii_bgkc extends ListFragment {

    private final String[] data = {"Курыя Апостальскай Візітатуры БГКЦ", "Цэнтральны дэканат", "Усходні дэканат", "Заходні дэканат", "Замежжа"};
    private long mLastClickTime = 0;

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
            if (position == 0) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.parafii_bgkc"));
                    intent.putExtra("bgkc_parafii", position);
                    intent.putExtra("bgkc", position);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.parafii_bgkc_dekanat"));
                    intent.putExtra("bgkc", position);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }
}
