package mightypork.rogue.screens.ingame;


import java.util.Random;

import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.world.MapGenerator;
import mightypork.rogue.world.World;


public class WorldLayer extends ScreenLayer {
	
	public WorldLayer(Screen screen)
	{
		super(screen);
		
		Random rand = new Random();
		World w = MapGenerator.createWorld(rand.nextLong());
		
		WorldRenderer wr = new WorldRenderer(w);
		wr.setRect(root);
		root.add(wr);
	}
	
	@Override
	public int getPriority()
	{
		return -1;
	}
	
}
