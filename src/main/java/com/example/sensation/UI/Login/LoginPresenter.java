package com.example.sensation.UI.Login;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {
    private LoginActivity loginActivity;
    private boolean hasUser = false;
    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }
    public void Login(String email, String password)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            loginActivity.signInSuccess();
        }
        else{
            boolean can = !email.isEmpty() && !password.isEmpty() && !email.contains(" ") && !password.contains(" ");
            if(can) {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginActivity.signInSuccess();
                            } else {
                                Toast.makeText(loginActivity, "User doesn't exist. Consider making one!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
            else{
                Toast.makeText(loginActivity, "Cannot login with empty parameters or with spaces!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
