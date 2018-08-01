package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.controller.GameController;
import com.controller.UnitType;
import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.GameForpost;
import com.model.StageOfEvolution;
import com.model.UserForpost;


public class GameGUI extends Stage implements InputProcessor
{
    private final Image hBarU;
    private final Image hBarG;
    private Label moneyL;
    private Label unitsL;
    private Label enemyL;
    private Label expL;
    private Label yourBaseL;
    private Label enemyBaseL;
    private final Label unitCost_1L;
    private final Label unitCost_2L;
    private final Label unitCost_3L;
    private final Label unitCost_4L;
    private final Label unitCost_5L;
    private Button menuB;
    private Button evolveB;
    private Button shieldB;
    private Music bgMusic;
    private GameController gameController;

    GameGUI (final GameController gameController)
    {
        super(new FitViewport(Resources.WORLD_WIDTH, Resources.WORLD_HEIGHT));

        this.gameController = gameController;
        
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(Resources.game1MusicPath));
        bgMusic.setLooping(true);
        bgMusic.setVolume(Resources.OPTIONS.getFloat("Music Volume"));
        bgMusic.play();

        Button.ButtonStyle bs = new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("unit_cell_s1"),
                Resources.guiSkin.getDrawable("unit_cell_s2"),
                Resources.guiSkin.getDrawable("unit_cell_s1"));

        Image bgBot = new Image(Resources.guiSkin.getDrawable("bg_gui_bot"));
        bgBot.setBounds(0,0, Resources.WORLD_WIDTH, 50);
        addActor(bgBot);

        moneyL = new Label("| Money: " + Resources.OPTIONS.getInteger("Start Money") + " |", Resources.simpleLSWhite);
        moneyL.setPosition(25, 28);
        addActor(moneyL);

        expL = new Label("| Experience: 0 |", Resources.simpleLSWhite);
        expL.setPosition(25, 8);
        addActor(expL);

        unitsL = new Label("| Units: 0 |", Resources.simpleLSWhite);
        unitsL.setPosition((moneyL.getWidth() > expL.getWidth()) ? moneyL.getX() + moneyL.getWidth() : expL.getX() + expL.getWidth() + 5, 28);
        addActor(unitsL);

        enemyL = new Label("| Enemy: 0 |", Resources.simpleLSWhite);
        enemyL.setPosition(unitsL.getX(), 8);
        addActor(enemyL);

        yourBaseL = new Label("| Your Base Health: " + UserForpost.USER_FORPOST_BASE_HEALTH + " / " + UserForpost.USER_FORPOST_BASE_HEALTH + " |", Resources.simpleLSWhite);
        yourBaseL.setPosition(unitsL.getX() + unitsL.getWidth() + 5, 28);
        addActor(yourBaseL);

        enemyBaseL = new Label("| Enemy Base Health: " + GameForpost.NPC_FORPOST_BASE_HEALTH + " / " + UserForpost.USER_FORPOST_BASE_HEALTH + " |", Resources.simpleLSWhite);
        enemyBaseL.setPosition(yourBaseL.getX(), 8);
        addActor(enemyBaseL);

        Group unitsG = new Group();

        Label unitName_1L = new Label("Archer", Resources.simpleLSBlack);
        unitName_1L.setPosition( 0, Resources.WORLD_HEIGHT - 120);
        unitsG.addActor(unitName_1L);
        unitCost_1L = new Label("Cost: " + gameController.getUnitPrice(UnitType.ARCHER), Resources.simpleLSBlack);
        unitCost_1L.setPosition( 0, Resources.WORLD_HEIGHT - 135);
        unitsG.addActor(unitCost_1L);

        Button setUnit_1B = new Button(bs);
        setUnit_1B.setPosition(0, Resources.WORLD_HEIGHT - 100);
        setUnit_1B.setSize(100, 100);
        setUnit_1B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(UnitType.ARCHER, true, 0);
                        updateLabels();
                        System.out.println("UNIT 1");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!Money!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_1B);

        Label unitName_2L = new Label("Warrior", Resources.simpleLSBlack);
        unitName_2L.setPosition( 110, Resources.WORLD_HEIGHT - 120);
        unitsG.addActor(unitName_2L);
        unitCost_2L = new Label("Cost: " + gameController.getUnitPrice(UnitType.INFATRYMAN), Resources.simpleLSBlack);
        unitCost_2L.setPosition( 110, Resources.WORLD_HEIGHT - 135);
        unitsG.addActor(unitCost_2L);

        Button setUnit_2B = new Button(bs);
        setUnit_2B.setPosition(110, Resources.WORLD_HEIGHT - 100);
        setUnit_2B.setSize(100, 100);
        setUnit_2B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(UnitType.INFATRYMAN, true, 0);
                        updateLabels();
                        System.out.println("UNIT 2");
                    } catch (NotEnoughMonyException e) {
                        System.out.println("!!!!!!!!!!!!Money!!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_2B);

        Label unitName_3L = new Label("Fat", Resources.simpleLSBlack);
        unitName_3L.setPosition( 220, Resources.WORLD_HEIGHT - 120);
        unitsG.addActor(unitName_3L);
        unitCost_3L = new Label("Cost: " + gameController.getUnitPrice(UnitType.FAT), Resources.simpleLSBlack);
        unitCost_3L.setPosition( 220, Resources.WORLD_HEIGHT - 135);
        unitsG.addActor(unitCost_3L);

        Button setUnit_3B = new Button(bs);
        setUnit_3B.setPosition(220, Resources.WORLD_HEIGHT - 100);
        setUnit_3B.setSize(100, 100);
        setUnit_3B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(UnitType.FAT,true, 0);
                        updateLabels();
                        System.out.println("UNIT 3");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!Money!!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_3B);

        Label unitName_4L = new Label("Rider", Resources.simpleLSBlack);
        unitName_4L.setPosition( 330, Resources.WORLD_HEIGHT - 120);
        unitsG.addActor(unitName_4L);
        unitCost_4L = new Label("Cost: " + gameController.getUnitPrice(UnitType.RIDER), Resources.simpleLSBlack);
        unitCost_4L.setPosition( 330, Resources.WORLD_HEIGHT - 135);
        unitsG.addActor(unitCost_4L);

        Button setUnit_4B = new Button(bs);
        setUnit_4B.setPosition(330, Resources.WORLD_HEIGHT - 100);
        setUnit_4B.setSize(100, 100);
        setUnit_4B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(UnitType.RIDER, true, 0);
                        updateLabels();
                        System.out.println("UNIT 4");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!Money!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_4B);

        Label unitName_5L = new Label("Incredible", Resources.simpleLSBlack);
        unitName_5L.setPosition( 440, Resources.WORLD_HEIGHT - 120);
        unitsG.addActor(unitName_5L);
        unitCost_5L = new Label("Cost: " + gameController.getUnitPrice(UnitType.INCREDIBLE), Resources.simpleLSBlack);
        unitCost_5L.setPosition( 440, Resources.WORLD_HEIGHT - 135);
        unitsG.addActor(unitCost_5L);

        Button setUnit_5B = new Button(bs);
        setUnit_5B.setPosition(440, Resources.WORLD_HEIGHT - 100);
        setUnit_5B.setSize(100, 100);
        setUnit_5B.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try
                    {
                        GameScreen.setUnit(UnitType.INCREDIBLE,true, 0);
                        updateLabels();
                        System.out.println("UNIT 5");
                    } catch (NotEnoughMonyException e)
                    {
                        System.out.println("!!!!!!!!!!!!!Money!!!!!!!!!!!!!!");
                    }
            }
        });
        unitsG.addActor(setUnit_5B);

