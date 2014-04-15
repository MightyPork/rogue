package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.AbstractLayoutComponent;
import mightypork.gamecore.gui.components.Component;
import mightypork.utils.math.constraints.rect.builders.TiledRect;
import mightypork.utils.math.constraints.rect.proxy.RectBound;


/**
 * Holder with evenly spaced rows
 * 
 * @author MightyPork
 */
public class RowHolder extends AbstractLayoutComponent {
	
	private final TiledRect tiler;
	private int row = 0;
	
	
	/**
	 * Make a row holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param rows number of rows
	 */
	public RowHolder(AppAccess app, int rows) {
		this(app, null, rows);
	}
	
	
	/**
	 * @param app app access
	 * @param context bounding context
	 * @param rows number of rows
	 */
	public RowHolder(AppAccess app, RectBound context, int rows) {
		super(app, context);
		this.tiler = getRect().rows(rows).zeroBased();
	}
	
	
	/**
	 * Add a row to the holder.
	 * 
	 * @param elem
	 */
	@Override
	public void add(final Component elem)
	{
		if (elem == null) return;
		
		elem.setRect(tiler.row(row++));
		
		attach(elem);
	}
	
}
