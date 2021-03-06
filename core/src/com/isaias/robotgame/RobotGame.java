package com.isaias.robotgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.isaias.robotgame.Screens.GameOver;
import com.isaias.robotgame.Screens.Menu;
import com.isaias.robotgame.Screens.Play;

/**
 * classe que seta a Screen
 * e inicializa todos os objetos principais do jogo como:
 * Cameras
 * Viewports
 * Seta a Screen
 */
public class RobotGame extends Game {

	public static final String TAG  = RobotGame.class.getName();

	@Override
	public void create () {

		Constants.bs = new SpriteBatch();
		//inicia todas as cameras e vierwports
		Constants.CAM = new OrthographicCamera(Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM);
		Constants.CAM.setToOrtho(false, Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM);
		Constants.viewport = new FitViewport(Gdx.graphics.getWidth() / Constants.PPM,Gdx.graphics.getHeight() / Constants.PPM, Constants.CAM);

		Constants.CAM_FIXED = new OrthographicCamera();
		Constants.CAM_FIXED.setToOrtho(false, Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM);
		Constants.viewportfixed = new FitViewport(Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM, Constants.CAM_FIXED);
		// seta a scrren para a Play e começa o jogo
		setScreen(new Play(this));

	}

	@Override
	public void render () {
		super.render();
	}


}
