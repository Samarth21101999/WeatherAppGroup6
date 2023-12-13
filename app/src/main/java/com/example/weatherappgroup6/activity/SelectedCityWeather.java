package com.example.weatherappgroup6.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherappgroup6.R;
import com.example.weatherappgroup6.adapter.WeatherRecyclerViewAdapter;
import com.example.weatherappgroup6.databinding.ActivitySelectedCityWeatherBinding;
import com.example.weatherappgroup6.databinding.WeatherColumnBinding;
import com.example.weatherappgroup6.model.Weather;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelectedCityWeather extends AppCompatActivity {
    ActivitySelectedCityWeatherBinding weatherBinding;

    Context context;
    WeatherRecyclerViewAdapter weatherAdapter;
    List<Weather> weatherList = new ArrayList<>();
    /*onCreate() method
     *   In onCreate view is getting set up (activity_selected_city_weather)
     *   After that calling getCurrentDataFromAPI method to get the current data using cityname.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherBinding = ActivitySelectedCityWeatherBinding.inflate(getLayoutInflater());
        View view = weatherBinding.getRoot();
        setContentView(view);
        context = this;
        String cityName=getIntent().getStringExtra("City");
        getCurrentDataFromAPI(cityName, context);
        weatherBinding.cityName.setText(cityName);

    }
    /*getCurrentDataFromAPI() method to fetch the data from OpenWeatherAPI using volley
     *   passing paramaters cityName which is API key.
     *   In onResponse() method calling setData() method to set the data to mainactivity,
     * */
    public void getCurrentDataFromAPI(String cityName, Context context) {
        RequestQueue request;

        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String orginalUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName+ "&appid=" + appid;
       
        request = Volley.newRequestQueue(context);
        StringRequest sendRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error :", error.toString());
            }
        });
        request.add(sendRequest);
    }
    /*setData()method to set the data onto view.
     *   JSONObject is used because API returns data into JSON format.
     *   So setting the on particular widget using  JSONObject and JSONArray
     *   From this method calling getForecastDataFromAPI() method by passing lat, lon and context
     * */
    private void setData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject coordinates= jsonObject.getJSONObject("coord");
            double lat= Double.parseDouble(coordinates.getString("lat"));
            double lon= Double.parseDouble(coordinates.getString("lon"));
            getForecastDataFromAPI(lat,lon,context);
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String icon = weatherObject.getString("icon");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");
            double temp = Double.parseDouble(mainObject.getString("temp"));
            if(icon.equals("01d") || icon.equals("01n")){
                weatherBinding.icon.setImageResource(R.drawable.sun);
            } else if (icon.equals("02d") || icon.equals("02n")) {
                weatherBinding.icon.setImageResource(R.drawable.cloudy);
            }else if (icon.equals("03d") || icon.equals("03n")) {
                weatherBinding.icon.setImageResource(R.drawable.cloud);
            }
            else if (icon.equals("04d") || icon.equals("04n")) {
                weatherBinding.icon.setImageResource(R.drawable.brokenclouds);
            }
            else if (icon.equals("09d") || icon.equals("09n")) {
                weatherBinding.icon.setImageResource(R.drawable.rainy);
            }else if (icon.equals("10d") || icon.equals("10n")) {
                weatherBinding.icon.setImageResource(R.drawable.rain);
            }else if (icon.equals("11d") || icon.equals("11n")) {
                weatherBinding.icon.setImageResource(R.drawable.storm);
            }else if (icon.equals("13d") || icon.equals("13n")) {
                weatherBinding.icon.setImageResource(R.drawable.snowy);
            }else if (icon.equals("50d") || icon.equals("50n")) {
                weatherBinding.icon.setImageResource(R.drawable.foog);
            }
            weatherBinding.temp.setText((int) (temp - 273.15) + "°C");
            weatherBinding.description.setText(weatherObject.getString("main"));
            weatherBinding.wind.setText("Wind: " + (windObject.getString("speed") + "m/s"));
            weatherBinding.humidity.setText("Humditiy: " + (mainObject.getString("humidity") + "%"));
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    /*getForecastDataFromAPI() method to fetch the data from OpenWeatherAPI using volley
     *  passing paramaters like lat, lon, and appid which is API key.
     *  In onResponse() method calling setForecast() method to set the data.
     *  Here there is a little bit change in URl, we have added cnt=7 which means requesting 7 days weather data
     * */
    public void getForecastDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon + "&cnt=7" + "&appid=" + appid;

        request = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setForecast(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error :", error.toString());
            }
        });
        request.add(stringRequest);
    }
    /*
     * setForecast() method to set the forecast data to recycler view
     *     JSONObject is used because API returns data into JSON format.
     *     So setting the on particular widget using  JSONObject and JSONArray
     *     After adding all data to arraylist named as weatherList
     *     calling thee setLayout() method and passing weatherlist and then setting the data to recycler view.
     * */
    private void setForecast(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray listData = jsonObject.getJSONArray("list");
            for (int i = 0; i < 7; i++) {
                JSONObject weatherData = listData.getJSONObject(i);
                JSONObject tempObject = weatherData.getJSONObject("temp");
                JSONArray weatherArray = weatherData.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                double temp = (Double.parseDouble(tempObject.getString("day"))-273.5);
                String icon = weatherObject.getString("icon");
                long date = Long.valueOf(weatherData.getString("dt")) * 1000;
                Date dateFormatted = new java.util.Date(date);
                String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(dateFormatted);
                weatherList.add(new Weather(String.format("%.0f",temp), icon, day.substring(0, 3)));
            }
            setLayout(weatherList);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    /* setLayout() method to set the horizontal recycler view.
     *  Passing the weatherlist and context to WeatherRecyclerViewAdapter() constructor in WeatherRecyclerViewAdapter
     * */
    private void setLayout(List<Weather> weatherList){

        weatherBinding.forecast.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        weatherBinding.forecast.setLayoutManager(mLayoutManager);
        weatherAdapter=new WeatherRecyclerViewAdapter(weatherList,getApplicationContext());
        weatherBinding.forecast.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();
    }


    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},5);
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},5);
            return;
        }
        

    }


}