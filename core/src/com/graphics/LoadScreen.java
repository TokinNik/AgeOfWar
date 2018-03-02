package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadScreen implements Screen, InputProcessor
{
    private OrthographicCamera camera;
    private Stage stage;
    private AnimObject animObject;
    private Label l;
    private long startTime = TimeUtils.millis();
    private ScreenType screen;
    private  boolean state;

    LoadScreen (ScreenType screen)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.WIDTH, Resources.HEIGHT);
        stage = new Stage(new ScreenViewport(camera));
        this.screen = screen;
        animObject = new AnimObject(Resources.loadAnimation);
        l = new Label("Loading...",new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE));
    }

    @Override
    public void show()
    {
        animObject.setPosition(Resources.WIDTH_2 - animObject.getWidth()/2, Resources.HEIGHT_2 - animObject.getHeight()/2);
        l.setPosition(Resources.WIDTH_2 - l.getPrefWidth()/2, animObject.getY() - 20);
        stage.addActor(l);
        stage.addActor(animObject);
        state = true;
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.timeSinceMillis(startTime) > 2500 && state)
        {
            pause();
            dispose();
            if (screen == ScreenType.MainMenu)
                Resources.game.setScreen(new MainMenu());
            if (screen == ScreenType.Game)
                Resources.game.setScreen(new GameScreen());
        }

        Gdx.input.setCursorPosition(0,0);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {
        Resources.reload();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
        System.out.println("Disposed:  Load Screen");
    }

    private void setWindowExit()
    {
        state = false;

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("exit_window_bg"));
        bg.setPosition((Resources.WORLD_WIDTH_2)-150, (Resources.WORLD_HEIGHT_2)-15);

        final Label l = new Label("Are you sure you want to exit?", Resources.simpleLSWhite);
        l.setPosition((Resources.WORLD_WIDTH_2)-l.getPrefWidth()/2, (Resources.WORLD_HEIGHT_2)+55);

        final TextButton bYes = new TextButton("Yes", Resources.tbs_s);
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
        final TextButton bNo = new TextButton("No", Resources.tbs_s);
        bNo.setPosition((Resources.WORLD_WIDTH_2)+25, (Resources.WORLD_HEIGHT_2));
        bNo.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                state = true;
                window.addAction(Actions.removeActor());
            }
        });

        window.addActor(bg);
        window.addActor(l);
        window.addActor(bYes);
        window.addActor(bNo);
        stage.addActor(window);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.ESCAPE)
            Resources.game.setScreen(new MainMenu());
        if (keycode == Input.Keys.BACK && state)
            setWindowExit();
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
