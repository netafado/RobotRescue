package com.isaias.robotgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.isaias.robotgame.Screens.Play;
import com.isaias.robotgame.objects.inimigos.interactiveEnimies;

/**
 * Created by casa on 5/29/2016.
 * Classe para lidar com colisões no jogo
 */
public class Colision implements ContactListener {
    private Play screen;
    public Colision(Play screen){

        this.screen = screen;

    }

    // no inicio do contado
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // se algum dos objetos for o robo
        if(a.getUserData() == "Robo" || b.getUserData() == "Robo"){
            // se o robo for a fixture a robo igual = caso contrario b;
            Fixture robo = a.getUserData() == "Robo" ? a : b;
            //seta o outro objeto para obj
            Fixture obj = robo.getUserData() == "Robo" ? a : b;
            //clama o metodo OnColision do objeto que tem como pai InteractiveEnimies
            if(obj.getUserData() instanceof interactiveEnimies){
                ((interactiveEnimies) obj.getUserData()).onColison();
            }

        }


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
