package net.sipty.mimap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mimap extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	public void create() {		
		
		// allows us to use any image as a texture
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();	// important!!
	}
	
	@Override
	public void dispose() {

		batch.dispose();
		font.dispose();
	}

}
