package com.yilmazgokhan.movieapp.v1.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.yilmazgokhan.movieapp.v1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Prepare View Model
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        showWelcomeMessage();
    }

    /**
     * Show the Toast message to user
     */
    private void showWelcomeMessage() {
        Toast.makeText(this, "Welcome to MyApp", Toast.LENGTH_SHORT).show();
    }
}