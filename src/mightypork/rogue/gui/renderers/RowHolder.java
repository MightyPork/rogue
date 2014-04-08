package mightypork.rogue.gui.renderers;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.rogue.AppAccess;
import mightypork.utils.math.constraints.RectEvaluable;


public class RowHolder extends ElementHolder {
	
	private final int rows;
	private int row = 0;
	
	
	public RowHolder(AppAccess app, RectEvaluable context, int rows) {
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
	@Override
	public void add(final PluggableRenderable elem)
	{
		if (elem == null) return;
		
		elem.setContext(c_row(this, rows, row++));
		
		attach(elem);
	}
	
}
