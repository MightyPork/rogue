package mightypork.utils.math.num;


public abstract class AbstractNum implements Num {
	
	@Override
	public Num getNum() {
		return this;
	}
	
	@Override
	public NumView view()
	{
		return new NumProxy(this);
	}
	
	@Override
	public NumVal copy()
	{
		return new NumVal(this);
	}
	
}
