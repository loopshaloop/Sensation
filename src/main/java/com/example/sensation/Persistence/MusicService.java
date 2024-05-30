package com.example.sensation.Persistence;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.sensation.R;

public class MusicService extends Service {
    public MusicService() {
    }
    private MediaPlayer mediaPlayer;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        mediaPlayer = MediaPlayer.create(this, R.raw.soundofsuccess);
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
        return Service.START_STICKY;
    }
}