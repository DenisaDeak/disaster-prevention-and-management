package com.example.disasterpreventionmanagement;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    private String mTemperature, micon, mcity,mWeatherType;
    private int mCondition;

    public static weatherData fromJson(JSONObject jsonObject)
    {

        try
        {
            weatherData weatherD=new weatherData();
            weatherD.mcity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon=updateWeatherIcon(weatherD.mCondition);
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);
            return weatherD;
        }


         catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition){
        if(condition >=200 && condition <= 202){
            return "storm";
        }
        if(condition >=230 && condition <= 232){
            return "storm";
        }
        if(condition >=210 && condition <= 221){
            return "thunderstorm";
        }
        else if(condition >=300 && condition < 600){
            return "rainy";
        }
        else if(condition >=600 && condition <= 700){
            return "snowflake";
        }
        else if(condition >=701 && condition <= 771){
            return "foggy";
        }
        else if(condition == 781){
            return "tornado";
        }
        else if(condition >=801 && condition <= 804){
            return "cloud";
        }
        else if(condition == 800){
            return "sun";
        }
        return "Oops :(";
    }

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public int getmCondition() {
        return mCondition;
    }

}
