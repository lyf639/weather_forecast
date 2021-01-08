package com.example.liuyifan_weather_task4_0.data;

public class City {

    private int id;
    private String name;
    private int parentId=-1;
    private String enName="";
    private int level=0;
    public int getLevel() {
        return level; }
    public void setLevel(int level) {
        this.level = level;
    }
    public City(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private String initialName="";
    private String weather_id="";

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getInitialName() {
        return initialName;
    }

    public void setInitialName(String initialName) {
        this.initialName = initialName;
    }

    @Override
    public String toString() {
        return String.format("%s(%s),id=%d",name,enName,id) ;
    }
}
