package com.example.sensation.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sensation.Game.GameActivity;
import com.example.sensation.R;
import com.example.sensation.Statistics.StatisticsActivity;

public class HomeActivity extends AppCompatActivity {
    private AppCompatButton statsButt;
    private AppCompatButton gameButt;
    private HomePresenter homePresenter;
    private TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        statsButt = findViewById(R.id.stats_home);
        gameButt = findViewById(R.id.play_home);
        welcomeText = findViewById(R.id.welcome_home);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        welcomeText.setText("Hello, " + username);
        homePresenter = new HomePresenter(this);
        statsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });
        gameButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
