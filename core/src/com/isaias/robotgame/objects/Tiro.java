package com.isaias.robotgame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;

/**
 * Created by casa on 5/29/2016.
 */
public class Tiro {

    protected ShapeRenderer renderTiro;
    protected World mundo;
    protected Body body;
    protected float x, y;

    public Tiro(World mundo, float x, float y, boolean isRight){
        this.mundo = mundo;
        this.x = x;
        this.y = y;
        BodyDef bodDef = new BodyDef();
        bodDef.type = BodyDef.BodyType.DynamicBody;
        bodDef.bullet = true;
        if(isRight){
            bodDef.position.set(x + 16 / Constants.PPM, y);
        }else{
            bodDef.position.set(x - 16 / Constants.PPM, y);
        }


        this.body = this.mundo.createBody(bodDef);
        if(isRight){
            body.setLinearVelocity(30, 0);
        }else
        {
            body.setLinearVelocity(-30, 0);
        }

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3/ Constants.PPM);

        fixDef.shape = shape;
        fixDef.shape = shape;
        body.createFixture(fixDef);

        renderTiro = new ShapeRenderer();

    }

    public void draw(){
        renderTiro.setProjectionMatrix(Constants.CAM.combined);
        renderTiro.begin(ShapeRenderer.ShapeType.Filled);
        renderTiro.setColor(Color.CYAN);
        renderTiro.circle(x, y , 15 / Constants.PPM);
        renderTiro.end();
    }

    public void update(){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
    }


}
