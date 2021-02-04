package by.carkva_gazeta.malitounik;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by oleg on 22.4.17
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String WIDGETMUN = "widget_mun";
            if (extras.getBoolean(WIDGETMUN, false)) {
                intent.putExtra(WIDGETMUN, true);
                intent.putExtra("DayYear", extras.getInt("DayYear"));
                intent.putExtra("Year", extras.getInt("Year"));
            }
            String WIDGETDAY = "widget_day";
            if (extras.getBoolean(WIDGETDAY, false)) {
                intent.putExtra(WIDGETDAY, true);
            }
            if (extras.getBoolean("sabytie", false)) {
                intent.putExtra("data", extras.getInt("data"));
                intent.putExtra("year", extras.getInt("year"));
                intent.putExtra("sabytie", true);
                intent.putExtra("sabytieView", extras.getBoolean("sabytieView", false));
                intent.putExtra("sabytieTitle", extras.getString("sabytieTitle"));
            }
        }
        if (data != null) {
            intent.setData(data);
        }
        startActivity(intent);
        finish();
    }
}
