package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;

public class Unit extends Actor
{
    private Animation animation;
    private CharacterType type;
    private int direction;
    private float stateTime;
    private TextureRegion currentFrame;
    private Image hBar;
    private Label levelL;
    private Character character;


    Unit (CharacterType type) throws NotEnoughMonyException
    {
        character = CharacterController.createNewCharacter(type);

        setBounds(character.getPosition(), 50, 240, 288);

        animation = Resources.testAnimationR;

        this.type = type;
        this.direction = 1;

        levelL = new Label( character.getStage().toString() + " " + type, new Label.LabelStyle(Resources.game.font, Color.WHITE));
        levelL.setPosition(getX() - 5, getY() + getHeight() + 20);

        hBar = new Image(Resources.GUI_SKIN.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);
    }

    Unit(CharacterType type, Character c) throws  NotEnoughMonyException
    {
        this.type = type;
        this.character = c;
        setBounds(character.getPosition(), 50, 240, 288);

        animation = Resources.testAnimationL;

        this.type = type;
        this.direction = -1;

        levelL = new Label( character.getStage() + " " + type, new Label.LabelStyle(Resources.game.font, Color.WHITE));
        levelL.setPosition(getX(), getY() + getHeight() + 35);

        hBar = new Image(Resources.GUI_SKIN.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

        addAction(Actions.moveTo( character.getPosition()*2f, 50, 1 / (Gdx.graphics.getFramesPerSecond() + 1)));

        levelL.setPosition(getX(), getY() + getHeight() + 35);

        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth() / character.getMaxHealth(), 10);
        if (character.getHealth() <= character.getMaxHealth() * 0.6f)
            hBar.setDrawable(Resources.GUI_SKIN.getDrawable("hBar_yellow"));
        if (character.getHealth() <= character.getMaxHealth() * 0.3f)
            hBar.setDrawable(Resources.GUI_SKIN.getDrawable("hBar_red"));

        batch.draw(currentFrame, getX(), getY());
        levelL.draw(batch, parentAlpha);
        hBar.draw(batch,parentAlpha);

    }


    @Override
    public void act(float delta)
    {
        super.act(delta);
    }

    CharacterType getType() {return type;}

    int getDirection() {return direction;}

    public Character getCharacter(){return character;}
}
