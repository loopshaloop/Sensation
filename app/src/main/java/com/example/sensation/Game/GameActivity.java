package com.example.sensation.Game;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sensation.R;

import java.text.DecimalFormat;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    protected TextView time;
    protected ImageView menu;
    protected AppCompatButton levelButt;
    protected AppCompatButton finishButt;
    private double max = 0;
    private TextView levelText;
    private TextView accelerationText;
    private TextView maxAccel;
    GamePresenter gamePresenter;
    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds;
    private SensorManager sensorManager;
    double ax,ay,az;   // these are the acceleration in x,y and z axis


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gamePresenter = new GamePresenter(this);
        timeLeftinMilliseconds = gamePresenter.baseTime * 1000;
        menu = findViewById(R.id.menu_game);
        time = findViewById(R.id.time_game);
        levelText = findViewById(R.id.level_game);
        accelerationText = findViewById(R.id.acceleration_game);
        maxAccel = findViewById(R.id.max_accel);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        countDownTimer = new CountDownTimer(timeLeftinMilliseconds, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftinMilliseconds = millisUntilFinished;
                updateTime();
                if(timeLeftinMilliseconds > 0){
                    if(gamePresenter.checkChallenge())
                        gamePresenter.challengeSuccess();
                }
            }
            @Override
            public void onFinish() {
                gamePresenter.challengeFailed();
            }
        }.start();
    }
    public void menuDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.menu_dialog);
        Button retButt = dialog.findViewById(R.id.retButt);
        Button quitButt = dialog.findViewById(R.id.quitButt);
        Button settButt = dialog.findViewById(R.id.settButt);
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void quitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you wan to quit?\nYour progress will not be saved!");
        builder.setTitle("Quitting!");
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void menuClick(View view){
        gamePresenter.menuClicked();
    }
    public void updateTime(){
        int minutes = (int)timeLeftinMilliseconds / 60000;
        int seconds = (int)timeLeftinMilliseconds % 60000 / 1000;
        String timeLeftText;
        timeLeftText = "" + minutes + "m ";
        timeLeftText += "" + seconds + "s";
        time.setText(timeLeftText);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];
            DecimalFormat df = new DecimalFormat("#.#");
            double acc = Math.abs(Math.sqrt(Math.pow(ax, 2) + Math.pow(ay, 2) + Math.pow(az, 2)));
            if (acc > max){
                max = acc;
                maxAccel.setText("Highest acceleration: " + max);
            }
            if (acc > 60){
                gamePresenter.challengeSuccess();
                countDownTimer.cancel();
                timeLeftinMilliseconds = gamePresenter.baseTime * 1000;
                updateTime();
                countDownTimer = new CountDownTimer(timeLeftinMilliseconds, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeLeftinMilliseconds = millisUntilFinished;
                        updateTime();
                        if(timeLeftinMilliseconds > 0){
                            if(gamePresenter.checkChallenge())
                                gamePresenter.challengeSuccess();
                        }
                    }
                    @Override
                    public void onFinish() {
                        gamePresenter.challengeFailed();
                    }
                }.start();
                levelText.setText("Level: " + gamePresenter.level);
            }
            accelerationText.setText("Your acceleration: " + df.format(acc) + "m/s^2");
            System.out.println(df.format(acc));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}