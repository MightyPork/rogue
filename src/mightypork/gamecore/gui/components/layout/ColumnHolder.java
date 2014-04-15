package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.components.AbstractLayoutComponent;
import mightypork.gamecore.gui.components.Component;
import mightypork.utils.math.constraints.rect.builders.TiledRect;
import mightypork.utils.math.constraints.rect.proxy.RectBound;


/**
 * Holder with evenly spaced columns
 * 
 * @author MightyPork
 */
public class ColumnHolder extends AbstractLayoutComponent {
	
	private final TiledRect tiler;
	private int col = 0;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param cols number of columns
	 */
	public ColumnHolder(AppAccess app, RectBound context, int cols) {
		super(app, context);
		this.tiler = getRect().columns(cols).zeroBased();
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param cols number of columns
	 */
	public ColumnHolder(AppAccess app, int cols) {
		this(app, null, cols);
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
		
		elem.setRect(tiler.column(col++));
		
		attach(elem);
	}
	
}
