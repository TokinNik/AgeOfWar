package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen implements Screen
{
    private static Start game;
    private static OrthographicCamera camera;
    private static TextureAtlas atlas;
    private static Skin skin;
    private static Stage stage;
    private static GameGUI gui;
    private static Array<Unit> units;

    GameScreen (final Start gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resourses.width, Resourses.height);
        stage = new Stage(new ScreenViewport(camera));
        stage.setDebugAll(true);
        gui = new GameGUI(game, this);
        Resourses.state = State.GAME;
        units = new Array<Unit>();
    }

    @Override
    public void show()
    {
        atlas = new TextureAtlas("android/assets/gui/gui.atlas");
        skin = new Skin();
        skin.addRegions(atlas);

        Unit unit = new Unit(1,1);
        units.add(unit);
        stage.addActor(unit);

        Gdx.input.setInputProcessor(gui);
    }

    @Override
    public void render(float delta)
    {
        if (Resourses.state == State.GAME)
        {
            Gdx.gl.glClearColor(0, 0.2f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.act(delta);
            stage.draw();
        }


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
    public void dispose()
    {
        atlas.dispose();
        skin.dispose();
    }

    void setUnit(int type, int dir)
    {
        System.out.println("Set Unit type " + type + " direction " + dir);

        Unit unit = new Unit(type, dir);
        units.add(unit);
        stage.addActor(unit);
    }

    void delUnit(int num)
    {
        units.get(num).addAction(Actions.removeActor());
        System.out.println("Delete Unit type " + units.get(num).getType() + " direction " + units.get(num).getDirection());
        units.removeIndex(num);
    }


}
