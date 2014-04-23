package mightypork.rogue.screens.gamescreen;


import java.io.File;
import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.Paths;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.World;
import mightypork.util.files.ion.Ion;


public class ScreenGame extends LayeredScreen {
	
	public ScreenGame(AppAccess app)
	{
		super(app);
		
		addLayer(new WorldLayer(this, obtainWorld())); //TODO with provided world
		addLayer(new HudLayer(this));
	}
	
	
	private World obtainWorld()
	{
		
		// FIXME just temporary test here
		
		final Random rand = new Random();
		final File f = new File(Paths.WORKDIR, "test-world.ion");
		
		// SAVE
		
		final World world = MapGenerator.createWorld(rand.nextLong());
		addChildClient(world);
		
		try {
			Ion.toFile(f, world);
		} catch (final IOException e) {
			e.printStackTrace();
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
		
		return world;
	}
	
	
	@Override
	public String getName()
	{
		return "game_screen";
	}
	
}
