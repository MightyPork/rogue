package mightypork.gamecore.graphics.fonts;


import mightypork.gamecore.graphics.textures.FilterMode;
import mightypork.gamecore.resources.BaseDeferredResource;


/**
 * Abstract deferred font stub.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class DeferredFont extends BaseDeferredResource implements IFont {
	
	public static enum FontStyle
	{
		PLAIN(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
		
		public int numval;
		
		
		/**
		 * Font style
		 * 
		 * @param style style index as in awt Font. Not using constants to be
		 *            independent on awt.
		 */
		private FontStyle(int style) {
			this.numval = style;
		}
	}
	
	protected double size = 12;
	protected FontStyle style = FontStyle.PLAIN;
	protected String chars = Glyphs.basic;
	protected FilterMode filter = FilterMode.NEAREST;
	protected boolean antialias = false;
	protected double discardTop = 0;
	protected double discardBottom = 0;
	
	
	public DeferredFont(String resource) {
		super(resource);
	}
	
	
	public void setSize(double size)
	{
		this.size = size;
	}
	
	
	public void setStyle(FontStyle style)
	{
		this.style = style;
	}
	
	
	public void setChars(String chars)
	{
		this.chars = chars;
	}
	
	
	public void setFilter(FilterMode filter)
	{
		this.filter = filter;
	}
	
	
	public void setAntialias(boolean antialias)
	{
		this.antialias = antialias;
	}
	
	
	@Override
	public void setDiscardRatio(double top, double bottom)
	{
		discardTop = top;
		discardBottom = bottom;
	}
	
	
	@Override
	public double getTopDiscardRatio()
	{
		return discardTop;
	}
	
	
	@Override
	public double getBottomDiscardRatio()
	{
		return discardBottom;
	}
	
}
