package com.isaias.robotgame.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.objects.inimigos.Cortador;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by casa on 5/29/2016.
 */
public class TiledMapCreatorBox2d {

    private ArrayList<Cortador> cortadores;

     public TiledMapCreatorBox2d(World mundo, TiledMap tilemap){

        cortadores = new ArrayList<Cortador>();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        CircleShape circleShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // body = mundo.createBody(bdef);
        for(MapObject object : tilemap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2)/ Constants.PPM);

            body = mundo.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
            //Gdx.app.log(RobotGame.TAG, "rect " + rect.x);

        }



        // body = mundo.createBody(bdef);
        for(MapObject object : tilemap.getLayers().get(3).getObjects().getByType(EllipseMapObject.class) ){
            Ellipse circle = ((EllipseMapObject) object).getEllipse();

            cortadores.add(new Cortador(mundo, tilemap, circle));

            //Gdx.app.log(RobotGame.TAG, "circle w: " + circle.width + " circle h: " + circle.height);

        }
    }

    public void draw(){
        for(int i = 0; i < cortadores.size(); i++){
            cortadores.get(i).draw();
        }
    }

    public void dispose(){
        for(int i = 0; i < cortadores.size(); i++){
            cortadores.get(i).dispose();
        }
    }
}
