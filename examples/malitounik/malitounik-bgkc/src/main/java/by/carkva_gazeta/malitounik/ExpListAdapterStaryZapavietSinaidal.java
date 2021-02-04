package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by oleg on 3.10.16
 */

class ExpListAdapterStaryZapavietSinaidal extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<String>> mGroups;
    private final Activity mContext;

    ExpListAdapterStaryZapavietSinaidal(Activity context, ArrayList<ArrayList<String>> groups) {
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mContext.getLayoutInflater().inflate(R.layout.group_view, parent, false);
        }

        TextView_Roboto_Condensed textGroup = convertView.findViewById(R.id.textGroup);
        SharedPreferences k = mContext.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        textGroup.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) textGroup.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
        switch (groupPosition) {
            case 0:
                textGroup.setText("Бытие");
                break;
            case 1:
                textGroup.setText("Исход");
                break;
            case 2:
                textGroup.setText("Левит");
                break;
            case 3:
                textGroup.setText("Числа");
                break;
            case 4:
                textGroup.setText("Второзаконие");
                break;
            case 5:
                textGroup.setText("Иисуса Навина");
                break;
            case 6:
                textGroup.setText("Судей израилевых");
                break;
            case 7:
                textGroup.setText("Руфи");
                break;
            case 8:
                textGroup.setText("1-я Царств");
                break;
            case 9:
                textGroup.setText("2-я Царств");
                break;
            case 10:
                textGroup.setText("3-я Царств");
                break;
            case 11:
                textGroup.setText("4-я Царств");
                break;
            case 12:
                textGroup.setText("1-я Паралипоменон");
                break;
            case 13:
                textGroup.setText("2-я Паралипоменон");
                break;
            case 14:
                textGroup.setText("1-я Ездры");
                break;
            case 15:
                textGroup.setText("Неемии");
                break;
            case 16:
                textGroup.setText("2-я Ездры");
                break;
            case 17:
                textGroup.setText("Товита");
                break;
            case 18:
                textGroup.setText("Иудифи");
                break;
            case 19:
                textGroup.setText("Есфири");
                break;
            case 20:
                textGroup.setText("Иова");
                break;
            case 21:
                textGroup.setText("Псалтирь");
                break;
            case 22:
                textGroup.setText("Притчи Соломона");
                break;
            case 23:
                textGroup.setText("Екклезиаста");
                break;
            case 24:
                textGroup.setText("Песнь песней Соломона");
                break;
            case 25:
                textGroup.setText("Премудрости Соломона");
                break;
            case 26:
                textGroup.setText("Премудрости Иисуса, сына Сирахова");
                break;
            case 27:
                textGroup.setText("Исаии");
                break;
            case 28:
                textGroup.setText("Иеремии");
                break;
            case 29:
                textGroup.setText("Плач Иеремии");
                break;
            case 30:
                textGroup.setText("Послание Иеремии");
                break;
            case 31:
                textGroup.setText("Варуха");
                break;
            case 32:
                textGroup.setText("Иезекииля");
                break;
            case 33:
                textGroup.setText("Даниила");
                break;
            case 34:
                textGroup.setText("Осии");
                break;
            case 35:
                textGroup.setText("Иоиля");
                break;
            case 36:
                textGroup.setText("Амоса");
                break;
            case 37:
                textGroup.setText("Авдия");
                break;
            case 38:
                textGroup.setText("Ионы");
                break;
            case 39:
                textGroup.setText("Михея");
                break;
            case 40:
                textGroup.setText("Наума");
                break;
            case 41:
                textGroup.setText("Аввакума");
                break;
            case 42:
                textGroup.setText("Сафонии");
                break;
            case 43:
                textGroup.setText("Аггея");
                break;
            case 44:
                textGroup.setText("Захарии");
                break;
            case 45:
                textGroup.setText("Малахии");
                break;
            case 46:
                textGroup.setText("1-я Маккавейская");
                break;
            case 47:
                textGroup.setText("2-я Маккавейская");
                break;
            case 48:
                textGroup.setText("3-я Маккавейская");
                break;
            case 49:
                textGroup.setText("3-я Ездры");
                break;
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mContext.getLayoutInflater().inflate(R.layout.child_view, parent, false);
        }

        TextView_Roboto_Condensed textChild = convertView.findViewById(R.id.textChild);
        SharedPreferences k = mContext.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        textChild.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);
        boolean dzenNoch = k.getBoolean("dzen_noch", false);
        if (dzenNoch) {
            textChild.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            textChild.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        }
        textChild.setText(mGroups.get(groupPosition).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
