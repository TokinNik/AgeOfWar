package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by TNR on 02.02.2018.
 */

public class MainMenuGUI extends Stage
{
    public static TextureAtlas atlas;
    public static Skin skin;
    public  static Start game;

    public MainMenuGUI (final Start game)
    {
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        this.game = game;

        atlas = new TextureAtlas("android/assets/gui/gui.atlas");

        skin = new Skin();
        skin.addRegions(atlas);

        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = skin.getDrawable("exitButton2");
        bs.down = skin.getDrawable("exitButton3");
        Button b = new Button(bs);
        b.setPosition(10,10);
        b.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setWindowExit();
            }
        });

        addActor(b);
    }

    private void setWindowExit()
    {
        final Image bg = new Image(skin.getDrawable("exit_window_bg"));
        bg.setPosition((Gdx.graphics.getWidth()/2)-150, (Gdx.graphics.getHeight()/2)-15);

        final Label l = new Label("Are you sure you want to exit?", new Label.LabelStyle(game.font, Color.WHITE));
        l.setPosition((Gdx.graphics.getWidth()/2)-l.getPrefWidth()/2, (Gdx.graphics.getHeight()/2)+55);

        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("text_button_s1");
        tbs.down = skin.getDrawable("text_button_s2");
        tbs.font = game.font;
        final TextButton bYes = new TextButton("Yes", tbs);
        bYes.setPosition((Gdx.graphics.getWidth()/2)-125, (Gdx.graphics.getHeight()/2));
        bYes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
        final TextButton bNo = new TextButton("No", tbs);
        bNo.setPosition((Gdx.graphics.getWidth()/2)+25, (Gdx.graphics.getHeight()/2));
        bNo.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                bYes.setVisible(false);
                bNo.setVisible(false);
                l.setVisible(false);
                bg.setVisible(false);
            }
        });

        addActor(bg);
        addActor(l);
        addActor(bYes);
        addActor(bNo);
    }
}
