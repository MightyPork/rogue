package mightypork.gamecore.util.math.constraints.num.proxy;


import mightypork.gamecore.util.math.constraints.num.Num;


public abstract class NumAdapter extends Num {
	
	protected abstract Num getSource();
	
	
	@Override
	public double value()
	{
		return getSource().value();
	}
	
}
