package mightypork.rogue;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.AppModule;
import mightypork.gamecore.gui.events.CrossfadeRequest;
import mightypork.gamecore.logging.Log;


public class RogueStateManager extends AppModule {
	
	public RogueStateManager(AppAccess app)
	{
		super(app);
	}
	
	public static enum RogueState
	{
		MAIN_MENU, SELECT_WORLD, PLAY_WORLD, EXIT
	}
	
	
	public void triggerAction(RogueState state)
	{
		switch (state) {
			case MAIN_MENU:
				getEventBus().send(new CrossfadeRequest("main_menu"));
				break;
			
			case SELECT_WORLD:
				getEventBus().send(new CrossfadeRequest("select_world"));
				break;
			
			case PLAY_WORLD:
				getEventBus().send(new CrossfadeRequest("game"));
				break;
			
			case EXIT:
				getEventBus().send(new CrossfadeRequest(null));
				break;
			
			default:
				Log.w("Unknown action: " + state);
				break;
		}
	}
	
	
}
