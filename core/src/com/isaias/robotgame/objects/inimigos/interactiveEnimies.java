package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;


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
