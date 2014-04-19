package mightypork.util.constraints.rect.builders;


import mightypork.util.constraints.num.Num;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.constraints.rect.proxy.RectProxy;
import mightypork.util.logging.Log;


/**
 * Utility for cutting rect into evenly sized cells.
 * 
 * @author MightyPork
 */
public class TiledRect extends RectProxy {
	
	final private int tilesY;
	final private int tilesX;
	final private Num perRow;
	final private Num perCol;
	
	
	public TiledRect(Rect source, int horizontal, int vertical)
	{
		super(source);
		this.tilesX = horizontal;
		this.tilesY = vertical;
		
		this.perRow = height().div(vertical);
		this.perCol = width().div(horizontal);
	}
	
	
	/**
	 * Get a tile.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return tile
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect tile(int x, int y)
	{
		if (x >= tilesX || x < 0) {
			throw new IndexOutOfBoundsException("X coordinate out fo range.");
		}
		
		if (y >= tilesY || y < 0) {
			throw new IndexOutOfBoundsException("Y coordinate out of range.");
		}
		
		final Num leftMove = left().add(perCol.mul(x));
		final Num topMove = top().add(perRow.mul(y));
		
		return Rect.make(perCol, perRow).move(leftMove, topMove);
	}
	
	
	/**
	 * Get a span (tile spanning across multiple cells)
	 * 
	 * @param x_from x start position
	 * @param y_from y start position
	 * @param size_x horizontal size (columns)
	 * @param size_y vertical size (rows)
	 * @return tile the tile
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect span(int x_from, int y_from, int size_x, int size_y)
	{
		final int x_to = x_from + size_x - 1;
		final int y_to = y_from + size_y - 1;
		
		if (size_x <= 0 || size_y <= 0) {
			Log.w("Size must be > 0.", new IllegalAccessException());
		}
		
		if (x_from >= tilesX || x_from < 0 || x_to >= tilesX || x_to < 0) {
			Log.w("X coordinate(s) out of range.", new IllegalAccessException());
		}
		
		if (y_from >= tilesY || y_from < 0 || y_to >= tilesY || y_to < 0) {
			Log.w("Y coordinate(s) out of range.", new IllegalAccessException());
		}
		
		final Num leftMove = left().add(perCol.mul(x_from));
		final Num topMove = top().add(perRow.mul(y_from));
		
		return Rect.make(perCol.mul(size_x), perRow.mul(size_y)).move(leftMove, topMove);
	}
	
	
	/**
	 * Get n-th column
	 * 
	 * @param n column index
	 * @return the column tile
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect column(int n)
	{
		return tile(n, 0);
	}
	
	
	/**
	 * Get n-th row
	 * 
	 * @param n row index
	 * @return the row rect
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect row(int n)
	{
		return tile(0, n);
	}
}
