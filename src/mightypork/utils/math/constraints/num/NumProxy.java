package mightypork.utils.math.constraints.num;


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
