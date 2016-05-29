package com.isaias.robotgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.inputs.InputHandrer;
import com.isaias.robotgame.objects.Background;
import com.isaias.robotgame.objects.Hud;
import com.isaias.robotgame.objects.Robo;


/**
 * Created by casa on 5/16/2016.
 */
public class Play implements Screen{

    private TiledMap tilemap;
    private OrthogonalTiledMapRenderer tmr;
    private ShapeRenderer render;
    private Background background;
    private Hud hud;

    private RobotGame game;
    public Robo robo;

    //handle user inputs
    private InputHandrer input;

    //BOX2D
    private World mundo;
    private Box2DDebugRenderer b2dr;

    public Play(RobotGame game){
        this.game  = game;
        Constants.CAM.position.x = (Constants.viewport.getScreenWidth() / 2)  / Constants.PPM;
        Constants.CAM.position.y = (Constants.viewport.getScreenHeight() / 2) / Constants.PPM;
        // BOX2d
        mundo = new World(new Vector2(0, -8), true);
        b2dr = new Box2DDebugRenderer();

        //tiled
        tilemap = new TmxMapLoader().load("tilled.tmx");
        tmr = new OrthogonalTiledMapRenderer(tilemap, 1 / Constants.PPM);

        Gdx.input.setInputProcessor(new GestureDetector( input = new InputHandrer(this)));


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

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((circle.x + circle.width / 2) / Constants.PPM, (circle.y  + circle.height / 2) / Constants.PPM);

            body = mundo.createBody(bdef);

            circleShape.setRadius((circle.width / 2) / Constants.PPM);
            fdef.shape = circleShape;
            body.createFixture(fdef);

            //Gdx.app.log(RobotGame.TAG, "circle w: " + circle.width + " circle h: " + circle.height);

        }


    }
    @Override
    public void show() {

        //graficos
        robo = new Robo(mundo);
        background = new Background();
        hud = new Hud(Constants.bs);
    }
    public void InputHandler(){
        //if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && robo.getVelocityY() <  0.5  ){
        //    robo.body.applyLinearImpulse(new Vector2(0, 4f), robo.body.getWorldCenter(), true);
         //   robo.setState("JUMPING");
        //}


         if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
             robo.changeLeft();
             robo.setIsRight(false);
             robo.setState("RUNNING");
         }


        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            robo.changeRight();
            robo.setIsRight(true);
            robo.setState("RUNNING");
        }

    }
    public void update(float dt){
        Constants.CAM.position.x = robo.body.getPosition().x + (Constants.viewport.getScreenWidth() / 3)/ Constants.PPM;
        Constants.CAM.update();
        background.update(dt,robo.body.getPosition().x );

        robo.update(dt);

        InputHandler();

        mundo.step(1/60f, 6 , 2);
    }
    @Override
    public void render(float delta) {
        //limpa tela
        Gdx.gl.glClearColor(0, 0f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.setView(Constants.CAM);


        Constants.viewport.apply();
        Constants.viewportfixed.apply();

        background.draw(Constants.bs);
        Constants.bs.setProjectionMatrix(Constants.CAM.combined);
        Constants.bs.begin();
        robo.draw( Constants.bs);
        Constants.bs.end();

        //render tilepmap
        tmr.render();
        //render BOX2d
        //b2dr.render(mundo,Constants.CAM.combined);

        hud.drawStage();
        update(Gdx.graphics.getDeltaTime());


    }

    @Override
    public void resize(int width, int height) {
        Constants.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tmr.dispose();
    }
}
