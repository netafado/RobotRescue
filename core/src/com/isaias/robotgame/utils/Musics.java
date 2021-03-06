package com.isaias.robotgame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Isaias on 6/5/2016.
 * Classe responsabel pela Musicas do jogo
 */
public class Musics extends Thread {
    public AssetManager manager;
    private Music background;
    private Sound coin;
    private Sound saw;
    public Musics(){

    }
    public void Musics(){

    }

    public void run(){
        // carrega os arquivos em outra thread;
        manager = new AssetManager();
        manager.load("background.mp3", Music.class);
        manager.load("coin.mp3", Sound.class);
        manager.load("saw.mp3", Sound.class);
        manager.finishLoading();

        background = manager.get("background.mp3", Music.class);
        background.play();
        background.setLooping(true);

        coin = manager.get("coin.mp3", Sound.class);
        saw = manager.get("saw.mp3", Sound.class);






    }


    public void playCoin(){
        coin.play();
    }

    public void playSaw(){
        saw.play();
    }

    public void stopMusic(){
        background.stop();
    }
}
