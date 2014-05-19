package mightypork.gamecore.gui.components.layout;


import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.gui.components.Component;
import mightypork.gamecore.util.math.constraints.rect.proxy.RectBound;


public class ColumnLayout extends GridLayout {
	
	private int col = 0;
	
	
	public ColumnLayout(AppAccess app, int rows)
	{
		this(app, null, rows);
	}
	
	
	public ColumnLayout(AppAccess app, RectBound context, int cols)
	{
		super(app, context, 1, cols);
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
