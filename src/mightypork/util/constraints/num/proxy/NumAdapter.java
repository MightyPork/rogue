package mightypork.util.constraints.num.proxy;


import mightypork.util.constraints.num.Num;


public abstract class NumAdapter extends Num {
	
	protected abstract Num getSource();
	
	
	@Override
	public double value()
	{
		return getSource().value();
	}
	
}
