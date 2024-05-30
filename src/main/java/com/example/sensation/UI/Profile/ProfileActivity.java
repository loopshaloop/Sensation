package com.example.sensation.UI.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensation.UI.Home.HomeActivity;
import com.example.sensation.UI.Login.LoginActivity;
import com.example.sensation.Model.Game;
import com.example.sensation.Model.User;
import com.example.sensation.R;
import com.example.sensation.UI.Statistics.StatisticsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileActivity  extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private ProfilePresenter profilePresenter;
    private BottomNavigationView navBar;
    protected TextView usernameText;
    protected TextView emailText;
    protected TextView pbText;
    protected ImageView imageProfile;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        navBar = findViewById(R.id.nav_bar_profile);
        navBar.setOnItemSelectedListener(this);
        usernameText = findViewById(R.id.username_profile);
        emailText = findViewById(R.id.email_profile);
        imageProfile = findViewById(R.id.image_profile);
        pbText = findViewById(R.id.pb_profile);
        profilePresenter = new ProfilePresenter(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.home_nav) {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.statistics_nav) {
            Intent intent = new Intent(ProfileActivity.this, StatisticsActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.logout_nav) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public void setTexts(User user){
        usernameText.setText("Username: " + user.getUsername());
        emailText.setText("Email: " + user.getEmail());
        Game game = user.bestGame();
        int min = (int)Math.floor((double)(game.getTime() / 60));
        int sec = (int)(game.getTime() % 60);
        pbText.setText("PB Score: " + game.getScore() + "  PB Time: " + min + "m " + sec+ "s");
    }
}
