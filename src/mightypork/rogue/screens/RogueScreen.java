package mightypork.rogue.screens;


import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.core.events.UserQuitRequestListener;
import mightypork.gamecore.gui.screens.LayeredScreen;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.utils.annotations.Stub;


public class RogueScreen extends LayeredScreen implements UserQuitRequestListener {
	
	public RogueScreen(AppAccess app) {
		super(app);
	}
	
	
	@Override
	@Stub
	public void onQuitRequest(UserQuitRequest event)
	{
		getEventBus().send(new RogueStateRequest(RogueState.EXIT));
		event.consume();
	}
}
