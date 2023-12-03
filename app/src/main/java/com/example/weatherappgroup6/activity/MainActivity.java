package com.example.weatherappgroup6.activity;

import static android.location.Location.convert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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
//    private ArrayList permissionsToRequest;
//    private ArrayList permissionsRejected = new ArrayList();
//    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    ActivityMainBinding mainBinding;
    private LocationRequest locationRequest;
    private final static int REQUEST_CODE = 100;

    List<Weather> weatherList = new ArrayList<>();
    List<Weather> weather=new ArrayList<>();

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, locationListenerGPS);
        //isLocationEnabled();
        // fusedlocation = LocationServices.getFusedLocationProviderClient(this);

        //getLocation();
    }

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

                    mainBinding.forecast.setHasFixedSize(true);

                    LinearLayoutManager mLayoutManager=new LinearLayoutManager(context);
                    mainBinding.forecast.setLayoutManager(mLayoutManager);
                    weatherAdapter=new WeatherRecyclerViewAdapter(weatherList,context);
//                    weatherColumnBinding.
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
        double temp, wind;
        String description, main;
        int humidity;
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
            switch (icon) {
                case "1n":
                    mainBinding.imageView.setImageResource(R.drawable.sun);
                    break;
                case "2n":
                    mainBinding.imageView.setImageResource(R.drawable.cloudy);
                    break;
                case "3n":
                    mainBinding.imageView.setImageResource(R.drawable.cloud);
                    break;
                case "4n":
                    mainBinding.imageView.setImageResource(R.drawable.brokenclouds);
                    break;
                case "9n":
                    mainBinding.imageView.setImageResource(R.drawable.rainy);
                    break;
                case "10n":
                    mainBinding.imageView.setImageResource(R.drawable.rain);
                    break;
                case "11n":
                    mainBinding.imageView.setImageResource(R.drawable.storm);
                    break;
                case "13n":
                    mainBinding.imageView.setImageResource(R.drawable.snowy);
                    break;
                case "50n":
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
//        List<Weather> weatherData = new ArrayList<>();
        String appid = "21e8295debb700f5f00ed1a6ee5f95ad";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        String orginalUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon=" + lon + "&cnt=7" + "&appid=" + appid;
        Log.d("URL", orginalUrl);
        request = Volley.newRequestQueue(context);
        double temp = 0, wind;
        String description, main;
        int humidity;
        // String Request initialized
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
        setLayout(weatherList);
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
                double temp = Double.parseDouble(tempObject.getString("day"));
                String icon = weatherObject.getString("icon");
                long date = Long.valueOf(weatherData.getString("dt")) * 1000;// its need to be in milisecond
                Date dateFormatted = new java.util.Date(date);
                String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(dateFormatted);
                Log.d("Day", day.substring(0, 3));
                weatherList.add(new Weather(temp, icon, day.substring(0, 3)));
                Log.d("Array Size", String.valueOf(weatherList.size()));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
        private void setLayout(List<Weather> weatherList){
        mainBinding.forecast.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        mainBinding.forecast.setLayoutManager(mLayoutManager);
        //updateTaskAdapter();
        bindAdapter(weatherList);
    }
//    private void updateTaskAdapter(){
//        bindAdapter();
//    }
    private void bindAdapter(List<Weather> weatherList){
        weatherAdapter=new WeatherRecyclerViewAdapter(weatherList,getApplicationContext());
        mainBinding.forecast.setAdapter(weatherAdapter);
        weatherAdapter.notifyDataSetChanged();
    }
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000,
                10, locationListenerGPS);
        //isLocationEnabled();

    }

//    private void isLocationEnabled() {
//
//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
//            alertDialog.setTitle("Enable Location");
//            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
//            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int which){
//                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(intent);
//                }
//            });
//            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int which){
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alert=alertDialog.create();
//            alert.show();
//        }
//        else{
//            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
//            alertDialog.setTitle("Confirm Location");
//            alertDialog.setMessage("Your Location is enabled, please enjoy");
//            alertDialog.setNegativeButton("Back to interface",new DialogInterface.OnClickListener(){
//                public void onClick(DialogInterface dialog, int which){
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alert=alertDialog.create();
//            alert.show();
//        }
//    }

//    //    public void getCityName(){
////
////
////    }
//    public void getLocation() {
//Log.d("Here","Here");
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
//        {
//            Log.d("HERE","HERE");
//            fusedlocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if(location!=null){
//                        Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
//                        try {
//                            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                            Log.d("Address",addresses.get(0).getPostalCode());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }
//                }
//            });
//        }
////        Log.d("Here", "Here");
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            Log.d("If", "Here");
////            return;
////        }
////
////        fusedlocation.requestLocationUpdates(locationRequest, new LocationCallback(){
////            public void onLocationResult(@NonNull LocationResult locationResult){
////                onLocationResult(locationResult);
////                Log.d("MSG",String.valueOf(locationResult));
////                LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
////                if(locationRequest!=null && locationResult.getLocations().size()>0){
////                    int index=locationResult.getLocations().size()-1;
////                    double latitude=locationResult.getLocations().get(index).getLatitude();
////                    Log.d("Latitude",String.valueOf(latitude));
////                }
////            }
//
//        //  },Looper.getMainLooper());
////        fusedlocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
////            @Override
////            public void onComplete(@NonNull Task<Location> task) {
////                Location location = task.getResult();
////                Log.d("Location", String.valueOf(location));
////                if (location != null) {
////                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
////                    try {
////                        List<Address> addresses = geocoder.getFromLocation(
////                                location.getLatitude(), location.getLongitude(), 1
////                        );
////                        Log.d("Location", String.valueOf(addresses.get(0).getLatitude()));
////                    } catch (IOException e) {
////                        throw new RuntimeException(e);
////                    }
////
////                } else {
////                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////                        // TODO: Consider calling
////                        //    ActivityCompat#requestPermissions
////                        // here to request the missing permissions, and then overriding
////                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                        //                                          int[] grantResults)
////                        // to handle the case where the user grants the permission. See the documentation
////                        // for ActivityCompat#requestPermissions for more details.
////                        return;
////                    }
////                    fusedlocation.requestLocationUpdates(new LocationRequest(), locationCallback, Looper.getMainLooper());
////                }
////            }
////        });
//    }
}
