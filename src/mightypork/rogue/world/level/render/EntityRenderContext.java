package mightypork.rogue.world.level.render;


import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.level.LevelReadAccess;


public class EntityRenderContext extends MapRenderContext {
	
	public EntityRenderContext(LevelReadAccess map, Rect drawArea)
	{
		super(map, drawArea);
	}
	
}
