package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.Event;
import mightypork.utils.control.bus.SingularEvent;


public class ScreenRequestEvent implements Event<ScreenRequestEvent.Listener>, SingularEvent {
	
	private final String scrName;
	
	
	public ScreenRequestEvent(String screenKey) {
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(Listener handler)
	{
		handler.showScreen(scrName);
	}
	
	public interface Listener {
		
		public void showScreen(String key);
	}
	
}
