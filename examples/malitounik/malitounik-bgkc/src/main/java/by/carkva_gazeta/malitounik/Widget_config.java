package by.carkva_gazeta.malitounik;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Widget_config extends AppCompatActivity implements Dialog_widget_config.Dialog_widget_config_Listener {

    private int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Intent resultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        setResult(RESULT_CANCELED, resultValue);

        Dialog_widget_config config = Dialog_widget_config.getInstance(widgetID);
        config.show(getSupportFragmentManager(), "config");
    }

    @Override
    public void onDialogWidgetConfigPositiveClick() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Widget.kaliandar(this, appWidgetManager, widgetID);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
