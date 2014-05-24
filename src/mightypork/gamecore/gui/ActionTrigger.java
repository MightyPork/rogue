package mightypork.gamecore.gui;


/**
 * Element that can be assigned an action (ie. button);
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface ActionTrigger {
	
	/**
	 * Assign an action
	 * 
	 * @param action action
	 */
	void setAction(Action action);
}
