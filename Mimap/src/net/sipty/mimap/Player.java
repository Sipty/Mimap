package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	// declaration
	private static Texture player= new Texture(Gdx.files.internal("player.png"));
	
	private static int start=100,
					   x=start, y=start,
					   speed=5;
	
	public static void draw() {
		GameScreen.game.batch.draw(player, x, y);
	}
	
	public static void up() {
		y+=speed;
	}
	public static void down() {
		y-=speed;
	}
	public static void right() {
		x+=speed;
	}
	public static void left() {
		x-=speed;
	}
}
