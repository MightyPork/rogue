package mightypork.gamecore.render.textures;


import org.lwjgl.opengl.GL11;


/**
 * Texture wrap mode
 * 
 * @author MightyPork
 */
public enum WrapMode
{
	CLAMP(GL11.GL_CLAMP), REPEAT(GL11.GL_REPEAT);
	
	public final int num;
	
	
	private WrapMode(int gl) {
		this.num = gl;
	}
}
