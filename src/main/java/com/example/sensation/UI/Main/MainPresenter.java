package com.example.sensation.UI.Main;

import static androidx.core.content.ContextCompat.getSystemService;

import com.google.firebase.auth.FirebaseAuth;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainPresenter {
    private MainActivity mainActivity;
    public MainPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public int getConnectivityStatus() {
        ConnectivityManager cm = getSystemService(mainActivity, ConnectivityManager.class);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser() != null) {
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
