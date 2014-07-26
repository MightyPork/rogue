package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.rect.builders.TiledRect;


/**
 * Holder with table cells
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class GridLayout extends LayoutComponent {
	
	private final TiledRect tiler;
	
	
	/**
	 * @param context context
	 * @param rows number of rows
	 * @param cols number of columns
	 */
	public GridLayout(RectBound context, int rows, int cols) {
		super(context);
		this.tiler = tiles(cols, rows);
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param rows number of rows
	 * @param cols number of columns
	 */
	public GridLayout(int rows, int cols) {
		this(null, rows, cols);
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
	 * @param elem
	 * @param row
	 * @param column
	 * @param rowspan
	 * @param colspan
	 */
	public void put(Component elem, int row, int column, int rowspan, int colspan)
	{
		if (elem == null) return;
		
		elem.setRect(tiler.span(column, row, colspan, rowspan));
		
		attach(elem);
	}
	
}
