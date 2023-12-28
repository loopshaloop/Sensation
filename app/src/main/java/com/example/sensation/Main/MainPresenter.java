package com.example.sensation.Main;

import static android.content.Context.CONNECTIVITY_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import com.example.sensation.Main.MainActivity;
import android.net.ConnectivityManager;

public class MainPresenter {
    private MainActivity mainActivity;
    public MainPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
}
