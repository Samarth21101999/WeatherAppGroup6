package com.example.weatherappgroup6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.weatherappgroup6.R;
import com.example.weatherappgroup6.databinding.ActivityListCitiesBinding;
import com.example.weatherappgroup6.databinding.ActivityMainBinding;

public class ListCitiesActivity extends AppCompatActivity {
    ActivityListCitiesBinding activityListCitiesBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListCitiesBinding = ActivityListCitiesBinding.inflate(getLayoutInflater());
        View view = activityListCitiesBinding.getRoot();
        setContentView(view);
        activityListCitiesBinding.addCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int i, KeyEvent keyEvent) {
                if(i==EditorInfo.IME_ACTION_SEND){
                    String textInput=view.getText().toString();

                }
                return false;
            }
        });
    }
}