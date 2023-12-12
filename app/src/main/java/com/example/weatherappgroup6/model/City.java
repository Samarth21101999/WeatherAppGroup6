package com.example.weatherappgroup6.model;

public class City {
    //generate constructor and getter and setter
    private String Id = "";
    private String cityName = "";

    public City(String id, String cityName) {
        Id = id;
        this.cityName = cityName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCity() {
        return cityName;
    }

    public void setCity(String cityName) {
        this.cityName = cityName;
    }
}
