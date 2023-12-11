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

    LocationManager locationManager;
    Context context;
    WeatherRecyclerViewAdapter weatherAdapter;
    WeatherColumnBinding weatherColumnBinding;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    ActivityMainBinding mainBinding;
    private LocationRequest locationRequest;
    private final static int REQUEST_CODE = 100;

    List<Weather> weatherList = new ArrayList<>();
//    List<Weather> weather=new ArrayList<>();

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
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, locationListenerGPS);

    }
//Get the location and call getCurrentDataFromAPI and getForecastDataFromAPI methods
    LocationListener locationListenerGPS = new LocationListener() {
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

    public void getCurrentDataFromAPI(double latitude, double longitude, Context context) {
        RequestQueue request;
        List<Weather> weatherData = new ArrayList<>();
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
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String icon = weatherObject.getString("icon");
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");
            double temp = Double.parseDouble(mainObject.getString("temp"));
            Log.d("Current Icon",icon);
            switch (icon) {
                case "01n":
                    mainBinding.imageView.setImageResource(R.drawable.sun);
                    break;
                case "01d":
                    mainBinding.imageView.setImageResource(R.drawable.sun);
                    break;
                case "02n":
                    mainBinding.imageView.setImageResource(R.drawable.cloudy);
                    break;
                case "02d":
                    mainBinding.imageView.setImageResource(R.drawable.cloudy);
                    break;
                case "03n":
                    mainBinding.imageView.setImageResource(R.drawable.cloud);
                    break;
                case "03d":
                    mainBinding.imageView.setImageResource(R.drawable.cloud);
                    break;
                case "04n":
                    mainBinding.imageView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "04d":
                    mainBinding.imageView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "09n":
                    mainBinding.imageView.setImageResource(R.drawable.rainy);
                    break;
                case "09d":
                    mainBinding.imageView.setImageResource(R.drawable.rainy);
                    break;
                case "10n":
                    mainBinding.imageView.setImageResource(R.drawable.rain);
                    break;
                case "10d":
                    mainBinding.imageView.setImageResource(R.drawable.rain);
                    break;
                case "11n":
                    mainBinding.imageView.setImageResource(R.drawable.storm);
                    break;
                case "11d":
                    mainBinding.imageView.setImageResource(R.drawable.storm);
                    break;
                case "13n":
                    mainBinding.imageView.setImageResource(R.drawable.snowy);
                    break;
                case "13d":
                    mainBinding.imageView.setImageResource(R.drawable.snowy);
                    break;
                case "50n":
                    mainBinding.imageView.setImageResource(R.drawable.foog);
                    break;
                case "50d":
                    mainBinding.imageView.setImageResource(R.drawable.foog);
                    break;
                default:
                    Toast.makeText(context, "Error in getting image", Toast.LENGTH_SHORT);
            }
            mainBinding.temp.setText((int) (temp - 273.15) + "°C");
            mainBinding.description.setText(weatherObject.getString("main"));
            mainBinding.wind.setText("Wind: " + (windObject.getString("speed") + "m/s"));
            mainBinding.humidity.setText("Humditiy: " + (mainObject.getString("humidity") + "%"));
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
        mainBinding.forecast.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        mainBinding.forecast.setLayoutManager(mLayoutManager);
        weatherAdapter=new WeatherRecyclerViewAdapter(weatherList,getApplicationContext());
        mainBinding.forecast.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, locationListenerGPS);

    }


}
