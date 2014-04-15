package mightypork.utils.math.constraints.vect;


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
