package com.mygdx.graphics;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.CharacterType;


public class GameGUI extends Stage implements InputProcessor
{
    private static Start game;
    private Label moneyL;
    private Label unitsL;
    private Label enemyL;

    GameGUI (final Start game, final GameScreen gs)
    {
        super(new FitViewport(Resources.width, Resources.height));

        GameGUI.game = game;

        Button.ButtonStyle bs = new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("unit_cell_s1"),
                Resources.guiSkin.getDrawable("unit_cell_s2"),
                Resources.guiSkin.getDrawable("unit_cell_s1"));

        Image bgBot = new Image(Resources.guiSkin.getDrawable("bg_gui_bot"));
        bgBot.setBounds(0,0, Resources.width, 50);
        addActor(bgBot);

        moneyL = new Label("| Money: " + CharacterController.getTotalMoney() + " |", new Label.LabelStyle(game.font, Color.WHITE));
        moneyL.setPosition(25, 15);
        addActor(moneyL);

        unitsL = new Label("| Units: " + CharacterController.getUserArmyCount() + " |", new Label.LabelStyle(game.font, Color.WHITE));
        unitsL.setPosition(moneyL.getWidth() + 50, 15);
        addActor(unitsL);

        enemyL = new Label("| Enemy: " + CharacterController.getGameArmyCount() + " |", new Label.LabelStyle(game.font, Color.WHITE));
        enemyL.setPosition(moneyL.getWidth() + unitsL.getWidth() + 75, 15);
        addActor(enemyL);

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
                        updateLabels();
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
                        updateLabels();
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
                    try
                    {
                        GameScreen.setUnit(CharacterType.ARCHER);
                        updateLabels();
                        System.out.println("UNIT 3");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
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
                    try
                    {
                        GameScreen.setUnit(CharacterType.ARCHER);
                        updateLabels();
                        System.out.println("UNIT 4");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_4B);
        addActor(unitsG);

        Button menuB = new Button(new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("menu_b1"),
                Resources.guiSkin.getDrawable("menu_b2"),
                Resources.guiSkin.getDrawable("menu_b1")));
        menuB.setPosition(Resources.width - 200,0);
        menuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    setWindowMenu();
            }
        });
        addActor(menuB);
    }

    public void updateLabels()
    {
        moneyL.setText("| Money: " + CharacterController.getTotalMoney() + " |");
        unitsL.setText("| Units: " + CharacterController.getUserArmyCount() + " |");
        enemyL.setText("| Enemy: " + CharacterController.getGameArmyCount() + " |");
    }

    private void setWindowMenu()
    {
        Resources.state = State.PAUSE;

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.width2-150, Resources.height2-215);

        final Label l = new Label("Menu", new Label.LabelStyle(game.font, Color.WHITE));
        l.setPosition(Resources.width2-l.getPrefWidth()/2, Resources.height2+155);

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_m);
        toMenuB.setPosition(Resources.width2-125, Resources.height2-100);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MainMenu(game));
            }
        });
        final TextButton settingsB = new TextButton("Settings", Resources.tbs_m);
        settingsB.setPosition(Resources.width2-125, Resources.height2);
        settingsB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

            }
        });
        final TextButton continueB = new TextButton("Continue", Resources.tbs_m);
        continueB.setPosition(Resources.width2-125, Resources.height2+100);
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
