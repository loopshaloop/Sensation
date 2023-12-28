package com.example.sensation.Register;

import android.content.Intent;
import android.widget.Toast;

import com.example.sensation.Home.HomeActivity;
import com.example.sensation.Main.MainActivity;
import com.example.sensation.R;

public class RegisterPresenter {
    private RegisterActivity registerActivity;
    public RegisterPresenter(RegisterActivity registerActivity){
        this.registerActivity = registerActivity;
    }
    public boolean Register(){
        boolean good = true;
        String username = registerActivity.username.getText().toString();
        if(username == "" || username.contains(" "))
            good = false;

        if(good){
            //do something if meets all the standard
            Intent intent = new Intent(this.registerActivity, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            registerActivity.startActivity(intent);
        } else if (!good) {
            Toast.makeText(registerActivity, "Username is not good!", Toast.LENGTH_LONG).show();
        }
        return good;
    }
}
