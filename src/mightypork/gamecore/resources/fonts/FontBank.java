package mightypork.gamecore.resources.fonts;


import java.util.HashMap;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.LightAppModule;
import mightypork.gamecore.resources.events.ResourceLoadRequest;
import mightypork.gamecore.resources.fonts.impl.DeferredFont;

import org.newdawn.slick.opengl.Texture;


/**
 * Font loader and registry
 * 
 * @author MightyPork
 */
public class FontBank extends LightAppModule {
	
	/**
	 * @param app app access
	 */
	public FontBank(AppAccess app)
	{
		super(app);
	}
	
	private final HashMap<String, GLFont> fonts = new HashMap<>();
	private final HashMap<String, String> aliases = new HashMap<>();
	
	
	/**
	 * Load a {@link DeferredFont}
	 * 
	 * @param key font key
	 * @param font font instance
	 */
	public void loadFont(String key, DeferredFont font)
	{
		getEventBus().send(new ResourceLoadRequest(font));
		
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
	 * Add a font alias.
	 * 
	 * @param alias_key alias key
	 * @param font_key font key
	 */
	public void addAlias(String alias_key, String font_key)
	{
		aliases.put(alias_key, font_key);
	}
	
	
	/**
	 * Get a loaded {@link Texture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public GLFont getFont(String key)
	{
		GLFont f = fonts.get(key);
		
		if (f == null) f = fonts.get(aliases.get(key));
		
		if (f == null) {
			throw new RuntimeException("There's no font called " + key + "!");
		}
		
		return f;
	}
	
}
