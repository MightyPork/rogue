package mightypork.gamecore.render.textures;


import mightypork.util.math.constraints.rect.Rect;

import org.newdawn.slick.opengl.Texture;


/**
 * Texture Quad (describing a part of a texture)
 * 
 * @author MightyPork
 */
public class TxQuad {
	
	/** The texture */
	public final Texture tx;
	/** Coords in texture (0-1) */
	public final Rect uvs;
	
	
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
	public static TxQuad fromSizePx(Texture tx, double xPx, double yPx, double widthPx, double heightPx)
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
	public static TxQuad fromSize(Texture tx, double x1, double y1, double width, double height)
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
	public TxQuad(Texture tx, double x1, double y1, double x2, double y2) {
		this(tx, Rect.make(x1, y1, x2, y2));
	}
	
	
	/**
	 * @param tx Texture
	 * @param uvs Rect of texture UVs (0-1); will be stored as is.
	 */
	public TxQuad(Texture tx, Rect uvs) {
		this.tx = tx;
		this.uvs = uvs;
	}
	
	
	/**
	 * Clone another
	 * 
	 * @param txQuad a copied quad
	 */
	public TxQuad(TxQuad txQuad) {
		this.tx = txQuad.tx;
		this.uvs = txQuad.uvs;
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
}
