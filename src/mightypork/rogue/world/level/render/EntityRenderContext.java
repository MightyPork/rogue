package mightypork.rogue.world.level.render;


import mightypork.rogue.world.level.MapAccess;
import mightypork.util.math.constraints.rect.Rect;


public class EntityRenderContext extends MapRenderContext {
	
	public EntityRenderContext(MapAccess map, Rect drawArea)
	{
		super(map, drawArea);
	}
	
}
