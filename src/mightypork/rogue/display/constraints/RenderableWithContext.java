package mightypork.rogue.display.constraints;


import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.constraints.SettableContext;


/**
 * {@link Renderable} with {@link SettableContext}
 * 
 * @author MightyPork
 */
public interface RenderableWithContext extends Renderable, SettableContext {
	
	@Override
	public void render();
	
	
	@Override
	public void setContext(ConstraintContext context);
}
