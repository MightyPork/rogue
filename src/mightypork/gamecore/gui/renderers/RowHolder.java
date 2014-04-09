package mightypork.gamecore.gui.renderers;


import static mightypork.utils.math.constraints.ConstraintFactory.*;
import mightypork.gamecore.control.AppAccess;
import mightypork.utils.math.constraints.RectConstraint;


/**
 * Holder with evenly spaced rows
 * 
 * @author MightyPork
 */
public class RowHolder extends ElementHolder {
	
	private final int rows;
	private int row = 0;
	
	
	/**
	 * @param app app access
	 * @param context bounding context
	 * @param rows number of rows
	 */
	public RowHolder(AppAccess app, RectConstraint context, int rows) {
		super(app, context);
		this.rows = rows;
	}
	
	
	/**
	 * Make a row holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param rows number of rows
	 */
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
