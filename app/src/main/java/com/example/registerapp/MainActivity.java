package com.example.registerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Set the first fragment to be displayed in the fragment layout.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, RegisterFragment.class, null)
                .commit();
    }
}