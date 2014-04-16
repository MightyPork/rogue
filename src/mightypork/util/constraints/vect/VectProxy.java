package mightypork.util.constraints.vect;


import mightypork.util.constraints.vect.proxy.VectAdapter;


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
