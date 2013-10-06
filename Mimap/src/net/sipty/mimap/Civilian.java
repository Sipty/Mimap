package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

public class Civilian extends Entity{
	
	// declarations
	public static Texture stand_left, stand_right,
				stand_up, stand_down;
	@SuppressWarnings("unused")
	private static Animation walk_left, walk_right, 
  				walk_up, walk_down;
	private static int START=100, SPEED=150, 
					width=32, height=64;

	// constructor
	public Civilian(TiledMapTileLayer collisionLayer) {
		super(collisionLayer,
				
			stand_left = new Texture(Gdx.files.internal("player_stand_left.png")),
			stand_right = new Texture(Gdx.files.internal("player_stand_right.png")),
			stand_up = new Texture(Gdx.files.internal("player_stand_up.png")),
			stand_down = new Texture(Gdx.files.internal("player_stand_down.png")),
			
			walk_left = prepAnima("player_walk_left.png", 4, 1, 0),
			walk_right = prepAnima("player_walk_right.png", 4, 1, 0),
			walk_up = prepAnima("player_walk_up.png", 2, 1, 1),
			walk_down = prepAnima("player_walk_down.png", 2, 1, 1),
			
			SPEED, START, width, height);
	}
	
	// make him move
	private Direction dir = Direction.LEFT;
	private int walked=0;
	public void moveCiv() {	
		// movet, movet
		move(dir);
		walked++;
		System.out.println(Integer.toString(walked));
		// on collision, change direction
		if(collisionX || collisionY || walked>100) {
			int rnd = MathUtils.random(0,3);
			int rndDistance = MathUtils.random(1,30);
			walked = 0;
			walked = rndDistance;
			switch(rnd) {
			case 0:
				dir = Direction.DOWN;
				break;
			case 1:
				dir = Direction.LEFT;
				break;
			case 2:
				dir = Direction.RIGHT;
				break;
			case 3:
				dir = Direction.UP;
				break;
			}
		}
		
	}
	
}
