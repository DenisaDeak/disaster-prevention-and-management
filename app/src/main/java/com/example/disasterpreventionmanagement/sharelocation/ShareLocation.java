package com.example.disasterpreventionmanagement.sharelocation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import com.example.disasterpreventionmanagement.MainActivity;
import com.example.disasterpreventionmanagement.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;

import java.util.StringTokenizer;


public class ShareLocation extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public String  finalLongitude;
    public String finalLatitude;
    private LocationRequest locationRequest;
    MaterialButton close;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);
        close = findViewById(R.id.closeShareLoc);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        getCurrentLocation();

        sharedPreferences = getSharedPreferences("CoordinatesLocation", Context.MODE_PRIVATE);
        finalLatitude = sharedPreferences.getString("Latitude", "");
        finalLongitude = sharedPreferences.getString("Longitude", "");
        String message = "Help! My coordinates: " + finalLatitude + ", " + finalLongitude ;

        Intent mIntent=getIntent();
        String nrTel= mIntent.getStringExtra("Phone Number");
        StringTokenizer st=new StringTokenizer(nrTel,";");
        while (st.hasMoreElements())
        {
            String tempMobileNumber = (String)st.nextElement();
            if(tempMobileNumber.length()>0 && message.trim().length()>0) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(tempMobileNumber, null, message, null, null);
            }

        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareLocation.this, ShareLocationForm.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                 getCurrentLocation();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }


    private void getCurrentLocation() {


        if (ActivityCompat.checkSelfPermission(ShareLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {



                LocationServices.getFusedLocationProviderClient(ShareLocation.this)
                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(ShareLocation.this)
                                        .removeLocationUpdates(this);

                                if (locationResult.getLocations().size() > 0){

                                    int index = locationResult.getLocations().size() - 1;
                                    double latitude = locationResult.getLocations().get(index).getLatitude();
                                    double longitude = locationResult.getLocations().get(index).getLongitude();

                                   // Log.d("LAT", String.valueOf(latitude));
                                   // Log.d("LONG", String.valueOf(longitude));

                                    //store current loc
                                    sharedPreferences = getSharedPreferences("CoordinatesLocation", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.putString("Latitude",  String.valueOf(latitude));
                                    editor.putString("Longitude", String.valueOf(longitude));
                                    editor.commit();

                                }
                            }
                        }, Looper.getMainLooper());


        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

}