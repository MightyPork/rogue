package mightypork.rogue.screens;

import mightypork.gamecore.eventbus.BusEvent;


public class LoaderRequest extends BusEvent<LoadingOverlay> {
	
	private final String msg;
	private Runnable task;
	

	public LoaderRequest(String msg, Runnable task) {
		this.task = task;
		this.msg = msg;
	}
	@Override
	protected void handleBy(LoadingOverlay handler)
	{
		handler.show(msg, task);
	}
	
}
