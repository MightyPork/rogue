package mightypork.gamecore.gui.components.input;


import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.ActionTrigger;
import mightypork.gamecore.gui.components.InputComponent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseButtonHandler;


public abstract class ClickableComponent extends InputComponent implements ActionTrigger, MouseButtonHandler {
	
	protected boolean btnDownOver;
	private Action action;
	
	
	@Override
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	
	protected void triggerAction()
	{
		if (action != null && isEnabled()) action.run();
	}
	
	
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
				event.consume();
			}
			
			btnDownOver = false;
		}
	}
}
