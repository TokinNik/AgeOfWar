package com.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.sun.org.apache.regexp.internal.RE;

public class MainMenu implements Screen,InputProcessor
{
    private static Skin skin;
    private Stage stage;
    private boolean state;
    private Music bgMusic;

    MainMenu()
    {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.WORLD_WIDTH, Resources.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(Resources.WORLD_WIDTH, Resources.WORLD_HEIGHT));
        state = true;
    }

    @Override
    public void show()
    {
        skin = new Skin();
        skin.addRegions(Resources.guiAtlas);

        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("text_button_s1");
        tbs.down = skin.getDrawable("text_button_s2");
        tbs.font = Resources.game.standartFontWhite;
        Resources.tbs_small = tbs;
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.up = skin.getDrawable("text_button_m1");
        tbs1.down = skin.getDrawable("text_button_m2");
        tbs1.font = Resources.game.standartFontWhite;
        Resources.tbs_medium = tbs1;
        TextButton.TextButtonStyle tbs2 = new TextButton.TextButtonStyle();
        tbs2.up = skin.getDrawable("text_button_unactive_m");
        tbs2.down = skin.getDrawable("text_button_unactive_m");
        tbs2.font = Resources.game.standartFontWhite;
        Resources.tbs_unactive_medium = tbs2;
        TextButton.TextButtonStyle tbs3 = new TextButton.TextButtonStyle();
        tbs3.up = skin.getDrawable("text_button_unactive_s");
        tbs3.down = skin.getDrawable("text_button_unactive_s");
        tbs3.font = Resources.game.standartFontWhite;
        Resources.tbs_unactive_small = tbs3;

        Resources.simpleLSWhite = new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE);
        Resources.simpleLSBlack = new Label.LabelStyle(Resources.game.standartFontWhite, Color.BLACK);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(Resources.menuMusicPath));
        bgMusic.setLooping(true);
        bgMusic.setVolume(Resources.OPTIONS.getFloat("Music Volume"));
        bgMusic.play();

