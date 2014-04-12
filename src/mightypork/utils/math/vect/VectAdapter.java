package mightypork.utils.math.vect;


public abstract class VectAdapter extends VectView {
	
	/**
	 * @return the proxied coord
	 */
	protected abstract Vect getSource();

	
	@Override
	public double x()
	{
		return processX(getSource().x());
	}
	
	
	@Override
	public double y()
	{
		return processY(getSource().y());
	}
	
	
	@Override
	public double z()
	{
		return processZ(getSource().z());
	}
	
	
	/**
	 * Process X before it's returned by x()
	 * 
	 * @param x original X
	 * @return output X
	 */
	protected double processX(double x)
	{
		return x;
	}
	
	
	/**
	 * Process Y before it's returned by y()
	 * 
	 * @param y original Y
	 * @return output Y
	 */
	protected double processY(double y)
	{
		return y;
	}
	
	
	/**
	 * Process Z before it's returned by z()
	 * 
	 * @param z original Z
	 * @return output Z
	 */
	protected double processZ(double z)
	{
		return z;
	}
	
}
