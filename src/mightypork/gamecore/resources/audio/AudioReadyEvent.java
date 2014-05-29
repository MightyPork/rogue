package mightypork.gamecore.resources.audio;


import mightypork.utils.eventbus.BusEvent;


public class AudioReadyEvent extends BusEvent<AudioReadyListener> {
	
	@Override
	protected void handleBy(AudioReadyListener handler)
	{
		handler.onInputReady();
	}
	
}
