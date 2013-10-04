package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	// declaration
	private static Texture stand_left, stand_right, stand_up, stand_down;
	private final static int START=300,SPEED=3;
	private static boolean notMoving=true;
    private static Animation walk_right, walk_left, walk_up, walk_down;
    private static int side=3;
    private static Rectangle player;
    
    private Texture sheet;             
    private TextureRegion[] frames;             
    private static TextureRegion currentFrame;            
    private static float stateTime= 0f;                   

    private static TiledMapTileLayer collisionLayer;
    
	// constructor
	public Player(TiledMapTileLayer collisionlayer) {
		
		// collision layer
		Player.collisionLayer = collisionlayer;
		
		// create player
		player = new Rectangle();
		player.x = START;
		player.y = START;
		player.width = 32;
		player.height = 64;

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
	
	// prepares the animation
    public Animation prepAnima(String file, int cols, int rows, int type) {
    		
            sheet = new Texture(Gdx.files.internal(file));    
            TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / cols, sheet.getHeight() / rows); 
        	
            // if the sheet's animations are one after the other
	        if(type == 1) {
	        	frames = new TextureRegion[cols * rows];
	            int index = 0;
	            for (int i = 0; i < rows; i++) {
	                    for (int j = 0; j < cols; j++) {
	                            frames[index++] = tmp[i][j];
	                    }
	            }
    		}
	        // the order of the player_walk_* sheet is 0,1,3,2,3,1
	        else if(type == 0) {
	        	frames = new TextureRegion[cols * rows + 2];
	        	frames[0] = tmp[0][0];
	        	frames[1] = tmp[0][1];
	        	frames[2] = tmp[0][3];
	        	frames[3] = tmp[0][2];
	        	frames[4] = tmp[0][3];
	        	frames[5] = tmp[0][1];
	        }
	        
            Animation walk = new Animation(0.16f, frames);
            
            return walk;
    }

    // draws the standing sprite
	public static void drawStand() {
		if(notMoving) {
			switch(side) {
			case 1:
				InHouseScreen.game.batch.draw(stand_up, player.x, player.y);
				break;
			case 2:
				InHouseScreen.game.batch.draw(stand_down, player.x, player.y);
				break;
			case 3:
				InHouseScreen.game.batch.draw(stand_right, player.x, player.y);
				break;
			case 4:
				InHouseScreen.game.batch.draw(stand_left, player.x, player.y);
				break;
			}
		}
	}
	
	// draws the animation
    public static void drawAnima(float x, float y, Animation walk) {
            stateTime += Gdx.graphics.getDeltaTime();                      
            currentFrame = walk.getKeyFrame(stateTime, true);      
            InHouseScreen.game.batch.draw(currentFrame, x, y);                        
    }
    

    private static boolean collisionX=false, collisionY=false;
    
    // collision
    public static void collision(int side) {
        
	        /* x axis collision
	         * LEFT:
        	// upper
        	collisionX = collisionLayer.getCell( (int)(player.x/tileWidth), (int)((player.y+player.height)/(tileHeight)) ).getTile().getProperties().containsKey("blocked");
        	// middle
        	if(!collisionX)
        		collisionX = collisionLayer.getCell( (int)(player.x/tileWidth), (int)((player.y+player.height/2)/tileHeight) ).getTile().getProperties().containsKey("blocked");
        	*
        	* RIGHT:
        	// upper
    		collisionX = collisionLayer.getCell( (int)((player.x+player.width)/tileWidth), (int)((player.y+player.height)/(tileHeight)) ).getTile().getProperties().containsKey("blocked");
        	// middle
        	if(!collisionX)
        		collisionX = collisionLayer.getCell( (int)((player.x+player.width)/tileWidth), (int)((player.y+player.height/2)/tileHeight) ).getTile().getProperties().containsKey("blocked");
	        *
	        *The above block has been commented out, just in case more collision is needed.
	        *At the time of writing 'lower' collision is not only sufficient, but also required.
	        */

        // tile info
        float tileWidth=collisionLayer.getTileWidth(), tileHeight=collisionLayer.getTileHeight();
    	
        //reset collision detecter
        collisionX=false;
        collisionY=false;

        // X-AXIS COLLISION:
        // Left
        if(!notMoving && side==4) {
    		collisionX = collisionLayer.getCell( (int)((player.x-2)/tileWidth), (int)(player.y/tileHeight) ).getTile().getProperties().containsKey("blocked");
        }
        // Right
        if(!notMoving && side==3) {
    		collisionX = collisionLayer.getCell( (int)((player.x+player.width)/tileWidth), (int)(player.y/tileHeight) ).getTile().getProperties().containsKey("blocked");
        }
        
        // Y-AXIS COLLISION:
        // Down
        if(!notMoving && side==2) {
    		collisionY = collisionLayer.getCell( (int)((player.x)/tileWidth), (int)((player.y-3)/tileHeight) ).getTile().getProperties().containsKey("blocked");
        }   
        // Up
        if(!notMoving && side==1) {
        	collisionY = collisionLayer.getCell( (int)((player.x)/tileWidth), (int)((player.y+10)/tileHeight) ).getTile().getProperties().containsKey("blocked");
        }   
        
    }
    
	public static void down() {
		notMoving=false;
		side = 2;
		collision(side);
		drawAnima(player.x, player.y, walk_down);
		if(!collisionY)
			player.y-=SPEED;
	}
	public static void left() {
		notMoving=false;
		side = 4;
		collision(side);
		drawAnima(player.x, player.y, walk_left);
		if(!collisionX)
			player.x-=SPEED;
	}

	// NOTE: The checks for up and down keep in mind
	// the upper and most right borders, respectively, of the player rectangle.
	public static void up() {
		notMoving=false;
		side = 1;
		collision(side);
		drawAnima(player.x, player.y, walk_up);
		if(!collisionY)
			player.y+=SPEED;
	}
	public static void right() {
		notMoving=false;
		side = 3;
		collision(side);
		drawAnima(player.x, player.y, walk_right);
		if(!collisionX)
			player.x+=SPEED;
	}
	
	
	// getters & setters
	public static void setNotMoving(boolean notMoving) {
		Player.notMoving = notMoving;
	}

	public static Animation getWalk_left() {
		return walk_left;
	}

	public static float getX() {
		return player.x;
	}
	public static float getY() {
		return player.y;
	}
	
}
