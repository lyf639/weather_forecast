package com.example.liuyifan_weather_task4_0.data;

public class AirNowCity {
    public String aqi;
    public String qlty;
    public String main;
    public String pm25;
    public String pm10;
    public String no2;
    public String so2;
    public String co;
    public String o3;

    @Override
    public String toString() {
        return "AirNowCity{" +
                "aqi='" + aqi + '\'' +
                ", qlty='" + qlty + '\'' +
                ", main='" + main + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", no2='" + no2 + '\'' +
                ", so2='" + so2 + '\'' +
                ", co='" + co + '\'' +
                ", o3='" + o3 + '\'' +
                ", pub_time='" + pub_time + '\'' +
                '}';
    }

    public String pub_time;

}