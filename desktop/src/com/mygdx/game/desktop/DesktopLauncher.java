package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.graphics.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		//config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.width = 1280;
		config.height = 720;
		// fullscreen
		//config.fullscreen = true;
		// vSync
		config.vSyncEnabled = true;
		new LwjglApplication(new Start(), config);
	}
}
