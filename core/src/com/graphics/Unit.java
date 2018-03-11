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
import com.badlogic.gdx.utils.TimeUtils;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterState;
import com.model.CharacterType;

import java.util.Random;

public class Unit extends Actor
{
    private Animation animation;
    private CharacterType type;
    private int direction;
    private float stateTime;
    private long atackTime;
    private TextureRegion currentFrame;
    private Image hBar;
    private Label levelL;
    private Character character;


    Unit (CharacterType type) throws NotEnoughMonyException
    {
        character = CharacterController.createNewCharacter(type);
        this.direction = 1;
        atackTime = TimeUtils.millis();
        setBounds(character.getPosition() * 2f, 50, 240, 288);

        switch (type)
        {
            case INFATRYMAN:
                animation = Resources.blueAnimationR;
                break;
            case ARCHER:
                animation = Resources.yellowAnimationR;
                break;
            case FAT:
                animation = Resources.greenAnimationR;
                break;
            case RIDER:
                animation = Resources.purpleAnimationR;
                break;
            case INCREDIBLE:
                animation = Resources.redAnimationR;
                break;
            default:
                animation = Resources.testAnimationR;
        }

        levelL = new Label( character.getStage().toString() + " " + type, new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE));
        levelL.setPosition(getX() - 5, getY() + getHeight() + 20);

        hBar = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);
    }

    Unit(CharacterType type, Character c) throws  NotEnoughMonyException
    {
        this.character = c;
        this.direction = -1;
        setBounds(character.getPosition() * 2f, 50, 240, 288);

        switch (type)
        {
            case INFATRYMAN:
                animation = Resources.blueAnimationL;
                break;
            case ARCHER:
                animation = Resources.yellowAnimationL;
                break;
            case FAT:
                animation = Resources.greenAnimationL;
                break;
            case RIDER:
                animation = Resources.purpleAnimationL;
                break;
            case INCREDIBLE:
                animation = Resources.redAnimationL;
                break;
            default:
                animation = Resources.testAnimationL;
        }

        levelL = new Label( character.getStage() + " " + type, new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE));
        levelL.setPosition(getX(), getY() + getHeight() + 35);

        hBar = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

        addAction(Actions.moveTo( character.getPosition() * 2f, 50, 1 / (Gdx.graphics.getFramesPerSecond() + 1)));

        levelL.setPosition(getX(), getY() + getHeight() + 35);

        if (character.getHealth() > 0)
            hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth() / character.getMaxHealth(), 10);

        if (character.getHealth() <= character.getMaxHealth() * 0.6f)
            hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
        if (character.getHealth() <= character.getMaxHealth() * 0.3f)
            hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));

        batch.draw(currentFrame, getX(), getY());
        levelL.draw(batch, parentAlpha);
        hBar.draw(batch,parentAlpha);

    }


    @Override
    public void act(float delta)
    {
        if(character.getType() == CharacterType.ARCHER && character.getState() == CharacterState.REDYTOFIGHT && TimeUtils.timeSinceMillis(atackTime) > 800)
            if (direction == 1)
            {
                if (Math.random() > 0.5)
                    GameScreen.setArrow(direction, getX() + (getWidth() / 2), (getY() + getHeight()) * 0.8f,
                        getX() + (getWidth() / 2) + 200, (getY() + getHeight() + (int) (Math.random() * 30)) * 0.8f);
                else
                    GameScreen.setArrow(direction, getX() + (getWidth() / 2), (getY() + getHeight()) * 0.8f,
                            getX() + (getWidth() / 2) + 200, (getY() + getHeight() - (int) (Math.random() * 30)) * 0.8f);
                atackTime = TimeUtils.millis();
            }
            else
            {
                if (Math.random() > 0.5)
                    GameScreen.setArrow(direction, getX() + (getWidth() / 2), (getY() + getHeight()) * 0.8f,
                            getX() + (getWidth() / 2) - 200, (getY() + getHeight() + (int) (Math.random() * 30)) * 0.8f);
                else
                    GameScreen.setArrow(direction, getX() + (getWidth() / 2), (getY() + getHeight()) * 0.8f,
                            getX() + (getWidth() / 2) - 200, (getY() + getHeight() - (int) (Math.random() * 30)) * 0.8f);
                atackTime = TimeUtils.millis();
            }
        super.act(delta);
    }

    CharacterType getType() {return type;}

    int getDirection() {return direction;}

    public Character getCharacter(){return character;}
}
