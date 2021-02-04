package by.carkva_gazeta.resources;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import by.carkva_gazeta.malitounik.MainActivity;
import by.carkva_gazeta.malitounik.MaranAta_Global_List;
import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

/**
 * Created by oleg on 7.12.16
 */

class ExpArrayAdapterParallel extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> stixi;
    private final int kniga;
    private final int glava;
    private final boolean Zapavet;
    private final int mPerevod; // 1-Сёмуха, 2-Синоидальный, 3-Псалтырь Надсана

    ExpArrayAdapterParallel(Activity ctx, ArrayList<String> bible, int Kniga, int Glava, boolean nonyZapavet, int Perevod) {
        super(ctx, by.carkva_gazeta.malitounik.R.layout.simple_list_item_bible, bible);
        context = ctx;
        stixi = bible;
        kniga = Kniga;
        glava = Glava;
        Zapavet = nonyZapavet;
        mPerevod = Perevod;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup) {

        SharedPreferences k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        ExpArrayAdapterParallelItems ea;
        if (convertView == null) {
            ea = new ExpArrayAdapterParallelItems();
            convertView = context.getLayoutInflater().inflate(by.carkva_gazeta.malitounik.R.layout.simple_list_item_bible, viewGroup, false);
            ea.textView = convertView.findViewById(by.carkva_gazeta.malitounik.R.id.label);
            ea.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, k.getInt("font_malitounik", 18));
            convertView.setTag(ea);
        } else {
            ea = (ExpArrayAdapterParallelItems) convertView.getTag();
        }
        Biblia_parallel_chtenia parallel = new Biblia_parallel_chtenia();
        String res = "+-+";
        if (Zapavet) {
            if (kniga == 0) {
                res = parallel.kniga51(glava + 1, position + 1);
            }
            if (kniga == 1) {
                res = parallel.kniga52(glava + 1, position + 1);
            }
            if (kniga == 2) {
                res = parallel.kniga53(glava + 1, position + 1);
            }
            if (kniga == 3) {
                res = parallel.kniga54(glava + 1, position + 1);
            }
            if (kniga == 4) {
                res = parallel.kniga55(glava + 1, position + 1);
            }
            if (kniga == 5) {
                res = parallel.kniga56(glava + 1, position + 1);
            }
            if (kniga == 6) {
                res = parallel.kniga57(glava + 1, position + 1);
            }
            if (kniga == 7) {
                res = parallel.kniga58(glava + 1, position + 1);
            }
            if (kniga == 8) {
                res = parallel.kniga59(glava + 1, position + 1);
            }
            if (kniga == 9) {
                res = parallel.kniga60(glava + 1, position + 1);
            }
            if (kniga == 10) {
                res = parallel.kniga61(glava + 1, position + 1);
            }
            if (kniga == 11) {
                res = parallel.kniga62(glava + 1, position + 1);
            }
            if (kniga == 12) {
                res = parallel.kniga63(glava + 1, position + 1);
            }
            if (kniga == 13) {
                res = parallel.kniga64(glava + 1, position + 1);
            }
            if (kniga == 14) {
                res = parallel.kniga65(glava + 1, position + 1);
            }
            if (kniga == 15) {
                res = parallel.kniga66(glava + 1, position + 1);
            }
            if (kniga == 16) {
                res = parallel.kniga67(glava + 1, position + 1);
            }
            if (kniga == 17) {
                res = parallel.kniga68(glava + 1, position + 1);
            }
            if (kniga == 18) {
                res = parallel.kniga69(glava + 1, position + 1);
            }
            if (kniga == 19) {
                res = parallel.kniga70(glava + 1, position + 1);
            }
            if (kniga == 20) {
                res = parallel.kniga71(glava + 1, position + 1);
            }
            if (kniga == 21) {
                res = parallel.kniga72(glava + 1, position + 1);
            }
            if (kniga == 22) {
                res = parallel.kniga73(glava + 1, position + 1);
            }
            if (kniga == 23) {
                res = parallel.kniga74(glava + 1, position + 1);
            }
            if (kniga == 24) {
                res = parallel.kniga75(glava + 1, position + 1);
            }
            if (kniga == 25) {
                res = parallel.kniga76(glava + 1, position + 1);
            }
            if (kniga == 26) {
                res = parallel.kniga77(glava + 1, position + 1);
            }
        } else {
            if (kniga == 0) {
                res = parallel.kniga1(glava + 1, position + 1);
            }
            if (kniga == 1) {
                res = parallel.kniga2(glava + 1, position + 1);
            }
            if (kniga == 2) {
                res = parallel.kniga3(glava + 1, position + 1);
            }
            if (kniga == 3) {
                res = parallel.kniga4(glava + 1, position + 1);
            }
            if (kniga == 4) {
                res = parallel.kniga5(glava + 1, position + 1);
            }
            if (kniga == 5) {
                res = parallel.kniga6(glava + 1, position + 1);
            }
            if (kniga == 6) {
                res = parallel.kniga7(glava + 1, position + 1);
            }
            if (kniga == 7) {
                res = parallel.kniga8(glava + 1, position + 1);
            }
            if (kniga == 8) {
                res = parallel.kniga9(glava + 1, position + 1);
            }
            if (kniga == 9) {
                res = parallel.kniga10(glava + 1, position + 1);
            }
            if (kniga == 10) {
                res = parallel.kniga11(glava + 1, position + 1);
            }
            if (kniga == 11) {
                res = parallel.kniga12(glava + 1, position + 1);
            }
            if (kniga == 12) {
                res = parallel.kniga13(glava + 1, position + 1);
            }
            if (kniga == 13) {
                res = parallel.kniga14(glava + 1, position + 1);
            }
            if (kniga == 14) {
                res = parallel.kniga15(glava + 1, position + 1);
            }
            if (kniga == 15) {
                res = parallel.kniga16(glava + 1, position + 1);
            }
            if (kniga == 16) {
                res = parallel.kniga17(glava + 1, position + 1);
            }
            if (kniga == 17) {
                res = parallel.kniga18(glava + 1, position + 1);
            }
            if (kniga == 18) {
                res = parallel.kniga19(glava + 1, position + 1);
            }
            if (kniga == 19) {
                res = parallel.kniga20(glava + 1, position + 1);
            }
            if (kniga == 20) {
                res = parallel.kniga21(glava + 1, position + 1);
            }
            if (kniga == 21) {
                res = parallel.kniga22(glava + 1, position + 1);
            }
            if (kniga == 22) {
                res = parallel.kniga23(glava + 1, position + 1);
            }
            if (kniga == 23) {
                res = parallel.kniga24(glava + 1, position + 1);
            }
            if (kniga == 24) {
                res = parallel.kniga25(glava + 1, position + 1);
            }
            if (kniga == 25) {
                res = parallel.kniga26(glava + 1, position + 1);
            }
            if (kniga == 26) {
                res = parallel.kniga27(glava + 1, position + 1);
            }
            if (kniga == 27) {
                res = parallel.kniga28(glava + 1, position + 1);
            }
            if (kniga == 28) {
                res = parallel.kniga29(glava + 1, position + 1);
            }
            if (kniga == 29) {
                res = parallel.kniga30(glava + 1, position + 1);
            }
            if (kniga == 30) {
                res = parallel.kniga31(glava + 1, position + 1);
            }
            if (kniga == 31) {
                res = parallel.kniga32(glava + 1, position + 1);
            }
            if (kniga == 32) {
                res = parallel.kniga33(glava + 1, position + 1);
            }
            if (kniga == 33) {
                res = parallel.kniga34(glava + 1, position + 1);
            }
            if (kniga == 34) {
                res = parallel.kniga35(glava + 1, position + 1);
            }
            if (kniga == 35) {
                res = parallel.kniga36(glava + 1, position + 1);
            }
            if (kniga == 36) {
                res = parallel.kniga37(glava + 1, position + 1);
            }
            if (kniga == 37) {
                res = parallel.kniga38(glava + 1, position + 1);
            }
            if (kniga == 38) {
                res = parallel.kniga39(glava + 1, position + 1);
            }
            if (kniga == 39) {
                res = parallel.kniga40(glava + 1, position + 1);
            }
            if (kniga == 40) {
                res = parallel.kniga41(glava + 1, position + 1);
            }
            if (kniga == 41) {
                res = parallel.kniga42(glava + 1, position + 1);
            }
            if (kniga == 42) {
                res = parallel.kniga43(glava + 1, position + 1);
            }
            if (kniga == 43) {
                res = parallel.kniga44(glava + 1, position + 1);
            }
            if (kniga == 44) {
                res = parallel.kniga45(glava + 1, position + 1);
            }
            if (kniga == 45) {
                res = parallel.kniga46(glava + 1, position + 1);
            }
            if (kniga == 46) {
                res = parallel.kniga47(glava + 1, position + 1);
            }
            if (kniga == 47) {
                res = parallel.kniga48(glava + 1, position + 1);
            }
            if (kniga == 48) {
                res = parallel.kniga49(glava + 1, position + 1);
            }
            if (kniga == 49) {
                res = parallel.kniga50(glava + 1, position + 1);
            }
        }
        String stix = stixi.get(position);
        stix = stix.replace("\\n", "\n");
        if (!Zapavet && kniga == 21 && mPerevod == 1) {
            ea.textView.setText(MainActivity.fromHtml(stix));
        } else {
            ea.textView.setText(stix);
        }
        if (!res.contains("+-+")) {
            if (mPerevod == 1)
                res = MainActivity.translateToBelarus(res);
            SpannableStringBuilder ssb = new SpannableStringBuilder(ea.textView.getText()).append("\n").append(res);//.append("\n");
            int start = ea.textView.getText().length();
            int end = ea.textView.getText().length() + 1 + res.length();
            ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorSecondary_text)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new RelativeSizeSpan(0.7f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            int pos = MaranAta_Global_List.checkPosition(glava, position);
            if (pos != -1) {
                if (MaranAta_Global_List.getVydelenie().get(pos).get(2) == 1) {
                    if (k.getBoolean("dzen_noch", false))
                        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text)), 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorYelloy)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (MaranAta_Global_List.getVydelenie().get(pos).get(3) == 1)
                    ssb.setSpan(new UnderlineSpan(), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (MaranAta_Global_List.getVydelenie().get(pos).get(4) == 1)
                    ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ea.textView.setText(ssb);
        } else {
            SpannableStringBuilder ssb = new SpannableStringBuilder(ea.textView.getText());// + "\n");
            int end = ea.textView.length();
            int pos = MaranAta_Global_List.checkPosition(glava, position);
            if (pos != -1) {
                if (MaranAta_Global_List.getVydelenie().get(pos).get(2) == 1) {
                    if (k.getBoolean("dzen_noch", false))
                        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorPrimary_text)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorYelloy)), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (MaranAta_Global_List.getVydelenie().get(pos).get(3) == 1)
                    ssb.setSpan(new UnderlineSpan(), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (MaranAta_Global_List.getVydelenie().get(pos).get(4) == 1)
                    ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ea.textView.setText(ssb);
        }
        if (position == MaranAta_Global_List.getListPosition() && MaranAta_Global_List.getmPedakVisable()) {
            if (k.getBoolean("dzen_noch", false)) {
                ea.textView.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorprimary_material_dark2);
                ea.textView.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
            } else {
                ea.textView.setBackgroundResource(by.carkva_gazeta.malitounik.R.color.colorDivider);
            }
        } else {
            if (k.getBoolean("dzen_noch", false)) {
                ea.textView.setBackgroundDrawable(ContextCompat.getDrawable(context, by.carkva_gazeta.malitounik.R.drawable.selector_dark));
                ea.textView.setTextColor(ContextCompat.getColor(context, by.carkva_gazeta.malitounik.R.color.colorIcons));
            } else {
                ea.textView.setBackgroundDrawable(ContextCompat.getDrawable(context, by.carkva_gazeta.malitounik.R.drawable.selector_default));
            }
        }
        if (mPerevod == 1) {
            String zav = "0";
            if (Zapavet) zav = "1";
            if (MaranAta_Global_List.getNatatkiSemuxa().size() > 0) {
                for (int i = 0; i < MaranAta_Global_List.getNatatkiSemuxa().size(); i++) {
                    if (MaranAta_Global_List.getNatatkiSemuxa().get(i).get(0).contains(zav) && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(1)) == kniga && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(2)) == glava && Integer.parseInt(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(3)) == position) {
                        SpannableStringBuilder ssb = new SpannableStringBuilder(ea.textView.getText());
                        int nachalo = ssb.length();
                        ssb.append("\nНататка:\n").append(MaranAta_Global_List.getNatatkiSemuxa().get(i).get(5)).append("\n");
                        ssb.setSpan(new StyleSpan(Typeface.ITALIC), nachalo, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ea.textView.setText(ssb);
                        break;
                    }
                }
            }
        }
        if (mPerevod == 2) {
            String zav = "0";
            if (Zapavet) zav = "1";
            if (MaranAta_Global_List.getNatatkiSinodal().size() > 0) {
                for (int i = 0; i < MaranAta_Global_List.getNatatkiSinodal().size(); i++) {
                    if (MaranAta_Global_List.getNatatkiSinodal().get(i).get(0).contains(zav) && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(1)) == kniga && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(2)) == glava && Integer.parseInt(MaranAta_Global_List.getNatatkiSinodal().get(i).get(3)) == position) {
                        SpannableStringBuilder ssb = new SpannableStringBuilder(ea.textView.getText());
                        int nachalo = ssb.length();
                        ssb.append("\nНататка:\n").append(MaranAta_Global_List.getNatatkiSinodal().get(i).get(5)).append("\n");
                        ssb.setSpan(new StyleSpan(Typeface.ITALIC), nachalo, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ea.textView.setText(ssb);
                        break;
                    }
                }
            }
        }
        /*if (mPerevod == 3) {
            String zav = "0";
            if (Zapavet) zav = "1";
            if (MaranAta_Global_List.getNatatkiPsalterNadsana().size() > 0) {
                for (int i = 0; i < MaranAta_Global_List.getNatatkiPsalterNadsana().size(); i++) {
                    if (MaranAta_Global_List.getNatatkiPsalterNadsana().get(i).get(0).contains(zav) && Integer.valueOf(MaranAta_Global_List.getNatatkiPsalterNadsana().get(i).get(1)) == kniga && Integer.valueOf(MaranAta_Global_List.getNatatkiPsalterNadsana().get(i).get(2)) == glava && Integer.valueOf(MaranAta_Global_List.getNatatkiPsalterNadsana().get(i).get(3)) == position) {
                        SpannableStringBuilder ssb = new SpannableStringBuilder(ea.textView.getText());
                        int nachalo = ssb.length();
                        ssb.append("\nНататка:\n").append(MaranAta_Global_List.getNatatkiPsalterNadsana().get(i).get(5)).append("\n");
                        ssb.setSpan(new StyleSpan(Typeface.ITALIC), nachalo, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        ea.textView.setText(ssb);
                        break;
                    }
                }
            }
        }*/
        return convertView;
    }

    static class ExpArrayAdapterParallelItems {
        TextView_Roboto_Condensed textView;
    }
}
