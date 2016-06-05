package com.isaias.robotgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.isaias.robotgame.RobotGame;
import com.isaias.robotgame.Screens.Play;

/**
 * Created by casa on 5/29/2016.
 */
public class Colision implements ContactListener {
    private Play screen;
    public Colision(Play screen){

        this.screen = screen;

    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if((a.getUserData() == "Robo" && b.getUserData() == "Cortador" ) || (a.getUserData() == "Cortador" && b.getUserData() == "Robo" )){
            Gdx.app.log(RobotGame.TAG, "Morreu:");
            screen.robo.setState("DEAD");

        }

        //Gdx.app.log("A:" + a.getUserData(), " A");
       // Gdx.app.log("A:" + b.getUserData(), " B");
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
