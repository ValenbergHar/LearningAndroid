package com.example.playingaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    int variant = 1;
    Button button;

    SeekBar seekBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);


        mediaPlayer = MediaPlayer.create(this, R.raw.stuff);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("Progrss chsnged", ""+i);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void play(View view) {
        switch (variant) {
            case (1):
                mediaPlayer.start();
                button = findViewById(R.id.play);
                button.setText("PLAY");
                variant = 2;
                break;
            case (2):
                mediaPlayer.pause();
                button = findViewById(R.id.play);
                button.setText("STOP");
                variant = 1;
                break;
        }
    }
}
