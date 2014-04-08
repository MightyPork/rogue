package mightypork.gamecore.gui.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;


public class ImagePainter extends PluggableRenderer {
	
	private TxQuad texture;
	
	
	public ImagePainter(TxQuad texture) {
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
