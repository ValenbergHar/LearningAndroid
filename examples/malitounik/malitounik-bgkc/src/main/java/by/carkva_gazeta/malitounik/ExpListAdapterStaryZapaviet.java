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

class ExpListAdapterStaryZapaviet extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<String>> mGroups;
    private final Activity mContext;

    ExpListAdapterStaryZapaviet(Activity context, ArrayList<ArrayList<String>> groups) {
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
                textGroup.setText("Быцьцё");
                break;
            case 1:
                textGroup.setText("Выхад");
                break;
            case 2:
                textGroup.setText("Лявіт");
                break;
            case 3:
                textGroup.setText("Лікі");
                break;
            case 4:
                textGroup.setText("Другі Закон");
                break;
            case 5:
                textGroup.setText("Ісуса сына Нава");
                break;
            case 6:
                textGroup.setText("Судзьдзяў");
                break;
            case 7:
                textGroup.setText("Рут");
                break;
            case 8:
                textGroup.setText("1-я Царстваў");
                break;
            case 9:
                textGroup.setText("2-я Царстваў");
                break;
            case 10:
                textGroup.setText("3-я Царстваў");
                break;
            case 11:
                textGroup.setText("4-я Царстваў");
                break;
            case 12:
                textGroup.setText("1-я Летапісаў");
                break;
            case 13:
                textGroup.setText("2-я Летапісаў");
                break;
            case 14:
                textGroup.setText("Эздры");
                break;
            case 15:
                textGroup.setText("Нээміі");
                break;
            case 16:
                textGroup.setText("Эстэр");
                break;
            case 17:
                textGroup.setText("Ёва");
                break;
            case 18:
                textGroup.setText("Псалтыр");
                break;
            case 19:
                textGroup.setText("Выслоўяў Саламонавых");
                break;
            case 20:
                textGroup.setText("Эклезіяста");
                break;
            case 21:
                textGroup.setText("Найвышэйшая Песьня Саламонава");
                break;
            case 22:
                textGroup.setText("Ісаі");
                break;
            case 23:
                textGroup.setText("Ераміі");
                break;
            case 24:
                textGroup.setText("Ераміін Плач");
                break;
            case 25:
                textGroup.setText("Езэкііля");
                break;
            case 26:
                textGroup.setText("Данііла");
                break;
            case 27:
                textGroup.setText("Асіі");
                break;
            case 28:
                textGroup.setText("Ёіля");
                break;
            case 29:
                textGroup.setText("Амоса");
                break;
            case 30:
                textGroup.setText("Аўдзея");
                break;
            case 31:
                textGroup.setText("Ёны");
                break;
            case 32:
                textGroup.setText("Міхея");
                break;
            case 33:
                textGroup.setText("Навума");
                break;
            case 34:
                textGroup.setText("Абакума");
                break;
            case 35:
                textGroup.setText("Сафона");
                break;
            case 36:
                textGroup.setText("Агея");
                break;
            case 37:
                textGroup.setText("Захарыі");
                break;
            case 38:
                textGroup.setText("Малахіі");
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
