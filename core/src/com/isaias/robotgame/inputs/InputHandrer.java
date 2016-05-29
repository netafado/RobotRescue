package com.isaias.robotgame.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.Screens.Play;

/**
 * Created by casa on 5/29/2016.
 */
public class InputHandrer implements GestureDetector.GestureListener {

    private Play screen;
    public InputHandrer(Play screen){
        this.screen = screen;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // if the tap occur on the LEFT side of the screen
        if(x < Gdx.graphics.getWidth() / 2){
            if(screen.robo.getVelocityY() <  1) {
                screen.robo.body.applyLinearImpulse(new Vector2(0, 5f), screen.robo.body.getWorldCenter(), true);
                screen.robo.setState("JUMPING");
            }
            Gdx.app.log(RobotGame.TAG, "esquerda:");
        }

        // if the tap occur on the RIGHT side of the screen
        if(x > Gdx.graphics.getWidth() / 2)
            Gdx.app.log(RobotGame.TAG, "ATIRA:");


        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

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
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log(RobotGame.TAG, "pan: " +x);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log(RobotGame.TAG, "panStop: " +x);
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        Gdx.app.log(RobotGame.TAG, "panStop: " +initialDistance);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        Gdx.app.log(RobotGame.TAG, "pinch: " +initialPointer1);
        return false;
    }
}
