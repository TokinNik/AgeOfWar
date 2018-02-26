package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadScreen implements Screen
{
    private OrthographicCamera camera;
    private Stage stage;
    private AnimObject animObject;
    private Label l;

    LoadScreen ()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.width, Resources.height);
        stage = new Stage(new ScreenViewport(camera));
        animObject = new AnimObject(Resources.loadAnimation);
        l = new Label("Loading...",new Label.LabelStyle(Resources.game.font, Color.WHITE));
    }

    @Override
    public void show()
    {
        animObject.setPosition(Resources.width2 - animObject.getWidth()/2, Resources.height2 - animObject.getHeight()/2);
        l.setPosition(Resources.width2 - l.getPrefWidth()/2, animObject.getY() - 20);
        stage.addActor(l);
        stage.addActor(animObject);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
