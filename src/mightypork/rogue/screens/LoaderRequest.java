package mightypork.rogue.screens;

import mightypork.gamecore.eventbus.BusEvent;


public class LoaderRequest extends BusEvent<LoadingOverlay> {
	
	private final boolean show;
	private final String msg;
	

	public LoaderRequest(boolean show, String msg) {
		this.show = show;
		this.msg = msg;
	}

	public LoaderRequest(boolean show) {
		this.show = show;
		this.msg = null;
	}

	@Override
	protected void handleBy(LoadingOverlay handler)
	{
		if(show) {
			handler.show(msg);
		}else {
			handler.hide();
		}
	}
	
}
