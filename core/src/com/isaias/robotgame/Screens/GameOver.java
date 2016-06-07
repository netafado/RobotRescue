package com.isaias.robotgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;


/**
 * Created by casa on 6/6/2016.
 */
public class GameOver implements Screen{

    private Viewport view;
    private Stage stage;
    private RobotGame game;
    Texture back;

    public GameOver(RobotGame game){
        this.game = game;

        view = new FitViewport(Constants.WIDTH, Constants.HEIGHT, new OrthographicCamera());
        stage = new Stage(view, Constants.bs);

        back = new Texture(Gdx.files.internal("fun1.jpg"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GameOver", font);
        Label playAgain = new Label("Quer jogar de novo", font);

        table.add(gameOverLabel).expandX();
        table.row();

        table.add(playAgain).expandX().padTop(10f);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched())
            game.setScreen(new Play(game));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Constants.bs.setProjectionMatrix(stage.getCamera().combined);
        Constants.bs.begin();
        Constants.bs.draw(back, 0, 0);
        Constants.bs.end();

        stage.draw();



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
