package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;
import com.model.GameForpost;
import com.model.UserForpost;


public class GameScreen implements Screen, InputProcessor
{
    private static OrthographicCamera camera;
    private static Stage stage;
    private static GameGUI gui;
    private static Array<Unit> units;
    private static float prefX;
    //private static float prefY;
    private static UserForpost userForpost;
    private static GameForpost gameForpost;

    GameScreen ()
    {
        camera = new OrthographicCamera(Resources.WORLD_WIDTH, Resources.WORLD_HEIGHT);
        camera.setToOrtho(false);

        stage = new Stage(new ScreenViewport(camera));
        //stage.setDebugAll(true);
        userForpost = UserForpost.getInstance();
        gameForpost = GameForpost.getInstance();
        gui = new GameGUI();
        Resources.state = State.GAME;
        units = new Array<Unit>();
        prefX = -1;
        //prefY = -1;

        CharacterController.start();
    }

    @Override
    public void show()
    {
        final Image bg = Resources.BG_FOREST;
        bg.setBounds(0, 0, Resources.GAME_WIDTH, Resources.GAME_HEIGHT);
        stage.addActor(bg);

        Forpost gameF = new Forpost(-1);
        Forpost userF = new Forpost(1);

        stage.addActor(userF);
        stage.addActor(gameF);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(this, gui);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCursorCatched(false);
    }

    @Override
    public void render(float delta)
    {
        if (Resources.state == State.GAME)
        {
            Gdx.gl.glClearColor(0, 0.2f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if (CharacterController.isGameWin())
                gui.setGameEndMenu(true);
            if (CharacterController.isUserWin())
                gui.setGameEndMenu(false);


            gui.updateBaseHealth();
            gui.updateLabels();

            int i = 0;
            for (Unit u: units)
            {

                if (!u.getCharacter().isAlive())
                    delUnit(i);

                i++;
            }
            stage.act(delta);
            stage.draw();
        }
        else
        {
            Resources.game.batch.begin();
            Resources.BG_FOREST_BLUR.setBounds(0, 0, Resources.GAME_WIDTH, Resources.GAME_HEIGHT);
            Resources.BG_FOREST_BLUR.draw(Resources.game.batch, 1);
            Resources.game.batch.end();
        }

        gui.act(delta);
        gui.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause()
    {
        Resources.state = State.PAUSE;
        CharacterController.setPause(true);
    }

    @Override
    public void resume()
    {
        Resources.state = State.GAME;
        CharacterController.setPause(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
        gui.dispose();
        gui.dispose();
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

    private void delUnit(int num)
    {
        units.get(num).remove();
        System.out.println("Delete Unit type " + units.get(num).getType() + " direction " + units.get(num).getDirection());
        units.removeIndex(num);
    }

    static void clear()
    {
        for (Unit u: units)
        {
            u.remove();
            System.out.println("Delete Unit type " + u.getType() + " direction " + u.getDirection());
        }
        units.clear();
        prefX = -1;
        //prefY = -1;
        gui.updateLabels();
        gui.updateBaseHealth();
    }

    static void exit()
    {
        for (Unit u: units)
        {
            u.remove();
            System.out.println("Delete Unit type " + u.getType() + " direction " + u.getDirection());
        }
        units.clear();
        stage.clear();
    }

    static UserForpost getUserForpost() {
        return userForpost;
    }

    static GameForpost getGameForpost() {
        return gameForpost;
    }

    @Override
    public boolean keyDown(int keycode)
    {
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
        //prefY = -1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        if (Resources.state == State.GAME)
            if (prefX != -1)//&& prefY != -1) //&& (camera.position.x >= Resources.WORLD_WIDTH_2 || prefX - screenX > 0)
                    //&& (camera.position.x <= Resources.GAME_WIDTH - Resources.WORLD_WIDTH_2 || prefX - screenX < 0))
            {
                camera.translate(prefX - screenX, 0);
                if (camera.position.x - camera.viewportWidth/2 < 0)
                    camera.position.x = camera.viewportWidth/2;
                if (camera.position.x + camera.viewportWidth/2 > Resources.GAME_WIDTH)
                    camera.position.x = Resources.GAME_WIDTH - camera.viewportWidth/2;
                if (camera.position.y - camera.viewportHeight/2 < 0)
                    camera.position.y = camera.viewportHeight/2;
                if (camera.position.y + camera.viewportHeight/2 > Resources.GAME_HEIGHT)
                    camera.position.y = Resources.GAME_HEIGHT - camera.viewportHeight/2;
            }
        prefX = screenX;
        //prefY = screenY;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        prefX = screenX;
        //prefY = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        if (amount > 0
                && camera.viewportHeight * 1.1f < Resources.GAME_HEIGHT
                && camera.viewportWidth * 1.1f < Resources.GAME_WIDTH )
        {
            camera.viewportWidth *= 1.1f;
            camera.viewportHeight *= 1.1f;
            if (camera.position.x - camera.viewportWidth/2 < 0)
                camera.position.x = camera.viewportWidth/2;
            if (camera.position.x + camera.viewportWidth/2 > Resources.GAME_WIDTH)
                camera.position.x = Resources.GAME_WIDTH - camera.viewportWidth/2;
//            if (camera.position.y - camera.viewportHeight/2 < 0)
//                camera.position.y = camera.viewportHeight/2;
//            if (camera.position.y + camera.viewportHeight/2 > Resources.GAME_HEIGHT)
//                camera.position.y = Resources.GAME_HEIGHT - camera.viewportHeight/2;
            camera.position.y = camera.viewportHeight/2;
        }
        if (amount < 0
                && camera.viewportHeight * 0.9f >= Resources.WORLD_HEIGHT
                && camera.viewportWidth * 0.9f >= Resources.WORLD_WIDTH)
        {
            camera.viewportWidth *= 0.9f;
            camera.viewportHeight *= 0.9f;
            camera.position.y = camera.viewportHeight/2;
        }
        return false;
    }
}
