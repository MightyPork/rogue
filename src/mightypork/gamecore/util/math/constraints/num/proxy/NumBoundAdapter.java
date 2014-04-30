package mightypork.gamecore.util.math.constraints.num.proxy;


import mightypork.gamecore.util.math.constraints.num.Num;


public class NumBoundAdapter extends NumAdapter implements PluggableNumBound {
	
	private NumBound backing = null;
	
	
	public NumBoundAdapter()
	{
	}
	
	
	public NumBoundAdapter(NumBound bound)
	{
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
