package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class IntroScreen implements Screen, InputProcessor {

	// declarations
	private Mimap game;
	private OrthographicCamera camera;
	private Texture background;
	
	// constructor 
	public IntroScreen(final Mimap gam) {
		game = gam;
		
		// prep camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		// load texture
		background = new Texture(Gdx.files.internal("intro_background.jpg"));
	}
	
	@Override
	public void render(float delta) {
		// screen cleaner
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// camera work
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		// batch begin
		game.batch.begin();
		game.batch.draw(background, 0,0);
		game.font.draw(game.batch, "Mimap", 365, 50);
		game.font.draw(game.batch, "Press any button", 380, 25);
		game.font.draw(game.batch, "10/10 'Better than CoD'- IGN 2013", 100, 150);
		// batch end
		game.batch.end();
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		game.setScreen(new GameScreen(game));
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		//game.setScreen(new SetupScreen(game));
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
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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