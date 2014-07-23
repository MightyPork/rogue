package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.utils.math.constraints.rect.RectBound;


/**
 * Layout for components with arbitrary constraints.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ConstraintLayout extends LayoutComponent {
	
	public ConstraintLayout(AppAccess app) {
		super(app);
	}
	
	
	public ConstraintLayout(AppAccess app, RectBound context) {
		super(app, context);
	}
	
	
	/**
	 * Add a component to the layout.<br>
	 * The component's rect must be set up manually.
	 * 
	 * @param component
	 */
	public void add(Component component)
	{
		attach(component);
	}
	
}
