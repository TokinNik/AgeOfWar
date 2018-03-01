package com.graphics;

import com.badlogic.gdx.Gdx;
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
    public static TextButton.TextButtonStyle tbs_s;
    public static TextButton.TextButtonStyle tbs_m;
    public static Label.LabelStyle simpleLSWhite;
    public static Label.LabelStyle simpleLSBlack;
    public static State state = State.GAME;
    public static Start game;
    public static Animation<TextureRegion> testAnimationR = setAnim("android/assets/units/example/runner_testR.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> testAnimationL = setAnim("android/assets/units/example/runner_testL.png", 6, 5, 0.025f);
    public static Animation<TextureRegion> fortDamageAnimationL = setAnim("android/assets/units/caveL_damage_anim.png", 2, 1, 0.1f);
    public static Animation<TextureRegion> fortDamageAnimationR = setAnim("android/assets/units/caveR_damage_anim.png", 2, 1, 0.1f);
    public static final TextureAtlas GUI_ATLAS = new TextureAtlas("android/assets/gui/gui.atlas");
    public static final Skin GUI_SKIN = new Skin(GUI_ATLAS);
    public static final Image BG_FOREST = new Image(new Texture("android/assets/gui/forest_bg.jpg"));//android/assets
    public static final Image BG_FOREST_BLUR = new Image(new Texture("android/assets/gui/forest_blur_bg.jpg"));
    public static final Image FORT_U = new Image(new Texture("android/assets/units/caveL.png"));
    public static final Image FORT_G = new Image(new Texture("android/assets/units/caveR.png"));
    public static final Image ARROW = new Image(new Texture("android/assets/units/example/arrow1.jpg"));
    public static final Animation<TextureRegion> LOAD_ANIMATION = setAnim("android/assets/gui/load_anim.png", 5, 1, 0.5f);
    public static final String menuMusicPath = "android/assets/music/main_menu.mp3";
    public static final String game1MusicPath = "android/assets/music/in_game_1.mp3";


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
