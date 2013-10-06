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
	private static boolean notMoving=true;
    private static Side side = Side.DOWN;

    //static boolean collisionX=false, collisionY=false;

	// constructor
	public Player(TiledMapTileLayer collisionLayer) {
		super(collisionLayer,
				notMoving,
				side,
				stand_left = new Texture(Gdx.files.internal("player_stand_left.png")),
				stand_right = new Texture(Gdx.files.internal("player_stand_right.png")),
				stand_up = new Texture(Gdx.files.internal("player_stand_up.png")),
				stand_down = new Texture(Gdx.files.internal("player_stand_down.png")),
				walk_left, walk_right, walk_up, walk_down,
				START, SPEED, width, height);
		
		// Load images:
			// animations
		    walk_left = prepAnima("player_walk_left.png", 4, 1, 0);
		    walk_right = prepAnima("player_walk_right.png", 4, 1, 0);
		    walk_up = prepAnima("player_walk_up.png", 2, 1, 1);
		    walk_down = prepAnima("player_walk_down.png", 2, 1, 1);
	}
	
	public void move(Side side) {
		notMoving = false;
		Player.side = side;
		collision(side);
		// choose movement direction
		switch(side) {
		case DOWN:
			drawAnima(entity.x, entity.y, walk_down);
			if(!collisionY)
				entity.y-=SPEED;
			break;
		case LEFT:
			drawAnima(entity.x, entity.y, walk_left);
			if(!collisionX)
				entity.x-=SPEED;
			break;
		case UP:
			drawAnima(entity.x, entity.y, walk_up);
			if(!collisionY)
				entity.y+=SPEED;
			break;
		case RIGHT:
			drawAnima(entity.x, entity.y, walk_right);
			if(!collisionX)
				entity.x+=SPEED;
			break;
		}
		// update the super's rectangle
		update(entity.x, entity.y, entity.width, entity.height);
	}
	
	public static void setNotMoving(boolean notMoving) {
		Player.notMoving = notMoving;
	}

	public static Side getSide() {
		return side;
	}
	
	
	
}
