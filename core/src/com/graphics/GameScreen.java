package com.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.controller.GameChangeListaner;
import com.controller.GameController;
import com.model.UnitType;
import com.exception.NotEnoughMonyException;
import com.model.StageOfEvolution;
import com.model.UnitState;
import com.model.GameForpost;
import com.model.UserForpost;


public class GameScreen implements Screen, InputProcessor, GameChangeListaner
{
    private static OrthographicCamera camera;
    private static Stage stage;
    private static GameGUI gui;
    private static Array<GraphicUnit> units;
    private static float prefX;
    //private static float prefY;
    private static UserForpost userForpost;
    private static GameForpost gameForpost;
    private static Shield shieldU;
    private static Shield shieldG;
    public static GameController gameController;
    private static StageOfEvolution stageOfEvolution;

    GameScreen ()
    {
        camera = new OrthographicCamera(Resources.WORLD_WIDTH, Resources.WORLD_HEIGHT);
        camera.setToOrtho(false);

        gameController = new GameController(this);
        gameController.start();
        stageOfEvolution = StageOfEvolution.FIRST;

        stage = new Stage(new ScreenViewport(camera));
        //stage.setDebugAll(true);
        userForpost = UserForpost.getInstance();
        gameForpost = GameForpost.getInstance();
        gui = new GameGUI(gameController);
        units = new Array<GraphicUnit>();
        prefX = -1;
        //prefY = -1;

    }

