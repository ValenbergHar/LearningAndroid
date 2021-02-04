package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Objects;

public class Menu_psalter_nadsana extends Fragment implements View.OnClickListener {
    
    private SharedPreferences k;
    private TextView_Roboto_Condensed malitva_pered;
    private TextView_Roboto_Condensed malitva_posle;
    private TextView_Roboto_Condensed pesni;
    private long mLastClickTime = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        View rootView = inflater.inflate(R.layout.menu_psalter, container, false);
        TextView_Roboto_Condensed psalter = rootView.findViewById(R.id.psalter);
        psalter.setOnClickListener(this);
        TextView_Roboto_Condensed prodolzyt = rootView.findViewById(R.id.prodolzych);
        prodolzyt.setOnClickListener(this);
        TextView_Roboto_Condensed pravilaChtenia = rootView.findViewById(R.id.pravila_chtenia);
        pravilaChtenia.setOnClickListener(this);
        malitva_pered = rootView.findViewById(R.id.malitva_pered);
        malitva_pered.setOnClickListener(this);
        malitva_posle = rootView.findViewById(R.id.malitva_posle);
        malitva_posle.setOnClickListener(this);
        pesni = rootView.findViewById(R.id.pesni);
        pesni.setOnClickListener(this);
        TextView_Roboto_Condensed pravila = rootView.findViewById(R.id.pravila);
        pravila.setOnClickListener(this);
        if (dzenNoch) {
            psalter.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.knopka_red_black));
        }
        TextView_Roboto_Condensed t1 = rootView.findViewById(R.id.textView1);
        t1.setOnClickListener(this);
        TextView_Roboto_Condensed t2 = rootView.findViewById(R.id.textView2);
        t2.setOnClickListener(this);
        TextView_Roboto_Condensed t3 = rootView.findViewById(R.id.textView3);
        t3.setOnClickListener(this);
        TextView_Roboto_Condensed t4 = rootView.findViewById(R.id.textView4);
        t4.setOnClickListener(this);
        TextView_Roboto_Condensed t5 = rootView.findViewById(R.id.textView5);
        t5.setOnClickListener(this);
        TextView_Roboto_Condensed t6 = rootView.findViewById(R.id.textView6);
        t6.setOnClickListener(this);
        TextView_Roboto_Condensed t7 = rootView.findViewById(R.id.textView7);
        t7.setOnClickListener(this);
        TextView_Roboto_Condensed t8 = rootView.findViewById(R.id.textView8);
        t8.setOnClickListener(this);
        TextView_Roboto_Condensed t9 = rootView.findViewById(R.id.textView9);
        t9.setOnClickListener(this);
        TextView_Roboto_Condensed t10 = rootView.findViewById(R.id.textView10);
        t10.setOnClickListener(this);
        TextView_Roboto_Condensed t11 = rootView.findViewById(R.id.textView11);
        t11.setOnClickListener(this);
        TextView_Roboto_Condensed t12 = rootView.findViewById(R.id.textView12);
        t12.setOnClickListener(this);
        TextView_Roboto_Condensed t13 = rootView.findViewById(R.id.textView13);
        t13.setOnClickListener(this);
        TextView_Roboto_Condensed t14 = rootView.findViewById(R.id.textView14);
        t14.setOnClickListener(this);
        TextView_Roboto_Condensed t15 = rootView.findViewById(R.id.textView15);
        t15.setOnClickListener(this);
        TextView_Roboto_Condensed t16 = rootView.findViewById(R.id.textView16);
        t16.setOnClickListener(this);
        TextView_Roboto_Condensed t17 = rootView.findViewById(R.id.textView17);
        t17.setOnClickListener(this);
        TextView_Roboto_Condensed t18 = rootView.findViewById(R.id.textView18);
        t18.setOnClickListener(this);
        TextView_Roboto_Condensed t19 = rootView.findViewById(R.id.textView19);
        t19.setOnClickListener(this);
        TextView_Roboto_Condensed t20 = rootView.findViewById(R.id.textView20);
        t20.setOnClickListener(this);
        TextView_Roboto_Condensed title = rootView.findViewById(R.id.textViewtitle);
        if (dzenNoch) {
            title.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nadsanblack));
            t1.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t2.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t3.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t4.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t5.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t6.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t7.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t8.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t9.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t10.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t11.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t12.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t13.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t14.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t15.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t16.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t17.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t18.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t19.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            t20.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary_text));
            title.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorIcons));
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        int id = v.getId();
        if (id == R.id.psalter) {
            startActivity(new Intent(getActivity(), nadsanContent.class));
        }
        if (id == R.id.prodolzych) {
            String bible_time = k.getString("psalter_time_psalter_nadsan", "");
            if (!Objects.requireNonNull(bible_time).equals("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayMap<String, Integer>>() {
                }.getType();
                ArrayMap<String, Integer> set = gson.fromJson(bible_time, type);
                if (MainActivity.checkModule_resources(getActivity())) {
                    try {
                        Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.nadsanContentActivity"));
                        intent.putExtra("glava", set.get("glava"));
                        intent.putExtra("stix", set.get("stix"));
                        startActivity(intent);
                    } catch (ClassNotFoundException ignored) {
                    }
                } else {
                    Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                    dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                }
            } else {
                Dialog_no_bible_chtenia chtenia = new Dialog_no_bible_chtenia();
                chtenia.show(Objects.requireNonNull(getFragmentManager()), "no_bible_chtenia");
            }
        }
        if (id == R.id.pravila_chtenia) {
            Dialog_Nadsan_Pravila pravila = new Dialog_Nadsan_Pravila();
            pravila.show(Objects.requireNonNull(getFragmentManager()), "pravila");
        }
        if (id == R.id.malitva_pered) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.nadsan_malitvy_i_pesni"));
                    intent.putExtra("malitva", 0);
                    intent.putExtra("malitva_title", malitva_pered.getText().toString());
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
        if (id == R.id.malitva_posle) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.nadsan_malitvy_i_pesni"));
                    intent.putExtra("malitva", 1);
                    intent.putExtra("malitva_title", malitva_posle.getText().toString());
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
        if (id == R.id.pesni) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.nadsan_malitvy_i_pesni"));
                    intent.putExtra("malitva", 2);
                    intent.putExtra("malitva_title", pesni.getText().toString());
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
        if (id == R.id.pravila) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.psalterNadsana"));
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }
        int glava = -1;
        switch (id) {
            case R.id.textView1:
                glava = 1;
                break;
            case R.id.textView2:
                glava = 2;
                break;
            case R.id.textView3:
                glava = 3;
                break;
            case R.id.textView4:
                glava = 4;
                break;
            case R.id.textView5:
                glava = 5;
                break;
            case R.id.textView6:
                glava = 6;
                break;
            case R.id.textView7:
                glava = 7;
                break;
            case R.id.textView8:
                glava = 8;
                break;
            case R.id.textView9:
                glava = 9;
                break;
            case R.id.textView10:
                glava = 10;
                break;
            case R.id.textView11:
                glava = 11;
                break;
            case R.id.textView12:
                glava = 12;
                break;
            case R.id.textView13:
                glava = 13;
                break;
            case R.id.textView14:
                glava = 14;
                break;
            case R.id.textView15:
                glava = 15;
                break;
            case R.id.textView16:
                glava = 16;
                break;
            case R.id.textView17:
                glava = 17;
                break;
            case R.id.textView18:
                glava = 18;
                break;
            case R.id.textView19:
                glava = 19;
                break;
            case R.id.textView20:
                glava = 20;
                break;
        }
        if (glava != -1) {
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.nadsanContentActivity"));
                    intent.putExtra("kafizma", glava);
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
