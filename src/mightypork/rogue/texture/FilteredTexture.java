package mightypork.rogue.texture;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;


public interface FilteredTexture extends Texture {
	
	public static enum Filter
	{
		LINEAR(GL11.GL_LINEAR), NEAREST(GL11.GL_NEAREST);
		
		public final int num;
		
		
		private Filter(int gl) {
			this.num = gl;
		}
	}
	
	public static enum Wrap
	{
		CLAMP(GL11.GL_CLAMP), REPEAT(GL11.GL_REPEAT);
		
		public final int num;
		
		
		private Wrap(int gl) {
			this.num = gl;
		}
	}
	
	
	/**
	 * Set filter for scaling
	 * 
	 * @param filterMin downscale filter
	 * @param filterMag upscale filter
	 */
	public void setFilter(Filter filterMin, Filter filterMag);
	
	
	/**
	 * Set filter for scaling (both up and down)
	 * 
	 * @param filter filter
	 */
	public void setFilter(Filter filter);
	
	
	/**
	 * @param wrapping wrap mode
	 */
	public void setWrap(Wrap wrapping);
}
