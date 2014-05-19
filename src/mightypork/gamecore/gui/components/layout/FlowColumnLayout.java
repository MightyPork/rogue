package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


/**
 * Holder with same-sized columns, aligned to left or right
 * 
 * @author MightyPork
 */
public class FlowColumnLayout extends LayoutComponent {
	
	private int col = 0;
	private Num elementWidth;
	private final AlignX align;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param elementWidth width of all elements
	 * @param align component align. Legal values are LEFT and RIGHT.
	 */
	public FlowColumnLayout(AppAccess app, RectBound context, Num elementWidth, AlignX align)
	{
		super(app, context);
		this.elementWidth = elementWidth;
		this.align = align;
		
		if (align != AlignX.LEFT && align != AlignX.RIGHT) {
			throw new IllegalArgumentException("Can align only left or right.");
		}
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param elementWidth width of all elements
	 * @param align component align. Legal values are LEFT and RIGHT.
	 */
	public FlowColumnLayout(AppAccess app, Num elementWidth, AlignX align)
	{
		this(app, null, elementWidth, align);
	}
	
	
	/**
	 * Add an item
	 * 
	 * @param elem
	 */
	public void add(final Component elem)
	{
		if (elem == null) return;
		
		final Rect r;
		
		switch (align) {
			case LEFT:
				r = leftEdge().growRight(elementWidth).moveX(elementWidth.mul(col++));
				break;
			case RIGHT:
				r = rightEdge().growLeft(elementWidth).moveX(elementWidth.mul(-(col++)));
				break;
			default:
				throw new IllegalArgumentException("Bad align.");
		}
		
		elem.setRect(r);
		
		attach(elem);
	}
	
	
	public void setElementWidth(Num elementWidth)
	{
		this.elementWidth = elementWidth;
	}
	
}