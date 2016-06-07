package com.isaias.robotgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;

/**
 * Created by casa on 5/16/2016.
 */
public class Menu implements Screen, InputProcessor{

    private Texture background;
    private Texture background1;
    private Texture logo;
    private Texture btnInit;
    private InputProcessor input;

    private OrthographicCamera cam;
    private Viewport view;

    private Sprite spBackground;
    private Sprite spBackground1;
    private RobotGame game;
    public Menu(RobotGame game){
        this.game = game;
    }
    @Override
    public void show() {

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(),  Gdx.graphics.getWidth());
        view = new FitViewport( Gdx.graphics.getWidth(),  Gdx.graphics.getWidth(), cam);

        background = new Texture(Gdx.files.internal("fun1.jpg"));

        spBackground = new Sprite(background);
        logo = new Texture(Gdx.files.internal("logo-robot.png"));
        btnInit = new Texture(Gdx.files.internal("btn-iniciar.png"));

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        //limpa tela
        Gdx.gl.glClearColor(0, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Constants.bs.setProjectionMatrix(cam.combined);
        view.apply();

        Constants.bs.begin();
        //renderiza tudo aqui
        spBackground.setBounds(0, 0, Constants.WIDTH, Constants.HEIGHT);
        spBackground.draw(Constants.bs);
        Constants.bs.draw(logo, Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2, Gdx.graphics.getHeight() / 2 - logo.getHeight() * 0.2f);
        Constants.bs.draw(btnInit, Gdx.graphics.getWidth() / 2 - btnInit.getWidth() / 2, Gdx.graphics.getHeight() * 0.2f);

        Constants.bs.end();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    public void play(){

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

       background.dispose();
        logo.dispose();
        btnInit.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {return false;}

    @Override
    public boolean keyUp(int keycode) {return false;}

    @Override
    public boolean keyTyped(char character) {return false;}

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setScreen(new Play(game));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {return false;}

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false;}

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false;}

    @Override
    public boolean scrolled(int amount) { return false; }
}
