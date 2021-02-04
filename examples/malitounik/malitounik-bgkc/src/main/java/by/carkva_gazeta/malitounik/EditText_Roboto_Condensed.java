package by.carkva_gazeta.malitounik;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by oleg on 25.12.17
 */

public class EditText_Roboto_Condensed extends AppCompatEditText {

    public EditText_Roboto_Condensed(Context context) {
        super(context);
    }

    public EditText_Roboto_Condensed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText_Roboto_Condensed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        setTypeface(TextView_Roboto_Condensed.createFont(style));
    }
}
