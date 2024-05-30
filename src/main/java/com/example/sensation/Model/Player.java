package com.example.sensation.Model;

import com.google.firebase.auth.FirebaseAuth;

public class Player {
    private String id;
    private String username;
    private int score;
    private int time;
    public Player(String id, String username, int score, int time){
        this.id = id;
        this.username = username;
        this.score = score;
        this.time = time;
    }
    public Player(String id, String username){
        this.id = id;
        this.username = username;
        this.score = 0;
        this.time = 0;
    }
    public Player(String id){
        this.id = id;
        this.username = "Unknown";
        this.score = 0;
        this.time = 0;
    }
    public Player(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public static Player returnSelf(Player p1, Player p2)
    {
        if(p1 != null && p1.getId().equals(FirebaseAuth.getInstance().getUid())) {
           return p1;
        } else if (p2 != null && p2.getId().equals(FirebaseAuth.getInstance().getUid())) {
            return p2;
        }
        return null;
    }
}
