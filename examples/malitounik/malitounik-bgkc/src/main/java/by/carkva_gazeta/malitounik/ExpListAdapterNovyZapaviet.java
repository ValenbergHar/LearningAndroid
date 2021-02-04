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

class ExpListAdapterNovyZapaviet extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<String>> mGroups;
    private final Activity mContext;

    ExpListAdapterNovyZapaviet(Activity context, ArrayList<ArrayList<String>> groups) {
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
                textGroup.setText("Паводле Мацьвея");
                break;
            case 1:
                textGroup.setText("Паводле Марка");
                break;
            case 2:
                textGroup.setText("Паводле Лукаша");
                break;
            case 3:
                textGroup.setText("Паводле Яна");
                break;
            case 4:
                textGroup.setText("Дзеі Апосталаў");
                break;
            case 5:
                textGroup.setText("Якава");
                break;
            case 6:
                textGroup.setText("1-е Пятра");
                break;
            case 7:
                textGroup.setText("2-е Пятра");
                break;
            case 8:
                textGroup.setText("1-е Яна Багаслова");
                break;
            case 9:
                textGroup.setText("2-е Яна Багаслова");
                break;
            case 10:
                textGroup.setText("3-е Яна Багаслова");
                break;
            case 11:
                textGroup.setText("Юды");
                break;
            case 12:
                textGroup.setText("Да Рымлянаў");
                break;
            case 13:
                textGroup.setText("1-е да Карынфянаў");
                break;
            case 14:
                textGroup.setText("2-е да Карынфянаў");
                break;
            case 15:
                textGroup.setText("Да Галятаў");
                break;
            case 16:
                textGroup.setText("Да Эфэсянаў");
                break;
            case 17:
                textGroup.setText("Да Піліпянаў");
                break;
            case 18:
                textGroup.setText("Да Каласянаў");
                break;
            case 19:
                textGroup.setText("1-е да Фесаланікійцаў");
                break;
            case 20:
                textGroup.setText("2-е да Фесаланікійцаў");
                break;
            case 21:
                textGroup.setText("1-е да Цімафея");
                break;
            case 22:
                textGroup.setText("2-е да Цімафея");
                break;
            case 23:
                textGroup.setText("Да Ціта");
                break;
            case 24:
                textGroup.setText("Да Філімона");
                break;
            case 25:
                textGroup.setText("Да Габрэяў");
                break;
            case 26:
                textGroup.setText("Адкрыцьцё (Апакаліпсіс)");
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
