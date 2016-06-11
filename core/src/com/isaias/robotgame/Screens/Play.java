package com.isaias.robotgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.inputs.InputHandrer;
import com.isaias.robotgame.objects.Background;
import com.isaias.robotgame.objects.Hud;
import com.isaias.robotgame.objects.Robo;
import com.isaias.robotgame.objects.TiledMapCreatorBox2d;
import com.isaias.robotgame.objects.Tiro;
import com.isaias.robotgame.utils.Colision;
import com.isaias.robotgame.utils.Musics;

import java.util.ArrayList;


/**
 * Created by casa on 5/16/2016.
 * Classe responsavel pela Tela e lógica do jogo
 */
public class Play implements Screen{
    //Tilemap
    private TiledMap tilemap;
    private OrthogonalTiledMapRenderer tmr;

    //thread
    private TiledMapCreatorBox2d tileAndBox2d;

    private ArrayList<Tiro> tiros;

    private boolean isGameRunning;

    private Array<Body> destroy;

    //roda em outro thread
    Hud hud;
    private Background background;

    private RobotGame game;
    public Robo robo;

    //handle user inputs
    private InputHandrer input;

    //carrega as musicas em outro thread
    private Musics music;

    //BOX2D
    private World mundo;
    private Box2DDebugRenderer b2dr;

    public Play(RobotGame game){
        this.game  = game;
        //Posiona a camra de acordo com sua view
        Constants.CAM.position.x = (Constants.viewport.getScreenWidth() / 2)  / Constants.PPM;
        Constants.CAM.position.y = (Constants.viewport.getScreenHeight() / 2) / Constants.PPM;

        destroy = new Array<Body>();

        //cuida dos displays do jogo
        hud = new Hud(this, Constants.bs);
        hud.start();

        isGameRunning = true;

        // BOX2d
        mundo = new World(new Vector2(0, -8), true);
        b2dr = new Box2DDebugRenderer();
        mundo.setContactListener(new Colision(this));

        tiros = new ArrayList<Tiro>();
        //tiled
        tilemap = new TmxMapLoader().load("tilled.tmx");
        tmr = new OrthogonalTiledMapRenderer(tilemap, 1 / Constants.PPM);

        tileAndBox2d = new TiledMapCreatorBox2d(mundo, tilemap, this);
        tileAndBox2d.start();

        robo = new Robo(mundo);

        //Background tambem faz o update das variaveis em paralelo
        background = new Background(this);
        background.start();

        //Cuida da música
        music = new Musics();
        music.start();

        // interação entre usuário e maquina MOBILE
        Gdx.input.setInputProcessor(new GestureDetector( input = new InputHandrer(this)));
    }
    @Override
    public void show() {

        //graficos

    }

    /**
     * Interação entre user e maquina Desktop
     */
    public void InputHandler(){
        //se tecla pressionado for UP e robo tem tem força aplicada em Y ele pula e muda o estado para "jumping"
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && robo.getVelocityY() <  0.5  ){
          robo.body.applyLinearImpulse(new Vector2(0, 4f), robo.body.getWorldCenter(), true);
          robo.setState("JUMPING");
        }
        //muda a direção do robo para a esquerda
         if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
             robo.changeLeft();
             robo.setIsRight(false);
             robo.setState("RUNNING");
         }

        //muda a direção do robo para a direita
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            robo.changeRight();
            robo.setIsRight(true);
            robo.setState("RUNNING");
        }
        // tecla de espaço faz o robo atirar
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            addTiro();
        }

    }

    // faz o update de todas as variaveis e para todos os threads
    public void update(float dt){
        Constants.CAM.position.x = robo.body.getPosition().x + (Constants.viewport.getScreenWidth() / 3)/ Constants.PPM;
        Constants.CAM.update();
        robo.update(dt);
        InputHandler();
        for(int i = 0; i < tiros.size(); i++){
            tiros.get(i).update();
        }

        mundo.step(1/60f, 6 , 2);

        // VERIFICA se o jogo acabou seta a Tela para game over
        //espera todos os threads terminarem e limpa a memoria
        if(!isGameRunning){
            game.setScreen(new GameOver(game));
            try{
                background.join();
                hud.join();
                tileAndBox2d.join();
                music.join();
                music.stopMusic();

            }catch (Exception e){
                Gdx.app.log("Erro", " Game over dentro do update");
            }
            dispose();
        }


    }
    @Override
    public void render(float delta) {
        //limpa tela
        Gdx.gl.glClearColor(0, 0f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //seta a aréa de render para a camera principal
        tmr.setView(Constants.CAM);

        // atualiza as viewports para o render
        Constants.viewportfixed.apply();
        Constants.viewport.apply();
        // desenha o background
        background.draw(Constants.bs);

        //seta a camera do SpriteBach para a camera principal
        Constants.bs.setProjectionMatrix(Constants.CAM.combined);
        Constants.bs.begin();
        robo.draw( Constants.bs);
        tileAndBox2d.draw();
        Constants.bs.end();

        //draw do score, level e tempo
        hud.drawStage();

        // chama o metodo para limpar a array de tiros
        removeTiro();

        for(int i = 0; i < tiros.size(); i++){
            tiros.get(i).draw();
        }

        //render tilepmap
        tmr.render();
        //render BOX2d ajuda no debug descomente caso necessario
        // b2dr.render(mundo,Constants.CAM.combined);


        update(Gdx.graphics.getDeltaTime());


    }

    public void removeTiro(){
        for(int i = 0; i < tiros.size(); i++){
            if(tiros.get(i).getX() < 1 && tiros.get(i).getX() > -1){
                tiros.get(i).destroyBody();
                tiros.remove(i);
            }
        }

    }
    public void addTiro(){
        tiros.add(new Tiro(mundo, getRoboX(), robo.body.getPosition().y, robo.getIsRight()));
    }

    // caso o tamanho da tela mude esse metodo é chamado
    @Override
    public void resize(int width, int height) {
        Constants.viewport.update(width, height, true);
        Constants.viewportfixed.update(width, height, true);
        hud.stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    //Limpa memória
    @Override
    public void dispose() {
        tmr.dispose();
        b2dr.dispose();
        background.dispose();
        tileAndBox2d.dispose();



    }

    public void addDestroy(Body body){
        destroy.add(body);
    }

    public Hud getHud(){return hud;};

    // todos os threads vereficam se o jogo esta rodando
    public synchronized void setIsRunnuing(boolean isRunnuing){
        this.isGameRunning = isRunnuing;
    }
    public synchronized boolean getIsRunnuing(){
        return isGameRunning;
    }

    public synchronized float getRoboX(){
        return robo.body.getPosition().x;
    }

    public Musics getMusics(){return music;}
}
