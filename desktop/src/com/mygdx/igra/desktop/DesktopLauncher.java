package com.mygdx.igra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.igra.Igra;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "igra";
		config.width = 1600;
		config.height = 900;
		new LwjglApplication(new Igra(), config);
	}
}
