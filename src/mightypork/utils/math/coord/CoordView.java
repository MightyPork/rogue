package mightypork.utils.math.coord;


public class CoordView extends Coord {
	
	private CoordValue observed = null;
	
	
	public CoordView(CoordValue coord) {
		observed = coord;
	}
	
	
	@Override
	public boolean isView()
	{
		return true;
	}
	
	
	@Override
	public boolean isWritable()
	{
		return false;
	}
	
	
	@Override
	public Coord view()
	{
		return this;
	}
	
	
	@Override
	public Coord freeze()
	{
		return this; // no effect
	}
	
	
	@Override
	public double x()
	{
		return observed.x();
	}
	
	
	@Override
	public double y()
	{
		return observed.y();
	}
	
	
	@Override
	public double z()
	{
		return observed.z();
	}
	
}
