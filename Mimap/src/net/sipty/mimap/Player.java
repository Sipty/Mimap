package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	// declaration
	private static Texture player;
	private static int x, y;
	private final static int START=500,SPEED=3;
	private static Animator anim;
	private static boolean notMoving=true;
    private static Animation walkAnimation;
    private static TextureRegion currentFrame;           

	// constructor
	public Player() {
		anim = new Animator();
		x = START;
		y = START;
		
		player = new Texture(Gdx.files.internal("player_stand_left.png"));
		
	}
	
	public static void draw() {
		if(notMoving) {
			GameScreen.game.batch.draw(player, x, y);
		}
	}
	
	public static void up() {
		y+=SPEED;
	}
	public static void down() {
		y-=SPEED;
	}
	public static void right() {
		x+=SPEED;
	}
	public static void left() {
		notMoving=false;
		anim.draw(x, y);
		x-=SPEED;
	}

	// setter
	public static void setNotMoving(boolean notMoving) {
		Player.notMoving = notMoving;
	}
	
	
}
