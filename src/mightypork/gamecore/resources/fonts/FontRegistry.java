package mightypork.gamecore.resources.fonts;


import java.util.HashMap;

import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.resources.ResourceLoadRequest;
import mightypork.gamecore.resources.fonts.impl.LazyFont;
import mightypork.utils.eventbus.clients.BusNode;

import org.newdawn.slick.opengl.Texture;


/**
 * Font loader and registry
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class FontRegistry extends BusNode {
	
	private final HashMap<String, IFont> fonts = new HashMap<>();
	private final HashMap<String, String> aliases = new HashMap<>();
	
	
	/**
	 * Load a {@link LazyFont}
	 * 
	 * @param key font key
	 * @param font font instance
	 */
	public void addFont(String key, LazyFont font)
	{
		App.bus().send(new ResourceLoadRequest(font));
		
		fonts.put(key, font);
	}
	
	
	/**
	 * Add a {@link IFont} to the bank.
	 * 
	 * @param key font key
	 * @param font font instance
	 */
	public void addFont(String key, IFont font)
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
	public IFont getFont(String key)
	{
		IFont f = fonts.get(key);
		
		if (f == null) f = fonts.get(aliases.get(key));
		
		if (f == null) {
			throw new RuntimeException("There's no font called " + key + "!");
		}
		
		return f;
	}
	
}
