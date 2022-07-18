package com.example.disasterpreventionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterpreventionmanagement.kids.KidsActivity;
import com.example.disasterpreventionmanagement.safetytips.SafetyTips;
import com.example.disasterpreventionmanagement.sharelocation.ShareLocationForm;
import com.example.disasterpreventionmanagement.travel.TravelActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    final String APIkey = BuildConfig.WEATHER_KEY;
    final String APIurl = "https://api.openweathermap.org/data/2.5/weather";
    final long time = 5000;
    final float distance = 1000;
    final int req = 101;
    String Location_Provider = LocationManager.GPS_PROVIDER;
    TextView locationName, weatherState, Temperature;
    ImageView mweatherIcon;
    ImageView menuIcon;

    DrawerLayout navDrawer;

    MaterialButton kidsbutton;
    MaterialCardView safebutton, sharelocbutton, travelplanbutton;
    LocationManager _LocationManager;
    LocationListener _LocationListener;

    TextView username, userEmail;


    String accountEmail;
    String accountName; // email and username shown in nav header

    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view); //just the nav drawer
        menuIcon = findViewById(R.id.menuIcon);
        navDrawer = findViewById(R.id.drawer_layout);// includes main activity
        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        mweatherIcon = findViewById(R.id.weatherIcon);
        safebutton = findViewById(R.id.safety);
        travelplanbutton = findViewById(R.id.travel);
        kidsbutton = findViewById(R.id.kidsmodebutton);
        sharelocbutton = findViewById(R.id.shareloc);
        locationName = findViewById(R.id.locationName);



        sp = getSharedPreferences("UserCredentials",
                Context.MODE_PRIVATE);
        accountEmail = sp.getString("UserEmail", "");
        accountName = sp.getString("UserName", "");

        View header= navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.username);
        userEmail = (TextView) header.findViewById(R.id.useremail);
        userEmail.setText(accountEmail);
        username.setText("Hello, " + accountName + "!");



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logoutbutton) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE); //store logged in user's mail to filter the data that can be accessed by user
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("hasLoggedIn", false);
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navDrawer.openDrawer(Gravity.LEFT);
            }
        });


        travelplanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TravelActivity.class);
                startActivity(intent);
            }
        });

        sharelocbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareLocationForm.class);
                intent.putExtra("user email", accountEmail);
                startActivity(intent);
            }
        });

        safebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SafetyTips.class);
                startActivity(intent);
            }
        });



        final EditText inputLoc = findViewById(R.id.searchLocation);
        inputLoc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String inLoc= inputLoc.getText().toString();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                intent.putExtra("Location",inLoc);
                startActivity(intent);


                return false;
            }
        });
    }
    public void onClickKids(View v){
        if(kidsbutton.isChecked()){
            Intent intent = new Intent(MainActivity.this, KidsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this, KidsActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent=getIntent();
       String city= mIntent.getStringExtra("Location");
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            getWeatherForCurrentLocation();
        }

    }


    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APIkey);
        getData(params);

    }


    private void getWeatherForCurrentLocation() {

        _LocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        _LocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params =new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",APIkey);
                getData(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},req);
            return;
        }
        _LocationManager.requestLocationUpdates(Location_Provider, time, distance, _LocationListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==req)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Location refreshed",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }


    }



    private  void getData(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APIurl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(MainActivity.this,"Location updated",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });



    }

    private  void updateUI(weatherData weather){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notifications", "Notfications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }




        Temperature.setText(weather.getmTemperature());
        locationName.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"drawable",getPackageName());
        mweatherIcon.setImageResource(resourceID);
        int conditia = weather.getmCondition();
        if(conditia == 781){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Tornado Warning !");
            builder.setContentText("Seek shelter! Check our safety tips");
            builder.setSmallIcon(R.drawable.tornado);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }
        if(conditia == 602 || conditia == 622){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Heavy snow Warning !");
            builder.setContentText("Check our safety tips for snow storms");
            builder.setSmallIcon(R.drawable.snowflake);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        if(conditia == 741){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Fog Warning !");
            builder.setContentText("Careful on the road! Check our tips");
            builder.setSmallIcon(R.drawable.foggy);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        if(conditia >= 502 && conditia <= 504){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Heavy rain Alert !");
            builder.setContentText("High risk of flood! Check our tips");
            builder.setSmallIcon(R.drawable.rainy);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        if(conditia == 500 || conditia == 501){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Rain Alert !");
            builder.setContentText("Don't forget your umbrella !");
            builder.setSmallIcon(R.drawable.rainy);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        if(conditia >= 200 && conditia <= 232){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Thunderstorm Alert !");
            builder.setContentText("Stay indoors! Check our safety tips");
            builder.setSmallIcon(R.drawable.thunderstorm);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        if(conditia == 800){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Notifications");
            builder.setContentTitle("Clear sky");
            builder.setContentText("Time to go for a walk :)");
            builder.setSmallIcon(R.drawable.sun);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(_LocationManager!=null)
        {
            _LocationManager.removeUpdates(_LocationListener);
        }
    }




}