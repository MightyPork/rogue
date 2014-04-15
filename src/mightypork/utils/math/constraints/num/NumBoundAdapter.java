package mightypork.utils.math.constraints.num;


public class NumBoundAdapter extends NumAdapter implements PluggableNumBound {
	
	private NumBound backing = null;
	
	
	public NumBoundAdapter() {
	}
	
	
	public NumBoundAdapter(NumBound bound) {
		backing = bound;
	}
	
	
	@Override
	public void setNum(NumBound rect)
	{
		this.backing = rect;
	}
	
	
	@Override
	protected Num getSource()
	{
		return backing.getNum();
	}
	
}
