package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.gui.interaction.MIPKeyboard;
import mightypork.rogue.world.gui.interaction.MIPMouse;


public class WorldLayer extends ScreenLayer {
	
	private final MapView worldView;
	
	
	public WorldLayer(Screen screen)
	{
		super(screen);
		
		// render component
		
		worldView = new MapView();
		
		// map input plugins
		worldView.addPlugin(new MIPKeyboard(worldView));
		worldView.addPlugin(new MIPMouse(worldView));
		
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
