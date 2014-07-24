package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.gui.components.Component;
import mightypork.utils.math.constraints.rect.RectBound;


public class RowLayout extends GridLayout {
	
	private int row = 0;
	
	
	public RowLayout(AppAccess app, int rows) {
		this(app, null, rows);
	}
	
	
	public RowLayout(AppAccess app, RectBound context, int rows) {
		super(app, context, rows, 1);
	}
	
	
	public void add(final Component elem)
	{
		add(elem, 1);
	}
	
	
	public void add(final Component elem, int rowSpan)
	{
		if (elem == null) return;
		
		put(elem, row, 0, rowSpan, 1);
		row += rowSpan;
	}
	
	
	public void skip(int rows)
	{
		row += rows;
	}
}
