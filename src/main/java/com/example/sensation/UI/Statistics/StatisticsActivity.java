package com.example.sensation.UI.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sensation.UI.Home.HomeActivity;
import com.example.sensation.UI.Login.LoginActivity;
import com.example.sensation.Model.Game;
import com.example.sensation.Model.User;
import com.example.sensation.UI.Profile.ProfileActivity;
import com.example.sensation.R;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class StatisticsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    private StatisticsPresenter statisticsPresenter;
    private BottomNavigationView navBar;
    protected LineChart mChart;
    protected TextView scoreText;
    protected TextView timeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        navBar = findViewById(R.id.nav_bar_statistics);
        scoreText = findViewById(R.id.pb_score_statistics);
        timeText = findViewById(R.id.pb_time_statistics);
        mChart = findViewById(R.id.chart_statistics);
        statisticsPresenter = new StatisticsPresenter(this);
        navBar.setOnItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.profile_nav) {
            Intent intent = new Intent(StatisticsActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.home_nav) {
            Intent intent = new Intent(StatisticsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.logout_nav) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(StatisticsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void setBest(User user){
        Game game = user.bestGame();
        int min = (int)Math.floor((double)(game.getTime() / 60));
        int sec = (int)(game.getTime() % 60);
        scoreText.setText("PB Score: " + game.getScore());
        timeText.setText("PB Time: " + min + "m " + sec + "s");
        statisticsPresenter.initializeGraph();
    }
}
