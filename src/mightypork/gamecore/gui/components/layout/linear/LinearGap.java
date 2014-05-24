package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.gui.components.layout.NullComponent;
import mightypork.gamecore.util.math.constraints.num.Num;


/**
 * Gap in linear layout
 * 
 * @author Ondřej Hruška
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
