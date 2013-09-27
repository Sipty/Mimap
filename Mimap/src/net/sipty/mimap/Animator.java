package net.sipty.mimap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Animator {

        private static final int        FRAME_COLS = 4;         // #1
        private static final int        FRAME_ROWS = 1;         // #2
        
        private static Animation                walkAnimation;          // #3 --
        private Texture                         walkSheet;              // #4
        private TextureRegion[]                 walkFrames;             // #5
        private static TextureRegion            currentFrame;           // #7 --
        
        static float stateTime;                                         // #8 -- 
        
        public Animator() {
        		
                walkSheet = new Texture(Gdx.files.internal("player_walk_left.png"));     // #9
                TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);                                // #10
                walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
                int index = 0;
                for (int i = 0; i < FRAME_ROWS; i++) {
                        for (int j = 0; j < FRAME_COLS; j++) {
                                walkFrames[index++] = tmp[i][j];
                        }
                }
                walkAnimation = new Animation(0.16f, walkFrames);              // #11
                stateTime = 0f;                                                 // #13
        }

        public void draw(int x, int y) {
                //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
                stateTime += Gdx.graphics.getDeltaTime();                       // #15
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);      // #16
                GameScreen.game.batch.draw(currentFrame, x, y);                         // #17
        }
}
