package mightypork.rogue.world.gui;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseButtonHandler;
import mightypork.gamecore.render.Render;
import mightypork.rogue.world.PlayerControl;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.WorldRenderer;
import mightypork.rogue.world.events.WorldAscendRequestListener;
import mightypork.rogue.world.events.WorldDescendRequestListener;
import mightypork.rogue.world.gui.interaction.MapInteractionPlugin;
import mightypork.utils.eventbus.clients.DelegatingClient;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.animation.NumAnimated;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.timing.TimedTask;


/**
 * Level display component
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class MapView extends InputComponent implements DelegatingClient, MouseButtonHandler, Updateable, WorldAscendRequestListener,
		WorldDescendRequestListener {
	
	private static final double transition_time = 0.8;
	
	protected final WorldRenderer worldRenderer;
	public final PlayerControl plc;
	
	private final Set<MapInteractionPlugin> plugins = new LinkedHashSet<>();
	private final NumAnimated zoom = new NumAnimated(0, Easing.SINE_BOTH);
	private boolean zoom_in = true;
	
	private final NumAnimated descFadeAnim = new NumAnimated(0);
	private final Color blackColor = RGB.BLACK.withAlpha(descFadeAnim);
	
	private int descDir = 0;
	
	private final TimedTask timerDesc1 = new TimedTask() {
		
		@Override
		public void run()
		{
			descFadeAnim.fadeOut(transition_time);
			timerDesc2.start(transition_time);
			if (descDir == 1) {
				WorldProvider.get().getWorld().getPlayer().descend();
			} else {
				WorldProvider.get().getWorld().getPlayer().ascend();
			}
		}
	};
	
	private final TimedTask timerDesc2 = new TimedTask() {
		
		@Override
		public void run()
		{
			WorldProvider.get().getWorld().resume();
		}
	};
	
	
	private final Num tileSize;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection getChildClients()
	{
		return plugins;
	}
	
	
	@Override
	public boolean doesDelegate()
	{
		return true;
	}
	
	
	public MapView()
	{
		this.tileSize = height().min(width()).div(9).max(32).mul(Num.make(1).sub(zoom.mul(0.5)));
		this.worldRenderer = new WorldRenderer(this, tileSize);
		plc = WorldProvider.get().getPlayerControl();
		
		zoom.setDefaultDuration(0.5);
	}
	
	
	@Override
	protected void renderComponent()
	{
		worldRenderer.render();
		
		Render.quadColor(this, blackColor);
	}
	
	
	/**
	 * Get tile coord at a screen position
	 * 
	 * @param pos position on screen (px)
	 * @return position on map (tiles)
	 */
	public Vect toWorldPos(Vect pos)
	{
		return worldRenderer.getClickedTile(pos);
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isOver(this)) return;
		
		if (event.isButtonEvent()) {
			for (final MapInteractionPlugin p : plugins) {
				if (p.onClick(event.getPos(), event.getButton(), event.isDown())) {
					event.consume();
					break;
				}
			}
		}
		
		if (event.isWheelEvent()) {
			final int delta = event.getWheelDelta();
			if (!zoom.isFinished()) return;
			if (delta < 0) {
				zoom.fadeIn();
				zoom_in = false;
			} else {
				zoom.fadeOut();
				zoom_in = true;
			}
		}
	}
	
	
	public void toggleMag()
	{
		if (zoom_in) {
			zoom.fadeIn();
			zoom_in = false;
		} else {
			zoom.fadeOut();
			zoom_in = true;
		}
	}
	
	
	/**
	 * Add interaction plugin
	 * 
	 * @param plugin
	 */
	public void addPlugin(MapInteractionPlugin plugin)
	{
		plugins.add(plugin);
	}
	
	
	@Override
	public void update(double delta)
	{
		zoom.update(delta);
		descFadeAnim.update(delta);
		timerDesc1.update(delta);
		timerDesc2.update(delta);
	}
	
	
	@Override
	public void onAscendRequest()
	{
		if (descFadeAnim.isInProgress()) return;
		
		final World w = WorldProvider.get().getWorld();
		
		if (w.getPlayer().canAscend()) {
			descDir = -1;
			startDescAnim();
		}
	}
	
	
	private void startDescAnim()
	{
		WorldProvider.get().getWorld().pause();
		
		timerDesc2.stop();
		timerDesc1.start(transition_time);
		descFadeAnim.setTo(0);
		descFadeAnim.fadeIn(transition_time);
	}
	
	
	@Override
	public void onDescendRequest()
	{
		if (descFadeAnim.isInProgress()) return;
		
		final World w = WorldProvider.get().getWorld();
		
		if (w.getPlayer().canDescend()) {
			descDir = 1;
			startDescAnim();
		}
	}
}
