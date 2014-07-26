package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.utils.math.constraints.rect.RectBound;


/**
 * Layout for components with arbitrary constraints.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class ConstraintLayout extends LayoutComponent {
	
	public ConstraintLayout() {
	}
	
	
	public ConstraintLayout(RectBound context) {
		super(context);
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
