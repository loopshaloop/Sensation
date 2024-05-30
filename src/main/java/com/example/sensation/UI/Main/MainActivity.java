package com.example.sensation.UI.Main;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sensation.UI.Home.HomeActivity;
import com.example.sensation.UI.Login.LoginActivity;
import com.example.sensation.R;

public class MainActivity extends AppCompatActivity {
    protected ImageView connectionImg;
    protected TextView checkTextMain;
    protected Intent intent;
    private CountDownTimer countDownTimer;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionImg = findViewById(R.id.connect_img_main);
        checkTextMain = findViewById(R.id.check_text_main);
        mainPresenter = new MainPresenter(this);
        checkConnection();
    }
    public void checkConnection() {
        intent = new Intent(MainActivity.this, LoginActivity.class);
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                startActivity(intent);
                finish();
            }
        };
        switch (mainPresenter.getConnectivityStatus()){
            case 2:
                checkTextMain.setText("You have connection!\nMoving you to home!");
                connectionImg.setImageResource(R.drawable.check_mark);
                connectionImg.setVisibility(View.VISIBLE);
                intent = new Intent(MainActivity.this, HomeActivity.class);
                countDownTimer.start();
                break;
            case 1:
                checkTextMain.setText("You have connection!\nMoving you to login!");
                connectionImg.setImageResource(R.drawable.check_mark);
                connectionImg.setVisibility(View.VISIBLE);
                countDownTimer.start();
                break;
            case 0:
                connectionImg.setVisibility(View.VISIBLE);
                checkTextMain.setText("You don't have connection!\nExiting app!");
                countDownTimer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {}
                }.start();
                finish();
                break;
        }
    }
}