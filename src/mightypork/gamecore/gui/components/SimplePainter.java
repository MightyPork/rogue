package mightypork.gamecore.gui.components;


import mightypork.utils.annotations.DefaultImpl;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.rect.RectAdapter;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.rect.RectBoundAdapter;
import mightypork.utils.math.constraints.rect.RectCache;


/**
 * {@link Renderable} with pluggable context
 * 
 * @author MightyPork
 */
public abstract class SimplePainter extends RectAdapter implements PluggableRenderable {
	
	private RectCache source;
	
	
	@Override
	public Rect getRect()
	{
		return super.getRect();
	}
	
	
	@Override
	public void setRect(RectBound rect)
	{
		System.out.println("SP set rect");
		this.source = new RectBoundAdapter(rect).cached();
	}
	
	
	@Override
	protected Rect getSource()
	{
		return source;
	}
	
	
	/**
	 * Poll bounds
	 */
	@Override
	public final void poll()
	{
		System.out.println("SP poll, source: "+source);
		source.poll();
		super.poll();
		
		onPoll();
	}
	
	
	@Override
	public abstract void render();
	
	
	/**
	 * Called after painter was polled; contained constraints can now poll too.
	 */
	@DefaultImpl
	public void onPoll()
	{
	}
}
