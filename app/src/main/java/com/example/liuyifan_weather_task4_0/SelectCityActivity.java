package com.example.liuyifan_weather_task4_0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.liuyifan_weather_task4_0.db.CityDatabase;
import com.example.liuyifan_weather_task4_0.db.GenerateDatabaseTask;
import com.example.liuyifan_weather_task4_0.utility.HttpUtil;
import com.example.liuyifan_weather_task4_0.data.City;
import com.example.liuyifan_weather_task4_0.utility.JsonUtil;
import com.example.liuyifan_weather_task4_0.view.CityAdapter;

import java.util.List;

public class SelectCityActivity extends AppCompatActivity {
    //CityAdapter adapter;
    Toolbar toolbar;
    ListView lv;
    ArrayAdapter<City> adapter;
    String baseUrl= "http://guolin.tech/api/china";
    int level_0_id;
    CityDatabase cityDatabase;
    private static final String KEY_WEATHER_ID="weather_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        getAndUpdateCityList(baseUrl,-1,0);
        setContentView(R.layout.select_city_main);
        lv=findViewById(R.id.listview);



        cityDatabase = new CityDatabase(this);
        cityDatabase.open();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("China");
        getAndUpdateCityList(baseUrl,-1,0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = adapter.getItem(position);
                int level = city.getLevel();
                int cityId = city.getId();
                int parentId = city.getParentId();
                String url;
                switch (level) {
                    case 0:
                        level_0_id=cityId;
                        url = String.format("%s/%d", baseUrl, cityId);
                        getAndUpdateCityList(url,cityId,level+1);
                        break;
                    case 1:
                        url = String.format("%s/%d/%d", baseUrl,parentId, cityId);
                        getAndUpdateCityList(url,cityId,level+1);
                        break;
                    case 2:
                        Intent i = getIntent();
                        i.putExtra(KEY_WEATHER_ID,city.getWeather_id());
                        setResult(Activity.RESULT_OK,i);
                        finish();
                        break;
                } }


        }
        );

    }
    public static String getWeatherIdByIntent(Intent intent){
        String weather_id = intent.getStringExtra(KEY_WEATHER_ID);
        return weather_id;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu,menu);
        MenuItem item = menu.findItem(R.id.opt_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                cityDatabase.fuzzyQueryCityListAsync(newText, new CityDatabase.OnQueryFinished() {
                    @Override
                    public void onFinished(List<City> list) {
                        updateListView(list);
// TODO: fix the bug when click the query list and quit searchview
                        if(TextUtils.isEmpty(newText)){
                            toolbar.setTitle("China");
                        } }
                });
                return false; }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void updateListView(List<City> list) {
        adapter=new CityAdapter(this,list);
        lv.setAdapter(adapter);
    }
    private void showToast(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }
    private void getAndUpdateCityList(final String url, final int parentId, final int level) {
        cityDatabase.queryCityListByParentIdAsync(parentId, level, new CityDatabase.OnQueryFinished() {
            @Override
            public void onFinished(List<City> list) {
                if(list==null||list.size()==0) {
                    HttpUtil.getOkHttpAsync(SelectCityActivity.this, url, new HttpUtil.SimpleAsyncCall() {
                        @Override
                        public void onFailure(String e) {
                            showToast(e);
                        }
                        @Override
                        public void onResponse(String s) {
                            List<City> list = JsonUtil.getCityListFromJson(s, parentId, level);
                            cityDatabase.insertList(list);
                            showDbList(parentId, level);
                        }
                    });
                }else {
                    updateListView(list);
                }
                if(level==0){
                    toolbar.setTitle("China");
                }else {
                    City city = cityDatabase.queryCityById(parentId, level - 1);
                    toolbar.setTitle(city.getName());
                }


            }
        });
    }
    private void back() {
        if(adapter.getCount()>0){
            City city = adapter.getItem(0);
            int level = city.getLevel();
            if(level==2){
                City city1 = cityDatabase.queryCityById(city.getParentId(), 1);
                level_0_id=city1.getParentId();
                String url=String.format("%s/%d",baseUrl,level_0_id);
                getAndUpdateCityList(url,level_0_id,level-1);
            }
            if(level==1){
                getAndUpdateCityList(baseUrl,-1,level-1);
            } } }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_back:
                back();
                break;
            case R.id.opt_generate_db:
                new GenerateDatabaseTask(SelectCityActivity.this,cityDatabase).execute();
                break; }
        return super.onOptionsItemSelected(item);
    }
    private void showDbList(int parentId,int level) {
        cityDatabase.queryCityListByParentIdAsync(parentId, level, new CityDatabase.OnQueryFinished()
        {
            @Override
            public void onFinished(List<City> list) {
                updateListView(list);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cityDatabase.close();
    }

}



