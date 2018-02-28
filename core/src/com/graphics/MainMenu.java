package com.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen
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
        skin.addRegions(Resources.GUI_ATLAS);

        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("text_button_s1");
        tbs.down = skin.getDrawable("text_button_s2");
        tbs.font = Resources.game.font;
        Resources.tbs_s = tbs;
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.up = skin.getDrawable("text_button_m1");
        tbs1.down = skin.getDrawable("text_button_m2");
        tbs1.font = Resources.game.font;
        Resources.tbs_m = tbs1;

        Resources.simpleLS = new Label.LabelStyle(Resources.game.font, Color.WHITE);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(Resources.menuMusicPath));
        bgMusic.setLooping(true);
        bgMusic.setVolume(Options.musicVolume);
        bgMusic.play();

        final TextButton bNewGame = new TextButton("New Game", Resources.tbs_s);
        bNewGame.setPosition((Resources.WORLD_WIDTH_2) - 75, (Resources.WORLD_HEIGHT_2) + 150);
        bNewGame.setSize(150, 50);
        bNewGame.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("New Game");
                bgMusic.stop();
                bgMusic.dispose();
                Resources.game.setScreen(new LoadScreen(ScreenType.Game));
            }
        });
        stage.addActor(bNewGame);

        final TextButton bOptions = new TextButton("Options", Resources.tbs_s);
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
        final TextButton bAboutUs = new TextButton("About Us", Resources.tbs_s);
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
            final TextButton bExit = new TextButton("Exit", Resources.tbs_s);
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


        stage.addActor(bOptions);
        stage.addActor(bAboutUs);


        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorCatched(false);
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        skin.dispose();
    }

    private void setWindowExit()
    {
        state = false;
        final Group window = new Group();

        final Image bg = new Image(skin.getDrawable("exit_window_bg"));
        bg.setPosition((Resources.WORLD_WIDTH_2)-150, (Resources.WORLD_HEIGHT_2)-15);

        final Label l = new Label("Are you sure you want to exit?", Resources.simpleLS);
        l.setPosition((Resources.WORLD_WIDTH_2)-l.getPrefWidth()/2, (Resources.WORLD_HEIGHT_2)+55);

        final TextButton bYes = new TextButton("Yes", Resources.tbs_s);
        bYes.setPosition((Resources.WORLD_WIDTH_2)-125, (Resources.WORLD_HEIGHT_2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", Resources.tbs_s);
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

        final Image bg = new Image(Resources.GUI_SKIN.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.WORLD_WIDTH_2 - 150, Resources.WORLD_HEIGHT_2 -215);

        final Label optionsL = new Label("Options",Resources.simpleLS);
        optionsL.setPosition(Resources.WORLD_WIDTH_2 - optionsL.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);

        final Label musicL = new Label("Music volume", Resources.simpleLS);
        musicL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 100);

        Slider.SliderStyle slider = new Slider.SliderStyle(Resources.GUI_SKIN.getDrawable("slide_line"),
                                                        Resources.GUI_SKIN.getDrawable("slide_point"));
        final Slider musicS = new Slider(0f, 1f, 0.1f  , false, slider);
        musicS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 85);
        musicS.setSize(250 , musicS.getHeight());
        musicS.setValue(Options.musicVolume);
        musicS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.musicVolume = musicS.getValue();
                bgMusic.setVolume(Options.musicVolume);
            }
        });

        final Label effectL = new Label("Effects volume",Resources.simpleLS);
        effectL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 50);

        final Slider effectS = new Slider(0f, 1f, 0.1f  , false, slider);
        effectS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 35);
        effectS.setSize(250 , effectS.getHeight());
        effectS.setValue(Options.effectVolume);
        effectS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.effectVolume = effectS.getValue();
            }
        });

        final TextButton returnB = new TextButton("Return", Resources.tbs_m);
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
}
