package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;


public class Unit extends Actor
{


    private final Animation animation;
    private CharacterType type;
    private int direction;
    private float stateTime;
    private TextureRegion currentFrame;
    private Character character;


    public Unit (CharacterType type) throws NotEnoughMonyException
    {
        character = CharacterController.createNewCharacter(CharacterType.ARCHER);

        setBounds(100, 50, 240, 288);

        animation = Resources.testAnimationR;

        this.type = type;
        this.direction = 1;

        addAction(Actions.moveTo( 2268, 50, 6));
    }

    public Unit(CharacterType type, Character character) throws  NotEnoughMonyException
    {
        this.type = type;
        this.character = character;

        setBounds(2168, 50, 240, 288);

        animation = Resources.testAnimationL;

        this.type = type;
        this.direction = -1;

        addAction(Actions.moveTo( 50, 50, 6));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        //sprite.draw(batch);
        batch.draw(currentFrame, getX(), getY());
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
