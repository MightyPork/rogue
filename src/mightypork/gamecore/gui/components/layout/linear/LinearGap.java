package mightypork.gamecore.gui.components.layout.linear;


import mightypork.dynmath.num.Num;
import mightypork.gamecore.gui.components.layout.NullComponent;


/**
 * Gap in linear layout
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LinearGap extends LinearRectangle {
	
	public LinearGap(Num width)
	{
		super(new NullComponent(), width);
	}
	
	
	public LinearGap(double heightPercent)
	{
		this(Num.ZERO);
		setWidth(height().perc(heightPercent));
	}
}
