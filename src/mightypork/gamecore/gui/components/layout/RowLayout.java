package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.gui.components.Component;
import mightypork.utils.math.constraints.rect.RectBound;


public class RowLayout extends GridLayout {
	
	private int row = 0;
	
	
	public RowLayout(int rows) {
		this(null, rows);
	}
	
	
	public RowLayout(RectBound context, int rows) {
		super(context, rows, 1);
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
