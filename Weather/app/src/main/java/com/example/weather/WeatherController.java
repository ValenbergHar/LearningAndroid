package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherController extends AppCompatActivity {

    // Request Codes:
    final int REQUEST_CODE = 123; // Request Code for permission request callback
    final int NEW_CITY_CODE = 456; // Request code for starting new activity for result callback

    // Base URL for the OpenWeatherMap API. More secure https is a premium feature =(
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";

    // App ID to use OpenWeather data
    final String APP_ID = "e72ca729af228beabd5d20e3b7749713";

    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;

    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // Don't want to type 'Clima' in all the logs, so putting this in a constant here.
    final String LOGCAT_TAG = "Clima";

    // Set LOCATION_PROVIDER here. Using GPS_Provider for Fine Location (good for emulator):
    // Recommend using LocationManager.NETWORK_PROVIDER on physical devices (reliable & fast!)
    final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    // Declaring a LocationManager and a LocationListener here:
    LocationManager mLocationManager;

    LocationListener mLocationListener;

    // Member Variables:
    boolean mUseLocation = true;
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);
    }


    // onResume() life cyle callback:
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOGCAT_TAG, "onResume() called");
        if (mUseLocation) getWeatherForCurrentLocation();
    }


    // Location Listener callbacks here, when the location has changed.
    private void getWeatherForCurrentLocation() {

        Log.d(LOGCAT_TAG, "Getting weather for current location");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(LOGCAT_TAG, "onLocationChanged() callback received");
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d(LOGCAT_TAG, "longitude is: " + longitude);
                Log.d(LOGCAT_TAG, "latitude is: " + latitude);

                // Providing 'lat' and 'lon' (spelling: Not 'long') parameter values
//                RequestParams params = new RequestParams();
//                params.put("lat", latitude);
//                params.put("lon", longitude);
//                params.put("appid", APP_ID);
//                letsDoSomeNetworking(params);
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        // This is the permission check to access (fine) location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(LOGCAT_TAG, "onRequestPermissionsResult(): Permission granted!");
                // Getting weather only if we were granted permission.
                getWeatherForCurrentLocation();
            }else{
                Log.d(LOGCAT_TAG, "Permission denied =( ");
            }

        }


    }
}