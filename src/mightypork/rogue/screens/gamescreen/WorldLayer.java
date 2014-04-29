package mightypork.rogue.screens.gamescreen;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.gui.interaction.MIPClickPathfWalk;
import mightypork.rogue.world.gui.interaction.MIPKeyWalk;
import mightypork.util.math.constraints.num.Num;


public class WorldLayer extends ScreenLayer {
	
	private final MapView worldView;
	
	
	public WorldLayer(Screen screen)
	{
		super(screen);
		
		// render component
		
		worldView = new MapView();
		
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
