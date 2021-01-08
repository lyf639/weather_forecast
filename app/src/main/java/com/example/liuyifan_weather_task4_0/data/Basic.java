package com.example.liuyifan_weather_task4_0.data;

import com.google.gson.annotations.SerializedName;

public class Basic {
    public String cid;
    public String location;
    public String parent_city;
    public String admin_area;
    @SerializedName("cnty")
    public String country;
    // using @SerializedName("cnty") means the true tag in json is "cnty"
// which can be parsed as the value for country in Basic class
    public String lat;

    @Override
    public String toString() {
        return "Basic{" +
                "cid='" + cid + '\'' +
                ", location='" + location + '\'' +
                ", parent_city='" + parent_city + '\'' +
                ", admin_area='" + admin_area + '\'' +
                ", country='" + country + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }

    public String lon;
    @SerializedName("tz")
    public String timeZone; }
