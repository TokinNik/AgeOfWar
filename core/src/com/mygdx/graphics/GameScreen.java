package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;


public class GameScreen implements Screen, InputProcessor
{
    private static Start game;
    private static OrthographicCamera camera;
    private static Stage stage;
    private static GameGUI gui;
    private static Array<Unit> units;
    private static float prefX;

    GameScreen (final Start gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Resources.width, Resources.height);
        stage = new Stage(new ScreenViewport(camera));
        stage.setDebugAll(true);
        gui = new GameGUI(game, this);
        Resources.state = State.GAME;
        units = new Array<Unit>();
        prefX = -1;
        //new Thread(new CharacterController()).start();
        CharacterController.start();
    }

    @Override
    public void show()
    {
        final Image bg = Resources.bgForest;
        bg.setPosition(0, 0);
        stage.addActor(bg);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, gui);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta)
    {
        if (Resources.state == State.GAME)
        {
            Gdx.gl.glClearColor(0, 0.2f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            int i = 0;
            for (Unit u: units)
            {
                if (u.getX() > 2200 || u.getX() < 100)
                    delUnit(i);
                i++;
            }
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

    }

    static void setUnit(CharacterType type) throws NotEnoughMonyException
    {
        System.out.println("Set Unit type " + type);

        Unit unit = new Unit(type);
        units.add(unit);
        stage.addActor(unit);
    }

    public static void setCompUnit(CharacterType type, Character character) throws NotEnoughMonyException
    {
        System.out.println("Set Unit type " + type);

        Unit unit = new Unit(type, character);
        units.add(unit);
        stage.addActor(unit);
    }

    void delUnit(int num)
    {
        units.get(num).getCharacter().setAlive(false);
        units.get(num).remove();
        System.out.println("Delete Unit type " + units.get(num).getType() + " direction " + units.get(num).getDirection());
        units.removeIndex(num);
        gui.updateLabels();
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        prefX = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if (prefX != -1 && (camera.position.x >= Resources.width2 || prefX - screenX > 0)
                && (camera.position.x <= 2268- Resources.width2 || prefX - screenX < 0))
        {
            camera.translate(prefX - screenX, 0);
            if (camera.position.x < Resources.width2)
                camera.position.x = Resources.width2;
            if (camera.position.x > 2268- Resources.width2)
                camera.position.x = 2268- Resources.width2;
        }
        prefX = screenX;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        prefX = screenX;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
