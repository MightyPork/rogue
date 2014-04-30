package mightypork.rogue.world.level.render;


import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.world.level.MapAccess;


public class EntityRenderContext extends MapRenderContext {
	
	public EntityRenderContext(MapAccess map, Rect drawArea)
	{
		super(map, drawArea);
	}
	
}
