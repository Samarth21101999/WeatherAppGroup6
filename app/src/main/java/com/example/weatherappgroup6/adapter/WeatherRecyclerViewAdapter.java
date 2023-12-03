package com.example.weatherappgroup6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappgroup6.R;
import com.example.weatherappgroup6.databinding.WeatherColumnBinding;
import com.example.weatherappgroup6.model.Weather;

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
            this.recyclerColumnBinding=weatherColumnBinding;
        }
        void bindView( List<Weather> weatherList, final int position){
            recyclerColumnBinding.day.setText(weatherList.get(position).getDay());
            recyclerColumnBinding.temp.setText((int) weatherList.get(position).getTemp());
            switch(weatherList.get(position).getIcon()){
                case "1n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.sun);
                    break;
                case "2n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.cloudy);
                    break;
                case "3n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.cloud);
                    break;
                case "4n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.brokenclouds);
                    break;
                case "9n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.rainy);
                    break;
                case "10n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.rain);
                    break;
                case "11n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.storm);
                    break;
                case "13n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.snowy);
                    break;
                case "50n":
                    recyclerColumnBinding.imageView2.setImageResource(R.drawable.foog);
                    break;
                default:
                    Toast.makeText(context, "Error in getting image", Toast.LENGTH_SHORT);
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
