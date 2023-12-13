package com.example.weatherappgroup6.activity;

import static android.location.Location.convert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationListener;
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
import com.example.weatherappgroup6.databinding.ActivityMainBinding;
import com.example.weatherappgroup6.databinding.WeatherColumnBinding;
import com.example.weatherappgroup6.model.Weather;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager location;
    Context context;
    WeatherRecyclerViewAdapter weatherAdapter;
    ActivityMainBinding mainBinding;
    List<Weather> weatherList = new ArrayList<>();

    /*onCreate() method
     *   In onCreate view is getting set up (activity_main)
     *   After that getting the user location using LocationManager and also checking the permission for location.
     *   When clicking on list button app redirects to listcitiesactivity to add city
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        context = this;
        mainBinding.listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ListCitiesActivity.class);
                startActivity(i);
            }
        });
        location = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},5);
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},5);
            return;
        }
        location.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                20, locationListener);
    }


    /*LocationListener to get the longitude and latitude of user location.
     *   After that calling getCurrentDataFromAPI and getForecastDataFromAPI method
     *   Also getting the city name using geocoder from lat and lon.
     *   If any error Toast will give the error message.
     * */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                String cityname = geocoder.getFromLocation(latitude, longitude, 1).get(0).getLocality();
                mainBinding.cityName.setText(cityname);
                if (!(cityname == null)) {
                    getCurrentDataFromAPI(latitude, longitude, context);
                    getForecastDataFromAPI(latitude, longitude, context);
                } else {
                    Toast.makeText(context, "Error in getting location", Toast.LENGTH_SHORT);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    /*getCurrentDataFromAPI() method to fetch the data from OpenWeatherAPI using volley
     *   passing paramaters like lat, lon, and appid which is API key.
     *   In onResponse() method calling setData() method to set the data to mainactivity,
     * */
    public void getCurrentDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;

        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;
        request = Volley.newRequestQueue(context);
        StringRequest sendRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error in getting current data",Toast.LENGTH_SHORT).show();
            }
        });
        request.add(sendRequest);
    }
    /*setData()method to set the data onto view.
     *   JSONObject is used because API returns data into JSON format.
     *   So setting the on particular widget using  JSONObject and JSONArray
     * */
    private void setData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String icon = weatherObject.getString("icon");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");
            double temp = Double.parseDouble(mainObject.getString("temp"));
            if(icon.equals("01d") || icon.equals("01n")){
                mainBinding.icon.setImageResource(R.drawable.sun);
            } else if (icon.equals("02d") || icon.equals("02n")) {
                mainBinding.icon.setImageResource(R.drawable.cloudy);
            }else if (icon.equals("03d") || icon.equals("03n")) {
                mainBinding.icon.setImageResource(R.drawable.cloud);
            }
            else if (icon.equals("04d") || icon.equals("04n")) {
                mainBinding.icon.setImageResource(R.drawable.brokenclouds);
            }
            else if (icon.equals("09d") || icon.equals("09n")) {
                mainBinding.icon.setImageResource(R.drawable.rainy);
            }else if (icon.equals("10d") || icon.equals("10n")) {
                mainBinding.icon.setImageResource(R.drawable.rain);
            }else if (icon.equals("11d") || icon.equals("11n")) {
                mainBinding.icon.setImageResource(R.drawable.storm);
            }else if (icon.equals("13d") || icon.equals("13n")) {
                mainBinding.icon.setImageResource(R.drawable.snowy);
            }else if (icon.equals("50d") || icon.equals("50n")) {
                mainBinding.icon.setImageResource(R.drawable.foog);
            }
            mainBinding.temp.setText((int) (temp - 273.15) + "°C");
            mainBinding.description.setText(weatherObject.getString("main"));
            mainBinding.wind.setText("Wind: " + (windObject.getString("speed") + "m/s"));
            mainBinding.humidity.setText("Humditiy: " + (mainObject.getString("humidity") + "%"));
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /*getForecastDataFromAPI() method to fetch the data from OpenWeatherAPI using volley
     *  passing paramaters like lat, lon, and appid which is API key.
     *  In onResponse() method calling setForecast() method to set the data to mainactivity,
     *  Here there is a little bit change in URl, we have added cnt=7 which means requesting 7 days weather data
     * */
    public void getForecastDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon + "&cnt=7" + "&appid=" + appid;
        request = Volley.newRequestQueue(context);
        StringRequest sendRequest = new StringRequest(Request.Method.GET, orginalUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setForecast(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error in getting forecast data",Toast.LENGTH_SHORT).show();
            }
        });
        request.add(sendRequest);
    }
    /*
    * setForecast() method to set the forecast data to recycler view
    *     JSONObject is used because API returns data into JSON format.
    *     So setting the on particular widget using  JSONObject and JSONArray
    *     After adding all data to arraylist named as weatherList
    *     calling thee setLayout() method and passing weatherlist and then setting the data to recycler view.
    * */
    private void setForecast(String response) {
        weatherList.clear();
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
        mainBinding.forecast.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        mainBinding.forecast.setLayoutManager(mLayoutManager);
        weatherAdapter=new WeatherRecyclerViewAdapter(weatherList,getApplicationContext());
        mainBinding.forecast.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();
    }

    /*onResume() method
    *   When activiity resume get the location of user and using location listener
    *   and call all the appropriate methods from locationListener
    * */
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},5);
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},5);
            return;
        }
//        location.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                1000,
//                20, locationListener);

    }


}
