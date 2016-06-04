package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.Screens.Play;

/**
 * Created by casa on 5/25/2016.
 */
public class Background extends Thread{

    private Texture texture;
    private Sprite sp;

    private Texture texture2;
    private Sprite sp2;

    private Play screen;

    public Background(Play screen){

        this.screen = screen;
        texture = new Texture(Gdx.files.internal("fun1.jpg"));
        sp = new Sprite(texture);
        sp.setBounds(0, 0, texture.getWidth() / Constants.PPM, texture.getHeight() / Constants.PPM);
        sp.setPosition(0, 0);

        texture2 = new Texture(Gdx.files.internal("fund2.jpg"));
        sp2 = new Sprite(texture2);
        sp2.setBounds(0, 0, texture2.getWidth() / Constants.PPM, texture2.getHeight() / Constants.PPM);
        sp2.setPosition(1280 / 100, 0);
    }

    @Override
    public void run(){
        while (screen.getIsRunnuing()){
            try {
                Thread.sleep((long) (1000/60f));
            }catch (Exception e){

            }
            update();

        }
    }

    public void draw(SpriteBatch bs){
        bs.setProjectionMatrix(Constants.CAM_FIXED.combined);
        bs.begin();
        sp.draw(bs);
        sp2.draw(bs);
        bs.end();
    }

    public void update(){
        float direction = screen.getRoboX();
        sp.setPosition((-1)*direction / 80f, 0);
        sp2.setPosition((1280 / 100 )+ (-1)*direction / 80f, 0);

    }

    public void dispose(){
        sp.getTexture().dispose();
        sp2.getTexture().dispose();

    }

}
