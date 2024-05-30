package com.example.sensation.UI.Profile;

import com.example.sensation.Persistence.Repository;
import com.example.sensation.Model.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilePresenter implements Repository.LoadUserListener{
    private ProfileActivity profileActivity;
    private Repository repository;
    public ProfilePresenter(ProfileActivity profileActivity){
        this.profileActivity = profileActivity;
        this.repository = Repository.getInstance();
        this.repository.setLoadUserListener(this);
        this.repository.readUser(FirebaseAuth.getInstance().getUid());
    }
    public void updateUser(User user)
    { profileActivity.setTexts(user); }
}
