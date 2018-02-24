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
    private final Image hBarU;
    private final Image hBarG;
    private Label moneyL;
    private Label unitsL;
    private Label enemyL;
    private Label expL;
    private Label yourBaseL;
    private Label enemyBaseL;
    private Button menuB;

    GameGUI (final Start game)
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

        expL = new Label("| Experience: " + 0 + " |", new Label.LabelStyle(game.font, Color.WHITE));
        expL.setPosition(enemyL.getX() + enemyL.getWidth() + 50, 15);
        addActor(expL);

        yourBaseL = new Label("| Your Base Health: " + GameScreen.getUserForpost().getHealth() + " / " + GameScreen.getUserForpost().getMaxHealth() + " |", new Label.LabelStyle(game.font, Color.WHITE));
        yourBaseL.setPosition(expL.getX() + expL.getWidth() + 25, 28);
        addActor(yourBaseL);

        enemyBaseL = new Label("| Enemy Base Health: " + GameScreen.getGameForpost().getHealth() + " / " + GameScreen.getGameForpost().getMaxHealth() + " |", new Label.LabelStyle(game.font, Color.WHITE));
        enemyBaseL.setPosition(yourBaseL.getX(), 8);
        addActor(enemyBaseL);

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
                        System.out.println("!!!!!!!!!!!!!Money!!!!!!!!!!!!!!");
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
                        GameScreen.setUnit(CharacterType.INFATRYMAN);
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



        menuB = new Button(new Button.ButtonStyle(
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

        hBarU = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBarU.setPosition(enemyBaseL.getX() + enemyBaseL.getWidth() + 25, 30);
        hBarU.setSize((menuB.getX() - hBarU.getX() - 20) * GameScreen.getUserForpost().getHealth()/GameScreen.getUserForpost().getMaxHealth(), 10);
        addActor(hBarU);
        hBarG = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBarG.setPosition(enemyBaseL.getX() + enemyBaseL.getWidth() + 25, 10);
        hBarG.setSize((menuB.getX() - hBarG.getX() - 20) * GameScreen.getGameForpost().getHealth()/GameScreen.getGameForpost().getMaxHealth(), 10);
        addActor(hBarG);
    }

    void updateLabels()
    {
        moneyL.setText("| Money: " + CharacterController.getTotalMoney() + " |");
        unitsL.setText("| Units: " + CharacterController.getUserArmyCount() + " |");
        enemyL.setText("| Enemy: " + CharacterController.getGameArmyCount() + " |");
        expL.setText("| Experience: " + 0 + " |");
        yourBaseL.setText("| Your Base Health: " + GameScreen.getUserForpost().getHealth() + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        enemyBaseL.setText("| Enemy Base Health: " + GameScreen.getGameForpost().getHealth() + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
    }

    void updateBaseHealth()
    {
        hBarG.setSize( (menuB.getX() - hBarG.getX() - 20) * GameScreen.getGameForpost().getHealth() / GameScreen.getGameForpost().getMaxHealth(), 10);
        if (GameScreen.getGameForpost().getHealth() <= GameScreen.getGameForpost().getMaxHealth() * 0.6f)
            hBarG.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
        if (GameScreen.getGameForpost().getHealth() <= GameScreen.getGameForpost().getMaxHealth() * 0.3f)
            hBarG.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));

        hBarU.setSize( (menuB.getX() - hBarU.getX() - 20) * GameScreen.getUserForpost().getHealth() / GameScreen.getUserForpost().getMaxHealth(), 10);
        if (GameScreen.getUserForpost().getHealth() <= GameScreen.getUserForpost().getMaxHealth() * 0.6f)
            hBarU.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
        if (GameScreen.getUserForpost().getHealth() <= GameScreen.getUserForpost().getMaxHealth() * 0.3f)
            hBarU.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));
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

    void setGameEndMenu(boolean win)
    {
        Resources.state = State.PAUSE;

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.width2-150, Resources.height2-215);

        final Label l;
        if (win)
        {
            l = new Label("Congratulation!!!", new Label.LabelStyle(game.font, Color.WHITE));
            l.setPosition(Resources.width2-l.getPrefWidth()/2, Resources.height2+155);
            enemyBaseL.setText("| Enemy Base Health: " + 0 + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
        }
        else
        {
            l = new Label("You Lose(((", new Label.LabelStyle(game.font, Color.WHITE));
            l.setPosition(Resources.width2-l.getPrefWidth()/2, Resources.height2+155);
            yourBaseL.setText("| Your Base Health: " + 0 + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        }

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
        final TextButton resetB = new TextButton("Reset Level", Resources.tbs_m);
        resetB.setPosition(Resources.width2-125, Resources.height2);
        resetB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //Начать уровень заново
            }
        });
        final TextButton continueB = new TextButton("Continue", Resources.tbs_m);
        continueB.setPosition(Resources.width2-125, Resources.height2+100);
        continueB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //Переход на следующий уровень
            }
        });

        addActor(bg);
        addActor(l);
        addActor(continueB);
        addActor(resetB);
        addActor(toMenuB);
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
