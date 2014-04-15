package mightypork.utils.math.constraints.num.proxy;

import mightypork.utils.math.constraints.num.Num;


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
