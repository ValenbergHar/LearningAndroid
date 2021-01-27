package com.example.location_neco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocListenerInterface {
    private LocationManager locManager;
    private TextView txtDistance, txtVelocity;
    private Location lastLocation;
    private MyLocListener myLocListener;
    private int distance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtVelocity = findViewById(R.id.txtVelocity);
        txtDistance = findViewById(R.id.txtDistance);
        setContentView(R.layout.activity_main);
        init();


    }

    private void init() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(MainActivity.this);
        checkPermission();
        Log.d("myloc", "init" );
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            Log.d("myloc", "requestPermissions" );
        } else {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, myLocListener);
            Log.d("myloc", String.valueOf(locManager));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == RESULT_OK) {
            checkPermission();
        }
        else {
            Toast.makeText(this, "No GPS permission", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onLocationChanged(Location loc) {
        if (loc.hasSpeed() && lastLocation != null) {
            distance += lastLocation.distanceTo(loc);
        }
        Log.d("myloc", "loc " + loc);
        Log.d("myloc", "onLocationChanged" );
        lastLocation = loc;
        txtDistance.setText(String.valueOf(distance));
        txtVelocity.setText(String.valueOf(loc.getSpeed()));
    }
}