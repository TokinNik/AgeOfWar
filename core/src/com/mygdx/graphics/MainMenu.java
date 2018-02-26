package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.controller.CharacterController;


public class MainMenu implements Screen
{
    private OrthographicCamera camera;
    private static Skin skin;
    private Stage stage;
    boolean state;

    MainMenu()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.gameW, Resources.gameH);
        stage = new Stage(new ScreenViewport(camera));
        state = true;


    }

    @Override
    public void show()
    {
        skin = new Skin();
        skin.addRegions(Resources.guiAtlas);

        Button b = new Button(new Button.ButtonStyle(
                skin.getDrawable("exitButton2"),
                skin.getDrawable("exitButton3"),
                skin.getDrawable("exitButton2")));
        b.setPosition(0,0);
        b.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (state)
                    setWindowExit();
            }
        });

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

        if (false)//заменить на нормальную проверку
        {
            final TextButton bContinue = new TextButton("Continue", Resources.tbs_s);
            bContinue.setPosition((Resources.width2) - 75, (Resources.height2));
            bContinue.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Continue");
                }
            });
            stage.addActor(bContinue);
        }
        else
        {
            final TextButton bNewGame = new TextButton("New Game", Resources.tbs_s);
            bNewGame.setPosition((Resources.width2) - 75, (Resources.height/2));
            bNewGame.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    System.out.println("New Game");
                    Resources.game.setScreen(new GameScreen());
                }
            });
            stage.addActor(bNewGame);
        }
        final TextButton bOptions = new TextButton("Options", Resources.tbs_s);
        bOptions.setPosition((Resources.width2) - 75, (Resources.height2) - 100);
        bOptions.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("Options");
            }
        });
        final TextButton bAboutUs = new TextButton("About Us", Resources.tbs_s);
        bAboutUs.setPosition((Resources.width2) - 75, (Resources.height2) - 200);
        bAboutUs.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("About Us");
                Resources.game.setScreen(new LoadScreen());
            }
        });

        stage.addActor(bOptions);
        stage.addActor(bAboutUs);
        stage.addActor(b);

        Gdx.input.setInputProcessor(stage);
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
                bYes.setVisible(false);
                bNo.setVisible(false);
                l.setVisible(false);
                bg.setVisible(false);
                state = true;
            }
        });

        stage.addActor(bg);
        stage.addActor(l);
        stage.addActor(bYes);
        stage.addActor(bNo);
    }
}
