package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;

/**
 * Created by casa on 6/1/2016.
 */
public class Meteoro {
    private TextureAtlas texture;
    private Animation animation;
    private ShapeRenderer render;

    private float esperar;
    private float timer;
    private float dy, dx;

    private OrthographicCamera cam;
    private Viewport viewPort;

    public Meteoro(float deltaTime, OrthographicCamera cam, Viewport view){
        timer = deltaTime;
        render = new ShapeRenderer();
        this.cam = cam;
        this.viewPort = view;
    }

    public void draw(){
                //Gdx.app.log("render thread", "render thread");
                render.setProjectionMatrix(Constants.CAM.combined);
                render.begin(ShapeRenderer.ShapeType.Filled);
                render.setColor(1, 1, 0, 1);
                render.circle(400 / Constants.PPM, 200 / Constants.PPM, 20 / Constants.PPM, 20);
                render.end();


    }

    public void update(){
        this.viewPort.apply();
    }



}
