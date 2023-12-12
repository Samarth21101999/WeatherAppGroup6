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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListCitiesBinding = ActivityListCitiesBinding.inflate(getLayoutInflater());
        View view = activityListCitiesBinding.getRoot();
        setContentView(view);
       init();
    }
    private void init() {
        setLayout();
        initTask();
    }

    private void initTask() { //to add time in middle term
        activityListCitiesBinding.addCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String text = v.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        if(CityUtils.compareCity(text,getApplicationContext())){
                            City city=createTask(null,text);
                            updateTask(city, Constants.ACTION_ADD);
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

    private City createTask(String id, String city) {
        if (id == null) {
            id = String.valueOf(System.currentTimeMillis());
        }
        return new City(id, city);
    }

    private void updateTask(City city, int action) {
        if (action == Constants.ACTION_ADD) {
            if(!(myCities.size() >4)){
                for(int i=0;i<myCities.size();i++){
                    Log.d("City Name",myCities.get(i).getCity());
//                    if(myCities.get(i).getCity()==city.getCity()){
//                        Log.d("Message","City Exists");
//                    }
                }
//                Log.d("City Name",city.getCity());
                CityUtils.saveCity(city, this);
            }else{
                Toast.makeText(this,"You can only add five cities",Toast.LENGTH_LONG).show();
            }

            //ConfirmUtils.showSavedMessage(getString(R.string.task_saved), this);
        }
        updateTaskAdapter();
    }

    private void updateTaskAdapter() {
        myCities.clear();
        myCities.addAll(CityUtils.getAllCities(this));
        bindAdapter();
    }

    private void setLayout() {
        activityListCitiesBinding.cityRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        activityListCitiesBinding.cityRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void bindAdapter() {
        cAdapter = new CityRecyclerViewAdapter(myCities, getApplicationContext());
        activityListCitiesBinding.cityRecyclerView.setAdapter(cAdapter);
    }

    protected void onResume() {
        super.onResume();
        updateTaskAdapter();
    }
}