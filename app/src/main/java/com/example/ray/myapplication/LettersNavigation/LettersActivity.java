package com.example.ray.myapplication.LettersNavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ray.myapplication.R;

public class LettersActivity extends AppCompatActivity {
    private LettersView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);
        view = findViewById(R.id.letter_list);
    }
}
