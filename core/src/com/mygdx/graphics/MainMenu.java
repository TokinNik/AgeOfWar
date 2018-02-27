package com.mygdx.graphics;

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
        camera.setToOrtho(false, Resources.gameW, Resources.gameH);
        stage = new Stage(new ScreenViewport(camera));
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
        tbs.font = Resources.game.font;
        Resources.tbs_s = tbs;
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.up = skin.getDrawable("text_button_m1");
        tbs1.down = skin.getDrawable("text_button_m2");
        tbs1.font = Resources.game.font;
        Resources.tbs_m = tbs1;

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("android/assets/music/main_menu.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(Options.musicVolume);
        bgMusic.play();

        final TextButton bNewGame = new TextButton("New Game", Resources.tbs_s);
        bNewGame.setPosition((Resources.width2) - 75, (Resources.height2) + 150);
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
        bOptions.setPosition((Resources.width2) - 75, (Resources.height2) + 50);
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
        bAboutUs.setPosition((Resources.width2) - 75, (Resources.height2) - 50 );
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
        final TextButton exitB = new TextButton("Exit", Resources.tbs_s);
        exitB.setPosition((Resources.width2) - 75, (Resources.height2) - 150);
        exitB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (state)
                    setWindowExit();
            }
        });

        stage.addActor(bOptions);
        stage.addActor(bAboutUs);
        stage.addActor(exitB);

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
    public void resize(int width, int height) {

    }

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
        bg.setPosition((Resources.width2)-150, (Resources.height2)-15);

        final Label l = new Label("Are you sure you want to exit?", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        l.setPosition((Resources.width2)-l.getPrefWidth()/2, (Resources.height2)+55);

        final TextButton bYes = new TextButton("Yes", Resources.tbs_s);
        bYes.setPosition((Resources.width2)-125, (Resources.height2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", Resources.tbs_s);
        bNo.setPosition((Resources.width2)+25, (Resources.height2));
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
        bg.setPosition(Resources.width2 - 150, Resources.height2-215);

        final Label optionsL = new Label("Options", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        optionsL.setPosition(Resources.width2 - optionsL.getPrefWidth()/2, Resources.height2 + 155);

        final Label musicL = new Label("Music volume", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        musicL.setPosition(Resources.width2 - 125, Resources.height2 + 100);

        Slider.SliderStyle slider = new Slider.SliderStyle(Resources.guiSkin.getDrawable("slide_line"),
                                                        Resources.guiSkin.getDrawable("slide_point"));
        final Slider musicS = new Slider(0f, 1f, 0.1f  , false, slider);
        musicS.setPosition(Resources.width2 - 125, Resources.height2 + 85);
        musicS.setSize(250 , musicS.getHeight());
        musicS.setValue(Options.musicVolume);
        musicS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.musicVolume = musicS.getValue();
                bgMusic.setVolume(Options.musicVolume);
            }
        });

        final Label effectL = new Label("Effects volume", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        effectL.setPosition(Resources.width2 - 125, Resources.height2 + 50);

        final Slider effectS = new Slider(0f, 1f, 0.1f  , false, slider);
        effectS.setPosition(Resources.width2 - 125, Resources.height2 + 35);
        effectS.setSize(250 , effectS.getHeight());
        effectS.setValue(Options.musicVolume);
        effectS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.musicVolume = effectS.getValue();
            }
        });

        final TextButton returnB = new TextButton("Return", Resources.tbs_m);
        returnB.setPosition(Resources.width2 - 125, Resources.height2 - 200);
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
