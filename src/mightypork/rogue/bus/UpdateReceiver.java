package mightypork.rogue.bus;


import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.events.UpdateEvent;
import mightypork.utils.time.Updateable;


public abstract class UpdateReceiver extends SimpleBusClient implements UpdateEvent.Listener, Updateable {
	
	public UpdateReceiver(AppAccess app) {
		super(app);
	}
	
	
	@Override
	public void receive(UpdateEvent event)
	{
		update(event.getDeltaTime());
	}
	
	
	@Override
	public abstract void update(double delta);
	
}
