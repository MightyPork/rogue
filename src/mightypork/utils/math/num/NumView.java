package mightypork.utils.math.num;


public abstract class NumView extends NumMathDynamic {
	
	/**
	 * @deprecated No point in taking view of a view.
	 */
	@Override
	@Deprecated
	public NumView view()
	{
		return this; // no work here
	}
	
}
