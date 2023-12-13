package com.example.weatherappgroup6.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.weatherappgroup6.model.City;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CityUtils {
    /*  saveCity() method
    *   To save the cities to sharedpreference
    * */
    public static void saveCity(City city, Context context) {
        if (city != null) {
            SharedPreferences sharedPref = context.getSharedPreferences("cities", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(city.getId(), city.getCity());
            editor.commit();
        }
    }
    /*compareCity() method
    *   To prevent duplicate entry comparing the already existing cities from sharedPreference
    *   Return false if exists and true if not exists.
    * */
    public static boolean compareCity(String text, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("cities", Context.MODE_PRIVATE);
        List<City> cityList = new ArrayList<>();
        Map<String, ?> map = sharedPref.getAll();
        Set set = map.entrySet();
        Iterator itr = set.iterator();

        while (itr.hasNext())
        {
            Map.Entry entry = (Map.Entry)itr.next();
            String savedTask = (String) entry.getValue();
            if (savedTask != null) {
                text=text.toLowerCase();
                if(text.equals(((String) entry.getValue()).toLowerCase())){

                  return false;
                }else{
                    return true;
                }

            }
        }
        return true;
    }
    /*getAllCities() method to get all the cities in shared preference and adding to cityList and returning it.
    * */
    public static List<City> getAllCities (Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("cities", Context.MODE_PRIVATE);
        List<City> cityList = new ArrayList<>();
        Map<String, ?> map = sharedPref.getAll();

        Set set = map.entrySet();
        Iterator itr = set.iterator();

        while (itr.hasNext())
        {
            Map.Entry entry = (Map.Entry)itr.next();
            String savedTask = (String) entry.getValue();
            if (savedTask != null) {
                City city = new City(entry.getKey().toString(), savedTask);
                cityList.add(city);
            }
        }
        return cityList;
    }
}
