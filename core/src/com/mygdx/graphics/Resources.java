package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class Resources
{
    public static final float width = Gdx.graphics.getWidth();//LwjglApplicationConfiguration.getDesktopDisplayMode().width;
    public static final float height = Gdx.graphics.getHeight();//LwjglApplicationConfiguration.getDesktopDisplayMode().height;
    public static final float width2 = width/2;
    public static final float height2 = height/2;
    public static TextButton.TextButtonStyle tbs_s;
    public static TextButton.TextButtonStyle tbs_m;
    public static State state = State.GAME;
    public static Animation<TextureRegion> testAnimationR = setAnim("units/example/runner_testR.png", 6, 5);
    public static Animation<TextureRegion> testAnimationL = setAnim("units/example/runner_testL.png", 6, 5);
    public static final TextureAtlas guiAtlas = new TextureAtlas("gui/gui.atlas");
    public static final Image bgForest = new Image(new Texture("gui/forest_bg.jpg"));



    private static Animation<TextureRegion> setAnim(String path, int x, int y)
    {
        Texture sheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/x, sheet.getHeight()/y);
        TextureRegion[] frames = new TextureRegion[x * y];
        int index = 0;
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                frames[index++] = tmp[i][j];
        return new Animation<TextureRegion>(0.025f, frames);
    }
}
