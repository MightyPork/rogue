package mightypork.utils.math.constraints.vect;

import mightypork.utils.math.constraints.vect.proxy.VectAdapter;


public class VectProxy extends VectAdapter {
	
	private final Vect source;
	
	
	public VectProxy(Vect source) {
		this.source = source;
	}
	
	
	@Override
	protected Vect getSource()
	{
		return source;
	}
	
}
