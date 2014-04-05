package mightypork.rogue.gui.constraints;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.rogue.AppAccess;
import mightypork.utils.math.constraints.ConstraintContext;


public class ColumnHolder extends ElementHolder {
	
	private final int cols;
	private int col = 0;
	
	
	public ColumnHolder(AppAccess app, ConstraintContext context, int rows) {
		super(app, context);
		this.cols = rows;
	}
	
	
	public ColumnHolder(AppAccess app, int rows) {
		super(app);
		this.cols = rows;
	}
	
	
	/**
	 * Add a row to the holder.
	 * 
	 * @param elem
	 */
	public void addRow(RenderableWithContext elem)
	{
		if (elem == null) return;
		add(elem, c_column(null, cols, col++));
	}
	
	
	@Override
	public void remove(RenderableWithContext elem)
	{
		throw new UnsupportedOperationException("Can't remove from ColumnHolder.");
	}
	
}
