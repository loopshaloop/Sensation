package com.example.sensation.UI.Register;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.sensation.Model.Game;
import com.example.sensation.Persistence.Repository;
import com.example.sensation.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterPresenter {
    private RegisterActivity registerActivity;
    public RegisterPresenter(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    public void Register(String email, String password, String username)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        boolean can = !email.isEmpty() && !password.isEmpty() && !email.contains(" ") && !password.contains(" ");
        if (can) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username, email, mAuth.getUid(), new ArrayList<Game>());
                            Repository.getInstance().addUser(user);
                            registerActivity.signUpSuccess();
                        } else {
                            Toast.makeText(registerActivity, "Problem arised creating the user. Check yourself!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        } else {
            Toast.makeText(registerActivity, "Cannot login with empty parameters or with spaces!", Toast.LENGTH_LONG).show();
        }
    }
}