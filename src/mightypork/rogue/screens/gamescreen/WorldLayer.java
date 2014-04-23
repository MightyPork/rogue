package mightypork.rogue.screens.gamescreen;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.Paths;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.World;
import mightypork.util.constraints.num.Num;
import mightypork.util.ion.Ion;


public class WorldLayer extends ScreenLayer {
	
	private World world;
	private InputComponent worldView;
	
	
	public WorldLayer(Screen screen) {
		super(screen);
		
		// FIXME just temporary test here
		
		final Random rand = new Random();
		final File f = new File(Paths.WORKDIR, "test-world.ion");
		
		// SAVE
		
		world = MapGenerator.createWorld(rand.nextLong());
		updated.add(world);
		
		try {
			Ion.toFile(f, world);
		} catch (final IOException e) {
			e.printStackTrace();
			System.exit(1); // fail
			return;
		}
		
		// LOAD
		
//		final World w;
//		
//		try {
//			world = Ion.fromFile(f, World.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(1);
//			return;
//		}
		
		
		
		// render component
		
		worldView = new WRBasicControls(world);
		
		// size of lower navbar
		final Num lownav = root.width().min(root.height()).max(700).perc(7);
		worldView.setRect(root.shrinkBottom(lownav));
		
		root.add(worldView);
	}
	
	
	@Override
	public int getPriority()
	{
		return -1; // stay down
	}
	
}
