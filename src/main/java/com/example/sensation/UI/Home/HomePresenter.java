package com.example.sensation.UI.Home;

import com.example.sensation.Model.Match;
import com.example.sensation.Model.Player;
import com.example.sensation.Persistence.Repository;
import com.example.sensation.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class HomePresenter implements Repository.LoadUserListener, Repository.LoadMatchListener, Repository.LoadMatchesListener{
    private HomeActivity homeActivity;
    private Repository repository;
    private User user;
    private Match match;
    public HomePresenter(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
        this.repository = Repository.getInstance();
        repository.setLoadUserListener(this);
        repository.readUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
    @Override
    public void updateUser(User user) {
        this.user = user;
        homeActivity.showUsername(user.getUsername());
        homeActivity.setBest(user);
    }
    @Override
    public void updateMatch(Match match) {
        if(match != null && match.getStatus() == 1){
            homeActivity.moveToGame(match.getId());
        }
    }
    @Override
    public void updateMatches(ArrayList<Match> matches)
    {
        boolean create = true;
        for(Match match: matches){
            if(Match.isFree(match) && match.getStatus() == 0){
                create = false;
                Player player = new Player(user.getId(), user.getUsername());
                match.setPlayer2(player);
                match.setStatus(1);
                match.setStart(new Date());
                repository.createMatch(match);
                homeActivity.moveToGame(match.getId());
            }
        }
        if(create){
            Match match = new Match(new Player(FirebaseAuth.getInstance().getCurrentUser().getUid()));
            this.match = match;
            repository.createMatch(match);
            repository.readMatch(match.getId());
        }
    }
    public void searchForMatch(){
        repository.setLoadMatchesListener(this);
        repository.setLoadMatchListener(this);
        repository.readMatches();

    }
    public void cancelMatch()
    {
        repository.removeMatch(match);
    }
}
