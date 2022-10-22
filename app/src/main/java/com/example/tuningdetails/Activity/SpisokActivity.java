package com.example.tuningdetails.Activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuningdetails.R;
import com.example.tuningdetails.Fragments.SpisokFragment;


public class SpisokActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.activitySpisok,new SpisokFragment()).commit();
    }
}