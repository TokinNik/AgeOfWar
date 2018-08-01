package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.graphics.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		//config.width = 1280;
		//config.height = 720;
		//config.width = 1024;
		//config.height = 768;
		config.width = 960;
		config.height = 540;
		//config.width = 480;
		//config.height = 320;

		//config.fullscreen = true;

		config.resizable = false;
		new LwjglApplication(new Start(), config);
	}
}
