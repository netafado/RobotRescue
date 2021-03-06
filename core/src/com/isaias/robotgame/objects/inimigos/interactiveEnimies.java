package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;



/**
 * Created by Isaias on 5/29/2016.
 * Classe abstrata para interação entre os objetos do jogo
 */
public abstract class interactiveEnimies {
    protected World mundo;
    protected Body body;
    protected Fixture fixture;
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

    public abstract void onColison();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

}
