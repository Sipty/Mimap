package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class GameScreen implements Screen, InputProcessor {
	
	// declarations
	
	public static Mimap game;
	private OrthographicCamera camera;
	private Texture background;
	private int posX, posY, posYreversed;	// mouse coord info
	
	private final static int menuLeftX=45,	// drop down menu coords
							 menuRightX=105, 
							 menuY=15,
							 
							 optionsLeftX=menuRightX+1,	// options menu coords
							 optionsRightX=205,
							 optionsY=menuY+45;
	
	// constructor
	public GameScreen(final Mimap gam) {
		GameScreen.game = gam;
		
		//camera stuffs
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
		
		// load textures
		background = new Texture(Gdx.files.internal("intro_background.jpg"));
	}

	@Override
	public void render(float delta) {
		// gl and camera stuffs
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// batch begin
			game.batch.begin();
			// mouse coords info
			posX = Gdx.input.getX();
			posY = Gdx.input.getY();
			posYreversed = 720-posY;
			game.batch.draw(background, 0,0);
			game.font.draw(game.batch,  Integer.toString(posX)+", "+Integer.toString(posY)+" /"+Integer.toString(posYreversed), 1150, 700);
			
			//Menu
			Menu.draw();
		// batch end
		game.batch.end();
				
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		// menu
		if(screenX>menuLeftX && screenX<menuRightX-10 && screenY>menuY && screenY<menuY+24)
			if(Menu.getShowMenu()) {
				Menu.setShowMenu(false);
				Menu.setShowOptions(false);
			}
			else
				Menu.setShowMenu(true);
			
			// restart
			if(screenX>menuLeftX && screenX<menuRightX && screenY>menuY+25 && screenY<menuY+44)
				if(Menu.getShowMenu())
					System.out.println("restart");
			
			// options
			if(screenX>menuLeftX && screenX<menuRightX && screenY>menuY+45 && screenY<menuY+64)
				if(Menu.getShowMenu())
					if(Menu.getShowOptions())
						Menu.setShowOptions(false);
					else
						Menu.setShowOptions(true);			
				// option 1
				if(screenX>optionsLeftX && screenX<optionsRightX && screenY>optionsY && screenY<optionsY+20) {
					if(Menu.getShowMenu()) {
						if(Menu.getShowOptions());
							System.out.println("options1");
					}
				}
				// options 2
				if(screenX>optionsLeftX && screenX<optionsRightX && screenY>optionsY+20 && screenY<optionsY+40) {
					if(Menu.getShowMenu()) {
						if(Menu.getShowOptions());
							System.out.println("options2");
					}
				}
				// options 3
				if(screenX>optionsLeftX && screenX<optionsRightX && screenY>optionsY+40 && screenY<optionsY+60) {
					if(Menu.getShowMenu()) {
						if(Menu.getShowOptions());
							System.out.println("options3");
					}
				}
			
			// quit
			if(screenX>menuLeftX && screenX<menuRightX && screenY>menuY+65 && screenY<menuY+84)
				if(Menu.getShowMenu())
					System.out.println("quit");
		
		return false;
	}
	
	@Override
	public void show() {      
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
        Gdx.input.setInputProcessor(null);		
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
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
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
