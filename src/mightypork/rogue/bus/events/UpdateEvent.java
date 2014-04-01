package mightypork.rogue.bus.events;

import mightypork.utils.patterns.subscription.Handleable;


public class UpdateEvent implements Handleable<UpdateEvent.Listener> {
	
	private final double deltaTime;
	
	
	public UpdateEvent(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	
	public double getDeltaTime()
	{
		return deltaTime;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.receive(this);
	}
	
	
	public interface Listener {
		
		public void receive(UpdateEvent event);
	}
}
