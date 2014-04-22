package mightypork.rogue.screens.ingame;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.InputSystem;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.rogue.Paths;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.World;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.models.EntityMoveListener;
import mightypork.rogue.world.level.Level;
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
		
		final PlayerControl c = w.getPlayerControl();
		
		bindKey(new KeyStroke(true, Keys.LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				c.walkWest();
			}
		});
		
		bindKey(new KeyStroke(true, Keys.RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				c.walkEast();
			}
		});
		
		bindKey(new KeyStroke(true, Keys.UP), new Runnable() {
			
			@Override
			public void run()
			{
				c.walkNorth();
			}
		});
		
		bindKey(new KeyStroke(true, Keys.DOWN), new Runnable() {
			
			@Override
			public void run()
			{
				c.walkSouth();
			}
		});
		
		c.addMoveListener(new EntityMoveListener() {
			
			private void tryGo(Entity entity)
			{
				if (InputSystem.isKeyDown(Keys.LEFT)) {
					c.walkWest();
				} else if (InputSystem.isKeyDown(Keys.RIGHT)) {
					c.walkEast();
				} else if (InputSystem.isKeyDown(Keys.UP)) {
					c.walkNorth();
				} else if (InputSystem.isKeyDown(Keys.DOWN)) {
					c.walkSouth();
				}
			}
			
			
			@Override
			public void onStepFinished(Entity entity, World world, Level level)
			{
				entity.cancelPath(); // halt
				tryGo(entity);
			}
			
			
			@Override
			public void onPathFinished(Entity entity, World world, Level level)
			{
				entity.cancelPath(); // halt
				tryGo(entity);
			}
			
			
			@Override
			public void onPathAborted(Entity entity, World world, Level level)
			{
			}
		});
	}
	
	
	@Override
	public int getPriority()
	{
		return -1;
	}
	
}
