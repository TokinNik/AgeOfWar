package com.mygdx.game;

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


public class MainMenu implements Screen
{
    private Start game;
    private OrthographicCamera camera;
    private static TextureAtlas atlas;
    private static Skin skin;
    private Stage stage;
    boolean state;

    MainMenu(final Start gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resourses.width, Resourses.height);
        stage = new Stage(new ScreenViewport(camera));
        state = true;


    }

    @Override
    public void show()
    {
        atlas = new TextureAtlas("android/assets/gui/gui.atlas");
        skin = new Skin();
        skin.addRegions(atlas);

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
        tbs.font = game.font;
        Resourses.tbs_s = tbs;
        TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
        tbs1.up = skin.getDrawable("text_button_m1");
        tbs1.down = skin.getDrawable("text_button_m2");
        tbs1.font = game.font;
        Resourses.tbs_m = tbs1;

        if (false)//заменить на нормальную проверку
        {
            final TextButton bContinue = new TextButton("Continue", Resourses.tbs_s);
            bContinue.setPosition((Resourses.width / 2) - 75, (Resourses.height / 2));
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
            final TextButton bNewGame = new TextButton("New Game", Resourses.tbs_s);
            bNewGame.setPosition((Resourses.width/2) - 75, (Resourses.height/2));
            bNewGame.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    System.out.println("New Game");
                    game.setScreen(new GameScreen(game));
                }
            });
            stage.addActor(bNewGame);
        }
        final TextButton bOptions = new TextButton("Options", Resourses.tbs_s);
        bOptions.setPosition((Resourses.width/2) - 75, (Resourses.height/2) - 100);
        bOptions.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("Options");
            }
        });
        final TextButton bAboutUs = new TextButton("About Us", Resourses.tbs_s);
        bAboutUs.setPosition((Resourses.width/2) - 75, (Resourses.height/2) - 200);
        bAboutUs.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("About Us");
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
        //game.batch.setProjectionMatrix(camera.combined);

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
        atlas.dispose();
        skin.dispose();
    }

    private void setWindowExit()
    {
        state = false;

        final Image bg = new Image(skin.getDrawable("exit_window_bg"));
        bg.setPosition((Resourses.width/2)-150, (Resourses.height/2)-15);

        final Label l = new Label("Are you sure you want to exit?", new Label.LabelStyle(game.font, Color.WHITE));
        l.setPosition((Resourses.width/2)-l.getPrefWidth()/2, (Resourses.height/2)+55);

        final TextButton bYes = new TextButton("Yes", Resourses.tbs_s);
        bYes.setPosition((Resourses.width/2)-125, (Resourses.height/2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", Resourses.tbs_s);
        bNo.setPosition((Resourses.width/2)+25, (Resourses.height/2));
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
