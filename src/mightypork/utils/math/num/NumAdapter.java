package mightypork.utils.math.num;


public abstract class NumAdapter extends NumView {
	
	protected abstract Num getSource();
	
	
	@Override
	public double value()
	{
		return getSource().value();
	}
	
}
