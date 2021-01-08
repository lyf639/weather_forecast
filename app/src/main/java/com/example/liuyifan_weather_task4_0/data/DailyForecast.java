package com.example.liuyifan_weather_task4_0.data;

import com.google.gson.annotations.SerializedName;

public class DailyForecast {
    public String cond_code_d;
    public String cond_code_n;
    public String cond_txt_d;
    public String cond_txt_n;
    public String date;
    @SerializedName("hum")
    public String humidity;
    @SerializedName("mr")
    public String moonRise;
    @SerializedName("ms")
    public String moonSet;
    @SerializedName("pcpn")
    public String precipitation;
    @SerializedName("pop")
    public String probability;
    @SerializedName("pres")
    public String pressure;
    @SerializedName("sr")
    public String sunRise;
    @SerializedName("ss")
    public String sunSet;
    public String tmp_max;
    public String tmp_min;
    public String uv_index;
    public String vis;
    public String wind_deg;
    public String wind_dir;
    public String wind_sc;
    public String wind_spd;

    @Override
    public String toString() {
        return "DailyForecast{" +
                "cond_code_d='" + cond_code_d + '\'' +
                ", cond_code_n='" + cond_code_n + '\'' +
                ", cond_txt_d='" + cond_txt_d + '\'' +
                ", cond_txt_n='" + cond_txt_n + '\'' +
                ", date='" + date + '\'' +
                ", humidity='" + humidity + '\'' +
                ", moonRise='" + moonRise + '\'' +
                ", moonSet='" + moonSet + '\'' +
                ", precipitation='" + precipitation + '\'' +
                ", probability='" + probability + '\'' +
                ", pressure='" + pressure + '\'' +
                ", sunRise='" + sunRise + '\'' +
                ", sunSet='" + sunSet + '\'' +
                ", tmp_max='" + tmp_max + '\'' +
                ", tmp_min='" + tmp_min + '\'' +
                ", uv_index='" + uv_index + '\'' +
                ", vis='" + vis + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }
}