//        Resources.OPTIONS.putBoolean("Lvl 2 key", false);
//        Resources.OPTIONS.putBoolean("Lvl 3 key", false);
//        Resources.OPTIONS.flush();

        final TextButton bNewGame = new TextButton("New Game", Resources.tbs_small);
        final TextButton bOptions = new TextButton("Options", Resources.tbs_small);
        final TextButton bAboutUs = new TextButton("About Us", Resources.tbs_small);

        final TextButton bEasyLvl = new TextButton("Easy", Resources.tbs_small);
        final TextButton bNormalLvl = new TextButton("Normal", Resources.tbs_unactive_small);
        if (Resources.OPTIONS.getBoolean("Lvl 2 key"))
            bNormalLvl.setStyle(Resources.tbs_small);
        final TextButton bHardLvl = new TextButton("Hard", Resources.tbs_unactive_small);
        if (Resources.OPTIONS.getBoolean("Lvl 3 key"))
            bHardLvl.setStyle(Resources.tbs_small);
        final TextButton bReturn = new TextButton("Return", Resources.tbs_small);

        final TextButton bExit = new TextButton("Exit", Resources.tbs_small);

        final Group mainMenu = new Group();
        mainMenu.addActor(bNewGame);
        mainMenu.addActor(bOptions);
        mainMenu.addActor(bAboutUs);
        mainMenu.addActor(bExit);

        final Group choiceLvL = new Group();
        choiceLvL.addActor(bEasyLvl);
        choiceLvL.addActor(bNormalLvl);
        choiceLvL.addActor(bHardLvl);
        choiceLvL.addActor(bReturn);
        choiceLvL.setVisible(false);

        bNewGame.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) + 150);
        bNewGame.setSize(150, 50);
        bNewGame.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("New Game");
                mainMenu.setVisible(false);
                choiceLvL.setVisible(true);
            }
        });

        bOptions.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) + 50);
        bOptions.setSize(150, 50);
        bOptions.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setWindowOptions();
                System.out.println("Options");
            }
        });

        bAboutUs.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) - 50 );
        bAboutUs.setSize(150, 50);
        bAboutUs.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("About Us");
                bgMusic.stop();
                bgMusic.dispose();
                Resources.game.setScreen(new LoadScreen(ScreenType.MainMenu));
            }
        });
        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
        {

            bExit.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) - 150);
            bExit.setSize(150, 50);
            bExit.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    if (state)
                        setWindowExit();
                }
            });
            stage.addActor(bExit);
        }

        bEasyLvl.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) + 150);
        bEasyLvl.setSize(150, 50);
        bEasyLvl.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("Easy LvL");
                Resources.currentLvL = 1;
                bgMusic.stop();
                bgMusic.dispose();
                Resources.game.setScreen(new LoadScreen(ScreenType.Game));
            }
        });

        bNormalLvl.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) + 50 );
        bNormalLvl.setSize(150, 50);
        bNormalLvl.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.OPTIONS.getBoolean("Lvl 2 key"))
                {
                    System.out.println("Normal LvL");
                    Resources.currentLvL = 2;
                    bgMusic.stop();
                    bgMusic.dispose();
                    Resources.game.setScreen(new LoadScreen(ScreenType.Game));
                }
            }
        });

        bHardLvl.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) - 50 );
        bHardLvl.setSize(150, 50);
        bHardLvl.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.OPTIONS.getBoolean("Lvl 3 key"))
                {
                    System.out.println("Hard LvL");
                    Resources.currentLvL = 3;
                    bgMusic.stop();
                    bgMusic.dispose();
                    Resources.game.setScreen(new LoadScreen(ScreenType.Game));
                }
            }
        });

        bReturn.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) - 150 );
        bReturn.setSize(150, 50);
        bReturn.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("Return");
                mainMenu.setVisible(true);
                choiceLvL.setVisible(false);
            }
        });


        stage.addActor(mainMenu);
        stage.addActor(choiceLvL);


        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCursorCatched(false);
        Gdx.input.setCatchMenuKey(true);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)   {}

    @Override
    public void pause()
    {
        state = false;
    }

    @Override
    public void resume()
    {
        state = true;
    }

    @Override
    public void hide()
    {
        state = false;
    }

    @Override
    public void dispose()
    {
        skin.dispose();
        System.out.println("Disposed:  Main Menu");
    }

    private void setWindowExit()
    {
        state = false;
        final Group window = new Group();

        final Image bg = new Image(skin.getDrawable("exit_window_bg"));
        bg.setPosition((Resources.WORLD_WIDTH_2)-150, (Resources.WORLD_HEIGHT_2)-15);

        final Label l = new Label("Are you sure you want to exit?", Resources.simpleLSWhite);
        l.setPosition((Resources.WORLD_WIDTH_2)-l.getPrefWidth()/2, (Resources.WORLD_HEIGHT_2)+55);

        final TextButton bYes = new TextButton("Yes", Resources.tbs_small);
        bYes.setPosition((Resources.WORLD_WIDTH_2)-125, (Resources.WORLD_HEIGHT_2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", Resources.tbs_small);
        bNo.setPosition((Resources.WORLD_WIDTH_2)+25, (Resources.WORLD_HEIGHT_2));
        bNo.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());
                state = true;
            }
        });

        window.addActor(bg);
        window.addActor(l);
        window.addActor(bYes);
        window.addActor(bNo);
        stage.addActor(window);
    }

    private void  setWindowOptions()
    {
        state = false;

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.WORLD_WIDTH_2 - 150, Resources.WORLD_HEIGHT_2 -215);

        final Label optionsL = new Label("Options",Resources.simpleLSWhite);
        optionsL.setPosition(Resources.WORLD_WIDTH_2 - optionsL.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);

        final Label musicL = new Label("Music volume", Resources.simpleLSWhite);
        musicL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 100);

        Slider.SliderStyle slider = new Slider.SliderStyle(Resources.guiSkin.getDrawable("slide_line"),
                                                        Resources.guiSkin.getDrawable("slide_point"));
        final Slider musicS = new Slider(0f, 1f, 0.1f  , false, slider);
        musicS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 85);
        musicS.setSize(250 , musicS.getHeight());
        musicS.setValue(Resources.OPTIONS.getFloat("Music Volume"));
        musicS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Resources.OPTIONS.putFloat("Music Volume", musicS.getValue());
                Resources.OPTIONS.flush();
                bgMusic.setVolume(Resources.OPTIONS.getFloat("Music Volume"));
            }
        });

        final Label effectL = new Label("Effects volume",Resources.simpleLSWhite);
        effectL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 50);

        final Slider effectS = new Slider(0f, 1f, 0.1f  , false, slider);
        effectS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 35);
        effectS.setSize(250 , effectS.getHeight());
        effectS.setValue(Resources.OPTIONS.getFloat("Effect Volume"));
        effectS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Resources.OPTIONS.putFloat("Effect Volume", effectS.getValue());
                Resources.OPTIONS.flush();
            }
        });

        final TextButton returnB = new TextButton("Return", Resources.tbs_medium);
        returnB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 - 200);
        returnB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());
                state = true;
            }
        });

        window.addActor(bg);
        window.addActor(optionsL);
        window.addActor(musicL);
        window.addActor(musicS);
        window.addActor(effectL);
        window.addActor(effectS);
        window.addActor(returnB);

        stage.addActor(window);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.BACK && state)
            setWindowExit();
        if (keycode == Input.Keys.MENU && state)
            setWindowOptions();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
