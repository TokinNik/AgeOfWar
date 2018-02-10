package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.CharacterType;


public class GameGUI extends Stage implements InputProcessor
{
    private static Skin skin;
    private static Start game;

    GameGUI (final Start game, final GameScreen gs)
    {
        super(new FitViewport(Resources.width, Resources.height));

        GameGUI.game = game;

        skin = new Skin();
        skin.addRegions(Resources.guiAtlas);

        Button.ButtonStyle bs = new Button.ButtonStyle(
                skin.getDrawable("unit_cell_s1"),
                skin.getDrawable("unit_cell_s2"),
                skin.getDrawable("unit_cell_s1"));
        

        Button exitB = new Button(new Button.ButtonStyle(
                skin.getDrawable("exitButton2"),
                skin.getDrawable("exitButton3"),
                skin.getDrawable("exitButton2")));
        exitB.setPosition(0,0);
        exitB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    setWindowMenu();
            }
        });
        addActor(exitB);

        Group unitsG = new Group();

        Button setUnit_1B = new Button(bs);
        setUnit_1B.setPosition(0, Resources.height - 100);
        setUnit_1B.setSize(100, 100);
        setUnit_1B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(CharacterType.ARCHER);
                        System.out.println("UNIT 1");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_1B);

        Button setUnit_2B = new Button(bs);
        setUnit_2B.setPosition(110, Resources.height - 100);
        setUnit_2B.setSize(100, 100);
        setUnit_2B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setCompUnit(CharacterType.ARCHER, CharacterController.createNewCharacter(CharacterType.ARCHER));
                        System.out.println("UNIT 2");
                    } catch (NotEnoughMonyException e) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_2B);

        Button setUnit_3B = new Button(bs);
        setUnit_3B.setPosition(220, Resources.height - 100);
        setUnit_3B.setSize(100, 100);
        setUnit_3B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    System.out.println("UNIT 3");
                    //GameScreen.setUnit(CharacterType);
            }
        });
        unitsG.addActor(setUnit_3B);

        Button setUnit_4B = new Button(bs);
        setUnit_4B.setPosition(330, Resources.height - 100);
        setUnit_4B.setSize(100, 100);
        setUnit_4B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    System.out.println("UNIT 4");
                    //GameScreen.setUnit(CharacterType);
            }
        });
        unitsG.addActor(setUnit_4B);

        addActor(unitsG);
    }

    private void setWindowMenu()
    {
        Resources.state = State.PAUSE;

        final Image bg = new Image(skin.getDrawable("menu_bg_1"));
        bg.setPosition((Gdx.graphics.getWidth()/2)-150, (Gdx.graphics.getHeight()/2)-215);

        final Label l = new Label("Menu", new Label.LabelStyle(game.font, Color.WHITE));
        l.setPosition((Gdx.graphics.getWidth()/2)-l.getPrefWidth()/2, (Gdx.graphics.getHeight()/2)+155);

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_m);
        toMenuB.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2)-100);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MainMenu(game));
            }
        });
        final TextButton settingsB = new TextButton("Settings", Resources.tbs_m);
        settingsB.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2));
        settingsB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

            }
        });
        final TextButton continueB = new TextButton("Continue", Resources.tbs_m);
        continueB.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2)+100);
        continueB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                continueB.setVisible(false);
                settingsB.setVisible(false);
                toMenuB.setVisible(false);
                l.setVisible(false);
                bg.setVisible(false);
                Resources.state = State.GAME;
            }
        });

        addActor(bg);
        addActor(l);
        addActor(continueB);
        addActor(toMenuB);
        addActor(settingsB);
    }

    @Override
    public boolean keyDown(int keyCode)
    {
        switch (keyCode)
        {
            case Input.Keys.ESCAPE:
                if (Resources.state == State.GAME)
                    setWindowMenu();
                return super.keyDown(keyCode);

            default:
                return super.keyDown(keyCode);
        }
    }
}
