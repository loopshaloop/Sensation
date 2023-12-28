package com.example.sensation.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.sensation.Login.LoginActivity;
import com.example.sensation.R;

public class RegisterActivity extends AppCompatActivity {
    protected EditText username;
    private RegisterPresenter registerPresenter;
    private AppCompatButton loginButt;
    private AppCompatButton registerButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username_register);
        registerPresenter = new RegisterPresenter(this);
        loginButt = findViewById(R.id.login_register);
        registerButt = findViewById(R.id.register_register);
        registerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.Register();
            }
        });
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
