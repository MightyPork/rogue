package mightypork.rogue.world.entity.renderers;


import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.render.MapRenderContext;


public abstract class EntityRenderer {
	
	protected final Entity entity;
	
	
	public EntityRenderer(Entity entity) {
		this.entity = entity;
	}
	
	
	public abstract void render(MapRenderContext context);
	
}
