package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import by.carkva_gazeta.malitounik.MaranAta_Global_List;

public class PageFragmentStaryZapavietSinaidal extends ListFragment implements ListView.OnItemLongClickListener {

    private int kniga;
    private int page;
    private int pazicia;
    private ClicParalelListiner clicParalelListiner;
    private ListPosition listPosition;
    private LongClicListiner longClicListiner;
    private ArrayList<String> bible;
    private InputStream inputStream;

    public interface ClicParalelListiner {
        void setOnClic(String cytanneParalelnye, String cytanneSours);
    }

    public interface ListPosition {
        void getListPosition(int position);
    }

    public interface LongClicListiner {
        void onLongClick();
    }

    public static PageFragmentStaryZapavietSinaidal newInstance(int page, int kniga, int pazicia) {
        PageFragmentStaryZapavietSinaidal fragmentFirst = new PageFragmentStaryZapavietSinaidal();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putInt("kniga", kniga);
        args.putInt("pazicia", pazicia);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            clicParalelListiner = (ClicParalelListiner) context;
            listPosition = (ListPosition) context;
            longClicListiner = (LongClicListiner) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        kniga = Objects.requireNonNull(getArguments()).getInt("kniga");
        page = getArguments().getInt("page");
        pazicia = getArguments().getInt("pazicia");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MaranAta_Global_List.setListPosition(position);
        MaranAta_Global_List.setBible(bible);
        longClicListiner.onLongClick();
        return true;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (!MaranAta_Global_List.getmPedakVisable()) {
            Biblia_parallel_chtenia parallel = new Biblia_parallel_chtenia();
            String res = "+-+";
            String knigaName = "";
            boolean clic = false;
            if (kniga == 0) {
                knigaName = "Быт";
                res = parallel.kniga1(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 1) {
                knigaName = "Исх";
                res = parallel.kniga2(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 2) {
                knigaName = "Лев";
                res = parallel.kniga3(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 3) {
                knigaName = "Числа";
                res = parallel.kniga4(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 4) {
                knigaName = "Втор";
                res = parallel.kniga5(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 5) {
                knigaName = "Нав";
                res = parallel.kniga6(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 6) {
                knigaName = "Суд";
                res = parallel.kniga7(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 7) {
                knigaName = "Руфь";
                res = parallel.kniga8(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 8) {
                knigaName = "1 Цар";
                res = parallel.kniga9(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 9) {
                knigaName = "2 Цар";
                res = parallel.kniga10(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 10) {
                knigaName = "3 Цар";
                res = parallel.kniga11(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 11) {
                knigaName = "4 Цар";
                res = parallel.kniga12(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 12) {
                knigaName = "1 Пар";
                res = parallel.kniga13(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 13) {
                knigaName = "2 Пар";
                res = parallel.kniga14(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 14) {
                knigaName = "1 Езд";
                res = parallel.kniga15(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 15) {
                knigaName = "Неем";
                res = parallel.kniga16(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 16) {
                knigaName = "2 Езд";
                res = parallel.kniga17(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 17) {
                knigaName = "Тов";
                res = parallel.kniga18(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 18) {
                knigaName = "Иудифь";
                res = parallel.kniga19(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 19) {
                knigaName = "Есф";
                res = parallel.kniga20(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 20) {
                knigaName = "Иов";
                res = parallel.kniga21(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 21) {
                knigaName = "Пс";
                res = parallel.kniga22(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 22) {
                knigaName = "Притч";
                res = parallel.kniga23(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 23) {
                knigaName = "Еккл";
                res = parallel.kniga24(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 24) {
                knigaName = "Песн";
                res = parallel.kniga25(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 25) {
                knigaName = "Прем";
                res = parallel.kniga26(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 26) {
                knigaName = "Сир";
                res = parallel.kniga27(page + 1, position + 1);
                if (page + 1 == 1)
                    position = position - 8;
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 27) {
                knigaName = "Ис";
                res = parallel.kniga28(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 28) {
                knigaName = "Иер";
                res = parallel.kniga29(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 29) {
                knigaName = "Плач Иер";
                res = parallel.kniga30(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 30) {
                knigaName = "Посл Иеремии";
                res = parallel.kniga31(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 31) {
                knigaName = "Вар";
                res = parallel.kniga32(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 32) {
                knigaName = "Иез";
                res = parallel.kniga33(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 33) {
                knigaName = "Дан";
                res = parallel.kniga34(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 34) {
                knigaName = "Ос";
                res = parallel.kniga35(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 35) {
                knigaName = "Иоиль";
                res = parallel.kniga36(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 36) {
                knigaName = "Ам";
                res = parallel.kniga37(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 37) {
                knigaName = "Авдий";
                res = parallel.kniga38(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 38) {
                knigaName = "Иона";
                res = parallel.kniga39(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 39) {
                knigaName = "Мих";
                res = parallel.kniga40(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 40) {
                knigaName = "Наум";
                res = parallel.kniga41(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 41) {
                knigaName = "Аввакум";
                res = parallel.kniga42(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 42) {
                knigaName = "Сафония";
                res = parallel.kniga43(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 43) {
                knigaName = "Аггей";
                res = parallel.kniga44(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 44) {
                knigaName = "Зах";
                res = parallel.kniga45(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 45) {
                knigaName = "Мал";
                res = parallel.kniga46(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 46) {
                knigaName = "1 Макк";
                res = parallel.kniga47(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 47) {
                knigaName = "2 Макк";
                res = parallel.kniga48(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 48) {
                knigaName = "3 Макк";
                res = parallel.kniga49(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 49) {
                knigaName = "3 Езд";
                res = parallel.kniga50(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (clic) {
                clicParalelListiner.setOnClic(res, knigaName + " " + (page + 1) + ":" + (position + 1));
            }
        } else {
            longClicListiner.onLongClick();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setSelection(stary_zapaviet_sinaidal3.fierstPosition);
        getListView().setOnItemLongClickListener(this);
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (listPosition != null) {
                    listPosition.getListPosition(view.getFirstVisiblePosition());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        bible = new ArrayList<>();
        switch (kniga) {
            case 0:
                inputStream = getResources().openRawResource(R.raw.sinaidals1);
                break;
            case 1:
                inputStream = getResources().openRawResource(R.raw.sinaidals2);
                break;
            case 2:
                inputStream = getResources().openRawResource(R.raw.sinaidals3);
                break;
            case 3:
                inputStream = getResources().openRawResource(R.raw.sinaidals4);
                break;
            case 4:
                inputStream = getResources().openRawResource(R.raw.sinaidals5);
                break;
            case 5:
                inputStream = getResources().openRawResource(R.raw.sinaidals6);
                break;
            case 6:
                inputStream = getResources().openRawResource(R.raw.sinaidals7);
                break;
            case 7:
                inputStream = getResources().openRawResource(R.raw.sinaidals8);
                break;
            case 8:
                inputStream = getResources().openRawResource(R.raw.sinaidals9);
                break;
            case 9:
                inputStream = getResources().openRawResource(R.raw.sinaidals10);
                break;
            case 10:
                inputStream = getResources().openRawResource(R.raw.sinaidals11);
                break;
            case 11:
                inputStream = getResources().openRawResource(R.raw.sinaidals12);
                break;
            case 12:
                inputStream = getResources().openRawResource(R.raw.sinaidals13);
                break;
            case 13:
                inputStream = getResources().openRawResource(R.raw.sinaidals14);
                break;
            case 14:
                inputStream = getResources().openRawResource(R.raw.sinaidals15);
                break;
            case 15:
                inputStream = getResources().openRawResource(R.raw.sinaidals16);
                break;
            case 16:
                inputStream = getResources().openRawResource(R.raw.sinaidals17);
                break;
            case 17:
                inputStream = getResources().openRawResource(R.raw.sinaidals18);
                break;
            case 18:
                inputStream = getResources().openRawResource(R.raw.sinaidals19);
                break;
            case 19:
                inputStream = getResources().openRawResource(R.raw.sinaidals20);
                break;
            case 20:
                inputStream = getResources().openRawResource(R.raw.sinaidals21);
                break;
            case 21:
                inputStream = getResources().openRawResource(R.raw.sinaidals22);
                break;
            case 22:
                inputStream = getResources().openRawResource(R.raw.sinaidals23);
                break;
            case 23:
                inputStream = getResources().openRawResource(R.raw.sinaidals24);
                break;
            case 24:
                inputStream = getResources().openRawResource(R.raw.sinaidals25);
                break;
            case 25:
                inputStream = getResources().openRawResource(R.raw.sinaidals26);
                break;
            case 26:
                inputStream = getResources().openRawResource(R.raw.sinaidals27);
                break;
            case 27:
                inputStream = getResources().openRawResource(R.raw.sinaidals28);
                break;
            case 28:
                inputStream = getResources().openRawResource(R.raw.sinaidals29);
                break;
            case 29:
                inputStream = getResources().openRawResource(R.raw.sinaidals30);
                break;
            case 30:
                inputStream = getResources().openRawResource(R.raw.sinaidals31);
                break;
            case 31:
                inputStream = getResources().openRawResource(R.raw.sinaidals32);
                break;
            case 32:
                inputStream = getResources().openRawResource(R.raw.sinaidals33);
                break;
            case 33:
                inputStream = getResources().openRawResource(R.raw.sinaidals34);
                break;
            case 34:
                inputStream = getResources().openRawResource(R.raw.sinaidals35);
                break;
            case 35:
                inputStream = getResources().openRawResource(R.raw.sinaidals36);
                break;
            case 36:
                inputStream = getResources().openRawResource(R.raw.sinaidals37);
                break;
            case 37:
                inputStream = getResources().openRawResource(R.raw.sinaidals38);
                break;
            case 38:
                inputStream = getResources().openRawResource(R.raw.sinaidals39);
                break;
            case 39:
                inputStream = getResources().openRawResource(R.raw.sinaidals40);
                break;
            case 40:
                inputStream = getResources().openRawResource(R.raw.sinaidals41);
                break;
            case 41:
                inputStream = getResources().openRawResource(R.raw.sinaidals42);
                break;
            case 42:
                inputStream = getResources().openRawResource(R.raw.sinaidals43);
                break;
            case 43:
                inputStream = getResources().openRawResource(R.raw.sinaidals44);
                break;
            case 44:
                inputStream = getResources().openRawResource(R.raw.sinaidals45);
                break;
            case 45:
                inputStream = getResources().openRawResource(R.raw.sinaidals46);
                break;
            case 46:
                inputStream = getResources().openRawResource(R.raw.sinaidals47);
                break;
            case 47:
                inputStream = getResources().openRawResource(R.raw.sinaidals48);
                break;
            case 48:
                inputStream = getResources().openRawResource(R.raw.sinaidals49);
                break;
            case 49:
                inputStream = getResources().openRawResource(R.raw.sinaidals50);
                break;
        }
        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
            String[] split = builder.toString().split("===");
            String[] bibleline = split[page + 1].split("\n");
            bible.addAll(Arrays.asList(bibleline).subList(1, bibleline.length));
        } catch (Throwable ignored) {
        }
        ExpArrayAdapterParallel adapter = new ExpArrayAdapterParallel(getActivity(), bible, kniga, page, false, 2);
        getListView().setDivider(null);
        setListAdapter(adapter);
        getListView().setSelection(pazicia);
        getListView().setVerticalScrollBarEnabled(false);
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        if (k.getBoolean("dzen_noch", false))
            getListView().setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorbackground_material_dark);
        //float scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (scale * 10f);
        //getListView().setPadding(dpAsPixels, 0, dpAsPixels, dpAsPixels);
    }
}



