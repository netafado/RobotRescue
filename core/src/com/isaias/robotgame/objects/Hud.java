package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.isaias.robotgame.Constants;

/**
 * Created by casa on 5/26/2016.
 */
public class Hud {
    public Stage stage;
    private int level;
    private int time;
    private int score;

    Label levelLabel;
    Label scoreLabel;
    Label timeLabel;

    public Hud(SpriteBatch sb){
        this.level = 1;
        this.score = 0;
        this.time = 100;

        stage = new Stage(Constants.viewportfixed, sb);
        stage.setViewport(Constants.viewportfixed);
        stage.draw();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label("Level: " + level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label("tempo: "+ time, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Score " + score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        levelLabel.setFontScale(1f /  100);
        scoreLabel.setFontScale(1f /  100);
        timeLabel.setFontScale(1f /  100);

        levelLabel.setPosition(20 / Constants.PPM, 300  / Constants.PPM);
        table.add(levelLabel).expandX().padTop(10 / Constants.PPM);
        table.add(scoreLabel).expandX().padTop(10 / Constants.PPM);
        table.add(timeLabel).expandX().padTop(10 / Constants.PPM);

        stage.addActor(table);
    }

    public void drawStage() {

        stage.draw();
    }

}
