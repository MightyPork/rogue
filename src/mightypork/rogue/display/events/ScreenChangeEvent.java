package mightypork.rogue.display.events;


import mightypork.utils.math.coord.Coord;
import mightypork.utils.patterns.subscription.Handleable;


public class ScreenChangeEvent implements Handleable<ScreenChangeEvent.Listener> {

	private boolean fullscreen;
	private Coord screenSize;
	private boolean fsChanged;


	public ScreenChangeEvent(boolean fsChanged, boolean fullscreen, Coord size) {
		this.fullscreen = fullscreen;
		this.screenSize = size;
		this.fsChanged = fsChanged;
	}


	public boolean isFullscreen()
	{
		return fullscreen;
	}


	public boolean fullscreenChanged()
	{
		return fsChanged;
	}


	public Coord getScreenSize()
	{
		return screenSize;
	}


	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}

	public interface Listener {

		public void receive(ScreenChangeEvent event);
	}
}
