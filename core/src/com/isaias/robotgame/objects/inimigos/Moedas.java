package com.isaias.robotgame.objects.inimigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
 * Created by Isaas on 6/5/2016.
 * Classe para controlar as Moedas do jogo
 */
public class Moedas extends interactiveEnimies implements Disposable{

    protected Ellipse circle;

    //sera usado para limpar da tela por outra thread(TiledMapCreator)
    public boolean isalive;
    private Play screen;

    public Moedas(World mundo, TiledMap map, Ellipse circle, Play screen){
        super(mundo, map);
        this.circle = circle;
        this.screen = screen;

        //animação
        textureAtlas = new TextureAtlas(Gdx.files.internal("moedas.txt"));
        animation = new Animation(1/24f, textureAtlas.getRegions());

        isalive = true;

        //definição de como o corpo no box2d
        BodyDef bdef = new BodyDef();
        CircleShape circleShape = new CircleShape();
        FixtureDef fdef = new FixtureDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((circle.x + circle.width / 2) / Constants.PPM, (circle.y + circle.height / 2) / Constants.PPM);
        // quardo a posição x do circulo para usar na animação
        x = circle.x /Constants.PPM;
        y = circle.y /Constants.PPM;

        //adiciona o corpo ao mundo e quarda uma referencia a ele na variavel
        body = mundo.createBody(bdef);

        circleShape.setRadius((circle.width / 2) / Constants.PPM);
        fdef.shape = circleShape;
        fdef.filter.categoryBits = Constants.MOEDA_BIT;
        fixture = body.createFixture(fdef);
        //Sensor setado para verdadeiro, o objecto não colide
        fixture.setSensor(true);
        fixture.setUserData(this);



    }

    //controla a posição do desenho de acordo com mundo(box2d)
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

    //caso ocorra colisão
    @Override
    public void onColison() {
        //toca a musica da moeda
        Musics musics = screen.getMusics();
        musics.playCoin();

        //seta para que não haja mais colisão com a muda
        setCategoryFilter(Constants.DESTROYED_BIT);
        screen.getHud().addScore();

        isalive =  false;
        //retira o corpo do mundo
        if(body != null)
            screen.addDestroy(body);


    }
    //limpa memória
    public void dispose(){
        textureAtlas.dispose();
        mundo.destroyBody(body);
        textureAtlas.dispose();
    }

    public boolean getIsAlive()
    {
        return isalive;
    }





}
