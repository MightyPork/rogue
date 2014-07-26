package mightypork.gamecore.gui.components;


import mightypork.utils.interfaces.Enableable;
import mightypork.utils.interfaces.Hideable;
import mightypork.utils.math.constraints.num.Num;


/**
 * Basic UI component interface
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface Component extends Enableable, Hideable, PluggableRenderable {
	
	/**
	 * Render the component, if it is visible.
	 */
	@Override
	void render();
	
	
	/**
	 * The bounding rect was changed. The component should now update any cached
	 * constraints derived from it.
	 */
	void updateLayout();
	
	
	/**
	 * @return true if mouse is currently over the component
	 */
	boolean isMouseOver();
	
	
	/**
	 * Set alpha multiplier for this and nested components
	 * 
	 * @param alpha alpha multiplier (dynamic value)
	 */
	void setAlpha(Num alpha);
	
	
	/**
	 * Set alpha multiplier for this and nested components
	 * 
	 * @param alpha alpha multiplier (constant value)
	 */
	void setAlpha(double alpha);
	
	
	/**
	 * Indirectly enable / disable, used for nested hierarchies.<br>
	 * When component is twice indirectly disabled, it needs to be twice
	 * indirectly enabled to be enabled again.
	 * 
	 * @param yes
	 */
	void setIndirectlyEnabled(boolean yes);
	
	
	/**
	 * Check if the compionent is not indirectly disabled. May still be directly
	 * disabled.
	 * 
	 * @return indirectly enabled
	 */
	boolean isIndirectlyEnabled();
	
	
	/**
	 * Check if the component is directly enabled (set by setEnabled()). May
	 * still be indirectly disabled.
	 * 
	 * @return directly enabled
	 */
	boolean isDirectlyEnabled();
	
	
	/**
	 * Set directly enabled (must be both directly and indirectly enabled to be
	 * enabled completely)
	 */
	@Override
	public void setEnabled(boolean yes);
	
	
	/**
	 * Check if the component is both directly and indirectly enabled
	 * 
	 * @return enabled
	 */
	@Override
	public boolean isEnabled();
}
