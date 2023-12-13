package com.example.weatherappgroup6.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappgroup6.R;
import com.example.weatherappgroup6.adapter.CityRecyclerViewAdapter;
import com.example.weatherappgroup6.databinding.ActivityListCitiesBinding;
import com.example.weatherappgroup6.databinding.ActivityMainBinding;
import com.example.weatherappgroup6.model.City;
import com.example.weatherappgroup6.utils.CityUtils;
import com.example.weatherappgroup6.utils.Constants;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class ListCitiesActivity extends AppCompatActivity {
    ActivityListCitiesBinding activityListCitiesBinding;
    private List<City> myCities = new ArrayList<>();
    private CityRecyclerViewAdapter cAdapter;
    /*onCreate() method
     *  In the onCreate() method setting up the view
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListCitiesBinding = ActivityListCitiesBinding.inflate(getLayoutInflater());
        View view = activityListCitiesBinding.getRoot();
        setContentView(view);
        initCity();
        activityListCitiesBinding.cityRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityListCitiesBinding.cityRecyclerView.setLayoutManager(mLayoutManager);

    }

    /*initCity() method
     *  From initCity() method add the city to recycler view using onEditorAction
     *  And also comparing city if already exists or not
     * */
    private void initCity() {
        activityListCitiesBinding.addCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String text = v.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        if(CityUtils.compareCity(text,getApplicationContext())){
                            City city=new City(String.valueOf(myCities.size()+1),text);
                            updateCity(city, Constants.ACTION_ADD);
                            v.setText("");
                            return true;
                        }else{
                            Toast.makeText(getApplicationContext(),"City Already Exists",Toast.LENGTH_LONG).show();
                            v.setText("");
                        }
                    }
                }
                return false;
            }
        });
    }



    /*updateCity() method to save city to shared preference
     *  There is one condition only 5 cities can be added
     * */
    private void updateCity(City city, int action) {
        if (action == Constants.ACTION_ADD) {
            if(!(myCities.size() >4)){
                CityUtils.saveCity(city, this);
            }else{
                Toast.makeText(this,"You can only add five cities",Toast.LENGTH_LONG).show();
            }
        }
        updateCityAdapter();
    }

    /*updateCityAdapter()
    *   To add all cities that we get from sharedPreference
    *
    * */
    private void updateCityAdapter() {
        myCities.clear();
        myCities.addAll(CityUtils.getAllCities(this));
        cAdapter = new CityRecyclerViewAdapter(myCities, getApplicationContext());
        activityListCitiesBinding.cityRecyclerView.setAdapter(cAdapter);
    }


    /*onResume() method
    * When activity resumes call updateCityAadapter() to bind the data to recycler view.
    * */
    protected void onResume() {
        super.onResume();
        updateCityAdapter();
    }
}