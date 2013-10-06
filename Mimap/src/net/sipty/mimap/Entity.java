package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	
	// declaration
	private Texture stand_left, stand_right, stand_up, stand_down;
	private int speed;
    protected enum Side { LEFT, RIGHT, UP, DOWN }
    private Animation walk_right, walk_left, walk_up, walk_down;
    
    private Rectangle entity;
    
	private boolean notMoving;
	
    private Texture sheet;             
    private TextureRegion[] frames;             
    private TextureRegion currentFrame;            
    private float stateTime= 0f;                   

    private static TiledMapTileLayer collisionLayer;
    
	// constructor
	public Entity(TiledMapTileLayer collisionlayer,
			Rectangle entity,
			
			Side side,
			
			Texture stand_left,
			Texture stand_right,
			Texture stand_up, 
			Texture stand_down,
			
			Animation walk_left,
			Animation walk_right,
			Animation walk_up,
			Animation walk_down,
			
			boolean notMoving,
			int start,
			int speed,
			int width,
			int height
			) {
		
		Entity.collisionLayer = collisionlayer;
		
		this.entity = new Rectangle();
		this.entity.x = start;
		this.entity.y = start;
		this.entity.width = width;
		this.entity.height = height;
		
		this.stand_left = stand_left;
		this.stand_right = stand_right;
		this.stand_up = stand_up;
		this.stand_down = stand_down;
		
		this.walk_left = walk_left;
		this.walk_right = walk_right;
		this.walk_up = walk_up;
		this.walk_down = walk_down;
		
		this.speed = speed;
		this.notMoving = notMoving;
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
	public void drawStand(float x, float y, Side side) {
		if(notMoving) {
			switch(side) {
			case UP: 
				InHouseScreen.game.batch.draw(Player.stand_up, x, y);
				break;
			case DOWN:
				InHouseScreen.game.batch.draw(stand_down, x, y);
				break;
			case RIGHT:
				InHouseScreen.game.batch.draw(Player.stand_right, x, y);
				break;
			case LEFT:
				InHouseScreen.game.batch.draw(Player.stand_left, x, y);
				break;
			}
		}
	}
	
	// draws the animation
    public void drawAnima(float x, float y, Animation walk) {
            stateTime += Gdx.graphics.getDeltaTime();                      
            currentFrame = walk.getKeyFrame(stateTime, true);      
            InHouseScreen.game.batch.draw(currentFrame, x, y);                        
    }
    
    private final static int tweakY = 5;
    
    // collision
    public void collision(Side side, float width, float x, float y, boolean collisionX, boolean collisionY) {
        // tile info
        float tileWidth=collisionLayer.getTileWidth(), tileHeight=collisionLayer.getTileHeight();

        //reset collision detecter
        collisionX=false;
        collisionY=false;

        switch(side) {
            // X-AXIS COLLISION:
        	case LEFT:
               // if(!notMoving) {
            		Player.collisionX = collisionLayer.getCell( (int)((x-2)/tileWidth), (int)(y/tileHeight) ).getTile().getProperties().containsKey("blocked");
                //}   
            		System.out.println(collisionX);
                break;
        	case RIGHT:
        		//if(!notMoving) {
            		Player.collisionX = collisionLayer.getCell( (int)((x+width)/tileWidth), (int)(y/tileHeight) ).getTile().getProperties().containsKey("blocked");
                //}
        		break;
            // Y-AXIS COLLISION:
        	case DOWN:
        		//if(!notMoving) {
            		Player.collisionY = collisionLayer.getCell( (int)((x+tweakY)/tileWidth), (int)((y-3)/tileHeight) ).getTile().getProperties().containsKey("blocked");
                //}
        		break;
        	case UP:
                //if(!notMoving) {
                	Player.collisionY = collisionLayer.getCell( (int)((x+tweakY)/tileWidth), (int)((y+10)/tileHeight) ).getTile().getProperties().containsKey("blocked");
                //}   
                break;	
        }
    }	
	
	// getters & setters

	public Animation getWalk_left() {
		return walk_left;
	}

}