package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.util.math.constraints.num.Num;


public class LinearGap extends LinearRectangle {
	
	public LinearGap(Num width)
	{
		super(null, width);
	}
	
	
	public LinearGap(double heightPercent)
	{
		super(null, Num.ZERO);
		setWidth(height().perc(heightPercent));
	}
}
