package com.example.location_neco;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocListener implements LocationListener {
    private LocListenerInterface locListenerInterface;



    @Override
    public void onLocationChanged(Location location) {

        locListenerInterface.onLocationChanged(location);
        Log.d("myloc", "onLocationChanged" );
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void setLocListenerInterface(LocListenerInterface locListenerInterface) {
        this.locListenerInterface = locListenerInterface;
        Log.d("myloc", "setLocListenerInterface" );
    }
}
