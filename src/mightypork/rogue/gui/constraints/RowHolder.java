package mightypork.rogue.gui.constraints;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.rogue.AppAccess;
import mightypork.utils.math.constraints.ConstraintContext;


public class RowHolder extends ElementHolder {
	
	private final int rows;
	private int row = 0;
	
	
	public RowHolder(AppAccess app, ConstraintContext context, int rows) {
		super(app, context);
		this.rows = rows;
	}
	
	
	public RowHolder(AppAccess app, int rows) {
		super(app);
		this.rows = rows;
	}
	
	
	/**
	 * Add a row to the holder.
	 * 
	 * @param elem
	 */
	public void addRow(RenderableWithContext elem)
	{
		if (elem == null) return;
		add(elem, c_row(null, rows, row++));
	}
	
	
	@Override
	public void remove(RenderableWithContext elem)
	{
		throw new UnsupportedOperationException("Can't remove from RowHolder.");
	}
	
}
