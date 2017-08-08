package com.pixelswithcode.libgdx.rps.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pixelswithcode.libgdx.rps.RPSGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Rock, Paper, Scissors";
		config.width = 768;
		config.height = 768;
		//config.width = 1920;
		//config.height = 1080;
		//config.fullscreen = true;

		Application app = new LwjglApplication(new RPSGame(), config);
		app.setLogLevel(Application.LOG_DEBUG);
	}
}
