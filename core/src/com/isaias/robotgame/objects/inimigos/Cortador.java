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
import com.badlogic.gdx.utils.Disposable;
import com.isaias.robotgame.Constants;
import com.isaias.robotgame.Screens.Play;
import com.isaias.robotgame.utils.Musics;

/**
 * Created by Isaias on 5/29/2016.
 * Classe responsavel por controlar os cortadores a tela
 */
public class Cortador extends interactiveEnimies implements Disposable {

    protected  Ellipse circle;
    //referencia a Screen Play
    private Play screen;


    public Cortador(World mundo, TiledMap map, Ellipse circle, Play screen) {
        //invoca o contrutor pai
        super(mundo, map);
        this.circle = circle;
        this.screen = screen;

        //animação usando um texture Atlas
        textureAtlas = new TextureAtlas(Gdx.files.internal("cortador.txt"));
        // seta a animação para 24 frames por segundo
        animation = new Animation(1/24f, textureAtlas.getRegions());

        //define caracteristicas dos corpos para a engine de fispica box2d
        BodyDef bdef = new BodyDef();
        CircleShape circleShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((circle.x + circle.width / 2) / Constants.PPM, (circle.y + circle.height / 2) / Constants.PPM);

        x = circle.x /Constants.PPM;
        y = circle.y /Constants.PPM;
        //adiciona o corpo ao mundo e quarda uma referencia a ele na variavel
        body = mundo.createBody(bdef);
        //seta o tamanho do carpo
        circleShape.setRadius((circle.width / 2) / Constants.PPM);
        fdef.shape = circleShape;

        fixture = body.createFixture(fdef);
        //para facilitar a detecção de colisão
        fixture.setUserData(this);

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

    // caso ocorra um colisão esse metodo sera chamado
    @Override
    public void onColison() {
        Musics saw = screen.getMusics();
        saw.playSaw();
        screen.setIsRunnuing(false);

    }
    // limpa a memória
    public void dispose(){
        textureAtlas.dispose();
        mundo.destroyBody(body);

    }
}
