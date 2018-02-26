package com.mygdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.controller.CharacterController;
import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterType;

public class AnimObject extends Actor
{
    private Animation animation;
    private float stateTime;
    private TextureRegion currentFrame;

    AnimObject ( Animation<TextureRegion> animation)
    {
        this.animation = animation;
        setSize(animation.getKeyFrame(stateTime, true).getRegionWidth(),
                animation.getKeyFrame(stateTime, true).getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX(), getY());
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
    }
}
