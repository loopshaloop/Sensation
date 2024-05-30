package com.example.sensation.UI.Home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensation.UI.Game.GameActivity;
import com.example.sensation.UI.Login.LoginActivity;
import com.example.sensation.Model.Game;
import com.example.sensation.Model.User;
import com.example.sensation.UI.Profile.ProfileActivity;
import com.example.sensation.R;
import com.example.sensation.UI.Statistics.StatisticsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener  {
    private HomePresenter homePresenter;
    private TextView searchButt;
    private TextView welcomeText;
    protected TextView pbText;
    protected Dialog dialog;
    private BottomNavigationView navBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        searchButt = findViewById(R.id.search_home);
        welcomeText = findViewById(R.id.welcome_home);
        navBar = findViewById(R.id.nav_bar_profile);
        pbText = findViewById(R.id.pb_home);
        navBar.setOnItemSelectedListener(this);
        homePresenter = new HomePresenter(this);
        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog();
            }
        });

    }
    public void findDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_dialog);
        TextView cancelButt = dialog.findViewById(R.id.cancel_loading);
        cancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.cancelMatch();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        homePresenter.searchForMatch();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.profile_nav) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.statistics_nav) {
            Intent intent = new Intent(HomeActivity.this, StatisticsActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.logout_nav) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void showUsername(String string) {
        String text = "Hello, " + string;
        welcomeText.setText(text);
    }
    public void moveToGame(String id){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("ID", id);
        dialog.dismiss();
        startActivity(intent);
        finish();
    }
    public void setBest(User user){
        Game game = user.bestGame();
        int min = (int)Math.floor((double)(game.getTime() / 60));
        int sec = (int)(game.getTime() % 60);
        pbText.setText("PB Score: " + game.getScore() + "  PB Time: " + min + "m " + sec+ "s");
    }
}
