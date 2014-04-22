package mightypork.rogue.world.entity.renderers;


import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.render.EntityRenderContext;


public abstract class EntityRenderer {
	
	public static final EntityRenderer NONE = new NullEntityRenderer();
	
	
	public abstract void render(Entity entity, EntityRenderContext context);
	
}
