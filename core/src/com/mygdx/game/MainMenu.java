package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by TNR on 02.02.2018.
 */

public class MainMenu implements Screen,InputProcessor
{
    private Start game;
    private OrthographicCamera camera;
    private MainMenuGUI gui;

    MainMenu(final Start gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, LwjglApplicationConfiguration.getDesktopDisplayMode().width, LwjglApplicationConfiguration.getDesktopDisplayMode().height);

        gui = new MainMenuGUI(game);
        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, gui);
        Gdx.input.setInputProcessor(inputMultiplexer);


    }

    @Override
    public void show() {}

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to the Game!", (LwjglApplicationConfiguration.getDesktopDisplayMode().width / 2) - 150,
                (LwjglApplicationConfiguration.getDesktopDisplayMode().height * 0.8f));
        game.batch.end();

        camera.update();

        gui.act(delta);
        gui.draw();
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
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
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
