package com.example.sensation.Model;

import java.util.Date;

public class Game {
    private Date date;
    private int score;
    private long time;

    public Game() {}
    public Game(Date date, int score, long time){
        this.date = date;
        this.score = score;
        this.time = time;
    }
    public Date getDate() {
        return date;
    }
    public int getScore() {
        return score;
    }
    public long getTime() {
        return time;
    }
}
