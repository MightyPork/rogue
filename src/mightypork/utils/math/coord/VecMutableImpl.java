package mightypork.utils.math.coord;


/**
 * Mutable vec default implementation
 * 
 * @author MightyPork
 */
abstract class VecMutableImpl extends VecMathImpl<VecMutable> implements VecMutable {
	
	@Override
	public abstract double x();
	
	
	@Override
	public abstract double y();
	
	
	@Override
	public abstract double z();
	
	
	@Override
	public abstract VecMutable result(double x, double y, double z);
	
	
	@Override
	public VecMutable setTo(Vec copied)
	{
		return result(copied.x(), copied.y(), copied.z());
	}
	
	
	@Override
	public VecMutable setTo(double x, double y, double z)
	{
		return result(x, y, z);
	}
	
	
	@Override
	public VecMutable setTo(double x, double y)
	{
		return result(x, y, z());
	}
}
