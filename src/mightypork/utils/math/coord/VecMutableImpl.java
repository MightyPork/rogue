package mightypork.utils.math.coord;


/**
 * Mutable vec default implementation
 * 
 * @author MightyPork
 * @param <V> Return type of methods
 */
abstract class VecMutableImpl<V extends VecMutable> extends VecImpl<V> implements VecMutable {

	@Override
	public abstract double x();

	@Override
	public abstract double y();

	@Override
	public abstract double z();

	@Override
	public abstract V result(double x, double y, double z);
	
	
	@Override
	public VecMutable setTo(Vec copied)
	{
		return result(copied.x(), copied.y(), copied.z());
	}
	
	
	@Override
	public V setTo(double x, double y, double z)
	{
		return result(x, y, z);
	}
	
	
	@Override
	public V setTo(double x, double y)
	{
		return result(x, y, z());
	}
}
