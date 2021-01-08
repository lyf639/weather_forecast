package com.example.liuyifan_weather_task4_0.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.liuyifan_weather_task4_0.data.City;

import java.util.ArrayList;
import java.util.List;

public class CityDatabase {
    public static final String KEY_ID = "mid";//main id for url
    public static final String KEY_PID = "pid";// parent id
    public static final String KEY_NAME = "name";
    public static final String KEY_WEATHER_ID = "weather_id";
    public static final String KEY_EN_NAME = "en_name";
    public static final String KEY_INI_NAME = "ini_name";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_LOOK_UP = "key_look_up";
    private static final String DB_NAME = "citydb.db";
    public static final String CITY_TABLE = "city";
    private int version = 1;
    Activity activity;
    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;

    public CityDatabase(Activity activity) {
        this.activity = activity;
    }

    public void open() {// open database by DatabaseHelper
        if (db == null || !db.isOpen()) {
            databaseHelper = new DatabaseHelper();
            db = databaseHelper.getWritableDatabase();
        }
    }

    public void close() { // close the database
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper() {
            super(activity, DB_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format("create table if not exists %s " +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT,%s int,%s int,%s text,%s text, %s text,%s int,%s text,%s text)",
                    CITY_TABLE, KEY_ID, KEY_PID, KEY_NAME, KEY_EN_NAME, KEY_INI_NAME, KEY_LEVEL, KEY_LOOK_UP, KEY_WEATHER_ID);
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetData(db);
        }

        public void resetData(SQLiteDatabase db) {
            String sql = String.format("drop table if exists %s", CITY_TABLE);
            db.execSQL(sql);
            onCreate(db);
        }
    }
    private ContentValues enCodeCotentValues(City city) {
// parse the city to ContentValues
        ContentValues cv = new ContentValues();
        cv.put(KEY_EN_NAME, city.getEnName());
        cv.put(KEY_ID, city.getId());
        cv.put(KEY_INI_NAME, city.getInitialName());
        cv.put(KEY_LEVEL, city.getLevel());
// TODO: modify the lookup value
        cv.put(KEY_LOOK_UP, generateLookup(city));
        cv.put(KEY_PID, city.getParentId());
        cv.put(KEY_WEATHER_ID, city.getWeather_id());
        cv.put(KEY_NAME, city.getName());
        return cv;
    }
    private City getCityFromCursor(Cursor c) {
// parse the Cursor c to City by present position
// ctr+d copy current row
        String name = c.getString(c.getColumnIndex(KEY_NAME));
        String enName = c.getString(c.getColumnIndex(KEY_EN_NAME));
        String iniName = c.getString(c.getColumnIndex(KEY_INI_NAME));
        String weather_id = c.getString(c.getColumnIndex(KEY_WEATHER_ID));
        int id = c.getInt(c.getColumnIndex(KEY_ID));
        int pid = c.getInt(c.getColumnIndex(KEY_PID));
        int level = c.getInt(c.getColumnIndex(KEY_LEVEL));
        City city = new City(id, name, pid);
        city.setEnName(enName);
        city.setInitialName(iniName);
        city.setLevel(level);
        city.setWeather_id(weather_id);
        return city;
    }
    public long insertData(City city) { // insert city to the database
        ContentValues cv = enCodeCotentValues(city);
        return db.insert(CITY_TABLE, KEY_WEATHER_ID, cv);
    }
    public int insertList(List<City> list) {
// insert the whole list to the database and return the inserted count
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            City city = list.get(i);
            if (insertData(city) > 0) {
                count++;
            } }
        return count;
    }
    public void clearDatabase() {//clear the data
        if (db != null && db.isOpen()) {
            databaseHelper.resetData(db);
        } }
    public List<City> getCityListFromCursor(Cursor c) {
// change the cursor to a City list
        List<City> list = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            City city = getCityFromCursor(c);
            list.add(city);
        }
        return list;
    }
    public List<City> queryAllProvinces() {// get the root data
        String sql = String.format("select * from %s where %s=0", CITY_TABLE, KEY_LEVEL);
        return getCityListBySql(sql,null);
    }
    private List<City> getCityListBySql(String sql,String[] args) {
        Cursor c = db.rawQuery(sql, args);
        List<City> list = getCityListFromCursor(c);
        c.close();
        return list;
    }
    public List<City> queryCityListByParentId(int parentId,int level) {
        if(level==0){
            return queryAllProvinces();
        }
        String sql = String.format("select * from %s where %s=%d and %s=%d", CITY_TABLE, KEY_PID, parentId,KEY_LEVEL,level);
        return getCityListBySql(sql,null);
    }
    public List<City> fuzzyQueryCityList(String match){
// search city list by KEY_LOOK_UP
        if(TextUtils.isEmpty((match))){
            return queryAllProvinces();
        }
        String sql=String.format("select * from %s where %s like ?",CITY_TABLE,KEY_LOOK_UP);
                String[] args=new String[]{"%"+match+"%"};
        return getCityListBySql(sql,args);
    }
    private String generateLookup(City city){
        String name = city.getName();
        String enName = city.getEnName();
        String initialName = city.getInitialName();
        String[] enNameArray = enName.split("\\s");
        StringBuilder sb=new StringBuilder();
        sb.append(name+" ");
        sb.append(enName+" ");
        sb.append(initialName+" ");
        sb.append(enName.replaceAll("\\s", "")+" ");
//replace the white space in enName with empty ""
        for (int i = 1; i <enNameArray.length ; i++) {
            sb.append(initialName.substring(0,i));
            for (int j = i; j <enNameArray.length ; j++) {
                sb.append(enNameArray[j]);
            }
//combine the initialName with enName
            sb.append(" ");
        }
        return sb.toString();
    }
    public interface OnQueryFinished{
        // async response interface for querying the database
        public void onFinished(List<City> list);
    }
    interface LoaderWork{
        // embedded interface for asyncLoader design
        List<City> queryWork();
    }
    private void asyncLoader(final OnQueryFinished listener,
                             final LoaderWork work) {//embed thread and runOnUiThread
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<City> list = work.queryWork();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFinished(list);
                    }
                });
            }
        }).start();
    }
    public void queryAllProvincesAsync(final OnQueryFinished listener)
    {
        asyncLoader(listener, new LoaderWork() {
            @Override
            public List<City> queryWork() {
                return queryAllProvinces();
            }
        });
    }
    public void queryCityListByParentIdAsync(final int parentId,
                                             final int level, final OnQueryFinished listener) {
        asyncLoader(listener, new LoaderWork() {
            @Override
            public List<City> queryWork() {
                return queryCityListByParentId(parentId, level);
            }
        });
    }
    public void fuzzyQueryCityListAsync(final String match, final
    OnQueryFinished listener) {
        asyncLoader(listener, new LoaderWork() {
            @Override
            public List<City> queryWork() {
                return fuzzyQueryCityList(match);
            }
        });
    }
    public City queryCityById(int id,int level){
        String sql = String.format("select * from %s where %s=%d and %s=%d",
                CITY_TABLE, KEY_LEVEL,level,KEY_ID,id);
        List<City> list = getCityListBySql(sql, null);
        if(list.size()>0){
            return list.get(0);
        }
        return null; }
}
