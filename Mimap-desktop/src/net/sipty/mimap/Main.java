package net.sipty.mimap;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MimaP -- alpha";
		cfg.width = Mimap.SCREEN_X;
		cfg.height = Mimap.SCREEN_Y;
		
		new LwjglApplication(new Mimap(), cfg);
	}
}