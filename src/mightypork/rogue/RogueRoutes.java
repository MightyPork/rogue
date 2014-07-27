package mightypork.rogue;


public class RogueRoutes implements RouteSetup {
	
	@Override
	public void addRoutes(RouteOpts routeOpts)
	{
		routeOpts.addPath("slot1", "saves/slot_1.ion");
		routeOpts.addPath("slot2", "saves/slot_2.ion");
		routeOpts.addPath("slot3", "saves/slot_3.ion");
	}
	
}
