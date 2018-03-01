package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    LoadScreen (ScreenType screen)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.WIDTH, Resources.HEIGHT);
        stage = new Stage(new ScreenViewport(camera));
        this.screen = screen;
        animObject = new AnimObject(Resources.LOAD_ANIMATION);
        l = new Label("Loading...",new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE));
    }

    @Override
    public void show()
    {
        animObject.setPosition(Resources.WIDTH_2 - animObject.getWidth()/2, Resources.HEIGHT_2 - animObject.getHeight()/2);
        l.setPosition(Resources.WIDTH_2 - l.getPrefWidth()/2, animObject.getY() - 20);
        stage.addActor(l);
        stage.addActor(animObject);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.timeSinceMillis(startTime) > 2500)
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.ESCAPE)
            Resources.game.setScreen(new MainMenu());
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
