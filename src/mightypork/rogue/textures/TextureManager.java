package mightypork.rogue.textures;


import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import mightypork.utils.logging.Log;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


/**
 * Texture manager
 * 
 * @author MightyPork
 */
public class TextureManager {
	
	private static Texture lastBinded = null;
	
	
	/**
	 * Load texture
	 * 
	 * @param resourcePath
	 * @return the loaded texture
	 */
	public static Texture load(String resourcePath)
	{
		try {
			String ext = resourcePath.substring(resourcePath.length() - 4);
			
			Texture texture = TextureLoader.getTexture(ext.toUpperCase(), ResourceLoader.getResourceAsStream(resourcePath));
			
			if (texture != null) {
				return texture;
			}
			
			Log.w("Texture " + resourcePath + " could not be loaded.");
			return null;
		} catch (IOException e) {
			Log.e("Loading of texture " + resourcePath + " failed.", e);
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 * Bind texture
	 * 
	 * @param texture the texture
	 * @throws RuntimeException if not loaded yet
	 */
	public static void bind(Texture texture) throws RuntimeException
	{
		if (texture != lastBinded) {
			glBindTexture(GL_TEXTURE_2D, 0);
			glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
			lastBinded = texture;
		}
	}
	
	
	/**
	 * Unbind all
	 */
	public static void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
		lastBinded = null;
	}
}
