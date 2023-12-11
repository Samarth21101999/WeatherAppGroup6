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
    //    Constructor of TaskRecyclerViewAdapter
    public WeatherRecyclerViewAdapter(List<Weather> weatherList, Context context){
        this.weatherList=weatherList;
        this.context=context;
    }
    //    ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder{
        WeatherColumnBinding recyclerColumnBinding;
        //  View Holder constructor to get the root of taskRowBinding that is task_row
        public ViewHolder(WeatherColumnBinding weatherColumnBinding){
            super(weatherColumnBinding.getRoot());
            recyclerColumnBinding=weatherColumnBinding;
        }
        void bindView( List<Weather> weatherList, final int position){
            recyclerColumnBinding.day.setText(weatherList.get(position).getDay());
            recyclerColumnBinding.temp.setText(weatherList.get(position).getTemp());
            String icon=weatherList.get(position).getIcon();

            Log.d("Message", String.valueOf(weatherList.get(position).getIcon()));

//            if(icon.equals("1d") || icon.equals("1n")){
//                recyclerColumnBinding.icon.setImageResource(R.drawable.sun);
//            } else if (icon.equals("2d") || icon.equals("2n")) {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.cloudy);
//            }else if (icon.equals("3d") || icon.equals("3n")) {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.cloud);
//            }
//            else if (icon.equals("4d") || icon.equals("4n")) {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.brokenclouds);
//            }
//            else if (icon=="9d" || icon=="9n") {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.rainy);
//            }else if (icon=="10d" || icon=="10n") {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.rain);
//            }else if (icon=="11d" || icon=="11n") {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.storm);
//            }else if (icon=="13d" || icon=="13n") {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.snowy);
//            }else if (icon=="50d" || icon=="50n") {
//                recyclerColumnBinding.icon.setImageResource(R.drawable.foog);
//            }
            switch(weatherList.get(position).getIcon()){
                case "01d":
                    Log.d("1n","1n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.sun);
                    break;
                case "01n":
                    Log.d("1n","1n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.sun);
                    break;
                case "02d":
                    Log.d("2n","2n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.cloudy);
                    break;
                case "02n":
                    Log.d("2n","2n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.cloudy);
                    break;
                case "03d":
                    Log.d("3n","3n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.cloud);
                    break;
                case "03n":
                    Log.d("3n","3n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.cloud);
                    break;
                case "04d":
                    Log.d("4n","4n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.brokenclouds);
                    break;
                case "04n":
                    Log.d("4n","4n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.brokenclouds);
                    break;
                case "09d":
                    Log.d("9n","9n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.rainy);
                    break;
                case "09n":
                    Log.d("9n","9n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.rainy);
                    break;
                case "10d":
                    Log.d("10n","10n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.rain);
                    break;
                case "10n":
                    Log.d("10n","10n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.rain);
                    break;
                case "11d":
                    Log.d("11n","11n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.storm);
                    break;
                case "11n":
                    Log.d("11n","11n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.storm);
                    break;
                case "13d":
                    Log.d("13n","13n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.snowy);
                    break;
                case "13n":
                    Log.d("13n","13n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.snowy);
                    break;
                case "50d":
                    Log.d("50n","50n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.foog);
                    break;
                case "50n":
                    Log.d("50n","50n");
                    recyclerColumnBinding.icon.setImageResource(R.drawable.foog);
                    break;
                default:
                    Toast.makeText(context, "Error in getting image", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //    onCreateViewHolder() to set the task_row into recycler view
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
    //    getItemCount returns the size of dueTasks arraylist
    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
