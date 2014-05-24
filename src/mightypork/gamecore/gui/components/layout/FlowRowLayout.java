package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.gui.AlignY;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


/**
 * Holder with same-sized rows, aligned to top or bottom
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class FlowRowLayout extends LayoutComponent {
	
	private int row = 0;
	private Num elementHeight;
	private final AlignY align;
	
	
	/**
	 * @param app app access
	 * @param context context
	 * @param elementHeight height of all elements
	 * @param align component align. Legal values are TOP and BOTTOM.
	 */
	public FlowRowLayout(AppAccess app, RectBound context, Num elementHeight, AlignY align)
	{
		super(app, context);
		this.elementHeight = elementHeight;
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
	public FlowRowLayout(AppAccess app, Num elementHeight, AlignY align)
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
				r = topEdge().growDown(elementHeight).moveY(elementHeight.mul(row++));
				break;
			case BOTTOM:
				r = bottomEdge().growUp(elementHeight).moveY(elementHeight.mul(-(row++)));
				break;
			default:
				throw new IllegalArgumentException("Bad align.");
		}
		
		elem.setRect(r);
		
		attach(elem);
	}
	
	
	public void setElementHeight(Num elementHeight)
	{
		this.elementHeight = elementHeight;
	}
}
