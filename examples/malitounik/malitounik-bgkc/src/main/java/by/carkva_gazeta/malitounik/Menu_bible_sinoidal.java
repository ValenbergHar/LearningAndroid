package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Objects;

public class Menu_bible_sinoidal extends Fragment {

    private long mLastClickTime = 0;

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences k = Objects.requireNonNull(getActivity()).getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        View rootview = inflater.inflate(R.layout.menu_bible, container, false);
        TextView_Roboto_Condensed novyzavet = rootview.findViewById(R.id.novyZavet);
        novyzavet.setOnClickListener((v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            startActivity(new Intent(getActivity(), novy_zapaviet_sinaidal2.class));
        }));
        TextView_Roboto_Condensed staryzavet = rootview.findViewById(R.id.staryZavet);
        staryzavet.setOnClickListener((v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            startActivity(new Intent(getActivity(), stary_zapaviet_sinaidal2.class));
        }));
        TextView_Roboto_Condensed prodolzyt = rootview.findViewById(R.id.prodolzych);
        prodolzyt.setOnClickListener((v -> {
            String bible_time = k.getString("bible_time_sinodal", "");
            if (!Objects.requireNonNull(bible_time).equals("")) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayMap<String, Integer>>() {
                }.getType();
                ArrayMap<String, Integer> set = gson.fromJson(bible_time, type);
                if (set.get("zavet") == 1) {
                    if (MainActivity.checkModule_resources(getActivity())) {
                        Intent intent = new Intent(getActivity(), novy_zapaviet_sinaidal2.class);
                        intent.putExtra("kniga", set.get("kniga"));
                        intent.putExtra("glava", set.get("glava"));
                        intent.putExtra("stix", set.get("stix"));
                        intent.putExtra("prodolzyt", true);
                        startActivity(intent);
                    } else {
                        Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                        dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                    }
                } else {
                    if (MainActivity.checkModule_resources(getActivity())) {
                        Intent intent = new Intent(getActivity(), stary_zapaviet_sinaidal2.class);
                        intent.putExtra("kniga", set.get("kniga"));
                        intent.putExtra("glava", set.get("glava"));
                        intent.putExtra("stix", set.get("stix"));
                        intent.putExtra("prodolzyt", true);
                        startActivity(intent);
                    } else {
                        Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                        dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
                    }
                }
            } else {
                Dialog_no_bible_chtenia chtenia = new Dialog_no_bible_chtenia();
                chtenia.show(Objects.requireNonNull(getFragmentManager()), "no_bible_chtenia");
            }
        }));
        TextView_Roboto_Condensed zakladki = rootview.findViewById(R.id.zakladki);
        zakladki.setOnClickListener((v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.bible_zakladki"));
                    intent.putExtra("semuxa", 2);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }));
        TextView_Roboto_Condensed natatki = rootview.findViewById(R.id.natatki);
        natatki.setOnClickListener((v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.bible_natatki"));
                    intent.putExtra("semuxa", 2);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }));
        TextView_Roboto_Condensed saeche = rootview.findViewById(R.id.saeche);
        saeche.setOnClickListener((v -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (MainActivity.checkModule_resources(getActivity())) {
                try {
                    Intent intent = new Intent(getActivity(), Class.forName("by.carkva_gazeta.resources.search_biblia"));
                    intent.putExtra("zavet", 2);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                }
            } else {
                Dialog_install_Dadatak dadatak = new Dialog_install_Dadatak();
                dadatak.show(Objects.requireNonNull(getFragmentManager()), "dadatak");
            }
        }));
        TextView_Roboto_Condensed umovy = rootview.findViewById(R.id.umovy_karystannia);
        umovy.setVisibility(View.GONE);
        if (dzenNoch) {
            novyzavet.setBackgroundDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.knopka_red_black));
            staryzavet.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.knopka_red_black));
        }
        return rootview;
    }
}
