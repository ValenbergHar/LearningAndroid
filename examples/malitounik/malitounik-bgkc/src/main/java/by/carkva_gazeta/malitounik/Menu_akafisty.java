package by.carkva_gazeta.malitounik;

import android.app.Activity;
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
public class Menu_akafisty extends ListFragment {

    //String[] by.carkva_gazeta.malitounikApp.data = {"Пра Акафіст", "Найсьвяцейшай Багародзіцы", "Маці Божай Нястомнай Дапамогі", "перад Жыровіцкай іконай", "у гонар Падляшскіх мучанікаў", "няма Імю Ісусаваму", "да Духа Сьвятога", "сьв. Апосталам Пятру і Паўлу", "няма Жыцьцядайнаму Крыжу"};
    private final String[] data = {"Пра Акафіст", "Найсьвяцейшай Багародзіцы", "Маці Божай Нястомнай Дапамогі", "перад Жыровіцкай іконай", "у гонар Падляшскіх мучанікаў", "Імю Ісусаваму", "да Духа Сьвятога", "сьв. Апосталам Пятру і Паўлу"};
    private final Activity activity;
    private long mLastClickTime = 0;

    Menu_akafisty(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Menu_ListAdaprer adapter = new Menu_ListAdaprer(activity, data);
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
        if (MainActivity.checkModule_resources(activity)) {
            try {
                Intent intent = new Intent(activity, Class.forName("by.carkva_gazeta.resources.bogashlugbovya"));
                intent.putExtra("bogashlugbovya", position);
                intent.putExtra("menu", 3);
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }
}
