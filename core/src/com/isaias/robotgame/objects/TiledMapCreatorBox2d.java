package com.isaias.robotgame.objects;

import com.badlogic.gdx.Gdx;
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
import com.isaias.robotgame.Screens.Play;
import com.isaias.robotgame.objects.inimigos.Cortador;
import com.isaias.robotgame.objects.inimigos.Moedas;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by casa on 5/29/2016.
 */
public class TiledMapCreatorBox2d extends Thread {

    public static ArrayList<Cortador> cortadores;
    public static ArrayList<Moedas> moedas;

    private Play screen;

     public TiledMapCreatorBox2d(World mundo, TiledMap tilemap, Play screen){

        this.screen = screen;

       cortadores = new ArrayList<Cortador>();
         moedas = new ArrayList<Moedas>();

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

        //CRIA OS CORTADORES
        for(MapObject object : tilemap.getLayers().get(3).getObjects().getByType(EllipseMapObject.class) ){
            Ellipse circle = ((EllipseMapObject) object).getEllipse();

            cortadores.add(new Cortador(mundo, tilemap, circle));

            //Gdx.app.log(RobotGame.TAG, "circle w: " + circle.width + " circle h: " + circle.height);

        }

         //CRIA AS MOEDAS usando como referencia o tilemap
         for(MapObject object : tilemap.getLayers().get(5).getObjects().getByType(EllipseMapObject.class) ){
             Ellipse circle = ((EllipseMapObject) object).getEllipse();

             moedas.add(new Moedas(mundo, tilemap, circle, screen));

             //Gdx.app.log(RobotGame.TAG, "circle w: " + circle.width + " circle h: " + circle.height);

         }
    }
    // limpa as array list
    public void run(){
        while(screen.getIsRunnuing()){
            try{
                Thread.sleep(1000/60);
            }catch (Exception e){

            }
            //Gdx.app.log("thread", "thread");
            update();
        }

    }

    public void draw(){
        for(int i = 0; i < cortadores.size(); i++){
            cortadores.get(i).draw();
        }

        for(int i = 0; i < moedas.size(); i++){
            moedas.get(i).draw();
        }
    }

    public void update(){
        for(int i = 0; i < moedas.size(); i++){
            if(!moedas.get(i).getIsAlive())
                moedas.remove(i);
            }
    }

    public void dispose(){
        for(int i = 0; i < cortadores.size(); i++){
            cortadores.get(i).dispose();
        }

        for(int i = 0; i < cortadores.size(); i++){
            moedas.get(i).dispose();
        }
    }
}
