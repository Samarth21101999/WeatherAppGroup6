package com.example.weatherappgroup6.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappgroup6.activity.SelectedCityWeather;
import com.example.weatherappgroup6.databinding.CityRowBinding;
import com.example.weatherappgroup6.model.City;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    CityRowBinding rowBinding;
    private List<City> cities;
    Context context;

    //    Constructor of CityRecyclerViewAdapater
    public CityRecyclerViewAdapter(List<City> cityList, Context objContext) {
        super();
        this.cities = cityList;
        this.context = objContext;
    }

    //  //    onCreateViewHolder() to set the city_row into recycler view
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        //return null;
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        rowBinding=rowBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(rowBinding);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CityRowBinding recyclerRowBinding;

        public ViewHolder(CityRowBinding cityRowBinding) {
            super(cityRowBinding.getRoot());
            this.recyclerRowBinding = cityRowBinding;
        }
    /*bindView() method
     *    bind the data to recycler view
     *    upon clicking on particular city redirects to SelectedCityWeather,
     *    to get the detail weather of particular city.
     * */
        public void bindView(final String taskDetails, final int position) {
            recyclerRowBinding.cityName.setText (taskDetails);
            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(context, SelectedCityWeather.class);
                    intent.putExtra("City",taskDetails);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    //onBindViewHolder() method call the bindView and set the data and increase the row according to position
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindView(cities.get(position).getCity(),position);
    }
    public int getItemCount(){
        return cities.size();
    }
}
