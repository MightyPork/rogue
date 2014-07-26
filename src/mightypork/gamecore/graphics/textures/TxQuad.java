package mightypork.gamecore.graphics.textures;


import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectConst;


/**
 * Texture Quad (describing a part of a texture)
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TxQuad {
	
	/** The texture */
	public final ITexture tx;
	/** Coords in texture (0-1) */
	public final RectConst uvs;
	
	private boolean flipX;
	private boolean flipY;
	
	
	/**
	 * TxQuad from origin and size in pixels
	 * 
	 * @param tx texture
	 * @param xPx left top X (0-1)
	 * @param yPx left top Y (0-1)
	 * @param widthPx area width (0-1)
	 * @param heightPx area height (0-1)
	 * @return new TxQuad
	 */
	public static TxQuad fromSizePx(ITexture tx, double xPx, double yPx, double widthPx, double heightPx)
	{
		final double w = tx.getImageWidth();
		final double h = tx.getImageHeight();
		
		return fromSize(tx, xPx / w, yPx / h, widthPx / w, heightPx / h);
	}
	
	
	/**
	 * TxQuad from origin and size 0-1
	 * 
	 * @param tx texture
	 * @param x1 left top X (0-1)
	 * @param y1 left top Y (0-1)
	 * @param width area width (0-1)
	 * @param height area height (0-1)
	 * @return new TxQuad
	 */
	public static TxQuad fromSize(ITexture tx, double x1, double y1, double width, double height)
	{
		return new TxQuad(tx, x1, y1, x1 + width, y1 + height);
	}
	
	
	/**
	 * Make of coords
	 * 
	 * @param tx texture
	 * @param x1 left top X (0-1)
	 * @param y1 left top Y (0-1)
	 * @param x2 right bottom X (0-1)
	 * @param y2 right bottom Y (0-1)
	 */
	public TxQuad(ITexture tx, double x1, double y1, double x2, double y2) {
		this(tx, Rect.make(x1, y1, x2, y2));
	}
	
	
	/**
	 * @param tx Texture
	 * @param uvs Rect of texture UVs (0-1); will be frozen.
	 */
	public TxQuad(ITexture tx, Rect uvs) {
		this.tx = tx;
		this.uvs = uvs.freeze();
	}
	
	
	/**
	 * Clone another
	 * 
	 * @param txQuad a copied quad
	 */
	public TxQuad(TxQuad txQuad) {
		this.tx = txQuad.tx;
		this.uvs = txQuad.uvs;
		this.flipX = txQuad.flipX;
		this.flipY = txQuad.flipY;
	}
	
	
	/**
	 * Get copy
	 * 
	 * @return copy of this
	 */
	public TxQuad copy()
	{
		return new TxQuad(this);
	}
	
	
	/**
	 * Make a sheet starting with this quad, spannign to right and down.
	 * 
	 * @param width sheet width
	 * @param height sheet height
	 * @return sheet
	 */
	public TxSheet makeSheet(double width, double height)
	{
		return new TxSheet(this, (int) Math.round(width), (int) Math.round(height));
	}
	
	
	/**
	 * @return copy flipped X
	 */
	public TxQuad flipX()
	{
		final TxQuad copy = new TxQuad(this);
		copy.flipX ^= true;
		return copy;
	}
	
	
	/**
	 * @return copy flipped Y
	 */
	public TxQuad flipY()
	{
		final TxQuad copy = new TxQuad(this);
		copy.flipY ^= true;
		return copy;
	}
	
	
	public boolean isFlippedY()
	{
		return flipY;
	}
	
	
	public boolean isFlippedX()
	{
		return flipX;
	}
	
	
	/**
	 * Use the same flit/other attributes as the original txQuad
	 * 
	 * @param original
	 */
	public void dupeAttrs(TxQuad original)
	{
		this.flipX = original.flipX;
		this.flipY = original.flipY;
	}
}
