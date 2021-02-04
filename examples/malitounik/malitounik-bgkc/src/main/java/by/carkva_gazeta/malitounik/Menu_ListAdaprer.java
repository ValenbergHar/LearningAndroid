package by.carkva_gazeta.malitounik;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by oleg on 17.1.18
 */

public class Menu_ListAdaprer extends ArrayAdapter<String> {

    private final Activity mContext;
    private String[] items;
    private ArrayList<String> itemsL;
    private final SharedPreferences k;

    Menu_ListAdaprer(@NonNull Activity context, @NonNull String[] strings) {
        super(context, R.layout.simple_list_item_2, R.id.label, strings);
        mContext = context;
        items = strings;
        k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
    }

    public Menu_ListAdaprer(@NonNull Activity context, @NonNull ArrayList<String> strings) {
        super(context, R.layout.simple_list_item_2, R.id.label, strings);
        mContext = context;
        itemsL = strings;
        k = context.getSharedPreferences("biblia", Context.MODE_PRIVATE);
    }

    @Override
    public void add(@Nullable String string) {
        super.add(string);
        itemsL.add(string);
    }

    @Override
    public void remove(@Nullable String string) {
        super.remove(string);
        itemsL.remove(string);
    }

    @Override
    public void clear() {
        super.clear();
        itemsL.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View mView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (mView == null) {
            mView = mContext.getLayoutInflater().inflate(R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            mView.setTag(viewHolder);
            viewHolder.text = mView.findViewById(R.id.label);
        } else {
            viewHolder = (ViewHolder) mView.getTag();
        }

        boolean dzenNoch = k.getBoolean("dzen_noch", false);

        if (items != null) viewHolder.text.setText(items[position]);
        else if (itemsL != null) viewHolder.text.setText(itemsL.get(position));

        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, SettingsActivity.GET_FONT_SIZE_MIN);

        if (dzenNoch) {
            viewHolder.text.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.selector_dark));
            viewHolder.text.setTextColor(ContextCompat.getColor(mContext, R.color.colorIcons));
            viewHolder.text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stiker_black, 0, 0, 0);
        }
        return mView;
    }

    private static class ViewHolder {
        TextView_Roboto_Condensed text;
    }
}
