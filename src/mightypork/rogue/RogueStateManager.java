package mightypork.rogue;


import mightypork.gamecore.core.App;
import mightypork.gamecore.gui.screens.impl.CrossfadeRequest;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.logging.Log;


public class RogueStateManager extends BusNode {
	
	public static enum RogueState
	{
		MAIN_MENU, SELECT_WORLD, PLAY_WORLD, EXIT, STORY
	}
	
	
	public void triggerAction(RogueState state, boolean fromDark)
	{
		switch (state) {
			case MAIN_MENU:
				App.bus().send(new CrossfadeRequest("main_menu", fromDark));
				break;
			
			case SELECT_WORLD:
				App.bus().send(new CrossfadeRequest("select_world", fromDark));
				break;
			
			case PLAY_WORLD:
				App.bus().send(new CrossfadeRequest("game", fromDark));
				break;
			
			case STORY:
				App.bus().send(new CrossfadeRequest("story", fromDark));
				break;
			
			case EXIT:
				App.bus().send(new CrossfadeRequest(null));
				break;
			
			default:
				Log.w("Unknown action: " + state);
				break;
		}
	}
	
}
