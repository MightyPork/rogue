package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.utils.math.constraints.RectBound;


/**
 * Holder with evenly spaced columns
 * 
 * @author MightyPork
 */
public class ColumnHolder extends AbstractLayout {
	
	private final int cols;
	private int col = 0;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param rows number of rows
	 */
	public ColumnHolder(AppAccess app, RectBound context, int rows) {
		super(app, context);
		this.cols = rows;
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param rows number of rows
	 */
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
		
		elem.setRect(column(this, cols, col++));
		
		attach(elem);
	}
	
}
