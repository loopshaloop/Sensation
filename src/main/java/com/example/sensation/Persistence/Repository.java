package com.example.sensation.Persistence;
import com.example.sensation.Model.Match;
import com.example.sensation.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Repository {
    private static Repository repository;
    private LoadUserListener loadUserListener;
    private LoadMatchListener loadMatchListener;
    private LoadMatchesListener loadMatchesListener;
    public interface LoadUserListener{
        public void updateUser(User user);
    }
    public interface LoadMatchListener{
        public void updateMatch(Match match);
    }
    public interface LoadMatchesListener{
        public void updateMatches(ArrayList<Match> matches);
    }
    private Repository() {}
    public static Repository getInstance()
    {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }
    public LoadUserListener getLoadUserListener() {
        return loadUserListener;
    }

    public void setLoadUserListener(LoadUserListener loadUserListener) {
        this.loadUserListener = loadUserListener;
    }

    public LoadMatchListener getLoadMatchListener() {
        return loadMatchListener;
    }

    public void setLoadMatchListener(LoadMatchListener loadMatchListener) {
        this.loadMatchListener = loadMatchListener;
    }

    public LoadMatchesListener getLoadMatchesListener() {
        return loadMatchesListener;
    }

    public void setLoadMatchesListener(LoadMatchesListener loadMatchesListener) {
        this.loadMatchesListener = loadMatchesListener;
    }

    public void addUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Users/" + user.getId());
        reference.setValue(user);
    }
    public void removeUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Users/" + user.getId());
        reference.removeValue();
    }
    public void createMatch(Match match){
        if(match.getId() ==null){
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("Matches/").push();
            match.setId(reference.getKey());
            reference.setValue(match);
        }
       else{
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("Matches/" + match.getId());
            reference.setValue(match);
        }
    }
    public void removeMatch(Match match){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("Matches/" + match.getId());
        reference.removeValue();
    }
    public void readUser(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Users/" + id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                loadUserListener.updateUser(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public void readMatches(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Matches");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Match> matches = new ArrayList<Match>();
                for (DataSnapshot matchSnapshot: dataSnapshot.getChildren()) {
                    matches.add(matchSnapshot.getValue(Match.class));
                }
                loadMatchesListener.updateMatches(matches);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public void readMatch(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://sensation-a0bb4-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Matches/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadMatchListener.updateMatch(dataSnapshot.getValue(Match.class));
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
