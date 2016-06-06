package com.isaias.robotgame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by casa on 6/5/2016.
 */
public class Musics extends Thread {
    private AssetManager manager;
    private Music background;
    private Sound coin;

    public Musics(){

    }

    public void run(){
        // carrega os arquivos em outra threead;
        manager = new AssetManager();
        manager.load("background.mp3", Music.class);
        manager.load("coin.mp3", Sound.class);
        manager.finishLoading();

        background = manager.get("background.mp3", Music.class);
        background.play();
        background.setLooping(true);

        coin = manager.get("coin.mp3", Sound.class);

    }

    public void playBackground(){

    }

    public void playCoin(){
        coin.play();
    }
}
