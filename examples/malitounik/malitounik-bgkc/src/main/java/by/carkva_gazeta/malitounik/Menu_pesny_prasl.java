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
public class Menu_pesny_prasl extends ListFragment {

    private final ArrayList<String> data = new ArrayList<>();
    private long mLastClickTime = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data.add("Ён паўсюль");
        data.add("Ісус вызваліў мяне");
        data.add("Ісус нам дае збаўленьне");
        data.add("Айцец наш і наш Валадар");
        data.add("Алілуя!");
        data.add("Бог блаславіў гэты дзень");
        data.add("Бог ёсьць любоў");
        data.add("Богу сьпявай, уся зямля!");
        data.add("Божа мой");
        data.add("Браце мой");
        data.add("Весяліся і пляскай у далоні");
        data.add("Вольная воля");
        data.add("Вось маё сэрца");
        data.add("Вядзі мяне, Божа");
        data.add("Вялікім і цудоўным");
        data.add("Госпад мой заўсёды па маёй правіцы");
        data.add("Госпаду дзякуйце, бо добры Ён");
        data.add("Дай Духа любові");
        data.add("Дай уславіць Цябе");
        data.add("Дай, добры Божа");
        data.add("Дакраніся да маіх вачэй");
        data.add("Дзякуй за ўсё, што Ты стварыў");
        data.add("Дзякуй!");
        data.add("З намі — Пятро і Андрэй");
        data.add("Знайдзі мяне");
        data.add("Зоркі далёка");
        data.add("Кадош (Сьвяты)");
        data.add("Клічаш ты");
        data.add("Любоў Твая");
        data.add("Любіць — гэта ахвяраваць");
        data.add("Майго жыцьця — мой Бог крыніца");
        data.add("Маё сэрца");
        data.add("Маё шчасьце ў Iсуса");
        data.add("На псалтыры і на арфе");
        data.add("Настане дзень");
        data.add("Невычэрпныя ласкі ў Бога");
        data.add("О, калі б ты паслухаў Мяне");
        data.add("Ойча мой, к Табе іду");
        data.add("Ойча, мяне Ты любіш");
        data.add("Пакліканьне (Човен)");
        data.add("Пачуй мой кліч, чулы Ойча");
        data.add("Песьню славы засьпявайма");
        data.add("Песьня Давіда");
        data.add("Песьня вячэрняя");
        data.add("Песьня пілігрыма");
        data.add("Песьня ранішняя");
        data.add("Пяцёра пакутнікаў");
        data.add("Пілігрым");
        data.add("Руах");
        data.add("Сьвятло жыцьця");
        data.add("Сьпявайма добраму Богу");
        data.add("Сьпявайце Цару");
        data.add("Так, як імкнецца сарна");
        data.add("Твая любоў");
        data.add("Твая прысутнасьць");
        data.add("Толькі Ісус");
        data.add("Толькі Бог, толькі ты");
        data.add("Толькі Бог");
        data.add("Ты ведаеш сэрца маё");
        data.add("Ты ведаеш...");
        data.add("Ты — Госпад мой");
        data.add("Хвала Табе, вялікі Бог");
        data.add("Хвалім Цябе, Божа!");
        data.add("Хрыстос уваскрос! (Resucito)");
        data.add("Ці ты быў на Галгофе");
        data.add("Шалом алэхем (Мір вам)");
        data.add("Я люблю Цябе, Ойча міласэрны");
        data.add("Я ўстану рана, каб сьпяваць");
        data.add("Як гэта хораша й міла");
        data.add("Яму за ўсё слава");
        data.add("Цябе, Бога, хвалім");
        data.add("Мой Госпад, мой Збаўца");
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
                Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.pesny_prasl"));
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
