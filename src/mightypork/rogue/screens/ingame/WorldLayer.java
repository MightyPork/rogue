package mightypork.rogue.screens.ingame;


import java.io.IOException;
import java.util.Random;

import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.World;
import mightypork.util.files.ion.Ion;


public class WorldLayer extends ScreenLayer {
	
	public WorldLayer(Screen screen)
	{
		super(screen);
		
		final Random rand = new Random();
		final World w = MapGenerator.createWorld(rand.nextLong());
		try {
			Ion.toFile("amap.ion", w);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		final WorldRenderer wr = new WorldRenderer(w);
		wr.setRect(root);
		root.add(wr);
	}
	
	
	@Override
	public int getPriority()
	{
		return -1;
	}
	
}
