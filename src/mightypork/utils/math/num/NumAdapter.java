package mightypork.utils.math.num;


public abstract class NumAdapter extends Num {
	
	protected abstract Num getSource();
	
	
	@Override
	public double value()
	{
		return getSource().value();
	}
	
}
