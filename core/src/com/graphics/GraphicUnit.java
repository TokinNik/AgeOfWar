package com.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.controller.UnitType;
import com.exception.NotEnoughMonyException;
import com.model.UnitState;

public class GraphicUnit extends Actor
{
    private Animation animation;
    private boolean direction;
    private float stateTime;
    private long atackTime;
    private TextureRegion currentFrame;
    private Image hBar;
    private Label levelL;
    private UnitType unitType;
    private UnitState unitState;
    private int unitId;
    private float health;
    private int maxHealth;


    GraphicUnit(UnitType type, int id, boolean dir) throws NotEnoughMonyException
    {
        this.unitType = type;
        this.unitId = id;
        this.direction = dir;
        atackTime = TimeUtils.millis();
        setBounds(dir?0:2000 , 50, 240, 288);

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

        levelL = new Label( unitState + " " + type, new Label.LabelStyle(Resources.game.standartFontWhite, Color.WHITE));
        levelL.setPosition(getX() - 5, getY() + getHeight() + 20);

        hBar = new Image(Resources.guiSkin.getDrawable("hBar_green"));
        hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * health/ maxHealth, 10);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

        //addAction(Actions.moveTo( unit.getPosition() * 2f, 50, 1 / (Gdx.graphics.getFramesPerSecond() + 1)));

        levelL.setPosition(getX(), getY() + getHeight() + 35);

        if (health > 0)
            hBar.setBounds(getX(), getY() + getHeight() + 20, getWidth() * health / maxHealth, 10);

        if (health <= maxHealth * 0.6f)
            hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_yellow"));
        if (health <= maxHealth * 0.3f)
            hBar.setDrawable(Resources.guiSkin.getDrawable("hBar_red"));

        batch.draw(currentFrame, getX(), getY());
        levelL.draw(batch, parentAlpha);
        hBar.draw(batch,parentAlpha);

    }


    @Override
    public void act(float delta)
    {
        if(unitType == UnitType.ARCHER && unitState == UnitState.FIGHT && TimeUtils.timeSinceMillis(atackTime) > 800)
            if (direction)
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

    public void setPos(float x)
    {
        setX(x);
    }

    UnitType getType() {return unitType;}

    boolean getDirection() {return direction;}

    public UnitState getUnitState() {
        return unitState;
    }

    public void setUnitState(UnitState unitState) {
        this.unitState = unitState;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}