//        Button setUnit_6B = new Button(bs);
//        setUnit_6B.setPosition(550, Resources.WORLD_HEIGHT - 100);
//        setUnit_6B.setSize(100, 100);
//        setUnit_6B.addListener(new ClickListener()
//        {
//            @Override
//            public void clicked(InputEvent event, float x, float y)
//            {
//                if (Resources.state == State.GAME)
//                    GameScreen.setArrow(1, 100, 100, 1000, 100);
//            }
//        });
//        unitsG.addActor(setUnit_6B);
        addActor(unitsG);

        menuB = new Button(new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("menu_b1"),
                Resources.guiSkin.getDrawable("menu_b2"),
                Resources.guiSkin.getDrawable("menu_b1")));
        menuB.setPosition(Resources.WORLD_WIDTH - 200,0);
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


        evolveB = new Button(new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("evolve_b1"),
                Resources.guiSkin.getDrawable("evolve_b2"),
                Resources.guiSkin.getDrawable("evolve_b1")));
        evolveB.setPosition(0,bgBot.getHeight());
        evolveB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    try {
                        gameController.evolve(true);
                        //gameController.addScore(-200);
                        updateCost();
                        evolveB.setVisible(false);
                    } catch (LimitOfEvolutionException e) {
                        e.printStackTrace();
                    }
            }
        });
        evolveB.setVisible(false);
        addActor(evolveB);

        shieldB = new Button(new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("shield_b1"),
                Resources.guiSkin.getDrawable("shield_b2"),
                Resources.guiSkin.getDrawable("shield_b1")));
        shieldB.setBounds(Resources.WORLD_WIDTH - 110, Resources.WORLD_HEIGHT - 130, 100,120);
        shieldB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if (Resources.state == State.GAME)
                    GameScreen.setShield(true);
            }
        });
        //shieldB.setVisible(false);
        addActor(shieldB);

        hBarU = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBarU.setPosition(enemyBaseL.getX() + enemyBaseL.getWidth() + 25, 30);
        hBarU.setSize((menuB.getX() - hBarU.getX() - 20) * GameScreen.getUserForpost().getHealth()/GameScreen.getUserForpost().getMaxHealth(), 10);
        addActor(hBarU);
        hBarG = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBarG.setPosition(enemyBaseL.getX() + enemyBaseL.getWidth() + 25, 10);
        hBarG.setSize((menuB.getX() - hBarG.getX() - 20) * GameScreen.getGameForpost().getHealth()/GameScreen.getGameForpost().getMaxHealth(), 10);
        addActor(hBarG);
    }

    private void updateCost()
    {
        unitCost_1L.setText("Cost: " + gameController.getUnitPrice(UnitType.ARCHER));
        unitCost_2L.setText("Cost: " + gameController.getUnitPrice(UnitType.INFATRYMAN));
        unitCost_3L.setText("Cost: " + gameController.getUnitPrice(UnitType.FAT));
        unitCost_4L.setText("Cost: " + gameController.getUnitPrice(UnitType.RIDER));
        unitCost_5L.setText("Cost: " + gameController.getUnitPrice(UnitType.INCREDIBLE));
    }

    void updateMoney(int value)
    {
        moneyL.setText("| Money: " + value + " |");
    }

    void updateScore(int value)
    {
        expL.setText("| Experience: " + value + " |");
    }

    void updateLabels()
    {
        unitsL.setPosition((moneyL.getPrefWidth() > expL.getPrefWidth()) ? moneyL.getX() + moneyL.getPrefWidth() : expL.getX() + expL.getPrefWidth() + 5, 28);
        //TODO удалить всё это
        //unitsL.setText("| Units: " + gameController.getUserArmyCount() + " |");
        enemyL.setPosition(unitsL.getX(), 8);
        //enemyL.setText("| Enemy: " + gameController.getGameArmyCount() + " |");
        //todo переместить в updateScore()
        //if (gameController.getTotalScore() >= 200 && !evolveB.isVisible() && gameController.getUserEvolveStage() != StageOfEvolution.FOURTH)
            //evolveB.setVisible(true);
        //if (gameController.getTotalScore() < 200 && evolveB.isVisible())
            //evolveB.setVisible(false);

        yourBaseL.setPosition(unitsL.getX() + unitsL.getPrefWidth() + 5, 28);
        enemyBaseL.setPosition(yourBaseL.getX(), 8);
        if (GameScreen.getUserForpost().getHealth() > 0)
            yourBaseL.setText("| Your Base Health: " + ((int) GameScreen.getUserForpost().getHealth()) + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        else
            yourBaseL.setText("| Your Base Health: 0 |");
        if (GameScreen.getGameForpost().getHealth() > 0)
            enemyBaseL.setText("| Enemy Base Health: " + ((int) GameScreen.getGameForpost().getHealth()) + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
        else
            enemyBaseL.setText("| Enemy Base Health: 0 |");
    }

    void updateBaseHealth()
    {
        if (GameScreen.getGameForpost().getHealth() > 0)
        {
            hBarG.setSize((menuB.getX() - hBarG.getX() - 20) * GameScreen.getGameForpost().getHealth() / GameScreen.getGameForpost().getMaxHealth(), 10);
            if (GameScreen.getGameForpost().getHealth() <= GameScreen.getGameForpost().getMaxHealth() * 0.6f)
                hBarG.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
            if (GameScreen.getGameForpost().getHealth() <= GameScreen.getGameForpost().getMaxHealth() * 0.3f)
                hBarG.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));
            if (GameScreen.getGameForpost().getHealth() > GameScreen.getGameForpost().getMaxHealth() * 0.6f)
                hBarG.setDrawable(Resources.guiSkin.getDrawable("hBar_green"));
        }
        if (GameScreen.getUserForpost().getHealth() > 0)
        {
            hBarU.setSize((menuB.getX() - hBarU.getX() - 20) * GameScreen.getUserForpost().getHealth() / GameScreen.getUserForpost().getMaxHealth(), 10);
            if (GameScreen.getUserForpost().getHealth() <= GameScreen.getUserForpost().getMaxHealth() * 0.6f)
                hBarU.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
            if (GameScreen.getUserForpost().getHealth() <= GameScreen.getUserForpost().getMaxHealth() * 0.3f)
                hBarU.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));
            if (GameScreen.getUserForpost().getHealth() > GameScreen.getUserForpost().getMaxHealth() * 0.6f)
                hBarU.setDrawable(Resources.guiSkin.getDrawable("hBar_green"));
        }
    }

    void setWindowMenu()
    {
        Resources.state = State.PAUSE;
        gameController.pause();

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.WORLD_WIDTH_2 - 150, Resources.WORLD_HEIGHT_2 -215);

        final Label l = new Label("Menu", Resources.simpleLSWhite);
        l.setPosition(Resources.WORLD_WIDTH_2 - l.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_medium);
        toMenuB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 - 200);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                bgMusic.stop();
                bgMusic.dispose();
                GameScreen.exit();
                gameController.restart();
                Resources.game.setScreen(new LoadScreen(ScreenType.MainMenu));
                dispose();
            }
        });
        final TextButton optionsB = new TextButton("Options", Resources.tbs_medium);
        optionsB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 - 100);
        optionsB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setWindowOptions();
            }
        });

        final TextButton resetB = new TextButton("Reset Level", Resources.tbs_medium);

        final TextButton continueB = new TextButton("Continue", Resources.tbs_medium);
        continueB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 100);
        continueB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                Resources.state = State.GAME;
                gameController.resume();
            }
        });
        resetB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2);
        resetB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                GameScreen.clear();
                gameController.restart();
                gameController.start();
                Resources.state = State.GAME;
                updateCost();
            }
        });

        window.addActor(bg);
        window.addActor(l);
        window.addActor(continueB);
        window.addActor(toMenuB);
        window.addActor(optionsB);
        window.addActor(resetB);
        addActor(window);
    }

    void setGameEndMenu(boolean win)
    {
        Resources.state = State.PAUSE;
        gameController.pause();

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.WORLD_WIDTH_2 - 150, Resources.WORLD_HEIGHT_2 - 215);

        final Label l;
        if (win)
        {
            if (Resources.currentLvL == 1)
            {
                Resources.OPTIONS.putBoolean("Lvl 2 key", true);
                Resources.OPTIONS.flush();
            }
            if (Resources.currentLvL == 2)
            {
                Resources.OPTIONS.putBoolean("Lvl 3 key", true);
                Resources.OPTIONS.flush();
            }
            l = new Label("Congratulation!!!", Resources.simpleLSWhite);
            l.setPosition(Resources.WORLD_WIDTH_2 - l.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);
            enemyBaseL.setText("| Enemy Base Health: " + 0 + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
        }
        else
        {
            l = new Label("You Lose(((", Resources.simpleLSWhite);
            l.setPosition(Resources.WORLD_WIDTH_2 - l.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);
            yourBaseL.setText("| Your Base Health: " + 0 + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        }

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_medium);
        toMenuB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 - 100);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                bgMusic.stop();
                bgMusic.dispose();
                GameScreen.exit();
                gameController.restart();
                Resources.game.setScreen(new LoadScreen(ScreenType.MainMenu));
                dispose();
            }
        });

        final TextButton continueB = new TextButton("Continue", Resources.tbs_medium);
        continueB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 100);
        continueB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                window.addAction(Actions.removeActor());

                if (Resources.currentLvL == 1)
                    Resources.currentLvL = 2;
                if (Resources.currentLvL == 2)
                    Resources.currentLvL = 3;

                GameScreen.clear();
                gameController.restart();
                gameController.start();
                Resources.state = State.GAME;
                updateCost();
            }
        });
        if (!win)
            continueB.setVisible(false);

        final TextButton resetB = new TextButton("Reset Level", Resources.tbs_medium);
        resetB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2);
        resetB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                GameScreen.clear();
                gameController.restart();
                gameController.start();
                Resources.state = State.GAME;
                updateCost();
            }
        });

        window.addActor(bg);
        window.addActor(l);
        window.addActor(continueB);
        window.addActor(resetB);
        window.addActor(toMenuB);
        addActor(window);
    }

    private void  setWindowOptions()
    {
        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.WORLD_WIDTH_2 - 150, Resources.WORLD_HEIGHT_2 -215);

        final Label optionsL = new Label("Options", Resources.simpleLSWhite);
        optionsL.setPosition(Resources.WORLD_WIDTH_2 - optionsL.getPrefWidth()/2, Resources.WORLD_HEIGHT_2 + 155);

        final Label musicL = new Label("Music volume", Resources.simpleLSWhite);
        musicL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 100);

        Slider.SliderStyle slider = new Slider.SliderStyle(Resources.guiSkin.getDrawable("slide_line"),
                Resources.guiSkin.getDrawable("slide_point"));
        final Slider musicS = new Slider(0f, 1f, 0.1f  , false, slider);
        musicS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 85);
        musicS.setSize(250 , musicS.getHeight());
        musicS.setValue(Resources.OPTIONS.getFloat("Music Volume"));
        musicS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Resources.OPTIONS.putFloat("Music Volume", musicS.getValue());
                Resources.OPTIONS.flush();
                bgMusic.setVolume(Resources.OPTIONS.getFloat("Music Volume"));
            }
        });

        final Label effectL = new Label("Effects volume", Resources.simpleLSWhite);
        effectL.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 50);

        final Slider effectS = new Slider(0f, 1f, 0.1f  , false, slider);
        effectS.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 + 35);
        effectS.setSize(250 , effectS.getHeight());
        effectS.setValue(Resources.OPTIONS.getFloat("Effect Volume"));
        effectS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Resources.OPTIONS.putFloat("Music Volume", effectS.getValue());
                Resources.OPTIONS.flush();
            }
        });

        final TextButton returnB = new TextButton("Return", Resources.tbs_medium);
        returnB.setPosition(Resources.WORLD_WIDTH_2 - 125, Resources.WORLD_HEIGHT_2 - 200);
        returnB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());
            }
        });

        window.addActor(bg);
        window.addActor(optionsL);
        window.addActor(musicL);
        window.addActor(musicS);
        window.addActor(effectL);
        window.addActor(effectS);
        window.addActor(returnB);

        addActor(window);
    }

    void setWindowExit()
    {
        Resources.state = State.PAUSE;
        gameController.pause();

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("exit_window_bg"));
        bg.setPosition((Resources.WORLD_WIDTH_2)-150, (Resources.WORLD_HEIGHT_2)-15);

        final Label l = new Label("Are you sure you want to exit?", Resources.simpleLSWhite);
        l.setPosition((Resources.WORLD_WIDTH_2)-l.getPrefWidth()/2, (Resources.WORLD_HEIGHT_2)+55);

        final TextButton bYes = new TextButton("Yes", Resources.tbs_small);
        bYes.setPosition((Resources.WORLD_WIDTH_2)-125, (Resources.WORLD_HEIGHT_2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", Resources.tbs_small);
        bNo.setPosition((Resources.WORLD_WIDTH_2)+25, (Resources.WORLD_HEIGHT_2));
        bNo.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                Resources.state = State.GAME;
                gameController.resume();
            }
        });

        window.addActor(bg);
        window.addActor(l);
        window.addActor(bYes);
        window.addActor(bNo);
        addActor(window);
    }

    @Override
    public boolean keyDown(int keyCode)
    {
        if (Resources.state == State.GAME && (keyCode == Input.Keys.MENU || keyCode == Input.Keys.ESCAPE))
            setWindowMenu();
        return super.keyDown(keyCode);
    }
}
