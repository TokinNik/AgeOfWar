package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class Unit extends Actor
{
    private static final int FRAME_ROWS = 5;
    private static final int FRAME_COLS = 6;
//    private final Texture walkSheet;
//    private final TextureRegion[] walkFrames;
    private final Animation animation;
    private Sprite sprite;
    private int type;
    private int direction;
    private float stateTime;
    private TextureRegion currentFrame;
    private SpriteBatch spriteBatch;


    public Unit (int type, int dir)
    {
        sprite = new Sprite(new Texture("android/assets/units/example/arrow1.jpg"));
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        setBounds(110, 110, 100, 100);

        //animation = new Animation<TextureRegion>(0.02f, );

//        walkSheet = new Texture(Gdx.files.internal("android/assets/units/Walksequence_spritesheet.png")); // #9
//        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS); // #10
//        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
//        int index = 0;
//        for (int i = 0; i < FRAME_ROWS; i++) {
//            for (int j = 0; j < FRAME_COLS; j++) {
//                walkFrames[index++] = tmp[i][j];
//            }
//        }
        animation = Resourses.testAnimation;

        this.type = type;
        this.direction = dir;

        addAction(Actions.moveTo(Resourses.width - 100, 50, 5));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);
        //sprite.draw(batch);
        batch.draw(currentFrame, getX(), getY()); // #17
    }

    @Override
    public void act(float delta)
    {
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        super.act(delta);
    }
    public int getType() {return type;}

    public int getDirection() {return direction;}
}
