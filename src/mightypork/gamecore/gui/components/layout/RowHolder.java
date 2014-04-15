package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.PluggableRenderable;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.rect.TiledRect;


/**
 * Holder with evenly spaced rows
 * 
 * @author MightyPork
 */
public class RowHolder extends AbstractLayout {
	
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
	public void add(final PluggableRenderable elem)
	{
		if (elem == null) return;
		
		elem.setRect(tiler.row(row++));
		
		attach(elem);
	}
	
}
