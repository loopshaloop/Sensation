package com.example.sensation.UI.Game;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sensation.UI.Home.HomeActivity;
import com.example.sensation.Model.Player;
import com.example.sensation.Persistence.MusicService;
import com.example.sensation.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    protected TextView time;
    protected ImageView menu;
    private TextView levelText;
    private TextView accelerationText;
    private TextView maxAccel;
    private TextView opponentText;
    private TextView opponentLevelText;
    private GamePresenter gamePresenter;
    private Dialog dialog;
    private double max;
    private SensorManager sensorManager;
    protected boolean sfxPlayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        max = 0;
        sfxPlayable = true;
        gamePresenter = new GamePresenter(this, getIntent().getExtras().getString("ID"));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
        menu = findViewById(R.id.menu_game);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDialog();
            }
        });
        time = findViewById(R.id.time_game);
        levelText = findViewById(R.id.level_game);
        accelerationText = findViewById(R.id.acceleration_game);
        maxAccel = findViewById(R.id.max_accel);
        opponentText = findViewById(R.id.opponent_game);
        opponentLevelText = findViewById(R.id.opp_level_game);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(Integer.parseInt(Build.VERSION.SDK) < 5
            && keyCode == KeyEvent.KEYCODE_BACK
            && event.getRepeatCount() == 0){
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed(){}
    public void menuDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.menu_dialog);
        Button retButt = dialog.findViewById(R.id.ret_butt);
        Button quitButt = dialog.findViewById(R.id.quit_butt);
        Button settButt = dialog.findViewById(R.id.sett_butt);
        retButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        quitButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitDialog();
            }
        });
        settButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog();
            }
        });
        dialog.show();
    }
    private void settingsDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.settings_dialog);
        Button retButt = dialog.findViewById(R.id.ret_butt);
        Switch sfxSwitch = dialog.findViewById(R.id.sfx_switch);
        sfxSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sfxPlayable = isChecked;
            }
        });
        retButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void quitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit?\nYour progress will not be saved!");
        builder.setTitle("Quitting!");
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
            finishGame();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void updateTime(long timeLeftinMilliseconds)
    {
        int minutes = (int)(timeLeftinMilliseconds / 60000);
        int seconds = (int)((timeLeftinMilliseconds % 60000) / 1000);
        String timeLeftText;
        timeLeftText = "" + minutes + "m ";
        timeLeftText += "" + seconds + "s";
        time.setText(timeLeftText);
    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            DecimalFormat df = new DecimalFormat("#.#");
            double acc = Math.abs(Math.sqrt(Math.pow(ax, 2) + Math.pow(ay, 2) + Math.pow(az, 2)));
            if (acc > max){
                max = acc;
                maxAccel.setText("Highest acceleration: " + df.format(max) + "m/s^2");
            }
            if (acc > 10 + 2 * (gamePresenter.level - 1)){
                gamePresenter.challengeSuccess();
                soundOfSuccess();
            }
            accelerationText.setText("Your acceleration: " + df.format(acc) + "m/s^2");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }
    public void finishGame() {
        dialog.dismiss();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void updateOpponent(String opponent) {opponentText.setText("Opponent: " + opponent);}
    public void setLevels(Player player1, Player player2)
    {
        if(player1 != null && player1.getId().equals(FirebaseAuth.getInstance().getUid())){
            levelText.setText("Level: " + player1.getScore());
            opponentLevelText.setText("Opponent level: " + player2.getScore());
        }
        else if(player2 != null && player2.getId().equals(FirebaseAuth.getInstance().getUid())){
            levelText.setText("Level: " + player2.getScore());
            opponentLevelText.setText("Opponent level: " + player1.getScore());
        }
    }
    public void soundOfSuccess(){
        if(sfxPlayable){
            Intent svc = new Intent(this, MusicService.class);
            svc.putExtra("sound", "2");
            startService(svc);
        }
    }
}