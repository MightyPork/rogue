package mightypork.utils.math.constraints.vect;




public class VectBoundAdapter extends VectAdapter implements PluggableVectBound {
	
	private VectBound backing = null;
	
	
	public VectBoundAdapter() {
	}
	
	
	public VectBoundAdapter(VectBound bound) {
		backing = bound;
	}
	
	
	@Override
	public void setVect(VectBound rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	protected Vect getSource()
	{
		return backing.getVect();
	}
	
}
