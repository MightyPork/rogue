package mightypork.rogue.screens.gamescreen;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.screens.gamescreen.world.MIPClickPathfWalk;
import mightypork.rogue.screens.gamescreen.world.MIPKeyWalk;
import mightypork.rogue.screens.gamescreen.world.MapView;
import mightypork.rogue.world.World;
import mightypork.util.math.constraints.num.Num;


public class WorldLayer extends ScreenLayer {
	
	private final MapView worldView;
	
	
	public WorldLayer(Screen screen, World world)
	{
		super(screen);
		
		// render component
		
		worldView = new MapView(world);
		
		// map input plugins
		worldView.addPlugin(new MIPKeyWalk());
		worldView.addPlugin(new MIPClickPathfWalk());
		//worldView.addPlugin(new MIPMouseWalk());
		
		// size of lower navbar
		final Num lownav = root.width().min(root.height()).max(700).perc(7);
		worldView.setRect(root.shrinkBottom(lownav));
		
		root.add(worldView);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 0; // stay down
	}
	
	
	@Override
	public int getEventPriority()
	{
		return 0;
	}
	
}
