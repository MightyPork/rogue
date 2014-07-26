package mightypork.gamecore.graphics.textures;


import mightypork.utils.math.constraints.rect.Rect;


/**
 * {@link TxQuad} and {@link TxSheet} building utility
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class QuadGrid {
	
	private final ITexture tx;
	private final int txHeight;
	private final int txWidth;
	private final double tileW;
	private final double tileH;
	
	
	public QuadGrid(ITexture tx, int tilesX, int tilesY) {
		this.tx = tx;
		this.txWidth = tilesX;
		this.txHeight = tilesY;
		this.tileW = 1D / tilesX;
		this.tileH = 1D / tilesY;
	}
	
	
	/**
	 * Make square quad at given coords (one grid cell)
	 * 
	 * @param x x coordinate (cells)
	 * @param y y coordinate (cells)
	 * @return the quad
	 */
	public TxQuad makeQuad(double x, double y)
	{
		if (x < 0 || x >= txWidth || y < 0 || y >= txHeight) {
			throw new IndexOutOfBoundsException("Requested invalid txquad coordinates.");
		}
		
		return makeQuad(x, y, 1, 1);
	}
	
	
	/**
	 * Make square quad at given coords, with arbitrary size. Coordinates are
	 * multiples of cell size.
	 * 
	 * @param x x coordinate (cells)
	 * @param y y coordinate (cells)
	 * @param width width (cells)
	 * @param height height (cells)
	 * @return the quad
	 */
	public TxQuad makeQuad(double x, double y, double width, double height)
	{
		if (x < 0 || x >= txWidth || y < 0 || y >= txHeight) {
			throw new IndexOutOfBoundsException("Requested invalid txquad coordinates.");
		}
		
		if (x + width > txWidth || y + height > txHeight) {
			throw new IndexOutOfBoundsException("Requested invalid txquad size (would go beyond texture size).");
		}
		
		return tx.makeQuad(Rect.make(tileW * x, tileH * y, tileW * width, tileH * height));
	}
	
	
	/**
	 * Make a sheet.
	 * 
	 * @param x x origin coordinate (cells)
	 * @param y y origin coordinate (cells)
	 * @param width width (cells)
	 * @param height height (cells)
	 * @return the sheet
	 */
	public TxSheet makeSheet(double x, double y, double width, double height)
	{
		if (x < 0 || x >= txWidth || y < 0 || y >= txHeight) {
			throw new IndexOutOfBoundsException("Requested invalid txquad coordinates.");
		}
		
		if (x + width > txWidth || y + height > txHeight) {
			throw new IndexOutOfBoundsException("Requested invalid txsheet size (would go beyond texture size).");
		}
		
		return makeQuad(x, y).makeSheet(width, height);
	}
}
