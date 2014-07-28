package mightypork.rogue.screens;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.ShutdownEvent;
import mightypork.gamecore.core.events.ShutdownListener;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.utils.annotations.Stub;


public class RogueScreen extends LayeredScreen implements ShutdownListener {


	@Override
	@Stub
	public void onShutdown(ShutdownEvent event)
	{
		App.bus().send(new RogueStateRequest(RogueState.EXIT));
		event.consume();
	}
}
