package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.gui.components.Component;


public class LinearSquare extends AbstractLinearWrapper {
	
	public LinearSquare(Component wrapped) {
		super(wrapped);
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return height;
	}
	
}
