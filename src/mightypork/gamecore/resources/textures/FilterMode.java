package mightypork.gamecore.resources.textures;


import org.lwjgl.opengl.GL11;


/**
 * Texture filtering mode
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public enum FilterMode
{
	LINEAR(GL11.GL_LINEAR), NEAREST(GL11.GL_NEAREST);
	
	public final int num;
	
	
	private FilterMode(int gl) {
		this.num = gl;
	}
}
