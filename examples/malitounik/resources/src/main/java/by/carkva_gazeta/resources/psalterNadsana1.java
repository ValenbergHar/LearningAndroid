package by.carkva_gazeta.resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import by.carkva_gazeta.malitounik.TextView_Roboto_Condensed;

public class psalterNadsana1 extends Fragment implements View.OnClickListener {

    private final Activity activity;

    psalterNadsana1(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nadsan_pravila1, container, false);
        SharedPreferences chin = activity.getSharedPreferences("biblia", Context.MODE_PRIVATE);
        boolean dzenNoch = chin.getBoolean("dzen_noch", false);
        TextView_Roboto_Condensed t1 = rootView.findViewById(R.id.t1);
        TextView_Roboto_Condensed t2 = rootView.findViewById(R.id.t2);
        TextView_Roboto_Condensed t3 = rootView.findViewById(R.id.t3);
        TextView_Roboto_Condensed t4 = rootView.findViewById(R.id.t4);
        TextView_Roboto_Condensed t5 = rootView.findViewById(R.id.t5);
        TextView_Roboto_Condensed t6 = rootView.findViewById(R.id.t6);
        TextView_Roboto_Condensed t7 = rootView.findViewById(R.id.t7);
        TextView_Roboto_Condensed t8 = rootView.findViewById(R.id.t8);
        TextView_Roboto_Condensed t9 = rootView.findViewById(R.id.t9);
        TextView_Roboto_Condensed t10 = rootView.findViewById(R.id.t10);
        TextView_Roboto_Condensed t11 = rootView.findViewById(R.id.t11);
        TextView_Roboto_Condensed t12 = rootView.findViewById(R.id.t12);
        TextView_Roboto_Condensed t13 = rootView.findViewById(R.id.t13);
        TextView_Roboto_Condensed t14 = rootView.findViewById(R.id.textView1);
        t14.setOnClickListener(this);
        TextView_Roboto_Condensed t15 = rootView.findViewById(R.id.textView2);
        TextView_Roboto_Condensed t16 = rootView.findViewById(R.id.textView3);
        t16.setOnClickListener(this);
        TextView_Roboto_Condensed t17 = rootView.findViewById(R.id.textView4);
        TextView_Roboto_Condensed t18 = rootView.findViewById(R.id.textView5);
        t18.setOnClickListener(this);
        TextView_Roboto_Condensed t19 = rootView.findViewById(R.id.textView6);
        t19.setOnClickListener(this);
        TextView_Roboto_Condensed t20 = rootView.findViewById(R.id.textView7);
        t20.setOnClickListener(this);
        TextView_Roboto_Condensed t21 = rootView.findViewById(R.id.textView8);
        t21.setOnClickListener(this);
        TextView_Roboto_Condensed t22 = rootView.findViewById(R.id.textView9);
        t22.setOnClickListener(this);
        TextView_Roboto_Condensed t23 = rootView.findViewById(R.id.textView10);
        t23.setOnClickListener(this);
        TextView_Roboto_Condensed t24 = rootView.findViewById(R.id.textView11);
        t24.setOnClickListener(this);
        TextView_Roboto_Condensed t25 = rootView.findViewById(R.id.textView12);
        t25.setOnClickListener(this);
        TextView_Roboto_Condensed t26 = rootView.findViewById(R.id.textView13);
        t26.setOnClickListener(this);
        TextView_Roboto_Condensed t27 = rootView.findViewById(R.id.textView14);
        t27.setOnClickListener(this);
        TextView_Roboto_Condensed t28 = rootView.findViewById(R.id.textView15);
        t28.setOnClickListener(this);
        TextView_Roboto_Condensed t29 = rootView.findViewById(R.id.textView16);
        t29.setOnClickListener(this);
        TextView_Roboto_Condensed t30 = rootView.findViewById(R.id.textView17);
        t30.setOnClickListener(this);
        TextView_Roboto_Condensed t31 = rootView.findViewById(R.id.textView18);
        t31.setOnClickListener(this);
        TextView_Roboto_Condensed t32 = rootView.findViewById(R.id.textView19);
        t32.setOnClickListener(this);
        TextView_Roboto_Condensed t33 = rootView.findViewById(R.id.textView20);
        t33.setOnClickListener(this);
        TextView_Roboto_Condensed t34 = rootView.findViewById(R.id.textView21);
        t34.setOnClickListener(this);
        TextView_Roboto_Condensed t35 = rootView.findViewById(R.id.textView22);
        t35.setOnClickListener(this);
        TextView_Roboto_Condensed t36 = rootView.findViewById(R.id.textView23);
        t36.setOnClickListener(this);
        TextView_Roboto_Condensed t37 = rootView.findViewById(R.id.textView24);
        t37.setOnClickListener(this);
        TextView_Roboto_Condensed t38 = rootView.findViewById(R.id.textView25);
        t38.setOnClickListener(this);
        TextView_Roboto_Condensed t39 = rootView.findViewById(R.id.textView26);
        t39.setOnClickListener(this);
        TextView_Roboto_Condensed t40 = rootView.findViewById(R.id.textView27);
        t40.setOnClickListener(this);
        TextView_Roboto_Condensed t41 = rootView.findViewById(R.id.textView28);
        t41.setOnClickListener(this);
        if (dzenNoch) {
            t1.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t2.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t3.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t4.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t5.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t6.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t7.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t8.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t9.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t10.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t11.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t12.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t13.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t14.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t15.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t16.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t17.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t18.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t19.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t20.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t21.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t22.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t23.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t24.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t25.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t26.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t27.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t28.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t29.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t30.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t31.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t32.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t33.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t34.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t35.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t36.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t37.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t38.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t39.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t40.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));
            t41.setTextColor(ContextCompat.getColor(activity, by.carkva_gazeta.malitounik.R.color.colorIcons));

            t1.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t2.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t3.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t4.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t5.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t6.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t7.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t8.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t9.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t10.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t11.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t12.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t13.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t14.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t15.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t16.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t17.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t18.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t19.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t20.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t21.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t22.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t23.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t24.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t25.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t26.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t27.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t28.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t29.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t30.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t31.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t32.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t33.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t34.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t35.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t36.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t37.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t38.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t39.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t40.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
            t41.setBackgroundDrawable(ContextCompat.getDrawable(activity, by.carkva_gazeta.malitounik.R.drawable.nadsanblack));
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, nadsanContentActivity.class);
        int glava = 1;
        switch (v.getId()) {
            case R.id.textView1:
            case R.id.textView3:
                glava = 2;
                break;
            case R.id.textView5:
            case R.id.textView7:
                glava = 4;
                break;
            case R.id.textView6:
                glava = 6;
                break;
            case R.id.textView8:
            case R.id.textView24:
            case R.id.textView22:
            case R.id.textView20:
            case R.id.textView16:
            case R.id.textView12:
                glava = 18;
                break;
            case R.id.textView9:
            case R.id.textView11:
                glava = 7;
                break;
            case R.id.textView10:
                glava = 9;
                break;
            case R.id.textView13:
            case R.id.textView15:
                glava = 10;
                break;
            case R.id.textView14:
                glava = 12;
                break;
            case R.id.textView17:
            case R.id.textView19:
                glava = 13;
                break;
            case R.id.textView18:
                glava = 15;
                break;
            case R.id.textView21:
            case R.id.textView23:
                glava = 19;
                break;
            case R.id.textView25:
            case R.id.textView27:
                glava = 16;
                break;
            case R.id.textView26:
            case R.id.textView28:
                glava = 1;
                break;
        }
        intent.putExtra("kafizma", glava);
        startActivity(intent);
    }
}
