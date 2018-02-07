package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class Resourses
{
    public static final float width = Gdx.graphics.getWidth();//LwjglApplicationConfiguration.getDesktopDisplayMode().width;
    public static final float height = Gdx.graphics.getHeight();//LwjglApplicationConfiguration.getDesktopDisplayMode().height;
    public static TextButton.TextButtonStyle tbs_s;
    public static TextButton.TextButtonStyle tbs_m;
    public static State state = State.GAME;
    public static Animation testAnimation = setAnim("android/assets/units/Walksequence_spritesheet.png", 6, 5);


    static Animation setAnim(String path, int x, int y)
    {
        Texture walkSheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/x, walkSheet.getHeight()/y);
        TextureRegion[] walkFrames = new TextureRegion[x * y];
        int index = 0;
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                walkFrames[index++] = tmp[i][j];
        return new Animation(0.025f, walkFrames);
    }
}
