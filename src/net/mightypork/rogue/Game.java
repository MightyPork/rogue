package net.mightypork.rogue;

import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.game.Mini2DxGame;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Game extends BasicGame {

	@Override
	public void initialise() {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void interpolate(float arg0) {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics arg0) {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float arg0) {

		// TODO Auto-generated method stub
		
	}
	
    public static void main(String [] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "A basic game";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;
        cfg.useCPUSynch = false;
        cfg.vSyncEnabled = true;
        new LwjglApplication(new Mini2DxGame(new Game()), cfg);
    }

}
