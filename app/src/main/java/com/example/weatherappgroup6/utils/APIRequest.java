package com.example.weatherappgroup6.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherappgroup6.activity.MainActivity;
import com.example.weatherappgroup6.databinding.ActivityMainBinding;
import com.example.weatherappgroup6.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIRequest {

    ActivityMainBinding mainBinding;
    public static List<Weather> getDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;
        List<Weather> weatherData=new ArrayList<>();
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
        Log.d("URL", orginalUrl);
        request = Volley.newRequestQueue(context);

        // String Request initialized
        StringRequest stringRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);

                    Log.d("Description", weatherObject.getString("description"));
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    Log.d("Temp", mainObject.getString("temp"));
                    Log.d("Humdity", mainObject.getString("humidity"));
                    JSONObject windObject = jsonObject.getJSONObject("wind");
                    Log.d("Wind Speed", windObject.getString("speed"));
                    double temp = Double.parseDouble(mainObject.getString("temp"));
                    String description = weatherObject.getString("description");
                    int humidity = Integer.parseInt(mainObject.getString("humidity"));
                    double wind = Double.parseDouble(windObject.getString("speed"));
                    String main = weatherObject.getString("main");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error :", error.toString());
            }
        });

        request.add(stringRequest);

//        Log.d("Size", String.valueOf(weatherData.size()));
        return weatherData;

    }
}
