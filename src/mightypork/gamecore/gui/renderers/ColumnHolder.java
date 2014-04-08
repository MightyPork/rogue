package mightypork.gamecore.gui.renderers;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.gamecore.AppAccess;
import mightypork.utils.math.constraints.RectConstraint;


public class ColumnHolder extends ElementHolder {
	
	private final int cols;
	private int col = 0;
	
	
	public ColumnHolder(AppAccess app, RectConstraint context, int rows) {
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
	@Override
	public void add(final PluggableRenderable elem)
	{
		if (elem == null) return;
		
		elem.setContext(c_column(this, cols, col++));
		
		attach(elem);
	}
	
}
