package mightypork.rogue.world.entity;


import mightypork.rogue.world.level.render.MapRenderContext;


/**
 * Entity renderer
 * 
 * @author Ondřej Hruška
 */
public abstract class EntityRenderer {
	
	public abstract void render(MapRenderContext context);
	
}
