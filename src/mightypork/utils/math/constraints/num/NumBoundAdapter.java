package mightypork.utils.math.constraints.num;


import mightypork.utils.math.constraints.PluggableNumBound;


public class NumBoundAdapter extends NumAdapter implements PluggableNumBound {
	
	private NumBound backing = null;
	
	
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
