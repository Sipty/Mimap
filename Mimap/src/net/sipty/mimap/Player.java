package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends Entity{

	// declarations
	public static Texture stand_left, stand_right,
					stand_up, stand_down;
	@SuppressWarnings("unused")
	private static Animation walk_left, walk_right, 
					  walk_up, walk_down;
    private static int START=300, SPEED=3, 
    					width=32, height=64;

	// constructor
	public Player(TiledMapTileLayer collisionLayer) {
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
}