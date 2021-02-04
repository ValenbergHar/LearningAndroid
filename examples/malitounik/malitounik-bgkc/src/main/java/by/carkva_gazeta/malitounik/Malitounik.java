package by.carkva_gazeta.malitounik;

import android.app.Application;
import android.content.Context;

import com.google.android.play.core.splitcompat.SplitCompat;

public class Malitounik extends Application {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        SplitCompat.install(this);
    }
}
