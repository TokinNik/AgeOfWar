package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;

import java.awt.geom.Rectangle2D;

import javax.swing.Box;


public class Unit extends Actor
{
    private Animation animation;
    private CharacterType type;
    private int direction;
    private float stateTime;
    private TextureRegion currentFrame;
    private Image hBar;
    private Character character;


    public Unit (CharacterType type) throws NotEnoughMonyException
    {
        character = CharacterController.createNewCharacter(CharacterType.ARCHER);

        setBounds(100, 50, 240, 288);

        animation = Resources.testAnimationR;

        this.type = type;
        this.direction = 1;

        hBar = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);

        //addAction(Actions.moveTo( character.getPosition(), 50, 1/Gdx.graphics.getFramesPerSecond()));
    }

    public Unit(CharacterType type, Character c) throws  NotEnoughMonyException
    {
        this.type = type;
        this.character = c;
        setBounds(2168, 50, 240, 288);

        animation = Resources.testAnimationL;

        this.type = type;
        this.direction = -1;

        hBar = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth()/character.getMaxHealth(), 10);

       //addAction(Actions.moveTo( character.getPosition(), 50, 6));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if (Resources.state == State.GAME)
        {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

            //setX(character.getPosition());

            addAction(Actions.moveTo( character.getPosition()*2.2f, 50, 1 / (Gdx.graphics.getFramesPerSecond() + 1)));

            hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * character.getHealth() / character.getMaxHealth(), 10);
            if (character.getHealth() <= character.getMaxHealth() * 0.6f)
                hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
            if (character.getHealth() <= character.getMaxHealth() * 0.3f)
                hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));

            batch.draw(currentFrame, getX(), getY());
            hBar.draw(batch,parentAlpha);
        }
//        else
//            batch.draw(currentFrame, getX(), getY());
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
    }

    public CharacterType getType() {return type;}

    public int getDirection() {return direction;}

    public Character getCharacter(){return character;}
}
