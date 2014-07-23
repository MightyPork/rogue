package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.gui.components.DynamicWidthComponent;


public class LinearWrapper extends AbstractLinearWrapper {
	
	public LinearWrapper(DynamicWidthComponent wrapped) {
		super(wrapped);
	}
	
	
	@Override
	public double computeWidth(double height)
	{
		return ((DynamicWidthComponent) wrapped).computeWidth(height);
	}
	
}
