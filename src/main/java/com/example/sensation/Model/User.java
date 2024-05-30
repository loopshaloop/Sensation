package com.example.sensation.Model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class User {
    String username;
    String email;
    String id;
    ArrayList<Game> gameHistory;
    public User(){}

    public User(String username, String email, String id, ArrayList<Game> gameHistory) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.gameHistory = gameHistory;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<Game> getGameHistory() {
        return gameHistory;
    }
    public void setGameHistory(ArrayList<Game> gameHistory) {
        this.gameHistory = gameHistory;
    }
    public ArrayList<Game> sortGames()
    {
        ArrayList<Game> arr = new ArrayList<Game>();
        for(int k = 0; k < this.getGameHistory().size(); k++){
            arr.add(this.getGameHistory().get(k));
        }
        int i, j;
        int n = arr.size();
        Game temp;
        boolean swapped;
        if(!arr.isEmpty()){
            for (i = 0; i < n - 1; i++) {
                swapped = false;
                for (j = 0; j < n - i - 1; j++) {
                    if (arr.get(j).getDate().after(arr.get(j + 1).getDate())) {
                        temp = arr.get(j);
                        arr.set(j , arr.get(j + 1));
                        arr.set(j + 1, temp);
                        swapped = true;
                    }
                }
                if (swapped == false)
                    break;
            }
        }
        return arr;
    }
    public Game bestGame(){
        Game game = new Game();
        if(gameHistory == null){
            gameHistory = new ArrayList<>();
        }
        if(!gameHistory.isEmpty()){
            game = gameHistory.get(0);
            for(int i = 0; i < gameHistory.size(); i++){
                if(gameHistory.get(i).getScore() > game.getScore()){
                    game = gameHistory.get(i);
                }
            }
        }
        return game;
    }
}