    @Override
    public void show()
    {
        Resources.state = State.GAME;
        //gameController.start();
        final Image bg = Resources.bgForest;
        bg.setBounds(0, 0, Resources.GAME_WIDTH, Resources.GAME_HEIGHT);
        stage.addActor(bg);

        Forpost gameF = new Forpost(-1);
        Forpost userF = new Forpost(1);

        stage.addActor(userF);
        stage.addActor(gameF);

        shieldU = new Shield(true, StageOfEvolution.FIRST);
        shieldG = new Shield(false, StageOfEvolution.FIRST);

        stage.addActor(shieldG);
        stage.addActor(shieldU);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(new GestureDetector(new GestureDetector.GestureListener() {
            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY)
            {
                if (Resources.state == State.GAME)// && Gdx.app.getType() == Application.ApplicationType.Android)
                    if (prefX != -1)//&& prefY != -1) //&& (camera.position.x >= Resources.WORLD_WIDTH_2 || prefX - screenX > 0)
                    //&& (camera.position.x <= Resources.GAME_WIDTH - Resources.WORLD_WIDTH_2 || prefX - screenX < 0))
                    {
                        camera.translate(-deltaX, 0);
                        if (camera.position.x - camera.viewportWidth/2 < 0)
                            camera.position.x = camera.viewportWidth/2;
                        if (camera.position.x + camera.viewportWidth/2 > Resources.GAME_WIDTH)
                            camera.position.x = Resources.GAME_WIDTH - camera.viewportWidth/2;
                        if (camera.position.y - camera.viewportHeight/2 < 0)
                            camera.position.y = camera.viewportHeight/2;
                        if (camera.position.y + camera.viewportHeight/2 > Resources.GAME_HEIGHT)
                            camera.position.y = Resources.GAME_HEIGHT - camera.viewportHeight/2;
                    }
                prefX = x;
                //prefY = screenY;
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance)
            {
                float amount = distance - initialDistance;
                System.out.println("qwerty   " + amount);
                if (amount < 0
                        && camera.viewportHeight * 1.01f < Resources.GAME_HEIGHT
                        && camera.viewportWidth * 1.01f < Resources.GAME_WIDTH )
                {
                    camera.viewportWidth *= 1.01f;
                    camera.viewportHeight *= 1.01f;
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
                if (amount > 0
                        && camera.viewportHeight * 0.7f >= Resources.WORLD_HEIGHT
                        && camera.viewportWidth * 0.7f >= Resources.WORLD_WIDTH)
                {
                    camera.viewportWidth *= 0.99f;
                    camera.viewportHeight *= 0.99f;
                    camera.position.y = camera.viewportHeight/2;
                }
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return false;
            }

            @Override
            public void pinchStop() {

            }
        }),this, gui);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCursorCatched(false);
        Gdx.input.setCatchMenuKey(true);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta)
    {
        if (Resources.state == State.GAME)
        {
            Gdx.gl.glClearColor(0, 0.2f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            gui.updateBaseHealth();
            gui.updateLabels();

            for(int i = 0; i < units.size - 1; ++i)
            {
                if (units.get(i).getUnitState() == UnitState.DIE)
                    delUnit(i);
            }
            stage.act(delta);
            stage.draw();
        }
        else
        {
            Resources.game.batch.begin();
            Resources.bgForestBlur.setBounds(0, 0, Resources.GAME_WIDTH, Resources.GAME_HEIGHT);
            Resources.bgForestBlur.draw(Resources.game.batch, 1);
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
        gui.setWindowMenu();
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
       gui.setWindowMenu();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        gui.dispose();
        gui.dispose();
        System.out.println("Disposed:  Game Screen");
    }

    static void setUnit(UnitType type, boolean users, int id) throws NotEnoughMonyException
    {

        GraphicUnit graphicUnit = new GraphicUnit(type, users ? gameController.createNewUnit(type, true) : id, users, stageOfEvolution);
        System.out.println("Set GraphicUnit type " + type + " ID " + (users ? graphicUnit.getUnitId() : id) + " direction " + users);
        units.add(graphicUnit);
        stage.addActor(graphicUnit);
    }

    static void setShield(Boolean user)
    {
        System.out.println("Set Shield for " + (user ? "user" : "game"));
        if(user)
            shieldU.setActive(true);
        else
            shieldG.setActive(true);
    }

    static void setArrow(boolean dir, float x1, float y1, float x2, float y2)
    {
        //System.out.println("Set Arrow " + x1 + " " + y1 + " " + x2 + " " + y2);

        Arrow arrow = new Arrow(dir, x1, y1, x2, y2);
        stage.addActor(arrow);
    }

    private void delUnit(int num)
    {
        System.out.println("(1)Delete GraphicUnit type "+ units.get(num).getType()
                 + " ID " + units.get(num).getUnitId() + " direction " + units.get(num).getDirection());
        units.removeValue(units.get(num), true);
    }

    private void delUnit(GraphicUnit unit)
    {
        System.out.println("(2)Delete GraphicUnit type "+ unit.getType()
                 + " ID " + unit.getUnitId() + " direction " + unit.getDirection());
        units.removeValue(unit, true);
    }

    static void clear()
    {
        for (GraphicUnit u: units)
        {
            u.remove();
            System.out.println("Delete GraphicUnit type " + u.getType() + " direction " + u.getDirection());
        }
        units.clear();
        shieldG.setActive(false);
        shieldU.setActive(false);
        prefX = -1;
        //prefY = -1;
        gui.updateLabels();
        gui.updateBaseHealth();
    }

    static void exit()
    {
        for (GraphicUnit u: units)
        {
            u.remove();
            System.out.println("Delete GraphicUnit type " + u.getType() + " direction " + u.getDirection());
        }
        units.clear();
        //stage.clear();
    }

    private GraphicUnit findUnitToId(int id)
    {
        for (int i = 0; i <= units.size-1; i++)
        {
            if(units.get(i).getUnitId() == id)
            {
                return units.get(i);
            }
        }
        return null;
    }

    static UserForpost getUserForpost() {
        return userForpost;
    }

    static GameForpost getGameForpost() {
        return gameForpost;
    }

    public static void setStageOfEvolution(StageOfEvolution stageOfEvolution) {
        GameScreen.stageOfEvolution = stageOfEvolution;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (keycode == Input.Keys.BACK)
            gui.setWindowExit();
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
        if (Resources.state == State.GAME && Gdx.app.getType() == Application.ApplicationType.Desktop)
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

    @Override
    public void onUnitStateChange(boolean users, int unitID, UnitState newState)
    {
        System.out.println("(onUnitStateChange) dir " + users + " id " + unitID + " new state " + newState);
        try
        {
            if (newState == UnitState.DIE)
                delUnit(findUnitToId(unitID));
            else
                findUnitToId(unitID).setUnitState(newState);
        }catch (NullPointerException c)
        {
            System.out.println("(!onUnitStateChange) There is no such unit for ID " + unitID + " dir " + users + " new state" + newState);
        }
    }

    @Override
    public void onCoordinateChange(boolean users, int unitID, float newCoordinate)
    {
        //System.out.println("(onCoordinateChange) dir " + users + " id " + unitID + " new coordinate " + newCoordinate);
        try
        {
            findUnitToId(unitID).setPos(newCoordinate * 2f);
        }catch (NullPointerException c)
        {
            System.out.println("(!onCoordinateChange) There is no such unit for ID " + unitID + " dir " + users + " new coordinate" + newCoordinate);
        }
    }

    @Override
    public void onHealthChange(boolean users, int unitID, float health)
    {
        System.out.println("(onHealthChange) dir " + users + " id " + unitID + " health " + health);
        try
        {
            findUnitToId(unitID).setHealth(health);
        }catch (NullPointerException c)
        {
            System.out.println("(!onHealthChange) There is no such unit for ID " + unitID + " dir " + users + " health " + health);
        }
    }

    @Override
    public void onEnemyUnitCreate(UnitType type, int id) throws NotEnoughMonyException {
        setUnit(type, false, id);
    }

    @Override
    public void onForpostDestroy(boolean userWin)
    {
        gui.setGameEndMenu(userWin);
    }

    @Override
    public void onEnemyEvolveStageChange(StageOfEvolution newStage)
    {

    }

    @Override
    public void onManyCountChange(int value)
    {
        gui.updateMoney(value);
    }

    @Override
    public void onScoreChange(int value)
    {
        gui.updateScore(value);
    }
}
