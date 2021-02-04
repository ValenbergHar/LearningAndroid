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
public class Menu_bogashlugbovya extends ListFragment {

    private long mLastClickTime = 0;

    //String[] by.carkva_gazeta.malitounikApp.data = {"няма Літургія сьв. Яна Залатавуснага", "няма Літургія сьв. Васіля Вялікага", "няма Літургія раней асьвячаных дароў", "Набажэнства ў гонар Маці Божай Нястомнай Дапамогі", "Малітвы пасьля сьвятога прычасьця", "няма Ютрань", "няма Вячэрня", "Абедніца"};
    private final String[] data = {"Боская Літургія між сьвятымі айца нашага Яна Залатавуснага", "Набажэнства ў гонар Маці Божай Нястомнай Дапамогі", "Малітвы пасьля сьвятога прычасьця", "Ютрань нядзельная (у скароце)", "Абедніца", "Служба за памерлых — Малая паніхіда", "Трапары і кандакі нядзельныя васьмі тонаў", "Трапары і кандакі штодзённыя - на кожны дзень тыдня"};

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
        switch (position) {
            case 2: {
                Intent intent = new Intent(getActivity(), Malitvy_paslia_prychascia.class);
                startActivity(intent);
                break;
            }
            case 6: {
                Intent intent = new Intent(getActivity(), Ton_niadzelny.class);
                startActivity(intent);
                break;
            }
            case 7: {
                Intent intent = new Intent(getActivity(), Ton_na_kozny_dzen.class);
                startActivity(intent);
                break;
            }
            default: {
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.bogashlugbovya"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("bogashlugbovya", position);
                        intent.putExtra("menu", 1);
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
                break;
            }
        }
    }
}
