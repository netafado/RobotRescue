package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;



/**
 * Created by casa on 5/29/2016.
 */
public abstract class interactiveEnimies {
    protected World mundo;
    protected Body body;
    protected TiledMap map;
    protected float timer;
    protected float x,y;

    protected Animation animation;
    protected TextureAtlas textureAtlas;
    protected TextureRegion textureRegion;

    public interactiveEnimies(World mundo, TiledMap map){
        this.mundo = mundo;
        this.map = map;
        timer = 0;


    }

    public abstract void draw();

}
