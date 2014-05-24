package mightypork.gamecore.gui.components.layout.linear;


import mightypork.dynmath.num.Num;
import mightypork.gamecore.gui.components.Component;


public class LinearRectangle extends AbstractLinearWrapper {
	
	private Num width;
	
	
	public LinearRectangle(Component wrapped, Num width)
	{
		super(wrapped);
		this.width = width;
	}
	
	
	public void setWidth(Num width)
	{
		this.width = width;
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return this.width.value();
	}
	
}
