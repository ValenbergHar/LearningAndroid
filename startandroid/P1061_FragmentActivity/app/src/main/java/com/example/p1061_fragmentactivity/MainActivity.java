package com.example.p1061_fragmentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements Fragment2.onSomeEventListener {
    private String string = "Жыве Беларусь!";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment frag2 = new Fragment2();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment2, frag2);
        ft.commit();
    }

    @Override
    public void someEvent(String s) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.fragment1);
        ((TextView)frag1.getView().findViewById(R.id.textView)).setText(string);
    }
}