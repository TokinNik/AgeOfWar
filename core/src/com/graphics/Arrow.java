package com.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

class Arrow extends Actor
{
    private Sprite sprite = new Sprite();
    private float x;
    private float y;



    Arrow (int dir, float x1, float y1, float x2, float y2)
    {
        if (dir == 1)
        {
            sprite.setTexture(Resources.arrow);
            setBounds(x1, y1,100,10);
            sprite.setBounds(x1,y1,getWidth(),getHeight());
        }
        if (dir == -1)
        {
            sprite.setTexture(Resources.arrowL);
            setBounds(x1, y1,100,10);
            sprite.setBounds(x1, y1, getWidth(), getHeight());
        }
        x = x2;
        y = y2;
        addAction(Actions.moveTo(x2, y2, 0.2f));
        setDebug(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta)
    {
        sprite.setPosition(getX(), getY());
        if (getX() == x && getY() == y)
            addAction(Actions.removeActor());
        super.act(delta);
    }
}
