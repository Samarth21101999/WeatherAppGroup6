package com.example.weatherappgroup6.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappgroup6.R;
import com.example.weatherappgroup6.databinding.WeatherColumnBinding;
import com.example.weatherappgroup6.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    WeatherColumnBinding weatherColumnBinding;
    private List<Weather> weatherList;
    Context context;
    //    Constructor of WeatherRecyclerViewAdapater
    public WeatherRecyclerViewAdapter(List<Weather> weatherList, Context context){
        this.weatherList=weatherList;
        this.context=context;
    }
    //    ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder{
        WeatherColumnBinding recyclerColumnBinding;
        //  View Holder constructor to get the root of weatherColumnBinding that is weather_column
        public ViewHolder(WeatherColumnBinding weatherColumnBinding){
            super(weatherColumnBinding.getRoot());
            recyclerColumnBinding=weatherColumnBinding;
        }
        void bindView( List<Weather> weatherList, final int position){
            recyclerColumnBinding.day.setText(weatherList.get(position).getDay());
            recyclerColumnBinding.temp.setText(weatherList.get(position).getTemp()+"°C");
            String icon=weatherList.get(position).getIcon();
            if(icon.equals("01d") || icon.equals("01n")){
                recyclerColumnBinding.icon.setImageResource(R.drawable.sun);
            } else if (icon.equals("02d") || icon.equals("02n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.cloudy);
            }else if (icon.equals("03d") || icon.equals("03n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.cloud);
            }
            else if (icon.equals("04d") || icon.equals("04n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.brokenclouds);
            }
            else if (icon.equals("09d") || icon.equals("09n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.rainy);
            }else if (icon.equals("10d") || icon.equals("10n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.rain);
            }else if (icon.equals("11d") || icon.equals("11n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.storm);
            }else if (icon.equals("13d") || icon.equals("13n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.snowy);
            }else if (icon.equals("50d") || icon.equals("50n")) {
                recyclerColumnBinding.icon.setImageResource(R.drawable.foog);
            }
        }
    }
    //    onCreateViewHolder() to set the weather_column into recycler view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        weatherColumnBinding=WeatherColumnBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(weatherColumnBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(weatherList,position);
    }
    //    getItemCount returns the size of weatherList arraylist
    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
