package by.carkva_gazeta.malitounik;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.Objects;

/**
 * Created by oleg on 1.8.16
 */
public class pasxa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainActivity.checkBrightness) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) MainActivity.brightness / 100;
            getWindow().setAttributes(lp);
        }
        SharedPreferences chin = getSharedPreferences("biblia", MODE_PRIVATE);
        int fontBiblia = chin.getInt("font_malitounik", SettingsActivity.GET_DEFAULT_FONT_SIZE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        if (dzenNoch) setTheme(R.style.AppCompatDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasxa);
        TextView_Roboto_Condensed stv = findViewById(R.id.title_toolbar);
        stv.setOnClickListener((v) -> {
            stv.setHorizontallyScrolling(true);
            stv.setFreezesText(true);
            stv.setMarqueeRepeatLimit(-1);
            if (stv.isSelected()) {
                stv.setEllipsize(TextUtils.TruncateAt.END);
                stv.setSelected(false);
            } else {
                stv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                stv.setSelected(true);
            }
        });
        stv.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN + 4);
        stv.setText(R.string.pascha_kaliandar_bel);
        TextView textViewTitle = findViewById(R.id.title);
        textViewTitle.setText(getResources().getText(R.string.pascha_kaliandar_bel));
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
        TextView textView = findViewById(R.id.pasxa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (dzenNoch) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
                window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary_text));
            }
            textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_black));
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorIcons));
        }
        textView.setText("24 лютага 1582 году Папа Рыгор XIII зацьвердзіў адмысловай булай “Inter gravissimas” праект новага календара, падрыхтаванага італійскім матэматыкам і лекарам Луіджы Ліліё і нямецкім матэматыкам і астраномам езуітам Хрыстофам Клавіюсам. Гэты новы каляндар атрымаў назоў “грыгарыянскага календара”. З гэтага часу дата Вялікадня пачала вылічацца паводле новых табліцаў.\n\nУ Праваслаўнай Царкве дата Вялікадня па-ранейшаму разьлічваецца паводле сваёй александрыйскай пасхаліі.\n\nПаколькі ў выніку недакладнасьці старога юльянскага календара з IV па XVI стагоддзе назьбіралася 10 лішніх дзён, адлік дзён быў перасунуты наперад і адразу пасьля 4 кастрычніка 1582 году наступіў дзень 15 кастрычніка. Адначасова была выпраўлена памылка з часоў Нікейскага Сабору IV стагоддзя, і астранамічны час веснавога раўнадзенства (21 сакавіка) пачаў ізноў супадаць з каляндарным.\n\nУ 1583 годзе Папа Рыгор XIII накіраваў Канстанцінопальскаму Патрыярху Ераміі II пасольства з прапановай перайсьці на новы каляндар. Аднак на саборы ў Канстанцінопалі ў канцы 1583 году гэтую прапанову праваслаўныя палічылі неадпаведнай кананічным правілам і адкінулі.\n\nНа тэрыторыі сучаснай Беларусі, землі якой былі ў складзе Вялікага Княства Літоўскага, грыгарыянскі каляндар быў уведзены 15 кастрычніка 1582 году, як і ва ўсёй Рэчы Паспалітай і ва ўсіх каталіцкіх краінах Еўропы. Пры гэтым Ўсходняй Царкве (як Праваслаўнай, так і Зьяднанай з Рымам) у Рэчы Паспалітай было дазволена адзначаць свае царкоўныя сьвяты паводле старога юльянскага календара.\n\nПасьля падзелаў Рэчы Паспалітай і далучэньня беларускіх земляў да Расейскай імперыі ў 1772-1795 гг. нашыя продкі зноў вымушаныя былі жыць паводле старога стылю (юльянскага календара), хоць у пісьмовых дакументах нярэдка паралельна пазначаліся таксама даты паводле новага стылю (грыгарыянскага календара), паводле якога жыла амаль уся Еўропа.\n\nЗ 1918 году на беларускіх землях, якія апынуліся ў складзе Польшчы і на тэрыторыі пад Саветамі, зноў быў уведзены грыгарыянскі каляндар. Усходняя Царква працягвала адзначаць свае царкоўныя сьвяты паводле старога стылю.\n\nАд моманту адраджэньня Грэка-Каталіцкай Царквы ў Беларусі на пачатку 1990-х гадоў яна ў сваім царкоўным жыцьці таксама карыстаецца грыгарыянскім календаром (у адрозьненьне ад украінскіх і расейскіх грэка-католікаў).\n\nГрыгарыянскі каляндар (новы стыль) сёньня апярэджвае юльянскі (стары стыль) на 13 дзён (з 1900 году). У 1582-1699 гг. гэтая розьніца складала 10 дзён, у 1700-1799 гг. — 11 дзён, а ў 1800-1899 гады — 12 дзён.");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontBiblia);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.alphain, R.anim.alphaout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
