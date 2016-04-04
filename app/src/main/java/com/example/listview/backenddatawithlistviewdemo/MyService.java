package com.example.listview.backenddatawithlistviewdemo;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    SQLController dbcon;
    private static final String TAG = "HelloService";
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private String mydate;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    public MyService() {

    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        dbcon = new SQLController(this);
        dbcon.open();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service onStartCommand");
        mGoogleApiClient.connect();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        Log.i(TAG, "Service onDestroy");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Service onConnected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Service onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Service onConnectionFailed"+connectionResult);
    }

    @Override
    public void onLocationChanged(Location location) {
        mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Log.i(TAG, "Service onLocationChanged");
        Log.i(TAG, "Service onLocationChanged: "+location.toString());
        String val = "Lat: "+location.getLatitude()+"\nLon: "+location.getLongitude()+"\nTime: "+mydate;
        dbcon.insertData(val);
    }
}
