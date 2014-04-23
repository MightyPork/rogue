package mightypork.gamecore.gui.components;


import mightypork.gamecore.control.events.input.MouseButtonEvent;
import mightypork.gamecore.control.events.input.MouseButtonListener;
import mightypork.util.control.Action;
import mightypork.util.control.ActionTrigger;


public abstract class ClickableComponent extends InputComponent implements ActionTrigger, MouseButtonListener {
	
	private boolean btnDownOver;
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
