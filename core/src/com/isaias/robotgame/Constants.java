package com.isaias.robotgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by casa on 5/16/2016.
 */
public class Constants {
    public static final float WIDTH = 1028;
    public static final float HEIGHT = 720;
    public static final float PPM = 100;

    public static OrthographicCamera CAM;
    public static OrthographicCamera CAM_FIXED;
    public static Viewport viewportfixed;
    public static Viewport viewport;
    public static SpriteBatch bs;

    public static final short DEFAULT_BIT = 1;
    public static final short ROBO_BIT = 2;
    public static final short MOEDA_BIT = 4;
    public static final short CORTADOR_BIT = 8;
    public static final short DESTROYED_BIT = 16;
}
