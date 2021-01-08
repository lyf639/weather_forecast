package com.example.liuyifan_weather_task4_0.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.liuyifan_weather_task4_0.R;
import com.example.liuyifan_weather_task4_0.data.City;

import java.util.List;

public class CityAdapter extends
        ArrayAdapter<City> {
    private List<City> list;
    private Context context;
    public CityAdapter(@NonNull Context context,
                       List<City> list) {
        super(context,
                android.R.layout.simple_list_item_1,list);
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        View v;
        if(convertView==null) {
            v =
                    LayoutInflater.from(context).inflate(R.layout.city_row_view, null, false);
        }else{
            v=convertView;
        }
        TextView tv= (TextView) v.findViewById(R.id.city_row_view_tv);
        TextView tv0= (TextView) v.findViewById(R.id.city_row_view_province_tv);
        TextView tv1= (TextView) v.findViewById(R.id.city_row_view_city_tv);
        TextView tv2= (TextView) v.findViewById(R.id.city_row_view_county_tv);
        City city = list.get(position);
        tv.setText(city.toString());
        switch (city.getLevel()){
            case 0:
                tv0.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                break;
            case 1:
                tv0.setVisibility(View.GONE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv0.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
                break; }
        return v;
    } }