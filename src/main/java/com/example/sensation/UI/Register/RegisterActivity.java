package com.example.sensation.UI.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sensation.UI.Home.HomeActivity;
import com.example.sensation.UI.Login.LoginActivity;
import com.example.sensation.R;

public class RegisterActivity extends AppCompatActivity {
    protected EditText usernameRegister;
    protected EditText emailRegister;
    protected EditText passwordRegister;
    private RegisterPresenter registerPresenter;
    private TextView loginButt;
    private TextView registerButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this);
        usernameRegister = findViewById(R.id.username_register);
        emailRegister = findViewById(R.id.email_register);
        passwordRegister = findViewById(R.id.password_register);
        loginButt = findViewById(R.id.login_register);
        registerButt = findViewById(R.id.register_register);
        registerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                String username = usernameRegister.getText().toString();
                registerPresenter.Register(email, password, username);
            }
        });
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void signUpSuccess(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
