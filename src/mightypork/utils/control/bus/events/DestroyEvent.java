package mightypork.utils.control.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.interf.Destroyable;


public class DestroyEvent implements Event<Destroyable> {
	
	@Override
	public void handleBy(Destroyable handler)
	{
		handler.destroy();
	}
	
}
