package com.example.p0681_parcel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    final String LOG_TAG = "myLogs";

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View v) {
        MyObject myObj = new MyObject("text", 1);
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(MyObject.class.getCanonicalName(), myObj);
        Log.d(LOG_TAG, "startActivity");
        startActivity(intent);
    }
}