package net.sipty.mimap;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MimaP -- alpha";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new Mimap(), cfg);
	}
}
