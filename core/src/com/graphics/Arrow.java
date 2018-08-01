package com.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

class Arrow extends Actor
{
    private Image arrowImage;
    private float x;
    private float y;



    Arrow (boolean dir, float x1, float y1, float x2, float y2)
    {
        if (dir)
        {
            arrowImage = Resources.arrowR;
            setBounds(x1, y1,100,10);
            arrowImage.setBounds(x1,y1,getWidth(),getHeight());
        }
        else
        {
            arrowImage = Resources.arrowL;
            setBounds(x1, y1,100,10);
            arrowImage.setBounds(x1, y1, getWidth(), getHeight());
        }
        x = x2;
        y = y2;
        addAction(Actions.moveTo(x2, y2, 0.2f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        arrowImage.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta)
    {
        arrowImage.setPosition(getX(), getY());
        if (getX() == x && getY() == y)
            addAction(Actions.removeActor());
        super.act(delta);
    }
}
