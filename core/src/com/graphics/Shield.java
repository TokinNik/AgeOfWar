package com.graphics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.controller.CharacterController;
import com.model.Gate;

class Shield extends Actor
{
    private Animation animation;
    private int direction;
    private int animCount;
    private float currentHP;
    private float stateTime;
    private TextureRegion currentFrame;
    private Image shieldImage;
    private boolean active;
    private Gate gate;


    Shield (int dir)
    {
        if (dir == 1)
        {
            gate = new Gate(true, CharacterController.getUserEvolveStage());
            CharacterController.setUserGate(gate);
            animation = Resources.shieldDamageAnimationL;
            shieldImage = Resources.shieldU;
            shieldImage.setBounds(0,0,631,533);
            setBounds(0,0,631,533);
            this.currentHP = gate.getMaxHealth();
            setVisible(false);
            this.active = false;
        }
        if (dir == -1)
        {
            animation = Resources.shieldDamageAnimationR;
            shieldImage = Resources.shieldG;
            shieldImage.setBounds(Resources.GAME_WIDTH - 631, 0, 631, 533);
            setBounds(Resources.GAME_WIDTH - 631, 0, 631, 533);
            this.currentHP = GameScreen.getGameForpost().getMaxHealth();
            setVisible(false);
            this.active = false;
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
                shieldImage.draw(batch,parentAlpha);
        }
        else
            shieldImage.draw(batch,parentAlpha);
    }

    @Override
    public void act(float delta)
    {
        if (direction == 1 && currentHP > gate.getHealth())
        {
            currentHP = gate.getHealth();
            takeDamage();
        }
        if (direction == 1 && currentHP < gate.getHealth())
            currentHP = gate.getMaxHealth();
        super.act(delta);
    }

    private void takeDamage()
    {
        if (animCount <= 0)
            animCount = 10;
        System.out.println(CharacterController.clothestUserObjectPosition + "_+_+_+_ " + gate.getHealth());
    }

    public void setActive(boolean active)
    {
        this.active = active;
        setVisible(active);
    }
}
