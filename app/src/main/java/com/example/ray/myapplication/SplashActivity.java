package com.example.ray.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.example.ray.myapplication.UIView.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
