package com.tang.allmap.weathermanger;

public class WeatherData {

    public static final String key ="3da8b66803a64d7da687e099f8f8f769";
    public static final String UserName = "HE1609241525411274";

    public static String getWeatherNow(String city){
        return "https://free-api.heweather.net/s6/weather/now?location=" +city+
                "&key=" +key;
    }
}
