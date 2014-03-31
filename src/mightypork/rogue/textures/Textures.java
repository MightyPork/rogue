package mightypork.rogue.textures;


import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;


// TODO rewrite to use hashmap with keys

/**
 * Texture loading class
 * 
 * @author MightyPork
 */
public class Textures {

	protected static Texture logo;

	private static final String GUI = "res/images/gui/";


	/**
	 * Load what's needed for splash
	 */
	public static void load()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		logo = TextureManager.load(GUI + "logo.png");

		Tx.initForSplash();
		glDisable(GL_TEXTURE_2D);
		Tx.init();
	}

}
