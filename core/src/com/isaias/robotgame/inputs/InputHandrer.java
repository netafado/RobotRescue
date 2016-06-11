package com.isaias.robotgame.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.Screens.Play;

/**
 * Created by Isaias  on 5/29/2016.
 * Classe para lidar com Interaçõõs para Mobole
 */
public class InputHandrer implements GestureDetector.GestureListener {

    //faz referencia para a Play screen
    private Play screen;

    public InputHandrer(Play screen){
        this.screen = screen;
    }//construtor

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // se o toque na tela ocorrer na metade esquerda da tela
        if(x < Gdx.graphics.getWidth() / 2){
            // e se a velocidade em y for menor que 1, será adicionado um
            // impulso linear no eixo y ao robo e mudará o estado do robo para "jumping"
            if(screen.robo.getVelocityY() <  1) {
                screen.robo.body.applyLinearImpulse(new Vector2(0, 5f), screen.robo.body.getWorldCenter(), true);
                screen.robo.setState("JUMPING");
            }

        }

        // se o toque ocorrer do lado direito sera adicionado um tiro
        if(x > Gdx.graphics.getWidth() / 2){
            screen.addTiro();
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }


    // toque e arraste o dedo para o lado esquerdo, isso fará o robo ir para a esquerda caso contrario irá para a direita
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        Gdx.app.log(RobotGame.TAG, "fling: " +velocityX);
        //if velocity is negative change direction to the left
        if(velocityX < 0){
            screen.robo.changeLeft();
            screen.robo.setIsRight(false);
            screen.robo.setState("RUNNING");

        }else{
            screen.robo.changeRight();
            screen.robo.setIsRight(true);
            screen.robo.setState("RUNNING");
        }

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {return false; }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {return false; }

    @Override
    public boolean zoom(float initialDistance, float distance) { return false;}

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {return false;}
}
