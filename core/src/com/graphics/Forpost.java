package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.controller.GameController;


public class Forpost extends Actor
{
    private Animation animation;
    private int direction;
    private int animCount;
    private float currentHP;
    private float stateTime;
    private TextureRegion currentFrame;
    private Image forpostImage;



    Forpost (int dir)
    {
        if (dir == 1)
        {
            animation = Resources.fortDamageAnimationL;
            forpostImage = Resources.fortU;
            forpostImage.setBounds(0,0,600,400);
            setBounds(0,0,600,400);
            this.currentHP = GameScreen.getUserForpost().getMaxHealth();
        }
        if (dir == -1)
        {
            animation = Resources.fortDamageAnimationR;
            forpostImage = Resources.fortG;
            forpostImage.setBounds(Resources.GAME_WIDTH - 600, 0, 600, 400);
            setBounds(Resources.GAME_WIDTH - 600, 0, 600, 400);
            this.currentHP = GameScreen.getGameForpost().getMaxHealth();
        }

        this.direction = dir;
        this.animCount = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if (Resources.state == State.GAME)
        {
            if (animCount > 0)
            {
                stateTime += Gdx.graphics.getDeltaTime();
                currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
                animCount--;
                batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
            }
            else
                forpostImage.draw(batch,parentAlpha);
        }
        else
            forpostImage.draw(batch,parentAlpha);
    }

    @Override
    public void act(float delta)
    {
        if (direction == 1 && currentHP > GameScreen.getUserForpost().getHealth())
        {
            currentHP = GameScreen.getUserForpost().getHealth();
            takeDamage();
        }
        if (direction == 1 && currentHP < GameScreen.getUserForpost().getHealth())
            currentHP = GameScreen.getUserForpost().getMaxHealth();
        if (direction == -1 && currentHP > GameScreen.getGameForpost().getHealth())
        {
            currentHP = GameScreen.getGameForpost().getHealth();
            takeDamage();
        }
        if (direction == -1 && currentHP < GameScreen.getGameForpost().getHealth())
            currentHP = GameScreen.getGameForpost().getMaxHealth();
        super.act(delta);
    }

    private void takeDamage ()
    {
        if (animCount <= 0)
            animCount = 10;
    }
}
