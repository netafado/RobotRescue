package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.Screens.Play;

/**
 * Created by casa on 6/5/2016.
 */
public class Moedas extends interactiveEnimies implements Runnable{

    protected Ellipse circle;
    private Fixture fixture;

    public Moedas(World mundo, TiledMap map, Ellipse circle){
        super(mundo, map);
        this.circle = circle;
        //Animation
        textureAtlas = new TextureAtlas(Gdx.files.internal("moedas.txt"));
        animation = new Animation(1/24f, textureAtlas.getRegions());



        BodyDef bdef = new BodyDef();
        CircleShape circleShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();


        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((circle.x + circle.width / 2) / Constants.PPM, (circle.y + circle.height / 2) / Constants.PPM);

        x = circle.x /Constants.PPM;
        y = circle.y /Constants.PPM;

        body = mundo.createBody(bdef);

        circleShape.setRadius((circle.width / 2) / Constants.PPM);
        fdef.shape = circleShape;
        fixture = body.createFixture(fdef);
        fixture.setSensor(true);
        fixture.setUserData("Moeda");
        //Gdx.app.log(RobotGame.TAG, "circle w: " + circle.width + " circle h: " + circle.height);


    }
    @Override
    public void draw() {
        timer += Gdx.graphics.getDeltaTime();
        textureRegion = animation.getKeyFrame(timer, true);
        Constants.bs.draw(textureRegion,
                x, y,
                textureRegion.getRegionWidth() / Constants.PPM,
                textureRegion.getRegionHeight() / Constants.PPM,
                textureRegion.getRegionWidth() / Constants.PPM,
                textureRegion.getRegionHeight() / Constants.PPM,
                1,1, 0
        );
    }

    @Override
    public void run() {

    }
    public void dispose(){
        textureAtlas.dispose();
    }



}