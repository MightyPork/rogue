package mightypork.rogue.init;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.init.InitTaskUI;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.gamecore.gui.screens.impl.CrossfadeOverlay;
import mightypork.rogue.RogueStateManager;
import mightypork.rogue.screens.FpsOverlay;
import mightypork.rogue.screens.LoadingOverlay;
import mightypork.rogue.screens.game.ScreenGame;
import mightypork.rogue.screens.menu.ScreenMainMenu;
import mightypork.rogue.screens.select_world.ScreenSelectWorld;
import mightypork.rogue.screens.story.ScreenStory;
import mightypork.rogue.world.WorldProvider;


public class RogueInitUI extends InitTaskUI {

	@Override
	protected Renderable createMainRenderable()
	{
		final ScreenRegistry screens = new ScreenRegistry();
		app.addChildClient(screens);
		
		/* game screen references world provider instance */
		App.bus().subscribe(new RogueStateManager());
		App.bus().subscribe(WorldProvider.get());
		
		screens.addOverlay(new CrossfadeOverlay());

		screens.addScreen("main_menu", new ScreenMainMenu());
		screens.addScreen("select_world", new ScreenSelectWorld());
		screens.addScreen("game", new ScreenGame());
		screens.addScreen("story", new ScreenStory());

		screens.addOverlay(new FpsOverlay());
		screens.addOverlay(new LoadingOverlay());
		
		return screens;
	}
}
