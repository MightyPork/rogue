package mightypork.utils.control.bus.events;


import mightypork.utils.control.bus.Handleable;
import mightypork.utils.control.interf.Destroyable;


public class DestroyEvent implements Handleable<Destroyable> {
	
	@Override
	public void handleBy(Destroyable handler)
	{
		handler.destroy();
	}
	
}
