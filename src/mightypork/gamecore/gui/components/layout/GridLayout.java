package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.util.math.constraints.rect.builders.TiledRect;
import mightypork.util.math.constraints.rect.proxy.RectBound;


/**
 * Holder with table cells
 * 
 * @author MightyPork
 */
public class GridLayout extends LayoutComponent {
	
	private final TiledRect tiler;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param rows number of rows
	 * @param cols number of columns
	 */
	public GridLayout(AppAccess app, RectBound context, int rows, int cols)
	{
		super(app, context);
		this.tiler = tiles(cols, rows);
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param rows number of rows
	 * @param cols number of columns
	 */
	public GridLayout(AppAccess app, int rows, int cols)
	{
		this(app, null, rows, cols);
	}
	
	
	/**
	 * Add a row to the holder.
	 * 
	 * @param row row (one-based)
	 * @param column column (one-based)
	 * @param elem added component
	 */
	public void put(Component elem, int row, int column)
	{
		if (elem == null) return;
		
		elem.setRect(tiler.tile(column, row));
		
		attach(elem);
	}
	
	
	/**
	 * Put with span
	 * 
	 * @param row
	 * @param column
	 * @param rowspan
	 * @param colspan
	 * @param elem
	 */
	public void put(Component elem, int row, int column, int rowspan, int colspan)
	{
		if (elem == null) return;
		
		elem.setRect(tiler.span(column, row, colspan, rowspan));
		
		attach(elem);
	}
	
}
