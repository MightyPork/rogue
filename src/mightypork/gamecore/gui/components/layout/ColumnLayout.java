package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.gui.components.Component;
import mightypork.utils.math.constraints.rect.RectBound;


public class ColumnLayout extends GridLayout {
	
	private int col = 0;
	
	
	public ColumnLayout(int rows) {
		this(null, rows);
	}
	
	
	public ColumnLayout(RectBound context, int cols) {
		super(context, 1, cols);
	}
	
	
	public void add(final Component elem)
	{
		add(elem, 1);
	}
	
	
	public void add(final Component elem, int colSpan)
	{
		if (elem == null) return;
		
		put(elem, 0, col, 1, colSpan);
		col += colSpan;
	}
	
	
	public void skip(int cols)
	{
		col += cols;
	}
}
