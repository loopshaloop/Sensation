package com.example.sensation.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sensation.Login.LoginActivity;
import com.example.sensation.R;

public class MainActivity extends AppCompatActivity {
    protected ImageView connectionImg;
    protected TextView checkTextMain;
    private MainPresenter mainPresenter;
    private long waitTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionImg = findViewById(R.id.connect_img_main);
        checkTextMain = findViewById(R.id.check_text_main);
        mainPresenter = new MainPresenter(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkConnection();
            }
        }, waitTime);
    }

    public void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            checkTextMain.setText("You have connection!\nMoving you to login!");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }, waitTime);
        } else {
            connectionImg.setVisibility(View.VISIBLE);
            checkTextMain.setText("You don't have connection!\nExiting app!");
            finish();
        }


    }
}