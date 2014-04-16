package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.events.MouseButtonEvent;


public abstract class ClickableComponent extends InputComponent {
	
	private boolean btnDownOver;
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (!event.isButtonEvent()) return;
		
		if (event.isDown()) {
			btnDownOver = event.isOver(this);
		}
		
		if (event.isUp()) {
			
			if (btnDownOver && event.isOver(this)) {
				triggerAction();
			}
			
			btnDownOver = false;
		}
	}
}
