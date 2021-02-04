package by.carkva_gazeta.resources;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.SettingsActivity;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class psalterNadsana extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences k;
    private boolean dzenNoch;
    private TextView_Roboto_Condensed buttonL;
    private TextView_Roboto_Condensed buttonR;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        super.onCreate(savedInstanceState);
        k = getSharedPreferences("biblia", MODE_PRIVATE);
        dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
        setContentView(by.carkva_gazeta.malitounik.R.layout.nadsan_pravila);
        int pNadsana = k.getInt("pravalaNadsana", 1);
        buttonL = findViewById(by.carkva_gazeta.malitounik.R.id.buttonleft);
        buttonR = findViewById(by.carkva_gazeta.malitounik.R.id.buttonrighth);
        buttonL.setOnClickListener(this);
        buttonR.setOnClickListener(this);
        if (pNadsana == 1)
            buttonL.setVisibility(View.GONE);
        if (pNadsana == 5)
            buttonR.setVisibility(View.GONE);
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.setCustomAnimations(by.carkva_gazeta.malitounik.R.anim.alphainfragment, by.carkva_gazeta.malitounik.R.anim.alphaoutfragment);
        switch (pNadsana) {
            case 1:
                psalterNadsana1 nadsana1 = new psalterNadsana1(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana1);
                break;
            case 2:
                psalterNadsana2 nadsana2 = new psalterNadsana2(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana2);
                break;
            case 3:
                psalterNadsana3 nadsana3 = new psalterNadsana3(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana3);
                break;
            case 4:
                psalterNadsana4 nadsana4 = new psalterNadsana4(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana4);
                break;
            case 5:
                psalterNadsana5 nadsana5 = new psalterNadsana5(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana5);
                break;
        }
        ftrans.commit();
        setTollbarTheme();
    }

    @Override
    public void onClick(View v) {
        int pNadsana = k.getInt("pravalaNadsana", 1);
        SharedPreferences.Editor prefEditors = k.edit();
        int id = v.getId();
        switch (id) {
            case by.carkva_gazeta.malitounik.R.id.buttonleft:
                pNadsana = pNadsana - 1;
                prefEditors.putInt("pravalaNadsana", pNadsana);
                break;
            case by.carkva_gazeta.malitounik.R.id.buttonrighth:
                pNadsana = pNadsana + 1;
                prefEditors.putInt("pravalaNadsana", pNadsana);
                break;
        }
        prefEditors.apply();
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.setCustomAnimations(by.carkva_gazeta.malitounik.R.anim.alphainfragment, by.carkva_gazeta.malitounik.R.anim.alphaoutfragment);
        switch (pNadsana) {
            case 1:
                psalterNadsana1 nadsana1 = new psalterNadsana1(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana1);
                break;
            case 2:
                psalterNadsana2 nadsana2 = new psalterNadsana2(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana2);
                break;
            case 3:
                psalterNadsana3 nadsana3 = new psalterNadsana3(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana3);
                break;
            case 4:
                psalterNadsana4 nadsana4 = new psalterNadsana4(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana4);
                break;
            case 5:
                psalterNadsana5 nadsana5 = new psalterNadsana5(this);
                ftrans.replace(by.carkva_gazeta.malitounik.R.id.conteiner, nadsana5);
                break;
        }
        ftrans.commit();
        if (pNadsana == 1)
            buttonL.setVisibility(View.GONE);
        else
            buttonL.setVisibility(View.VISIBLE);
        if (pNadsana == 5)
            buttonR.setVisibility(View.GONE);
        else
            buttonR.setVisibility(View.VISIBLE);
    }

    private void setTollbarTheme() {
        Toolbar toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.toolbar);
        TextView_Roboto_Condensed title_toolbar = findViewById(by.carkva_gazeta.malitounik.R.id.title_toolbar);
        title_toolbar.setOnClickListener((v) -> {
            title_toolbar.setHorizontallyScrolling(true);
            title_toolbar.setFreezesText(true);
            title_toolbar.setMarqueeRepeatLimit(-1);
            if (title_toolbar.isSelected()) {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.END);
                title_toolbar.setSelected(false);
            } else {
                title_toolbar.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                title_toolbar.setSelected(true);
            }
        });
        title_toolbar.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        title_toolbar.setText(by.carkva_gazeta.malitounik.R.string.title_psalter_privila);
        if (dzenNoch) {
            toolbar.setPopupTheme(by.carkva_gazeta.malitounik.R.style.AppCompatDark);
            toolbar.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(by.carkva_gazeta.malitounik.R.anim.alphain, by.carkva_gazeta.malitounik.R.anim.alphaout);
    }
}
