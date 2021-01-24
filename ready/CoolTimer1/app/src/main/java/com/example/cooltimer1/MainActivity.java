package com.example.cooltimer1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private TextView textView;
    private SeekBar seekBar;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;
    private int defaultInterval;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "on", Toast.LENGTH_LONG);


        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        isTimerOn = false;
        seekBar.setMax(100);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setIntervalFromSharedPreference(sharedPreferences);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long progressInMills = progress * 1000;
                time(progressInMills);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "in startTracking", Toast.LENGTH_SHORT);
                Log.d("myTimer", "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "jbkjbjkn", Toast.LENGTH_SHORT);
                Log.d("myTimer", "onStopTrackingTouch");

            }
        });
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    public void onClickTimer(View view) {
        if (!isTimerOn) {
            button.setText("STOP");
            seekBar.setEnabled(false);
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sharedPreferences.getBoolean("Enable_sound", true)) {
                        String melodyName = sharedPreferences.getString("timer_melody", "bell");
                        if (melodyName.equals("bell_1")) {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                            mediaPlayer.start();
                        } else if (melodyName.equals("bell_2")) {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zangs_1);
                            mediaPlayer.start();
                        } else if (melodyName.equals("bell_3")) {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.zangs_2);
                            mediaPlayer.start();
                        }


                        Log.d("onFinish()", "Finish");

                    }
                    resetTime();
                }
            };
            countDownTimer.start();

        } else {
            resetTime();
        }
    }

    public void resetTime() {
        countDownTimer.cancel();
        button.setText("START");
        seekBar.setEnabled(true);
        isTimerOn = false;
        setIntervalFromSharedPreference(sharedPreferences);
    }

    private void time(long millisUntilFinished) {
        int minutes = (int) millisUntilFinished / 60000;
        int seconds = (int) millisUntilFinished / 1000 - minutes * 60;
        String minutesString = "";
        String secondsString = "";
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }
        textView.setText(minutesString + ":" + secondsString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent openSettings = new Intent(this, SettingsActivity.class);
                startActivity(openSettings);
                return true;


            case R.id.action_about:
                Intent openActivity = new Intent(this, AboutActivity.class);
                startActivity(openActivity);
                return true;

            case R.id.action_purchase:
                Intent openPurchase = new Intent(this, PurchaseActivity.class);
                startActivity(openPurchase);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setIntervalFromSharedPreference(SharedPreferences sharedPreference) {

        defaultInterval = Integer.valueOf(sharedPreference.getString("timer_default_interval", "30"));
        long defaultIntervalInMillis = defaultInterval * 1000;
        time(defaultIntervalInMillis);
        seekBar.setProgress(defaultInterval);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("timer_default_interval")) {
            setIntervalFromSharedPreference(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}

