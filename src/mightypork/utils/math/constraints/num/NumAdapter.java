package mightypork.utils.math.constraints.num;


public abstract class NumAdapter extends Num {
	
	protected abstract Num getSource();
	
	
	@Override
	public double value()
	{
		return getSource().value();
	}
	
}
