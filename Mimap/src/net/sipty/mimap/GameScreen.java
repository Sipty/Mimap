package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class GameScreen implements Screen, InputProcessor {
	
	// declarations
	
	public static Mimap game;
	private OrthographicCamera camera;
	private int posX, posY, posYreversed;	// mouse coord info
	
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

	// constructor
	public GameScreen(final Mimap gam) {
		GameScreen.game = gam;
		
		//camera stuffs
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
	}
	
	@Override
	public void render(float delta) {
		// gl and camera stuffs
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
//fps.log();
		// batch begin
			game.batch.begin();
			// mouse coords info
			posX = Gdx.input.getX();
			posY = Gdx.input.getY();
			posYreversed = 720-posY;
			game.font.draw(game.batch,  "Mouse coords: "+Integer.toString(posX)+", "+Integer.toString(posY)+" /"+Integer.toString(posYreversed), 1150, 700);
			game.font.draw(game.batch,  "Player coords: "+Float.toString(Player.getPlayer_X())+", "+Float.toString(Player.getPlayer_Y()), 1150, 680);
			
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
