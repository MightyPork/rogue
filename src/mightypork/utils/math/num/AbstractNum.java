package mightypork.utils.math.num;


abstract class AbstractNum implements Num {
	
	private NumView proxy;
	
	
	@Override
	public Num getNum()
	{
		return this;
	}
	
	
	@Override
	public NumView view()
	{
		if (proxy == null) proxy = new NumProxy(this);
		
		return proxy;
	}
	
	
	@Override
	public NumVal copy()
	{
		return new NumVal(this);
	}
	
	@Override
	public String toString()
	{
		return String.format("#{%.1f}",value());
	}
	
}
