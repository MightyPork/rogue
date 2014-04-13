package mightypork.utils.math.num;


class NumProxy extends NumAdapter {
	
	private final Num observed;
	
	
	public NumProxy(Num observed) {
		this.observed = observed;
	}
	
	
	@Override
	protected Num getSource()
	{
		return observed;
	}
	
}
