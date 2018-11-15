package com.example.f2846843.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.f2846843.myapplication.BDLocations.BDLocationActivity;
import com.example.f2846843.myapplication.BDLocations.BDNavigationActivity;
import com.example.f2846843.myapplication.BDLocations.NavigationActivity;
import com.example.f2846843.myapplication.GDLocations.GDLocationActivity;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBDLocation, mBDNaviBtn, mOpenMapBtn,mGDMapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        init();
    }
    private void init(){
        mBDLocation = findViewById(R.id.baidu_location_btn);
        mBDNaviBtn = findViewById(R.id.baidu_navigation_btn);
        mOpenMapBtn = findViewById(R.id.open_map_btn);
        mGDMapBtn = findViewById(R.id.gaode_location_btn);
        mBDLocation.setOnClickListener(this);
        mBDNaviBtn.setOnClickListener(this);
        mOpenMapBtn.setOnClickListener(this);
        mGDMapBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.baidu_location_btn:
                startActivity(new Intent(this, BDLocationActivity.class));
                break;
            case R.id.baidu_navigation_btn:
                startActivity(new Intent(this, BDNavigationActivity.class));
                break;
            case R.id.open_map_btn:
                startActivity(new Intent(this, NavigationActivity.class));
                break;
            case R.id.gaode_location_btn:
                startActivity(new Intent(this, GDLocationActivity.class));
                break;

            default:
                break;
        }
    }
}
