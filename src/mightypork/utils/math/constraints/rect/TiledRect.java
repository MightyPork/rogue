package mightypork.utils.math.constraints.rect;


import mightypork.utils.math.constraints.num.Num;


/**
 * Utility for cutting rect into evenly sized cells.<br>
 * It's by default one-based, but this can be switched by calling the oneBased()
 * and zeroBased() methods.
 * 
 * @author MightyPork
 */
public class TiledRect extends RectProxy {
	
	final private int tilesY;
	final private int tilesX;
	final private Num perRow;
	final private Num perCol;
	
	private int based = 1;
	
	
	TiledRect(Rect source, int horizontal, int vertical) {
		super(source);
		this.tilesX = horizontal;
		this.tilesY = vertical;
		
		this.perRow = height().div(vertical);
		this.perCol = width().div(horizontal);
	}
	
	
	/**
	 * Set to one-based mode, and return itself (for chaining).
	 * 
	 * @return this
	 */
	public TiledRect oneBased()
	{
		based = 1;
		return this;
	}
	
	
	/**
	 * Set to zero-based mode, and return itself (for chaining).
	 * 
	 * @return this
	 */
	public TiledRect zeroBased()
	{
		based = 0;
		return this;
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
		x -= based;
		y -= based;
		
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
		x_from -= based;
		y_from -= based;
		
		final int x_to = x_from + size_x;
		final int y_to = y_from + size_y;
		
		if (size_x <= 0 || size_y <= 0) {
			throw new IndexOutOfBoundsException("Size must be > 0.");
		}
		
		if (x_from >= tilesX || x_from < 0 || x_to >= tilesX || x_to < 0) {
			throw new IndexOutOfBoundsException("X coordinate(s) out of range.");
		}
		
		if (y_from >= tilesY || y_from < 0 || y_to >= tilesY || y_to < 0) {
			throw new IndexOutOfBoundsException("Y coordinate(s) out of range.");
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
		return tile(n, based);
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
		return tile(based, n);
	}
}
