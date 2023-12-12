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
    WeatherColumnBinding weatherColumnBinding;
    private final static int ALL_PERMISSIONS_RESULT = 101;

    private final static int REQUEST_CODE = 100;

    List<Weather> weatherList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherBinding = ActivitySelectedCityWeatherBinding.inflate(getLayoutInflater());
        View view = weatherBinding.getRoot();
        setContentView(view);
        context = this;
        String cityName=getIntent().getStringExtra("City");
        getCurrentDataFromAPI(cityName, context);
        //getForecastDataFromAPI(latitude, longitude, context);
        weatherBinding.cityName.setText(cityName);

    }
    public void getCurrentDataFromAPI(String cityName, Context context) {
        RequestQueue request;
        List<Weather> weatherData = new ArrayList<>();
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String orginalUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName+ "&appid=" + appid;
        Log.d("URL", orginalUrl);
        request = Volley.newRequestQueue(context);
        // String Request initialized
        StringRequest stringRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
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
        request.add(stringRequest);
    }
    private void setData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject coordinates= jsonObject.getJSONObject("coord");
            Log.d("coord", String.valueOf(coordinates.getString("lon")));
            double lat= Double.parseDouble(coordinates.getString("lat"));
            double lon= Double.parseDouble(coordinates.getString("lon"));
            getForecastDataFromAPI(lat,lon,context);
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String icon = weatherObject.getString("icon");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");
            double temp = Double.parseDouble(mainObject.getString("temp"));
            Log.d("Current Icon",icon);
            switch (icon) {
                case "01n":
                    weatherBinding.imageView.setImageResource(R.drawable.sun);
                    break;
                case "01d":
                    weatherBinding.imageView.setImageResource(R.drawable.sun);
                    break;
                case "02n":
                    weatherBinding.imageView.setImageResource(R.drawable.cloudy);
                    break;
                case "02d":
                    weatherBinding.imageView.setImageResource(R.drawable.cloudy);
                    break;
                case "03n":
                    weatherBinding.imageView.setImageResource(R.drawable.cloud);
                    break;
                case "03d":
                    weatherBinding.imageView.setImageResource(R.drawable.cloud);
                    break;
                case "04n":
                    weatherBinding.imageView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "04d":
                    weatherBinding.imageView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "09n":
                    weatherBinding.imageView.setImageResource(R.drawable.rainy);
                    break;
                case "09d":
                    weatherBinding.imageView.setImageResource(R.drawable.rainy);
                    break;
                case "10n":
                    weatherBinding.imageView.setImageResource(R.drawable.rain);
                    break;
                case "10d":
                    weatherBinding.imageView.setImageResource(R.drawable.rain);
                    break;
                case "11n":
                    weatherBinding.imageView.setImageResource(R.drawable.storm);
                    break;
                case "11d":
                    weatherBinding.imageView.setImageResource(R.drawable.storm);
                    break;
                case "13n":
                    weatherBinding.imageView.setImageResource(R.drawable.snowy);
                    break;
                case "13d":
                    weatherBinding.imageView.setImageResource(R.drawable.snowy);
                    break;
                case "50n":
                    weatherBinding.imageView.setImageResource(R.drawable.foog);
                    break;
                case "50d":
                    weatherBinding.imageView.setImageResource(R.drawable.foog);
                    break;
                default:
                    Toast.makeText(context, "Error in getting image", Toast.LENGTH_SHORT);
            }
            weatherBinding.temp.setText((int) (temp - 273.15) + "°C");
            weatherBinding.description.setText(weatherObject.getString("main"));
            weatherBinding.wind.setText("Wind: " + (windObject.getString("speed") + "m/s"));
            weatherBinding.humidity.setText("Humditiy: " + (mainObject.getString("humidity") + "%"));
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void getForecastDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon + "&cnt=7" + "&appid=" + appid;
        Log.d("URL", orginalUrl);
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
                long date = Long.valueOf(weatherData.getString("dt")) * 1000;// its need to be in milisecond
                Date dateFormatted = new java.util.Date(date);
                String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(dateFormatted);
                Log.d("Day", day.substring(0, 3));
                weatherList.add(new Weather(String.format("%.1f",temp), icon, day.substring(0, 3)));
                Log.d("Array Size", String.valueOf(weatherList.size()));
            }
            setLayout(weatherList);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void setLayout(List<Weather> weatherList){
        Log.d("METHOD","SETLAYOUT()");
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
            return;
        }
        

    }


}