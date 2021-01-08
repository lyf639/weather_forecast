package com.example.liuyifan_weather_task4_0.data;

import com.google.gson.annotations.SerializedName;

public class AirNowStation {
    @SerializedName("air_sta")
    public String airStation;
    public String aqi;
    @SerializedName("asid")
    public String airStationId;
    public String co;
    public String lat;
    public String lon;
    public String main;
    public String no2;
    public String o3;
    public String pm10;
    public String pm25;
    public String pub_time;
    public String qlty;
    public String so2;

    @Override
    public String toString() {
        return "AirNowStation{" +
                "airStation='" + airStation + '\'' +
                ", aqi='" + aqi + '\'' +
                ", airStationId='" + airStationId + '\'' +
                ", co='" + co + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", main='" + main + '\'' +
                ", no2='" + no2 + '\'' +
                ", o3='" + o3 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pub_time='" + pub_time + '\'' +
                ", qlty='" + qlty + '\'' +
                ", so2='" + so2 + '\'' +
                '}';
    }
}