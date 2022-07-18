package com.example.disasterpreventionmanagement.travel;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.os.Bundle;

import com.example.disasterpreventionmanagement.BuildConfig;
import com.example.disasterpreventionmanagement.R;
import com.example.disasterpreventionmanagement.weatherData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class WeatherTravelPlan extends AppCompatActivity {
    final String APIkey = BuildConfig.WEATHERTRAVEL_KEY;
    final String APIurl = "https://api.openweathermap.org/data/2.5/weather";
    TextView tCity, tWeatherCond, tTemp,
            tCity1, tWeatherCond1, tTemp1;
    TextView tCity2, tWeatherCond2, tTemp2;
    TextView tCity3, tWeatherCond3, tTemp3;
    TextView tCity4, tWeatherCond4, tTemp4;
    ImageView backbutton;
    RelativeLayout firstLoc, secondLoc, thirdLoc, fourthLoc, fifthLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_plan);
        backbutton = findViewById(R.id.backIcon);

        firstLoc = findViewById(R.id.firstLocation_transit);
        secondLoc = findViewById(R.id.secondlocation_transit);
        thirdLoc = findViewById(R.id.thirdlocation_transit);
        fourthLoc = findViewById(R.id.fourthlocation_transit);
        fifthLoc = findViewById(R.id.fifthlocation_transit);

        tCity = findViewById(R.id.cityTransit);
        tWeatherCond = findViewById(R.id.weatherConditionTransit);
        tTemp = findViewById(R.id.temperatureTransit);

        tCity1 = findViewById(R.id.cityTransit1);
        tWeatherCond1 = findViewById(R.id.weatherConditionTransit1);
        tTemp1 = findViewById(R.id.temperatureTransit1);

        tCity2 = findViewById(R.id.cityTransit2);
        tWeatherCond2 = findViewById(R.id.weatherConditionTransit2);
        tTemp2 = findViewById(R.id.temperatureTransit2);

        tCity3 = findViewById(R.id.cityTransit3);
        tWeatherCond3 = findViewById(R.id.weatherConditionTransit13);
        tTemp3 = findViewById(R.id.temperatureTransit3);

        tCity4 = findViewById(R.id.cityTransit4);
        tWeatherCond4 = findViewById(R.id.weatherConditionTransit4);
        tTemp4 = findViewById(R.id.temperatureTransit4);

        backbutton.setOnClickListener(view -> {
            Intent intent = new Intent(WeatherTravelPlan.this, TravelActivity.class);
            startActivity(intent);
        });



    }
    @Override
        protected void onResume() {
        super.onResume();
        Intent mIntent = getIntent();
        String city = mIntent.getStringExtra("TransitLocation");
        String city1 = mIntent.getStringExtra("TransitLocation1");
        String city2 = mIntent.getStringExtra("TransitLocation2");
        String city3 = mIntent.getStringExtra("TransitLocation3");
        String city4 = mIntent.getStringExtra("TransitLocation4");

        if (city != null) {

            Log.i(TAG,  city);
            getWeatherForNewCity(city);
        }
        if (city1 != null) {
            if (secondLoc.getVisibility() == View.INVISIBLE)
                secondLoc.setVisibility(View.VISIBLE);
            getWeatherForNewCity1(city1);
        }
        if (city2 != null) {
            if (thirdLoc.getVisibility() == View.INVISIBLE)
                thirdLoc.setVisibility(View.VISIBLE);
            getWeatherForNewCity2(city2);
        }
        if (city3 != null)
            if (fourthLoc.getVisibility() == View.INVISIBLE)
                fourthLoc.setVisibility(View.VISIBLE);
            getWeatherForNewCity3(city3);
        if (city4 != null)
            if (fifthLoc.getVisibility() == View.INVISIBLE)
                fifthLoc.setVisibility(View.VISIBLE);
            getWeatherForNewCity4(city4);


    }


        private void getWeatherForNewCity(String city)
        {
            RequestParams params=new RequestParams();
            params.put("q",city);
            params.put("appid",APIkey);
            getData(params);

        }

    private void getWeatherForNewCity1(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APIkey);
        getData1(params);

    }
    private void getWeatherForNewCity2(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APIkey);
        getData2(params);

    }
    private void getWeatherForNewCity3(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APIkey);
        getData3(params);

    }
    private void getWeatherForNewCity4(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APIkey);
        getData4(params);

    }



        private  void getData(RequestParams params)
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(APIurl,params,new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                    weatherData weatherD= weatherData.fromJson(response);
                    updateUI(weatherD);
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                }
            });
        }

    private  void getData1(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APIurl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                weatherData weatherD= weatherData.fromJson(response);
                updateUI1(weatherD);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private  void getData2(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APIurl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                weatherData weatherD= weatherData.fromJson(response);
                updateUI2(weatherD);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private  void getData3(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APIurl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                weatherData weatherD= weatherData.fromJson(response);
                updateUI3(weatherD);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    private  void getData4(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(APIurl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                weatherData weatherD= weatherData.fromJson(response);
                updateUI4(weatherD);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }


        private  void updateUI(weatherData tData){

                tCity.setText("Location: " + tData.getMcity());
                tTemp.setText("Temperature: " + tData.getmTemperature());
                tWeatherCond.setText(tData.getmWeatherType());


                tCity1.setText("Location: " + tData.getMcity());
                tTemp1.setText("Temperature: " + tData.getmTemperature());
                tWeatherCond1.setText(tData.getmWeatherType());


        }

    private  void updateUI1(weatherData tData) {

        tCity1.setText("Location: " + tData.getMcity());
        tTemp1.setText("Temperature: " + tData.getmTemperature());
        tWeatherCond1.setText(tData.getmWeatherType());

    }

    private  void updateUI2(weatherData tData) {

        tCity2.setText("Location: " + tData.getMcity());
        tTemp2.setText("Temperature: " + tData.getmTemperature());
        tWeatherCond2.setText(tData.getmWeatherType());


    }
    private  void updateUI3(weatherData tData) {

        tCity3.setText("Location: " + tData.getMcity());
        tTemp3.setText("Temperature: " + tData.getmTemperature());
        tWeatherCond3.setText(tData.getmWeatherType());


    }

    private  void updateUI4(weatherData tData) {

        tCity4.setText("Location: " + tData.getMcity());
        tTemp4.setText("Temperature: " + tData.getmTemperature());
        tWeatherCond4.setText(tData.getmWeatherType());


    }

}