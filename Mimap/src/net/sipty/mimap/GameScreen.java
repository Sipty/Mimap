package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


public class GameScreen implements Screen, InputProcessor {
	
	// declarations
	
	public static Mimap game;
	private OrthographicCamera camera;
	private int posX, posY, posYreversed;	// mouse coord info

	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	
	private final static int menuLeftX=45,	// drop down menu coords
							 menuRightX=105, 
							 menuY=15,
							 
							 secondMenuLeftX=menuRightX+1,	// second menu
							 secondMenuRightX=205,
							 
							 optionsY=menuY+45,	// options menu coords
							 
							 quitY=menuY+65, // quit coords
							 
							 restartY=menuY+25;	// restart coords
	
//FPSLogger fps = new FPSLogger();

	private Player player = new Player();

	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	
	// constructor
	public GameScreen(final Mimap gam) {
		GameScreen.game = gam;

		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
		map = new TmxMapLoader().load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		//camera stuffs
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
	}
	
	// please consider moving me to the player class
	private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {

		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
		rectPool.freeAll(tiles);
		tiles.clear();
		
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}
	
	@Override
	public void render(float delta) {
		// gl and camera stuffs
		Gdx.gl.glClearColor(0f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// set the camera on the player
		camera.position.x = Player.getPlayer_X();
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		// set the tile map rendere view based on what the
		// camera sees and render the map
		renderer.setView(camera);
		renderer.render();
//fps.log();
		
		

		// this should be moved to the player class
		Rectangle playerRect = rectPool.obtain();
		playerRect.set(Player.getPlayer_X(), Player.getPlayer_Y(), Player.player.width, Player.player.height);
		int startX, startY, endX, endY;
		if(Player.getVelocity() > 0) {
			startX = endX = (int)(Player.getPlayer_X() + Player.player.width + Player.getVelocity());
		} else {
			startX = endX = (int)(Player.getPlayer_X() + Player.getVelocity());
		}
		startY = (int)(Player.getPlayer_Y());
		endY = (int)(Player.getPlayer_Y() + Player.player.height);
		getTiles(startX, startY, endX, endY, tiles);
		playerRect.x += Player.getVelocity();
		for(Rectangle tile: tiles) {
System.out.println("check check check");
			if(playerRect.overlaps(tile)) {
				Player.setVelocity(0);
				System.out.println("collision");
				break;
			}
		}
		playerRect.x = Player.getPlayer_X();
		
		// batch begin
			game.batch.begin();
			// mouse coords info
			posX = Gdx.input.getX();
			posY = Gdx.input.getY();
			posYreversed = 720-posY;
			game.font.draw(game.batch,  "Mouse coords: "+Integer.toString(posX)+", "+Integer.toString(posY)+" /"+Integer.toString(posYreversed), 1050, 700);
			game.font.draw(game.batch,  "Player coords: "+Float.toString(Player.getPlayer_X())+", "+Float.toString(Player.getPlayer_Y()), 1050, 680);
			
			// menu
			Menu.draw();

		// Player:
			// standing
			Player.drawStand();
			// left
			if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) 
				Player.left();
			// right
			if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) 
				Player.right();
			// up
			if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) 
				Player.up();
			// down
			if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) 
				Player.down();

		// batch end
		game.batch.end();
				
	}

	@Override
	public boolean keyUp(int keycode) {
		// 19 - up arrow
		// 20 - down arrow
		// 21 - left arrow
		// 22 - right arrow
			Player.setNotMoving(true);
			Player.setVelocity(0);
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		// menu
		if(screenX>menuLeftX && screenX<menuRightX-10 && screenY>menuY && screenY<menuY+24) {
			if(Menu.getShowMenu()) {
				Menu.setShowMenu(false);
				Menu.setShowOptions(false);
				Menu.setShowQuit(false);
				Menu.setShowRestart(false);
			}
			else {
				Menu.setShowMenu(true);
			}
		}
		// restart
		if(screenX>menuLeftX && screenX<menuRightX && screenY>menuY+25 && screenY<menuY+44) {
			if(Menu.getShowMenu()) {
				if(Menu.getShowRestart()) {
					Menu.setShowRestart(false);
				}
				else {
					Menu.setShowRestart(true);
					Menu.setShowOptions(false);
					Menu.setShowQuit(false);
				}
			}
		}
		// restart confirmation
			// no
			if(screenX>secondMenuLeftX && screenX<secondMenuLeftX+34 && screenY>restartY+20 && screenY<restartY+40) {
				if(Menu.getShowMenu() && Menu.getShowRestart()) {
					Menu.setShowRestart(false);	
				}
			}
			// yes
				if(screenX>secondMenuLeftX+35 && screenX<secondMenuLeftX+70 && screenY>restartY+20 && screenY<restartY+40) {
					if(Menu.getShowMenu() && Menu.getShowRestart()) {
						game.setScreen(new IntroScreen(game));
						Menu.setShowRestart(false);
						Menu.setShowMenu(false);

					}
				}
			
		// options
		if(screenX>menuLeftX && screenX<menuRightX && screenY>optionsY && screenY<optionsY+19) {
			if(Menu.getShowMenu()) {
				if(Menu.getShowOptions()) {
					Menu.setShowOptions(false);
				}
				else {
					Menu.setShowOptions(true);
					Menu.setShowRestart(false);
					Menu.setShowQuit(false);
				}
			}
		}
		// option 1
		if(screenX>secondMenuLeftX && screenX<secondMenuRightX && screenY>optionsY && screenY<optionsY+20) {
			if(Menu.getShowMenu() && Menu.getShowOptions()) {
					System.out.println("options1");
			}
		}
		// options 2
		if(screenX>secondMenuLeftX && screenX<secondMenuRightX && screenY>optionsY+20 && screenY<optionsY+40) {
			if(Menu.getShowMenu() && Menu.getShowOptions()) {
					System.out.println("options2");
			}
		}
		// options 3
		if(screenX>secondMenuLeftX && screenX<secondMenuRightX && screenY>optionsY+40 && screenY<optionsY+60) {
			if(Menu.getShowMenu() && Menu.getShowOptions()) {
					System.out.println("options3");
			}
		}
		// quit
		if(screenX>menuLeftX && screenX<menuRightX && screenY>quitY && screenY<quitY+19) {
			if(Menu.getShowMenu()) {
				if(Menu.getShowQuit()) {
					Menu.setShowQuit(false);
				}
				else {
					Menu.setShowQuit(true);
					Menu.setShowRestart(false);
					Menu.setShowOptions(false);
				}
			}
		}
		// quit confirmation:
			// no
				if(screenX>secondMenuLeftX && screenX<secondMenuLeftX+34 && screenY>quitY+20 && screenY<quitY+40) {
					if(Menu.getShowMenu() && Menu.getShowQuit()) {
						Menu.setShowQuit(false);
				}
			}
			// yes
			if(Menu.getShowMenu() && Menu.getShowQuit()) {
				if(screenX>secondMenuLeftX+35 && screenX<secondMenuLeftX+70 && screenY>quitY+20 && screenY<quitY+40) {
					if(Menu.getShowMenu() && Menu.getShowQuit()) {
						Gdx.app.exit();
					}
				}
			}
		
		
		return false;
	}
	
	@Override
	public void show() {      
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
        Gdx.input.setInputProcessor(null);	
        this.dispose();
	}
	@Override
	public void dispose() {
        Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// getters
	public static int getMenuLeftX() {
		return menuLeftX;
	}

	
	public static int getMenuRightX() {
		return menuRightX;
	}

	public static int getMenuTopY() {
		return menuY;
	}

	
}
