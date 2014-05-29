package mightypork.rogue;


import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.core.modules.AppModule;
import mightypork.gamecore.gui.screens.impl.CrossfadeRequest;
import mightypork.utils.logging.Log;


public class RogueStateManager extends AppModule {
	
	public RogueStateManager(AppAccess app)
	{
		super(app);
	}
	
	public static enum RogueState
	{
		MAIN_MENU, SELECT_WORLD, PLAY_WORLD, EXIT, STORY
	}
	
	
	public void triggerAction(RogueState state, boolean fromDark)
	{
		switch (state) {
			case MAIN_MENU:
				getEventBus().send(new CrossfadeRequest("main_menu", fromDark));
				break;
			
			case SELECT_WORLD:
				getEventBus().send(new CrossfadeRequest("select_world", fromDark));
				break;
			
			case PLAY_WORLD:
				getEventBus().send(new CrossfadeRequest("game", fromDark));
				break;
			
			case STORY:
				getEventBus().send(new CrossfadeRequest("story", fromDark));
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
