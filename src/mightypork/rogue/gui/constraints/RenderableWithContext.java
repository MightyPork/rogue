package mightypork.rogue.gui.constraints;


import mightypork.rogue.render.Renderable;
import mightypork.utils.math.constraints.ConstraintContext;
import mightypork.utils.math.constraints.SettableContext;


/**
 * {@link Renderable} with {@link SettableContext}
 * 
 * @author MightyPork
 */
public interface RenderableWithContext extends Renderable, SettableContext {
	
	@Override
	void setContext(ConstraintContext context);
}
