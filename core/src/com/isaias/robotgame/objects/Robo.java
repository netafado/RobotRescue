package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;

/**
 * Created by casa on 5/25/2016.
 */
public class Robo{
    public enum State{
        RUNNING,
        JUMPING
    };
    private State currentState;
    private State previosState;
    private float x, y;
    private float dx, dy;

    private Texture texture;
    private Sprite sp;

    private TextureAtlas roboAtlas;
    private Animation animation;
    private TextureRegion textureRegion;

    private TextureAtlas roboAtlasJump;
    private Animation animationJump;

    private float timPassed = 0;

    private boolean isRight;

    //BOX2D
    private World mundo;
    public Body body;
    private float chanceDiretiion = -1f;



    public Robo(World mundo){
        this.mundo = mundo;
        defineRobo();
        dx = 0.1f;
        isRight = true;
        x = body.getPosition().x - 25;
        y =  body.getPosition().y - 33;
        texture = new Texture(Gdx.files.internal("robot.png"));
        sp = new Sprite(texture);
        sp.setPosition(50 / Constants.PPM, 200 / Constants.PPM);
        sp.setBounds(0,0, 55 / Constants.PPM, 67 / Constants.PPM);

        roboAtlas = new TextureAtlas(Gdx.files.internal("robot-sprite.txt"));
        animation = new Animation(1/24f, roboAtlas.getRegions());

        roboAtlasJump = new TextureAtlas(Gdx.files.internal("robot-sprite-pulando.txt"));
        animationJump = new Animation(1/6f, roboAtlasJump.getRegions());

        currentState = State.RUNNING;

    }

    public void defineRobo(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Constants.PPM, 200  / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(bdef);
        FixtureDef fixDef = new FixtureDef();


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / Constants.PPM, 35 / Constants.PPM);

        fixDef.shape = shape;

        fixDef.shape = shape;
        body.createFixture(fixDef);
    }

    public void draw(Batch batch) {

       if(currentState == State.RUNNING) {
            textureRegion = animation.getKeyFrame(timPassed, true);
        }else if(currentState == State.JUMPING && body.getLinearVelocity().y > 0){
            textureRegion = animationJump.getKeyFrame(timPassed, true);
        }else
        {
            textureRegion = animation.getKeyFrame(timPassed, true);
        }
        timPassed += Gdx.graphics.getDeltaTime();
        if(isRight){
            batch.draw(textureRegion,
                    x, y,
                    textureRegion.getRegionWidth() / Constants.PPM,
                    textureRegion.getRegionHeight() / Constants.PPM,
                    textureRegion.getRegionWidth() / Constants.PPM,
                    textureRegion.getRegionHeight() / Constants.PPM,
                    1,1, 0
            );

        }else{
            batch.draw(textureRegion,
                    x - textureRegion.getRegionWidth() / Constants.PPM , y,
                    textureRegion.getRegionWidth() / Constants.PPM,
                    textureRegion.getRegionHeight() / Constants.PPM,
                    textureRegion.getRegionWidth() / Constants.PPM,
                    textureRegion.getRegionHeight() / Constants.PPM,
                    -1,1, 0
            );

        }
        Gdx.app.log(RobotGame.TAG, "x: " + body.getPosition().x);
        Gdx.app.log(RobotGame.TAG, "y: " + body.getPosition().y);
    }

    public void update(float dt){
        x = body.getPosition().x - ( 60 / Constants.PPM) /2;
        y = body.getPosition().y - ( 72 / Constants.PPM) /2;

        if(getVelocityX() <= 2f && getVelocityX() >= -2)
            body.applyLinearImpulse(new Vector2(dx, 0), body.getWorldCenter(), true);

    }

    public float getX(){
        return sp.getX();
    }

    public void setIsRight(boolean diretion){
        this.isRight = diretion;
    }

    public float getVelocityX() {
        return body.getLinearVelocity().x;
    }
    public float getVelocityY() {
        return body.getLinearVelocity().y;
    }

    public void setState(String s){
        if(s.equals("RUNNING")){
            currentState = State.RUNNING;
        }

        if(s.equals("JUMPING")){
            currentState = State.JUMPING;
        }

    }

    public void changeRight(){
        dx = 0.1f;
    }

    public void changeLeft(){
        dx = -0.1f;
    }
}
