package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class Resources
{
    public static final float width = Gdx.graphics.getWidth();//LwjglApplicationConfiguration.getDesktopDisplayMode().width;
    public static final float height = Gdx.graphics.getHeight();//LwjglApplicationConfiguration.getDesktopDisplayMode().height;
    public static final float width2 = width/2;
    public static final float height2 = height/2;
    public static final float aspectRatio = width/height;
    public static final float gameW = 1024;
    public static final float gameH = gameW/aspectRatio;
    public static final float gameW2 = gameW/2;
    public static final float gameH2 = gameH/2;
    public static TextButton.TextButtonStyle tbs_s;
    public static TextButton.TextButtonStyle tbs_m;
    public static State state = State.GAME;
    public static Start game;
    public static Animation<TextureRegion> testAnimationR = setAnim("android/assets/units/example/runner_testR.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> testAnimationL = setAnim("android/assets/units/example/runner_testL.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> fortDamageAnimationL = setAnim("android/assets/units/caveL_damage_anim.png", 2, 1, 0.1f);
    public static Animation<TextureRegion> fortDamageAnimationR = setAnim("android/assets/units/caveR_damage_anim.png", 2, 1, 0.1f);
    public static final TextureAtlas guiAtlas = new TextureAtlas("android/assets/gui/gui.atlas");
    public static final Skin guiSkin = new Skin(guiAtlas);
    public static final Image bgForest = new Image(new Texture("android/assets/gui/forest_bg.jpg"));//android/assets/
    public static final Image bgForestBlur = new Image(new Texture("android/assets/gui/forest_blur_bg.jpg"));
    public static final Image fortU = new Image(new Texture("android/assets/units/caveL.png"));
    public static final Image fortG = new Image(new Texture("android/assets/units/caveR.png"));
    public static final Image arrow = new Image(new Texture("android/assets/units/example/arrow1.jpg"));
    public static final Animation<TextureRegion> loadAnimation = setAnim("android/assets/gui/load_anim.png", 5, 1, 0.5f);


    private static Animation<TextureRegion> setAnim(String path, int x, int y, float frameDuration)
    {
        Texture sheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/x, sheet.getHeight()/y);
        TextureRegion[] frames = new TextureRegion[x * y];
        int index = 0;
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                frames[index++] = tmp[i][j];
        return new Animation<TextureRegion>(frameDuration, frames);
    }
}
