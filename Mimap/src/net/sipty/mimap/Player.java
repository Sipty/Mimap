package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity{

	// declarations
	public static Texture stand_left, stand_right,
					stand_up, stand_down;
	private static Animation walk_left, walk_right, 
					  walk_up, walk_down;
    private static int START=300, SPEED=3, 
    					width=32, height=64;
    private static Rectangle player;
	private static boolean notMoving=true;
    private static Side side = Side.DOWN;

     static boolean collisionX=false, collisionY=false;

	// constructor
	public Player(TiledMapTileLayer collisionLayer) {
		super(collisionLayer,
				player,
				side,
				stand_left, stand_right, stand_up, stand_down = new Texture(Gdx.files.internal("player_stand_down.png")),
				walk_left, walk_right, walk_up, walk_down,
				notMoving, START, SPEED, width, height);
		
		player = new Rectangle();
		player.x = START;
		player.y = START;
		player.width = width;
		player.height = height;
		
		// Load images:
			// standing sprites
			stand_left = new Texture(Gdx.files.internal("player_stand_left.png"));
			stand_right = new Texture(Gdx.files.internal("player_stand_right.png"));
			stand_up = new Texture(Gdx.files.internal("player_stand_up.png"));
			stand_down = new Texture(Gdx.files.internal("player_stand_down.png"));
			// animations
		    walk_left = prepAnima("player_walk_left.png", 4, 1, 0);
		    walk_right = prepAnima("player_walk_right.png", 4, 1, 0);
		    walk_up = prepAnima("player_walk_up.png", 2, 1, 1);
		    walk_down = prepAnima("player_walk_down.png", 2, 1, 1);
	}
	

	public void down() {
		notMoving=false;
		side = Side.DOWN;
		collision(side, player.width, player.x, player.y, collisionX, collisionY);
		drawAnima(player.x, player.y, walk_down);
		if(!collisionY)
			player.y-=SPEED;
	}
	public void left() {
		notMoving=false;
		side = Side.LEFT;
		collision(side, player.width, player.x, player.y, collisionX, collisionY);
		drawAnima(player.x, player.y, walk_left);
		if(!collisionX)
			player.x-=SPEED;
	}

	// NOTE: The checks for up and down keep in mind
	// the upper and most right borders, respectively, of the player rectangle.
	public void up() {
		notMoving=false;
		side = Side.UP;
		collision(side, player.width, player.x, player.y, collisionX, collisionY);
		drawAnima(player.x, player.y, walk_up);
		if(!collisionY)
			player.y+=SPEED;
	}
	public void right() {
		notMoving=false;
		side = Side.RIGHT;
		collision(side, player.width, player.x, player.y, collisionX, collisionY);
		drawAnima(player.x, player.y, walk_right);
		if(!collisionX)
			player.x+=SPEED;
	}

	public static void setNotMoving(boolean notMoving) {
		Player.notMoving = notMoving;
	}

	public static float getX() {
		return player.x;
	}
	public static float getY() {
		return player.y;
	}

	public static Side getSide() {
		return side;
	}
	
	
	
}
