package net.sipty.mimap;

import net.sipty.mimap.Entity.Side;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class InHouseScreen implements Screen, InputProcessor {
	
	// declarations
	
	public static Mimap game;
	private OrthographicCamera camera;
	private TiledMap map;					 	// le map
	private OrthogonalTiledMapRenderer renderer;	// map renderer
	private int posX, posY, posYreversed;	// mouse coord info
	
	private final static int menuLeftX=45,	// drop down menu coords
							 //menuRightX=105, 
							 menuRightX=Menu.getRightMenuX(),
							 //menuY=15,
							 menuY=Mimap.SCREEN_Y - Menu.getMenuY()-5,	// the Menu.menuY starts at the top left corner, thus the substraction
							 											// the -5 is there to fix an unknown offset
							 
							 secondMenuLeftX=menuRightX+1,	// second menu
							 secondMenuRightX=205,
							 
							 optionsY=menuY+45,	// options menu coords
							 
							 quitY=menuY+65, // quit coords
							 
							 restartY=menuY+25;	// restart coords
	
//FPSLogger fps = new FPSLogger();
	
	Player player;	// initialized in show()

	// constructor
	public InHouseScreen(final Mimap gam) {
		InHouseScreen.game = gam;
		
		//camera stuffs
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
	}
	
	@Override
	public void render(float delta) {
		// gl and camera stuffs
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		renderer.setView(camera);
		renderer. render();	// render le map

		camera.position.x = Mimap.SCREEN_X/2;
		camera.position.y = Mimap.SCREEN_Y/2;
		camera.update();
		
		// let the camera follow the koala, x-axis only
//fps.log();
		// batch begin
			game.batch.begin();
			// debugging info
			posX = Gdx.input.getX();
			posY = Gdx.input.getY();
			posYreversed = 720-posY;
			game.font.draw(game.batch,  "Mouse coords: "+Integer.toString(posX)+", "+Integer.toString(posY)+" /"+Integer.toString(posYreversed), 1050, 700);
			game.font.draw(game.batch,  "Player coords: "+Float.toString(player.getX())+", "+Float.toString(player.getY()), 1050, 680);
			
			// menu
			Menu.draw();

		// Player:
			// standing
			player.drawStand(player.getX(), player.getY(), Player.getSide());
			// left
			if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) 
				player.move(Side.LEFT);
			// right
			if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) 
				player.move(Side.RIGHT);
			// up
			if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) 
				player.move(Side.UP);
			// down
			if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) 
				player.move(Side.DOWN);

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
		
		map = new TmxMapLoader().load("map/map.tmx");	// create le map
		renderer = new OrthogonalTiledMapRenderer(map);	// feed it le map
		
		camera = new OrthographicCamera();

		player = new Player((TiledMapTileLayer) map.getLayers().get(0));
	}

	@Override
	public void hide() {
        Gdx.input.setInputProcessor(null);	
        this.dispose();
        dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}
	
	@Override
	public void dispose() {
        Gdx.input.setInputProcessor(null);
        
        map.dispose();
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
