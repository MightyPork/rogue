package mightypork.rogue.screens.ingame;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.Paths;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.World;
import mightypork.util.ion.Ion;


public class WorldLayer extends ScreenLayer {
	
	public WorldLayer(Screen screen)
	{
		super(screen);
		
		// FIXME just temporary test here
		
		final Random rand = new Random();
		final World w = MapGenerator.createWorld(rand.nextLong());
		
		try {
			Ion.toFile(new File(Paths.WORKDIR, "test-world.ion"), w);
		} catch (final IOException e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
		
//		final World w;
//		
//		try {
//			w = Ion.fromFile("amap.ion", World.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(1);
//			return;
//		}
		
		final WorldRenderer wr = new WorldRenderer(w);
		wr.setRect(root);
		root.add(wr);
		
//		bindKey(new KeyStroke(true, Keys.LEFT), new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				w.getPlayer().walk(-1, 0);
//			}
//		});
//		bindKey(new KeyStroke(true, Keys.RIGHT), new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				w.getPlayer().walk(1, 0);
//			}
//		});
//		bindKey(new KeyStroke(true, Keys.UP), new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				w.getPlayer().walk(0, -1);
//			}
//		});
//		bindKey(new KeyStroke(true, Keys.DOWN), new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				w.getPlayer().walk(0, 1);
//			}
//		});
//		bindKey(new KeyStroke(true, Keys.SPACE), new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				w.getPlayer().walk(5, 5);
//			}
//		});
//		
//		w.getPlayer().setMoveListener(new Runnable() {
//			
//			@Override
//			public void run()
//			{
//				if (InputSystem.isKeyDown(Keys.LEFT)) {
//					w.getPlayer().walk(-1, 0);
//				} else if (InputSystem.isKeyDown(Keys.RIGHT)) {
//					w.getPlayer().walk(1, 0);
//				} else if (InputSystem.isKeyDown(Keys.UP)) {
//					w.getPlayer().walk(0, -1);
//				} else if (InputSystem.isKeyDown(Keys.DOWN)) {
//					w.getPlayer().walk(0, 1);
//				}
//			}
//		});
	}
	
	
	@Override
	public int getPriority()
	{
		return -1;
	}
	
}
