package mightypork.rogue.bus.events;


import mightypork.utils.control.bus.Event;


public class ScreenRequestEvent implements Event<ScreenRequestEvent.Listener> {
	
	private String scrName;
	
	
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
