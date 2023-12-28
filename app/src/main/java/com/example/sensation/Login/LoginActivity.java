package com.example.sensation.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sensation.R;
import com.example.sensation.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton loginButt;
    private AppCompatButton registerButt;
    protected EditText usernameLogin;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        loginButt = findViewById(R.id.login_login);
        usernameLogin = findViewById(R.id.username_login);
        registerButt = findViewById(R.id.register_login);
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.Login();
            }
        });
        registerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
