package mightypork.gamecore.resources.fonts;


import java.util.HashMap;

import mightypork.gamecore.AppAccess;
import mightypork.gamecore.AppAdapter;
import mightypork.gamecore.control.bus.events.ResourceLoadRequest;
import mightypork.utils.logging.Log;

import org.newdawn.slick.opengl.Texture;


/**
 * Font loader and registry
 * 
 * @author MightyPork
 */
public class FontBank extends AppAdapter {
	
	private static final GLFont NULL_FONT = new NullFont();
	
	
	public FontBank(AppAccess app) {
		super(app);
	}
	
	private final HashMap<String, GLFont> fonts = new HashMap<String, GLFont>();
	
	
	/**
	 * Load a {@link DeferredFont}
	 * 
	 * @param key font key
	 * @param font font instance
	 */
	public void loadFont(String key, DeferredFont font)
	{
		bus().send(new ResourceLoadRequest(font));
		
		fonts.put(key, font);
	}
	
	
	/**
	 * Add a {@link GLFont} to the bank.
	 * 
	 * @param key font key
	 * @param font font instance
	 */
	public void addFont(String key, GLFont font)
	{
		fonts.put(key, font);
	}
	
	
	/**
	 * Get a loaded {@link Texture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public GLFont getFont(String key)
	{
		final GLFont f = fonts.get(key);
		
		if (f == null) {
			Log.w("There's no font called " + key + "!");
			return NULL_FONT;
		}
		
		return f;
	}
	
}
