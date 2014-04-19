package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.gui.AlignY;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectBound;


/**
 * Holder with same-sized rows, aligned to top or bottom
 * 
 * @author MightyPork
 */
public class VerticalFixedFlowLayout extends LayoutComponent {
	
	private int row = 0;
	private final Num rowHeight;
	private final AlignY align;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param elementHeight height of all elements
	 * @param align component align. Legal values are TOP and BOTTOM.
	 */
	public VerticalFixedFlowLayout(AppAccess app, RectBound context, Num elementHeight, AlignY align)
	{
		super(app, context);
		this.rowHeight = elementHeight;
		this.align = align;
		
		if (align != AlignY.TOP && align != AlignY.BOTTOM) {
			throw new IllegalArgumentException("Can align only to top or bottom.");
		}
	}
	
	
	/**
	 * make a new holder.<br>
	 * Context must be assigned before rendering.
	 * 
	 * @param app app access
	 * @param elementHeight height of all elements
	 * @param align component align. Legal values are TOP and BOTTOM.
	 */
	public VerticalFixedFlowLayout(AppAccess app, Num elementHeight, AlignY align)
	{
		this(app, null, elementHeight, align);
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
			case TOP:
				r = topEdge().growDown(rowHeight).moveY(rowHeight.mul(row++));
				break;
			case BOTTOM:
				r = bottomEdge().growUp(rowHeight).moveY(rowHeight.mul(-(row++)));
				break;
			default:
				throw new IllegalArgumentException("Bad align.");
		}
		
		elem.setRect(r);
		
		attach(elem);
	}
	
}
