package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.Screens.Play;

import java.util.ArrayList;

/**
 * Created by casa on 6/1/2016.
 */
public class Meteoros  extends Thread{
    // Lista de meteoros
    // delta time para renderizar no momento
    // setar variaveis
    // passar para o metodo render usando o postRunnable
    public static ArrayList<Meteoro> METEOROS;
    public static float TIMER = 0;
    public static OrthographicCamera M_CAM;
    public static Viewport M_VIEW;

    private Play screen;



    public Meteoros(Play screen){

        this.screen = screen;
        M_CAM = new OrthographicCamera(Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM);
        M_CAM.setToOrtho(false, Gdx.graphics.getWidth() / Constants.PPM, Gdx.graphics.getHeight() / Constants.PPM);
        M_VIEW = new FitViewport(Gdx.graphics.getWidth() / Constants.PPM,Gdx.graphics.getHeight() / Constants.PPM, M_CAM);


        METEOROS = new ArrayList<Meteoro>();
        METEOROS.add(new Meteoro(Gdx.graphics.getDeltaTime(), M_CAM, M_VIEW));
        METEOROS.add(new Meteoro(Gdx.graphics.getDeltaTime(), M_CAM, M_VIEW));
        METEOROS.add(new Meteoro(Gdx.graphics.getDeltaTime(), M_CAM, M_VIEW));


    }

    @Override
    public void run(){
        while(screen.getIsRunnuing())
        {
            try{
                Thread.sleep(1000/60);
            }catch (Exception e){

            }
            //Gdx.app.log("Running", "Ok");
        }

    }

    public void draw(){

        for(int i = 0; i < METEOROS.size();  i++)
            METEOROS.get(i).draw();

    }

    public void update(float dt){

    }

}
