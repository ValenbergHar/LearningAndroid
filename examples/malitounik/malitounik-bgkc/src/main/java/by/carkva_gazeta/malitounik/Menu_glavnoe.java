package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class Menu_glavnoe extends ListFragment {

    private final String[] data = {"Апошнія навіны", "Гісторыя Царквы", "Сьвятло Ўсходу", "Царква і грамадзтва", "Катэдральны пляц", "Відэа", "Бібліятэка"};
    private boolean shortcuts;
    private long mLastClickTime = 0;

    public static Menu_glavnoe newInstance(boolean shortcuts) {
        Menu_glavnoe menu_glavnoe = new Menu_glavnoe();
        Bundle bundle = new Bundle();
        bundle.putBoolean("shortcuts", shortcuts);
        menu_glavnoe.setArguments(bundle);
        return menu_glavnoe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        shortcuts = Objects.requireNonNull(getArguments()).getBoolean("shortcuts");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Menu_ListAdaprer adapter = new Menu_ListAdaprer(Objects.requireNonNull(getActivity()), data);
        setListAdapter(adapter);
        getListView().setVerticalScrollBarEnabled(false);
        if (shortcuts)
            onListItemClick(getListView(), Objects.requireNonNull(getView()), 6, 0);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        SharedPreferences kq = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = kq.edit();
        prefEditor.putInt("naviny", position);
        prefEditor.apply();
        if (position == 6) {
            if (MainActivity.checkModule_resources(getActivity())) {
                if (MainActivity.checkModules_biblijateka(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.biblijateka.bibliotekaView"));
                        if (getActivity().getIntent().getData() != null)
                            intent.setData(getActivity().getIntent().getData());
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    MainActivity.downloadDynamicModule(getActivity());
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        } else {
            Intent intent = new Intent(getActivity(), naviny.class);
            startActivity(intent);
        }
    }
}
