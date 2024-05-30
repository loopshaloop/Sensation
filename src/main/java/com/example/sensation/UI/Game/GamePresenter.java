package com.example.sensation.UI.Game;
import android.hardware.Sensor;
import android.os.CountDownTimer;

import com.example.sensation.Model.Game;
import com.example.sensation.Model.Match;
import com.example.sensation.Model.Player;
import com.example.sensation.Persistence.Repository;
import com.example.sensation.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.Random;

public class GamePresenter implements Repository.LoadUserListener, Repository.LoadMatchListener {
    private GameActivity gameActivity;
    private Repository repository;
    protected int level;
    private final int TIME_CONST = 120;
    private boolean gameRunning = true;
    private long baseTime;
    private long timeLeftinMilliseconds;
    protected int finalTime;
    private CountDownTimer countDownTimer;
    private final int[] SENSOR_TYPES =
            {Sensor.TYPE_LINEAR_ACCELERATION, Sensor.TYPE_LIGHT};
    private Match match;
    public User user1;
    public User user2;
    private String id;

    public GamePresenter(GameActivity gameActivity, String id){
        this.gameActivity = gameActivity;
        this.id = id;
        this.level = 1;
        this.finalTime = 0;
        repository = Repository.getInstance();
        repository.setLoadUserListener(this);
        repository.setLoadMatchListener(this);
        repository.readMatch(id);
    }
    public void generateChallenge() {
        Random random = new Random();
        int i = random.nextInt(SENSOR_TYPES.length);
        int sensorType = SENSOR_TYPES[i];
    }
    public boolean checkChallenge(){
        boolean state = false;
        //check the challenge state and
        //return true if the challenge is done
        //and false otherwise
        return state;
    }
    public void recalibrateTimer(){
        if(countDownTimer != null)
            countDownTimer.cancel();
        baseTime = Math.round(TIME_CONST / Math.sqrt(level));
        timeLeftinMilliseconds = baseTime * 1000;
        if(user1.getId().equals(FirebaseAuth.getInstance().getUid())){
            timeLeftinMilliseconds += 5;
        }
        countDownTimer = new CountDownTimer(timeLeftinMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                finalTime += 1;
                timeLeftinMilliseconds = millisUntilFinished;
                gameActivity.updateTime(timeLeftinMilliseconds);
            }
            @Override
            public void onFinish() {
                challengeFailed();
            }
        };
    }
    public void challengeSuccess(){
        level++;
        Player self = Player.returnSelf(match.getPlayer1(), match.getPlayer2());
        self.setScore(level - 1);
        if(match.getPlayer1().getId().equals(self.getId())){
            match.setPlayer1(self);
        } else if (match.getPlayer2().getId().equals(self.getId())){
            match.setPlayer1(self);
        }
        Repository.getInstance().createMatch(match);
        recalibrateTimer();
        countDownTimer.start();
    }
    public void challengeFailed() {
        User self = new User();
        if(match.getPlayer1() != null && match.getPlayer1().getId().equals(FirebaseAuth.getInstance().getUid()) && this.user1.getId().equals(FirebaseAuth.getInstance().getUid())) {
            self = user1;
            this.match.setPlayer1(null);
        } else if(match.getPlayer2() != null && match.getPlayer2().getId().equals(FirebaseAuth.getInstance().getUid()) && this.user2.getId().equals(FirebaseAuth.getInstance().getUid())) {
            self = user2;
            this.match.setPlayer2(null);
        }
        if(self.getId() != null){
            if(match.getStatus() == 2){
                repository.removeMatch(this.match);
            }
            else{
                this.match.setStatus(2);
                repository.createMatch(match);
            }
            this.gameRunning = false;
            Game game = new Game(this.match.getStart(), level - 1, finalTime);
            if(self.getGameHistory() == null){
                self.setGameHistory(new ArrayList<>());
            }
            self.getGameHistory().add(game);
            repository.addUser(self);
            gameActivity.finishGame();
        }
    }
    @Override
    public void updateUser(User user){
        if(this.gameRunning) {
            if (user.getId().equals(match.getPlayer1().getId())) {
                this.user1 = user;
            } else if (user.getId().equals(match.getPlayer2().getId())) {
                this.user2 = user;
            }
            if (user1 != null && user2 != null) {
                if (user1.getId().equals(FirebaseAuth.getInstance().getUid())) {
                    gameActivity.updateOpponent(user2.getUsername());
                } else {
                    gameActivity.updateOpponent(user1.getUsername());
                }
                recalibrateTimer();
                countDownTimer.start();
            }
        }
    }
    @Override
    public void updateMatch(Match match) {
        if(match != null){
            if(this.match == null) {
                repository.readUser(match.getPlayer1().getId());
                repository.readUser(match.getPlayer2().getId());
            }
            this.match = match;
            if(match.getStatus() == 2){
                challengeFailed();
            }
            gameActivity.setLevels(match.getPlayer1(), match.getPlayer2());
        }
    }
}

