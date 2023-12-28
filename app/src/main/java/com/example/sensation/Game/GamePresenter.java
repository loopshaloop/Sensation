package com.example.sensation.Game;

import com.example.sensation.Game.GameActivity;

import java.util.Timer;
import java.util.TimerTask;

public class GamePresenter {
    private GameActivity gameActivity;
    final int TIME_EQUATION_CONST = 120;
    int level = 1;
    long baseTime = Math.round(TIME_EQUATION_CONST / Math.sqrt(level));
    boolean success = false;
    public GamePresenter(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }
    public void generateChallenge() {
        //make a random challenge out of a
        //procedural challenge list
    }
    public boolean checkChallenge(){
        boolean state = false;
        //check the challenge state and
        //return true if the challenge is done
        //and false otherwise
        return state;
    }
    public void challengeSuccess(){
        //raise the level and generate another challenge
        level++;
        baseTime = Math.round(TIME_EQUATION_CONST / Math.sqrt(level));
    }
    public void challengeFailed(){
        //register values to statistics
        //and quit current game
        gameActivity.finish();
    }
    public void menuClicked() {
        this.gameActivity.menuDialog();
    }
}

