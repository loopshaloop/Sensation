package com.example.sensation.Login;

import android.content.Intent;
import android.widget.Toast;

import com.example.sensation.Home.HomeActivity;

public class LoginPresenter {
    private LoginActivity loginActivity;
    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }
    public boolean Login(){
        boolean good = true;
        String username = loginActivity.usernameLogin.getText().toString();
        if(username == "" || username.contains(" "))
            good = false;

        if(good){
            //do something if meets all the standard
            Intent intent = new Intent(this.loginActivity, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            loginActivity.startActivity(intent);
        } else if (!good) {
            Toast.makeText(loginActivity, "Username is not good!", Toast.LENGTH_LONG).show();
        }
        return good;
    }
}
