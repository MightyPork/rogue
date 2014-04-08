package mightypork.rogue.gui.renderers;


import mightypork.rogue.render.Render;
import mightypork.rogue.textures.TxQuad;


public class ImageRenderer extends PluggableRenderer {
	
	private TxQuad texture;
	
	
	public ImageRenderer(TxQuad texture) {
		this.texture = texture;
	}
	
	
	public void setTexture(TxQuad texture)
	{
		this.texture = texture;
	}
	
	
	@Override
	public void render()
	{
		Render.quadTextured(getRect(), texture);
	}
	
}
