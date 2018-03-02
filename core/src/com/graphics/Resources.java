package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class Resources
{
    public static final float WIDTH = Gdx.graphics.getWidth();//LwjglApplicationConfiguration.getDesktopDisplayMode().WIDTH;
    public static final float HEIGHT = Gdx.graphics.getHeight();//LwjglApplicationConfiguration.getDesktopDisplayMode().HEIGHT;
    public static final float WIDTH_2 = WIDTH /2;
    public static final float HEIGHT_2 = HEIGHT /2;
    public static final float ASPECT_RATIO = WIDTH / HEIGHT;
    public static final float WORLD_WIDTH = 1000;
    public static final float WORLD_HEIGHT = WORLD_WIDTH / ASPECT_RATIO;
    public static final float WORLD_WIDTH_2 = WORLD_WIDTH /2;
    public static final float WORLD_HEIGHT_2 = WORLD_HEIGHT /2;
    public static final float GAME_WIDTH = 2000;
    public static final float GAME_HEIGHT = GAME_WIDTH / ASPECT_RATIO;
    public static final float GAME_WIDTH_2 = GAME_WIDTH / 2;
    public static final float GAME_HEIGHT_2 = GAME_HEIGHT / 2;
    public static final Preferences OPTIONS = Gdx.app.getPreferences("Options");
    public static Label.LabelStyle simpleLSWhite;
    public static Label.LabelStyle simpleLSBlack;
    public static State state = State.GAME;
    public static Start game;
    public static Animation<TextureRegion> testAnimationR = setAnim("units/example/runner_testR.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> testAnimationL = setAnim("units/example/runner_testL.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> fortDamageAnimationL = setAnim("units/caveL_damage_anim.png", 2, 1, 0.1f);
    public static Animation<TextureRegion> fortDamageAnimationR = setAnim("units/caveR_damage_anim.png", 2, 1, 0.1f);
    public static TextureAtlas guiAtlas = new TextureAtlas("gui/gui.atlas");
    public static Skin guiSkin = new Skin(guiAtlas);
    public static TextButton.TextButtonStyle tbs_s;
    public static TextButton.TextButtonStyle tbs_m;
    public static Image bgForest = new Image(new Texture("gui/forest_bg.jpg"));//android/assets
    public static Image bgForestBlur = new Image(new Texture("gui/forest_blur_bg.jpg"));
    public static Image fortU = new Image(new Texture("units/caveL.png"));
    public static Image fortG = new Image(new Texture("units/caveR.png"));
    public static Image arrow = new Image(new Texture("units/example/arrow1.jpg"));
    public static Animation<TextureRegion> loadAnimation = setAnim("gui/load_anim.png", 5, 1, 0.5f);
    public static String menuMusicPath = "music/main_menu.mp3";
    public static String game1MusicPath = "music/in_game_1.mp3";


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
    private static TextButton.TextButtonStyle setTBS(int size)
    {
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        String name;
        switch (size)
        {
            case 1:
                name = "text_button_s";
                break;
            case 2:
                name = "text_button_m";
                break;
            default:
                name = "text_button_s";
                break;
        }
        tbs.up = guiSkin.getDrawable(name + "1");
        tbs.down = guiSkin.getDrawable(name + "2");
        tbs.font = game.standartFontWhite;
        return tbs;
    }

    public static void reload()
    {
        System.out.println("Reload-------");
//        tbs_s = setTBS(1);
//        tbs_m = setTBS(2);
//        testAnimationR = setAnim("units/example/runner_testR.png", 6, 5, 0.025f);
//        testAnimationL = setAnim("units/example/runner_testL.png", 6, 5, 0.025f);
//        fortDamageAnimationL = setAnim("units/caveL_damage_anim.png", 2, 1, 0.1f);
//        fortDamageAnimationR = setAnim("units/caveR_damage_anim.png", 2, 1, 0.1f);
//        guiAtlas = new TextureAtlas("gui/gui.atlas");
//        guiSkin = new Skin(guiAtlas);
//        bgForest = new Image(new Texture("gui/forest_bg.jpg"));//android/assets
//        bgForestBlur = new Image(new Texture("gui/forest_blur_bg.jpg"));
//        fortU = new Image(new Texture("units/caveL.png"));
//        Image FORT_G = new Image(new Texture("units/caveR.png"));
//        Image ARROW = new Image(new Texture("units/example/arrow1.jpg"));
//        Animation<TextureRegion> LOAD_ANIMATION = setAnim("gui/load_anim.png", 5, 1, 0.5f);
    }
}
