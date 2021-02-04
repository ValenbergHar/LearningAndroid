package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by oleg on 30.5.16
 */
public class Menu_pesny_kal extends ListFragment {

    private final ArrayList<String> data = new ArrayList<>();
    private long mLastClickTime = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data.add("Ave Maria (Зорка зазьзяла)");
        data.add("А што гэта за сьпевы");
        data.add("А ў сьвеце нам навіна была");
        data.add("А ўчора з вячора");
        data.add("Вясёлых калядных сьвятаў");
        data.add("Зазьзяла зорачка над Бэтлеемам");
        data.add("Звон зьвініць");
        data.add("На шляху ў Бэтлеем");
        data.add("Неба і зямля");
        data.add("Нова радасьць стала");
        data.add("Ночка цiхая, зарыста");
        data.add("Ноччу сьвятой");
        data.add("Паказалась з неба яснасьць");
        data.add("Прыйдзіце да Збаўцы");
        data.add("Радасная вестка");
        data.add("У начную ціш");
        data.add("Учора зьвячора — засьвяціла зора");
        data.add("Ціхая ноч (пер. Н. Арсеньневай)");
        data.add("Ціхая ноч-2");
        data.add("Ціхая ноч-3");
        data.add("Прыйдзі, прыйдзі, Эмануэль (ХІХ ст.)");
        data.add("Прыйдзі, прыйдзі, Эмануэль (XII–ХVIII стст.)");
        // так же добавить в search_pesny.get_Menu_list_data
        Collections.sort(data);
        Menu_ListAdaprer adapter = new Menu_ListAdaprer(getActivity());
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
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.pesny_kal"));
                intent.putExtra("pesny", data.get(position));
                startActivity(intent);
            } catch (ClassNotFoundException ignored) {
            }
        } else {
            Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
            dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
        }
    }

    class Menu_ListAdaprer extends ArrayAdapter<String> {

        private final SharedPreferences k;
        private final Activity activity;

        Menu_ListAdaprer(Activity activity) {
            super(activity, R.layout.simple_list_item_2, R.id.label, data);
            k = activity.getSharedPreferences("biblia", Context.MODE_PRIVATE);
            this.activity = activity;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = activity.getLayoutInflater().inflate(R.layout.simple_list_item_2, parent, false);
                viewHolder = new ViewHolder();
                mView.setTag(viewHolder);
                viewHolder.text = mView.findViewById(R.id.label);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }

            boolean dzenNoch = k.getBoolean("dzen_noch", false);

            viewHolder.text.setText(data.get(position));

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

            if (dzenNoch) {
                viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.selector_dark));
                viewHolder.text.setTextColor(ContextCompat.getColor(activity, R.color.colorIcons));
                viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
            }
            return mView;
        }
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
