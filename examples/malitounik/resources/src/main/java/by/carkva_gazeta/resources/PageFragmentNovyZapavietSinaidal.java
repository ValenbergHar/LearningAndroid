package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class PageFragmentNovyZapavietSinaidal extends ListFragment implements ListView.OnItemLongClickListener {

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

    public static PageFragmentNovyZapavietSinaidal newInstance(int page, int kniga, int pazicia) {
        PageFragmentNovyZapavietSinaidal fragmentFirst = new PageFragmentNovyZapavietSinaidal();
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
                knigaName = "Мф";
                res = parallel.kniga51(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 1) {
                knigaName = "Мк";
                res = parallel.kniga52(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 2) {
                knigaName = "Лк";
                res = parallel.kniga53(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 3) {
                knigaName = "Ин";
                res = parallel.kniga54(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 4) {
                knigaName = "Деян";
                res = parallel.kniga55(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 5) {
                knigaName = "Иак";
                res = parallel.kniga56(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 6) {
                knigaName = "1 Пет";
                res = parallel.kniga57(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 7) {
                knigaName = "2 Пет";
                res = parallel.kniga58(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 8) {
                knigaName = "1 Ин";
                res = parallel.kniga59(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 9) {
                knigaName = "2 Ин";
                res = parallel.kniga60(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 10) {
                knigaName = "3 Ин";
                res = parallel.kniga61(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 11) {
                knigaName = "Иуд";
                res = parallel.kniga62(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 12) {
                knigaName = "Рим";
                res = parallel.kniga63(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 13) {
                knigaName = "1 Кор";
                res = parallel.kniga64(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 14) {
                knigaName = "2 Кор";
                res = parallel.kniga65(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 15) {
                knigaName = "Гал";
                res = parallel.kniga66(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 16) {
                knigaName = "Еф";
                res = parallel.kniga67(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 17) {
                knigaName = "Флп";
                res = parallel.kniga68(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 18) {
                knigaName = "Кол";
                res = parallel.kniga69(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 19) {
                knigaName = "1 Фес";
                res = parallel.kniga70(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 20) {
                knigaName = "2 Фес";
                res = parallel.kniga71(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 21) {
                knigaName = "1 Тим";
                res = parallel.kniga72(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 22) {
                knigaName = "2 Тим";
                res = parallel.kniga73(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 23) {
                knigaName = "Тит";
                res = parallel.kniga74(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 24) {
                knigaName = "Флм";
                res = parallel.kniga75(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 25) {
                knigaName = "Евр";
                res = parallel.kniga76(page + 1, position + 1);
                if (!res.contains("+-+")) clic = true;
            }
            if (kniga == 26) {
                knigaName = "Откр";
                res = parallel.kniga77(page + 1, position + 1);
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

        getListView().setSelection(novy_zapaviet_sinaidal3.fierstPosition);
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
                inputStream = getResources().openRawResource(R.raw.sinaidaln1);
                break;
            case 1:
                inputStream = getResources().openRawResource(R.raw.sinaidaln2);
                break;
            case 2:
                inputStream = getResources().openRawResource(R.raw.sinaidaln3);
                break;
            case 3:
                inputStream = getResources().openRawResource(R.raw.sinaidaln4);
                break;
            case 4:
                inputStream = getResources().openRawResource(R.raw.sinaidaln5);
                break;
            case 5:
                inputStream = getResources().openRawResource(R.raw.sinaidaln6);
                break;
            case 6:
                inputStream = getResources().openRawResource(R.raw.sinaidaln7);
                break;
            case 7:
                inputStream = getResources().openRawResource(R.raw.sinaidaln8);
                break;
            case 8:
                inputStream = getResources().openRawResource(R.raw.sinaidaln9);
                break;
            case 9:
                inputStream = getResources().openRawResource(R.raw.sinaidaln10);
                break;
            case 10:
                inputStream = getResources().openRawResource(R.raw.sinaidaln11);
                break;
            case 11:
                inputStream = getResources().openRawResource(R.raw.sinaidaln12);
                break;
            case 12:
                inputStream = getResources().openRawResource(R.raw.sinaidaln13);
                break;
            case 13:
                inputStream = getResources().openRawResource(R.raw.sinaidaln14);
                break;
            case 14:
                inputStream = getResources().openRawResource(R.raw.sinaidaln15);
                break;
            case 15:
                inputStream = getResources().openRawResource(R.raw.sinaidaln16);
                break;
            case 16:
                inputStream = getResources().openRawResource(R.raw.sinaidaln17);
                break;
            case 17:
                inputStream = getResources().openRawResource(R.raw.sinaidaln18);
                break;
            case 18:
                inputStream = getResources().openRawResource(R.raw.sinaidaln19);
                break;
            case 19:
                inputStream = getResources().openRawResource(R.raw.sinaidaln20);
                break;
            case 20:
                inputStream = getResources().openRawResource(R.raw.sinaidaln21);
                break;
            case 21:
                inputStream = getResources().openRawResource(R.raw.sinaidaln22);
                break;
            case 22:
                inputStream = getResources().openRawResource(R.raw.sinaidaln23);
                break;
            case 23:
                inputStream = getResources().openRawResource(R.raw.sinaidaln24);
                break;
            case 24:
                inputStream = getResources().openRawResource(R.raw.sinaidaln25);
                break;
            case 25:
                inputStream = getResources().openRawResource(R.raw.sinaidaln26);
                break;
            case 26:
                inputStream = getResources().openRawResource(R.raw.sinaidaln27);
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
        ExpArrayAdapterParallel adapter = new ExpArrayAdapterParallel(getActivity(), bible, kniga, page, true, 2);
        getListView().setDivider(null);
        setListAdapter(adapter);
        getListView().setSelection(pazicia);
        getListView().setVerticalScrollBarEnabled(false);
        //float scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (scale * 10f);
        //getListView().setPadding(dpAsPixels, 0, dpAsPixels, dpAsPixels);
    }
}
