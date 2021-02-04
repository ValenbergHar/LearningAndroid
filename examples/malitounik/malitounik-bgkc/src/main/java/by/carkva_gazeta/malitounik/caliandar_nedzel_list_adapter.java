package by.carkva_gazeta.malitounik;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

class caliandar_nedzel_list_adapter extends ArrayAdapter<String> {

    private final Activity mContext;
    private final ArrayList<ArrayList<String>> dannye;
    private final GregorianCalendar c;
    private final SharedPreferences chin;
    private final String[] munName = {"студзеня", "лютага", "сакавіка", "красавіка", "траўня", "чэрвеня", "ліпеня", "жніўня", "верасьня", "кастрычніка", "лістапада", "сьнежня"};
    private final String[] NedelName = {"", "нядзеля", "панядзелак", "аўторак", "серада", "чацьвер", "пятніца", "субота"};

    caliandar_nedzel_list_adapter(@NonNull Activity activity, @NonNull ArrayList<ArrayList<String>> arrayLists, String[] strings) {
        super(activity, R.layout.calaindar_nedel, strings);
        mContext = activity;
        c = (GregorianCalendar) Calendar.getInstance();
        chin = mContext.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        dannye = arrayLists;
    }

    @SuppressWarnings("MagicConstant")
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View rootView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (rootView == null) {
            rootView = mContext.getLayoutInflater().inflate(R.layout.calaindar_nedel, parent, false);
            viewHolder = new ViewHolder();
            rootView.setTag(viewHolder);
            viewHolder.textCalendar = rootView.findViewById(R.id.textCalendar);
            viewHolder.textPraz = rootView.findViewById(R.id.textCviatyGlavnyia);
            viewHolder.textSviat = rootView.findViewById(R.id.textSviatyia);
            viewHolder.textPostS = rootView.findViewById(R.id.textPost);
            viewHolder.linearLayout = rootView.findViewById(R.id.linearView);
        } else {
            viewHolder = (ViewHolder) rootView.getTag();
        }
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        //по умолчанию
        viewHolder.textCalendar.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
        viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDivider));
        viewHolder.textSviat.setVisibility(View.VISIBLE);
        viewHolder.textPraz.setVisibility(View.GONE);
        viewHolder.textPostS.setVisibility(View.GONE);
        viewHolder.textPraz.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        viewHolder.textPraz.setTypeface(null, Typeface.BOLD);

        if (dzenNoch) {
            viewHolder.textSviat.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            viewHolder.textPraz.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_black));
        }

        if (c.get(Calendar.YEAR) == Integer.parseInt(dannye.get(position).get(3)) && c.get(Calendar.DATE) == Integer.parseInt(dannye.get(position).get(1))  && c.get(Calendar.MONTH) == Integer.parseInt(dannye.get(position).get(2))) {
            if (dzenNoch) viewHolder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.calendar_nedel_today_black));
            else viewHolder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.calendar_nedel_today));
        } else {
            viewHolder.linearLayout.setBackgroundDrawable(null);
        }
        
        if (Integer.parseInt(dannye.get(position).get(3)) != c.get(Calendar.YEAR))
            viewHolder.textCalendar.setText(NedelName[Integer.parseInt(dannye.get(position).get(0))]+ " " + dannye.get(position).get(1) + " " + munName[Integer.parseInt(dannye.get(position).get(2))] + ", " + dannye.get(position).get(3));
        else viewHolder.textCalendar.setText(NedelName[Integer.parseInt(dannye.get(position).get(0))]+ " " + dannye.get(position).get(1) + " " + munName[Integer.parseInt(dannye.get(position).get(2))]);
        //viewHolder.textPraz.setText(dannye.get(position).get(3)); Год
        String sviatyia = dannye.get(position).get(4);
        if (dzenNoch) {
            sviatyia = sviatyia.replace("#d00505", "#f44336");
        }
        viewHolder.textSviat.setText(MainActivity.fromHtml(sviatyia));
        if (dannye.get(position).get(4).contains("no_sviatyia")) viewHolder.textSviat.setVisibility(View.GONE);

        viewHolder.textPraz.setText(dannye.get(position).get(6));
        if (!dannye.get(position).get(6).contains("no_sviaty"))
            viewHolder.textPraz.setVisibility(View.VISIBLE);
        // убот = субота
        if (dannye.get(position).get(6).contains("Пачатак") || dannye.get(position).get(6).contains("Вялікі") || dannye.get(position).get(6).contains("Вялікая") || dannye.get(position).get(6).contains("убот") || dannye.get(position).get(6).contains("ВЕЧАР") || dannye.get(position).get(6).contains("Палова") ) {
            viewHolder.textPraz.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
            viewHolder.textPraz.setTypeface(null, Typeface.NORMAL);
        }
        if (dannye.get(position).get(5).contains("1") || dannye.get(position).get(5).contains("2") || dannye.get(position).get(5).contains("3")) {
            viewHolder.textCalendar.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            if (dzenNoch) viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary_black));
            else viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else if (dannye.get(position).get(7).contains("2")) {
            viewHolder.textCalendar.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
            viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPost));
            viewHolder.textPostS.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
            viewHolder.textPostS.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPost));
            viewHolder.textPostS.setText(mContext.getResources().getString(R.string.Post));
        } else if (dannye.get(position).get(7).contains("3")) {
            viewHolder.textCalendar.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorStrogiPost));
        } else if (dannye.get(position).get(7).contains("1")) {
            viewHolder.textCalendar.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
            viewHolder.textCalendar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBezPosta));
            viewHolder.textPostS.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary_text));
            viewHolder.textPostS.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBezPosta));
            viewHolder.textPostS.setText(mContext.getResources().getString(R.string.No_post));
        }
        if (dannye.get(position).get(5).contains("2")) {
            viewHolder.textPraz.setTypeface(null, Typeface.NORMAL);
        }
        if (dannye.get(position).get(7).contains("3")) {
            viewHolder.textPostS.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            viewHolder.textPostS.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorStrogiPost));
            viewHolder.textPostS.setText(mContext.getResources().getString(R.string.Strogi_post));
            viewHolder.textPostS.setVisibility(View.VISIBLE);
        } else if (dannye.get(position).get(0).contains("6")) {// Пятница
            viewHolder.textPostS.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private static class ViewHolder {
        LinearLayout linearLayout;
        TextView_Roboto_Condensed textCalendar;
        TextView_Roboto_Condensed textPraz;
        TextView_Roboto_Condensed textSviat;
        TextView_Roboto_Condensed textPostS;
    }
}
