package com.example.liuyifan_weather_task4_0.data;

public class WeatherNow {
    public Basic basic;
    public Update update;
    public String status;

    @Override
    public String toString() {
        return "WeatherNow{" +
                "basic=" + basic +
                ", update=" + update +
                ", status='" + status + '\'' +
                ", now=" + now +
                '}';
    }

    public Now now;
}