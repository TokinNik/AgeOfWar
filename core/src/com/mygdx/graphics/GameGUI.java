package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import com.controller.CharacterController;
import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.CharacterType;


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
    private Button menuB;
    private Button evolveB;
    private Music bgMusic;

    GameGUI ()
    {
        super(new FitViewport(Resources.width, Resources.height));

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("android/assets/music/in_game_1.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(Options.musicVolume);
        bgMusic.play();

        Button.ButtonStyle bs = new Button.ButtonStyle(
                Resources.guiSkin.getDrawable("unit_cell_s1"),
                Resources.guiSkin.getDrawable("unit_cell_s2"),
                Resources.guiSkin.getDrawable("unit_cell_s1"));

        Image bgBot = new Image(Resources.guiSkin.getDrawable("bg_gui_bot"));
        bgBot.setBounds(0,0, Resources.width, 50);
        addActor(bgBot);

        moneyL = new Label("| Money: " + CharacterController.getTotalMoney() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        moneyL.setPosition(25, 15);
        addActor(moneyL);

        unitsL = new Label("| Units: " + CharacterController.getUserArmyCount() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        unitsL.setPosition(moneyL.getWidth() + 50, 15);
        addActor(unitsL);

        enemyL = new Label("| Enemy: " + CharacterController.getGameArmyCount() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        enemyL.setPosition(moneyL.getWidth() + unitsL.getWidth() + 75, 15);
        addActor(enemyL);

        expL = new Label("| Experience: " + CharacterController.getTotalScore() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        expL.setPosition(enemyL.getX() + enemyL.getWidth() + 50, 15);
        addActor(expL);

        yourBaseL = new Label("| Your Base Health: " + GameScreen.getUserForpost().getHealth() + " / " + GameScreen.getUserForpost().getMaxHealth() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        yourBaseL.setPosition(expL.getX() + expL.getWidth() + 25, 28);
        addActor(yourBaseL);

        enemyBaseL = new Label("| Enemy Base Health: " + GameScreen.getGameForpost().getHealth() + " / " + GameScreen.getGameForpost().getMaxHealth() + " |", new Label.LabelStyle(Resources.game.font, Color.WHITE));
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
                        System.out.println("!!!!!!!!!!!!Money!!!!!!!!!!!!!!!");
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
                        System.out.println("!!!!!!!!!!!!Money!!!!!!!!!!!!!!!");
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
                        System.out.println("!!!!!!!!!!!!!Money!!!!!!!!!!!!!!");
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
                        CharacterController.evolve();
                        CharacterController.addTotalScore(-200);
                        evolveB.setVisible(false);
                    } catch (LimitOfEvolutionException e) {
                        e.printStackTrace();
                    }
            }
        });
        evolveB.setVisible(false);
        addActor(evolveB);

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
        expL.setText("| Experience: " + CharacterController.getTotalScore() + " |");

        if (CharacterController.getTotalScore() >= 200 && !evolveB.isVisible())
            evolveB.setVisible(true);
        if (CharacterController.getTotalScore() < 200 && evolveB.isVisible())
            evolveB.setVisible(false);

        if (GameScreen.getUserForpost().getHealth() > 0)
            yourBaseL.setText("| Your Base Health: " + GameScreen.getUserForpost().getHealth() + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        else
            yourBaseL.setText("| Your Base Health: 0 |");
        if (GameScreen.getGameForpost().getHealth() > 0)
            enemyBaseL.setText("| Enemy Base Health: " + GameScreen.getGameForpost().getHealth() + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
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

    private void setWindowMenu()
    {
        Resources.state = State.PAUSE;
        CharacterController.setPause(true);

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.width2 - 150, Resources.height2-215);

        final Label l = new Label("Menu", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        l.setPosition(Resources.width2 - l.getPrefWidth()/2, Resources.height2 + 155);

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_m);
        toMenuB.setPosition(Resources.width2 - 125, Resources.height2 - 200);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                bgMusic.stop();
                bgMusic.dispose();
                GameScreen.exit();
                CharacterController.reset();
                Resources.game.setScreen(new LoadScreen(ScreenType.MainMenu));
                dispose();
            }
        });
        final TextButton optionsB = new TextButton("Options", Resources.tbs_m);
        optionsB.setPosition(Resources.width2 - 125, Resources.height2 - 100);
        optionsB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setWindowOptions();
            }
        });

        final TextButton resetB = new TextButton("Reset Level", Resources.tbs_m);

        final TextButton continueB = new TextButton("Continue", Resources.tbs_m);
        continueB.setPosition(Resources.width2 - 125, Resources.height2 + 100);
        continueB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                Resources.state = State.GAME;
                CharacterController.setPause(false);
                CharacterController.resume();
            }
        });
        resetB.setPosition(Resources.width2 - 125, Resources.height2);
        resetB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                GameScreen.clear();
                CharacterController.reset();
                CharacterController.start();
                Resources.state = State.GAME;
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
        CharacterController.setPause(true);

        final Group window = new Group();

        final Image bg = new Image(Resources.guiSkin.getDrawable("menu_bg_1"));
        bg.setPosition(Resources.width2 - 150, Resources.height2 - 215);

        final Label l;
        if (win)
        {
            l = new Label("Congratulation!!!", new Label.LabelStyle(Resources.game.font, Color.WHITE));
            l.setPosition(Resources.width2 - l.getPrefWidth()/2, Resources.height2 + 155);
            enemyBaseL.setText("| Enemy Base Health: " + 0 + " / " + GameScreen.getGameForpost().getMaxHealth() + " |");
        }
        else
        {
            l = new Label("You Lose(((", new Label.LabelStyle(Resources.game.font, Color.WHITE));
            l.setPosition(Resources.width2 - l.getPrefWidth()/2, Resources.height2 + 155);
            yourBaseL.setText("| Your Base Health: " + 0 + " / " + GameScreen.getUserForpost().getMaxHealth() + " |");
        }

        final TextButton toMenuB = new TextButton("Exit to Menu", Resources.tbs_m);
        toMenuB.setPosition(Resources.width2 - 125, Resources.height2 - 100);
        toMenuB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                bgMusic.stop();
                bgMusic.dispose();
                GameScreen.exit();
                CharacterController.reset();
                Resources.game.setScreen(new LoadScreen(ScreenType.MainMenu));
                dispose();
            }
        });
        final TextButton continueB = new TextButton("Continue", Resources.tbs_m);
        continueB.setPosition(Resources.width2 - 125, Resources.height2 + 100);
        continueB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //Переход на следующий уровень
            }
        });
        final TextButton resetB = new TextButton("Reset Level", Resources.tbs_m);
        resetB.setPosition(Resources.width2 - 125, Resources.height2);
        resetB.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                window.addAction(Actions.removeActor());

                GameScreen.clear();
                CharacterController.reset();
                CharacterController.start();
                Resources.state = State.GAME;
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
        bg.setPosition(Resources.width2 - 150, Resources.height2-215);

        final Label optionsL = new Label("Options", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        optionsL.setPosition(Resources.width2 - optionsL.getPrefWidth()/2, Resources.height2 + 155);

        final Label musicL = new Label("Music volume", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        musicL.setPosition(Resources.width2 - 125, Resources.height2 + 100);

        Slider.SliderStyle slider = new Slider.SliderStyle(Resources.guiSkin.getDrawable("slide_line"),
                Resources.guiSkin.getDrawable("slide_point"));
        final Slider musicS = new Slider(0f, 1f, 0.1f  , false, slider);
        musicS.setPosition(Resources.width2 - 125, Resources.height2 + 85);
        musicS.setSize(250 , musicS.getHeight());
        musicS.setValue(Options.musicVolume);
        musicS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.musicVolume = musicS.getValue();
                bgMusic.setVolume(Options.musicVolume);
            }
        });

        final Label effectL = new Label("Effects volume", new Label.LabelStyle(Resources.game.font, Color.WHITE));
        effectL.setPosition(Resources.width2 - 125, Resources.height2 + 50);

        final Slider effectS = new Slider(0f, 1f, 0.1f  , false, slider);
        effectS.setPosition(Resources.width2 - 125, Resources.height2 + 35);
        effectS.setSize(250 , effectS.getHeight());
        effectS.setValue(Options.effectVolume);
        effectS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Options.musicVolume = effectS.getValue();
            }
        });

        final TextButton returnB = new TextButton("Return", Resources.tbs_m);
        returnB.setPosition(Resources.width2 - 125, Resources.height2 - 200);
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
