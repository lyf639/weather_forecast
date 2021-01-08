package com.example.liuyifan_weather_task4_0.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.liuyifan_weather_task4_0.data.City;
import com.example.liuyifan_weather_task4_0.utility.HttpUtil;
import com.example.liuyifan_weather_task4_0.utility.JsonUtil;

import java.util.List;

public class GenerateDatabaseTask extends AsyncTask<Void,Integer,Integer> {
    private Activity activity;
    private ProgressDialog progressDialog;
    private String baseUrl = "http://guolin.tech/api/china";
    private CityDatabase cityDatabase;

    public GenerateDatabaseTask(Activity activity, CityDatabase cityDatabase) {
        this.activity = activity;
        this.cityDatabase = cityDatabase;
    }

    @Override
    protected void onPostExecute(Integer integer) {// in UI thread
        super.onPostExecute(integer);
        progressDialog.dismiss();
        showToast(String.format("Insert %d data to database", integer));
    }

    private void iniProgressDialog(int max) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Generating database");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Inserted data:");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(max);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private void showToast(String s) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
// in UI thread
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
        progressDialog.setMessage(String.format("Inserted data:%d", values[1]));
    }
    @Override
    protected Integer doInBackground(Void... params) {// in background thread
        int count=0;
        String ack = HttpUtil.getOkHttpBlock(baseUrl);
        final List<City> provinceList = JsonUtil.getCityListFromJson(ack, -1, 0);
        if(provinceList!=null&&provinceList.size()>0){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {iniProgressDialog(provinceList.size());}
            });
            cityDatabase.clearDatabase();
            count = count+cityDatabase.insertList(provinceList);
            for (int i = 0; i < provinceList.size(); i++) {
                City province = provinceList.get(i);
                int provinceId=province.getId();
                String urlProvince = String.format("%s/%d", baseUrl, provinceId);
                String s = HttpUtil.getOkHttpBlock(urlProvince);
                List<City> cityList = JsonUtil.getCityListFromJson(s, provinceId, 1);
                count=count+cityDatabase.insertList(cityList);
                for (int j = 0; j < cityList.size(); j++) {
                    City city = cityList.get(j);
                    int cityId = city.getId();
                    String urlCity=String.format("%s/%d",urlProvince,cityId);
                    String s1 = HttpUtil.getOkHttpBlock(urlCity);
                    List<City> countyList = JsonUtil.getCityListFromJson(s1, cityId, 2);
                    count+=cityDatabase.insertList(countyList);
                    publishProgress(i,count); }
                publishProgress(i+1,count); }
        }
        return count;
    } }