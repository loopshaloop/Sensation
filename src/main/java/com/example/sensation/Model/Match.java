package com.example.sensation.Model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class Match {
    Player player1;
    Player player2;
    Date start;
    String id;
    int status;
    public Match(Player player1){
        this.player1 = player1;
        this.start = new Date();
        this.status = 0;
    }
    public Match(){}
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public static boolean isFree(Match match){
        boolean a = match.getPlayer2() == null || match.getPlayer1() == null;
        return a;
    }
}
