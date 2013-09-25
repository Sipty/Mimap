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
	private int posX, posY;	// mouse coord info
				// coordinates for the box array (used in Mech.tick(), the drawing method)
	public static int	x1=145, x2=335, x3=530, y1=342, y2=180, y3=17;	
	//TODO: PLEASE, IN THE NAME OF EVERYTHING - MAKE THESE COORDS PRETTIER.
	// constructor
	public GameScreen(final Mimap gam) {
		GameScreen.game = gam;
		
		//camera stuffs
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1280,720);
		
		
		
		// load textures
		background = new Texture(Gdx.files.internal("intro_background.jpg"));

		//new Mech();
		
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
			game.batch.draw(background, 0,0);
			game.font.draw(game.batch,  Integer.toString(posX)+", ", 1200, 700);
			game.font.draw(game.batch,  Integer.toString(posY), 1230, 700);
		// batch end
		game.batch.end();
				
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
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

}
