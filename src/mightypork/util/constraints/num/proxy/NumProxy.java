package mightypork.util.constraints.num.proxy;


import mightypork.util.constraints.num.Num;


public class NumProxy extends NumAdapter {
	
	private final Num source;
	
	
	public NumProxy(Num source) {
		this.source = source;
	}
	
	
	@Override
	protected Num getSource()
	{
		return source;
	}
	
}
