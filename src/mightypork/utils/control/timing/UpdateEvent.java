package mightypork.utils.control.timing;


import mightypork.utils.control.bus.Handleable;


public class UpdateEvent implements Handleable<Updateable> {
	
	private final double deltaTime;
	
	
	public UpdateEvent(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	
	@Override
	public void handleBy(Updateable handler)
	{
		handler.update(deltaTime);
	}
}
