package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private static TextureAtlas atlas;
    private static Skin skin;
    private static Start game;

    GameGUI (final Start game, final GameScreen gs)
    {
        super(new FitViewport(Resourses.width, Resourses.height));

        GameGUI.game = game;

        atlas = new TextureAtlas("android/assets/gui/gui.atlas");

        skin = new Skin();
        skin.addRegions(atlas);

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
                if (Resourses.state == State.GAME)
                    setWindowMenu();
            }
        });
        addActor(exitB);

        Button setUnit_1B = new Button(bs);
        setUnit_1B.setPosition(0, Resourses.height - 100);
        setUnit_1B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resourses.state == State.GAME)
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
        addActor(setUnit_1B);

        Button setUnit_2B = new Button(bs);
        setUnit_2B.setPosition(110, Resourses.height - 100);
        setUnit_2B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resourses.state == State.GAME)
                    try
                    {
                        GameScreen.setCompUnit(CharacterType.ARCHER, CharacterController.createNewCharacter(CharacterType.ARCHER));
                        System.out.println("UNIT 2");
                    } catch (NotEnoughMonyException e) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
            }
        });
        addActor(setUnit_2B);

        Button setUnit_3B = new Button(bs);
        setUnit_3B.setPosition(220, Resourses.height - 100);
        setUnit_3B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resourses.state == State.GAME)
                    System.out.println("UNIT 3");
                    //GameScreen.setUnit(CharacterType);
            }
        });
        addActor(setUnit_3B);

        Button setUnit_4B = new Button(bs);
        setUnit_4B.setPosition(330, Resourses.height - 100);
        setUnit_4B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resourses.state == State.GAME)
                    System.out.println("UNIT 4");
                    //GameScreen.setUnit(CharacterType);
            }
        });
        addActor(setUnit_4B);


    }

    private void setWindowMenu()
    {
        Resourses.state = State.PAUSE;

        final Image bg = new Image(skin.getDrawable("menu_bg_1"));
        bg.setPosition((Gdx.graphics.getWidth()/2)-150, (Gdx.graphics.getHeight()/2)-215);

        final Label l = new Label("Menu", new Label.LabelStyle(game.font, Color.WHITE));
        l.setPosition((Gdx.graphics.getWidth()/2)-l.getPrefWidth()/2, (Gdx.graphics.getHeight()/2)+155);

        final TextButton toMenuB = new TextButton("Exit to Menu", Resourses.tbs_m);
        toMenuB.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2)-100);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MainMenu(game));
            }
        });
        final TextButton settingsB = new TextButton("Settings", Resourses.tbs_m);
        settingsB.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2));
        settingsB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

            }
        });
        final TextButton continueB = new TextButton("Continue", Resourses.tbs_m);
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
                Resourses.state = State.GAME;
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
                if (Resourses.state == State.GAME)
                    setWindowMenu();
                return super.keyDown(keyCode);

            default:
                return super.keyDown(keyCode);
        }
    }
}
