package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.Screens.Play;


/**
 * Created by Isaias on 5/26/2016.
 * Classe responveal pelo score, level e tempo do jogo
 */
public class Hud extends Thread{
    public Stage stage;
    private int level;
    private int time;
    private int score;

    private Play screen;

    Viewport view;

    Label levelLabel;
    Label scoreLabel;
    Label timeLabel;
    private SpriteBatch bs;

    public Hud(Play screen, SpriteBatch sb){
        this.screen = screen;
        this.bs = sb;
        this.level = 1;
        this.score = 0;
        this.time = 80;

        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());

        stage = new Stage(view, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //labels
        levelLabel = new Label("Level: " + level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label("Score: "+ score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Tempo " + time, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel.setFontScale(1.5f);
        timeLabel.setFontScale(2);
        scoreLabel.setFontScale(1.5f);


        table.add(levelLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        stage.addActor(table);

    }

    public void drawStage() {

        bs.setProjectionMatrix(Constants.CAM_FIXED.combined);

        stage.draw();

    }

    public void update(){
    //diminui o tempo a cada um segundo
    this.time -=1;
    scoreLabel.setText("Score: " + getScore());
    timeLabel.setText("Time: " + time);
        if(time < 20)
            timeLabel.setColor(1, 0, 0, 1);// se o tempo estiver acabando a cor muda para vermelho

        if(time < 0)
            screen.setIsRunnuing(false);
}

    @Override
    public void run(){
        if(screen.getIsRunnuing()){
            while(screen.getIsRunnuing()){
                try{
                    Thread.sleep(1000);
                    update();
                }
                catch(Exception e){

                }

            }

        }else{
            reset();
        }


    }
    public void reset(){

    }
    //adiciona pontuação ao score
    public synchronized void addScore(){
       score = getScore() + 100;
    }

    public synchronized int getScore(){
        return score;
    }

}
