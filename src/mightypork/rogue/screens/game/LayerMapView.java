package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.rogue.world.gui.MapView;
import mightypork.rogue.world.gui.interaction.MIPKeyboard;
import mightypork.rogue.world.gui.interaction.MIPMouse;


public class LayerMapView extends ScreenLayer {
	
	protected final MapView map;
	
	
	public LayerMapView(Screen screen)
	{
		super(screen);
		
		// render component
		
		map = new MapView();
		
		// map input plugins
		map.addPlugin(new MIPKeyboard(map));
		map.addPlugin(new MIPMouse(map));
		
		// size of lower navbar
		final Num lownav = root.height().perc(12);
		map.setRect(root.shrinkBottom(lownav));
		
		root.add(map);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 0; // stay down
	}
	
	
	@Override
	public int getEventPriority()
	{
		return 100;
	}
	
}
