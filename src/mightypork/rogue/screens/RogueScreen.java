package mightypork.rogue.screens;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.ShutdownRequest;
import mightypork.gamecore.core.events.ShutdownRequestListener;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.utils.annotations.Stub;


public class RogueScreen extends LayeredScreen implements ShutdownRequestListener {


	@Override
	@Stub
	public void onShutdownRequested(ShutdownRequest event)
	{
		App.bus().send(new RogueStateRequest(RogueState.EXIT));
		//event.consume(); FIXME we need a "shutdown request" event AND "shutdown" event.
	}
}
