package com.graphics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Start extends Game
{
	SpriteBatch batch;
	BitmapFont standartFontWhite;

	@Override
	public void create()
	{
		batch = new SpriteBatch();

		final String FONT_PATH = "Samson.ttf";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + FONT_PATH));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		parameter.characters = font_chars;
		parameter.size = 20;
		parameter.color = Color.WHITE;
		standartFontWhite = generator.generateFont(parameter);
		generator.dispose();

		Resources.OPTIONS.putInteger("Start Money", 100);

		Resources.game = this;
		this.setScreen(new MainMenu());
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		batch.dispose();
		standartFontWhite.dispose();
		System.out.println("Disposed:  Start");
		System.exit(0);
	}
}
