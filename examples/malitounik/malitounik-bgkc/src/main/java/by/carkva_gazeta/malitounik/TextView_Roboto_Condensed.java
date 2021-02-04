package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by oleg on 23.12.17
 */

public class TextView_Roboto_Condensed extends AppCompatTextView {

    public TextView_Roboto_Condensed(Context context) {
        super(context);
    }

    public TextView_Roboto_Condensed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView_Roboto_Condensed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        setTypeface(createFont(style));
    }

    public static Typeface createFont(int style) {
        Typeface font;
        switch (style) {
            case Typeface.BOLD:
                font = Typeface.create("sans-serif-condensed", Typeface.BOLD);
                break;
            case Typeface.ITALIC:
                font = Typeface.create("sans-serif-condensed", Typeface.ITALIC);
                break;
            case Typeface.BOLD_ITALIC:
                font = Typeface.create("sans-serif-condensed", Typeface.BOLD_ITALIC);
                break;
            default:
                font = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
                break;
        }
        return font;
    }
}
