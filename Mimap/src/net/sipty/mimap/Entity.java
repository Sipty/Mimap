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
	public Animation walk_left, walk_right, walk_up, walk_down;
    protected enum Direction { LEFT, RIGHT, UP, DOWN }
    protected Direction direction = Direction.DOWN;
    
    protected Rectangle entity;
    
	protected boolean collisionX, collisionY, notMoving=true;
	
    private static Texture sheet;             
    private static TextureRegion[] frames;             
    private TextureRegion currentFrame;            
    private float stateTime= 0f;                   

    private int speed;
    
    private static TiledMapTileLayer collisionLayer;
    
	// constructor
	public Entity(TiledMapTileLayer collisionlayer,
			
			Texture stand_left,
			Texture stand_right,
			Texture stand_up, 
			Texture stand_down,
			
			Animation walk_left,
			Animation walk_right,
			Animation walk_up,
			Animation walk_down,
			
			int speed,
			int start,
			int width,
			int height
			) {
		
		Entity.collisionLayer = collisionlayer;
		
		this.stand_left = stand_left;
		this.stand_right = stand_right;
		this.stand_up = stand_up;
		this.stand_down = stand_down;
		
		this.walk_left = walk_left;
		this.walk_right = walk_right;
		this.walk_up = walk_up;
		this.walk_down = walk_down;
		System.out.println(Integer.toString(start));
		this.speed = speed;
		this.entity = new Rectangle();
		this.entity.x = start;
		this.entity.y = start;
		this.entity.width = width;
		this.entity.height = height;
	}
	
	// prepares the animation
    public static Animation prepAnima(String file, int cols, int rows, int type) {
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
	public void drawStand(Direction direction) {
		if(notMoving) {
			switch(direction) {
			case UP: 
				InHouseScreen.game.batch.draw(stand_up, entity.x, entity.y);
				break;
			case DOWN:
				InHouseScreen.game.batch.draw(stand_down, entity.x, entity.y);
				break;
			case RIGHT:
				InHouseScreen.game.batch.draw(stand_right, entity.x, entity.y);
				break;
			case LEFT:
				InHouseScreen.game.batch.draw(stand_left, entity.x, entity.y);
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
    
    // needed to fine tune the collision detection
    private final static int tweakY = 5;
    // collision
    public void collision(Direction direction) {
        // tile info
        float tileWidth=collisionLayer.getTileWidth(), tileHeight=collisionLayer.getTileHeight();

        //reset collision detecter
        collisionX=false;
        collisionY=false;

        switch(direction) {
            // X-AXIS COLLISION:
        	case LEFT:        	
        		collisionX = collisionLayer.getCell( (int)((entity.x-2)/tileWidth), (int)(entity.y/tileHeight) ).getTile().getProperties().containsKey("blocked");
                break;
        	case RIGHT:
            	collisionX = collisionLayer.getCell( (int)((entity.x+entity.width)/tileWidth), (int)(entity.y/tileHeight) ).getTile().getProperties().containsKey("blocked");
        		break;
            // Y-AXIS COLLISION:
        	case DOWN:
            	collisionY = collisionLayer.getCell( (int)((entity.x+tweakY)/tileWidth), (int)((entity.y-3)/tileHeight) ).getTile().getProperties().containsKey("blocked");
        		break;
        	case UP:
                collisionY = collisionLayer.getCell( (int)((entity.x+tweakY)/tileWidth), (int)((entity.y+10)/tileHeight) ).getTile().getProperties().containsKey("blocked");
                break;	
        }
    }	
    
    public void move(Direction direction) {
    	// it makes the movement smooth. Get it? 
    	float smoothie = Gdx.graphics.getDeltaTime();
    	
		notMoving = false;
		this.direction = direction;
		collision(direction);
		// choose movement direction
		switch(direction) {
		case DOWN:
			drawAnima(entity.x, entity.y, walk_down);
			if(!collisionY)
				entity.y-=smoothie*speed;
			break;
		case LEFT:
			drawAnima(entity.x, entity.y, walk_left);
			if(!collisionX)
				entity.x-=smoothie*speed;
			break;
		case UP:
			drawAnima(entity.x, entity.y, walk_up);
			if(!collisionY)
				entity.y+=smoothie*speed;
			break;
		case RIGHT:
			drawAnima(entity.x, entity.y, walk_right);
			if(!collisionX)
				entity.x+=smoothie*speed;
			break;
		}
	}
    // getters and setters

	public float getX() {
		return entity.x;
	}
	public float getY() {
		return entity.y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setNotMoving(boolean notMoving) {
		this.notMoving = notMoving;
	}
}