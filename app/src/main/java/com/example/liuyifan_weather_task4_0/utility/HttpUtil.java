package com.example.liuyifan_weather_task4_0.utility;

import android.app.Activity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public interface SimpleAsyncCall{
        public void onFailure(String e);
        public void onResponse(String s);
    }
    public static String getOkHttpBlock(String url){
//get url data by block method
        OkHttpClient client=new OkHttpClient();
        Request build = new Request.Builder().url(url).get().build();
        Call call = client.newCall(build);
        try {
            Response res = call.execute();
            String s = res.body().string();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; }
    public static void getOkHttpAsync(final Activity activity, String url, final SimpleAsyncCall l){
        OkHttpClient client=new OkHttpClient();
        Request build = new Request.Builder().url(url).get().build();
        Call call = client.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l.onFailure(e.toString());
                    }
                });
            }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String res;
            try {
                res = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l.onResponse(res);
                    }
                });
            } catch (final IOException e) {
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l.onFailure(e.toString());
                    }
                });
            } }
    });
} }