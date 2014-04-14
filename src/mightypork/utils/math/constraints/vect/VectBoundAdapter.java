package mightypork.utils.math.constraints.vect;


import mightypork.utils.math.constraints.PluggableVectBound;
import mightypork.utils.math.constraints.VectBound;


public class VectBoundAdapter extends VectAdapter implements PluggableVectBound {
	
	private VectBound backing = null;
	
	
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
