package mightypork.utils.math.constraints.rect;


import mightypork.utils.math.constraints.num.Num;


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
	
	
	TiledRect(Rect source, int horizontal, int vertical) {
		super(source);
		this.tilesX = horizontal;
		this.tilesY = vertical;
		
		this.perRow = height().div(vertical);
		this.perCol = width().div(horizontal);
	}
	
	
	/**
	 * Get a tile. Tiles count from 0 to n-1
	 * 
	 * @param x x position (zero based)
	 * @param y y position (zero based)
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
		
		return Rect.make(perCol,perRow).move(leftMove, topMove);
	}
	
	
	/**
	 * Get a span (tile spanning across multiple cells)
	 * 
	 * @param x_from x start position
	 * @param x_to x end position (included)
	 * @param y_from y start position
	 * @param y_to y end position (included)
	 * @return tile the tile
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect span(int x_from, int x_to, int y_from, int y_to)
	{
		if (x_from >= tilesX || x_from < 0 || x_to >= tilesX || x_to < 0) {
			throw new IndexOutOfBoundsException("X coordinate(s) out of range.");
		}
		if (y_from >= tilesY || y_from < 0 || y_to >= tilesY || y_to < 0) {
			throw new IndexOutOfBoundsException("Y coordinate(s) out of range.");
		}
		if (x_from > x_to) {
			throw new IndexOutOfBoundsException("x_from > x_to");
		}
		
		if (y_from > y_to) {
			throw new IndexOutOfBoundsException("y_from > y_to");
		}

		final Num leftMove = left().add(perCol.mul(x_from));
		final Num topMove = top().add(perRow.mul(y_from));
		
		return Rect.make(perCol.mul(x_to - x_from + 1), perRow.mul(x_to - x_from + 1)).move(leftMove, topMove);
	}
	
	
	/**
	 * Get n-th column (tile n,1)
	 * 
	 * @param n column index (zero based)
	 * @return the column tile
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect column(int n)
	{
		return tile(n, 1);
	}
	
	
	/**
	 * Get n-th row (tile 1,n)
	 * 
	 * @param n row index (zero based)
	 * @return the row rect
	 * @throws IndexOutOfBoundsException when invalid index is specified.
	 */
	public Rect row(int n)
	{
		return tile(1, n);
	}
}
