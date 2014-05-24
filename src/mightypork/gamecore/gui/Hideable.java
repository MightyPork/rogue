package mightypork.gamecore.gui;


/**
 * Element that can be hidden or visible
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface Hideable {
	
	void setVisible(boolean yes);
	
	
	boolean isVisible();
}
