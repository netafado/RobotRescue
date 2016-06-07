package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;

/**
 * Created by casa on 5/25/2016.
 */
public class Robo{
    public enum State{
        RUNNING,
        JUMPING,
        DEAD
    };
    private State currentState;
    private State previosState;
    private float x, y;
    private float dx, dy;

    // posição inicial
    private float xI, yI;

    private Fixture fixture;
    private TextureAtlas roboAtlas;
    private Animation animation;
    private TextureRegion textureRegion;

    private TextureAtlas roboAtlasJump;
    private Animation animationJump;

    private TextureAtlas roboAtlasDead;
    private Animation animationDead;

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

        xI = x;
        yI = y;

        roboAtlas = new TextureAtlas(Gdx.files.internal("robot-sprite.txt"));
        animation = new Animation(1/24f, roboAtlas.getRegions());

        roboAtlasJump = new TextureAtlas(Gdx.files.internal("robot-sprite-pulando.txt"));
        animationJump = new Animation(1/12f, roboAtlasJump.getRegions());

        roboAtlasDead = new TextureAtlas(Gdx.files.internal("robo-morrendo.txt"));
        animationDead = new Animation(1/12f, roboAtlasDead.getRegions());
        currentState = State.RUNNING;

    }

    public void defineRobo(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Constants.PPM, 200  / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = mundo.createBody(bdef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(28/Constants.PPM);
        fixDef.filter.categoryBits = Constants.ROBO_BIT;
        fixDef.filter.maskBits = Constants.DEFAULT_BIT | Constants.CORTADOR_BIT | Constants.MOEDA_BIT;

        fixDef.shape = shape;
        fixture = body.createFixture(fixDef);
        fixture.setUserData("Robo");
    }

    public void draw(Batch batch) {

       if(currentState == State.RUNNING) {
            textureRegion = animation.getKeyFrame(timPassed, true);
        }
       else if(currentState == State.JUMPING && body.getLinearVelocity().y > 0){
            textureRegion = animationJump.getKeyFrame(timPassed, true);
        }
       else if(currentState == State.DEAD){
           textureRegion = animationDead.getKeyFrame(timPassed, true);
           }
        else
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

    }

    public void update(float dt){
        x = body.getPosition().x - ( 60 / Constants.PPM) /2;
        y = body.getPosition().y - ( 72 / Constants.PPM) /2;

        if(getVelocityX() <= 2f && getVelocityX() >= -2)
            body.applyLinearImpulse(new Vector2(dx, 0), body.getWorldCenter(), true);

    }



    public void setIsRight(boolean diretion){
        this.isRight = diretion;
    }
    public boolean getIsRight(){return isRight;};

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

        if(s.equals("DEAD")){
            currentState = State.DEAD;
        }

    }

    public void dead(){
        body.setLinearVelocity(0, 0);
       body.setActive(false);
    }

    public void reset(){
        body.setTransform(new Vector2(xI, yI), 0);
    }

    public void changeRight(){
        dx = 0.1f;
    }

    public void changeLeft(){
        dx = -0.1f;
    }
}
