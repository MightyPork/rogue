package mightypork.gamecore.render.textures;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppAdapter;
import mightypork.gamecore.control.events.ResourceLoadRequest;
import mightypork.util.constraints.rect.Rect;
import mightypork.util.error.KeyAlreadyExistsException;


/**
 * Texture loader and quad registry
 * 
 * @author MightyPork
 */
public class TextureBank extends AppAdapter {
	
	private final Map<String, GLTexture> textures = new HashMap<>();
	private final Map<String, TxQuad> quads = new HashMap<>();
	private final Map<String, TxSheet> sheets = new HashMap<>();
	
	
	/**
	 * @param app app access
	 */
	public TextureBank(AppAccess app)
	{
		super(app);
	}
	
	
	/**
	 * Load a texture from resource. A full-sized quad with the same key will be
	 * automatically added.
	 * 
	 * @param key texture key
	 * @param resourcePath texture resource path
	 * @param filter filter
	 * @param wrap texture wrapping
	 * @return the loaded texture.
	 */
	public GLTexture loadTexture(String key, String resourcePath, FilterMode filter, WrapMode wrap)
	{
		if (textures.containsKey(key)) throw new KeyAlreadyExistsException();
		
		final DeferredTexture texture = new DeferredTexture(resourcePath);
		texture.setFilter(filter);
		texture.setWrap(wrap);
		
		loadTexture(key, texture);
		
		return texture;
	}
	
	
	/**
	 * Add an already initialized deferred texture to textures registry
	 * 
	 * @param key
	 * @param texture
	 */
	public void loadTexture(String key, DeferredTexture texture)
	{
		getEventBus().send(new ResourceLoadRequest(texture));
		
		textures.put(key, texture);
		
		addQuad(key, texture.makeQuad(Rect.ONE));
	}
	
	
	/**
	 * Add already created quad to the quad registry
	 * 
	 * @param quadKey key
	 * @param quad quad to add
	 */
	public void addQuad(String quadKey, TxQuad quad)
	{
		if (quads.containsKey(quadKey)) throw new KeyAlreadyExistsException();
		
		quads.put(quadKey, quad);
	}
	
	
	/**
	 * Add an already created sheet
	 * 
	 * @param sheetKey key
	 * @param sheet sheet to add
	 */
	public void addSheet(String sheetKey, TxSheet sheet)
	{
		if (sheets.containsKey(sheetKey)) throw new KeyAlreadyExistsException();
		
		sheets.put(sheetKey, sheet);
	}
	
	
	/**
	 * Get a {@link TxQuad} for key
	 * 
	 * @param key quad key
	 * @return the quad
	 */
	public TxQuad getQuad(String key)
	{
		final TxQuad qu = quads.get(key);
		
		if (qu == null) throw new RuntimeException("There's no quad called " + key + "!");
		
		return qu;
	}
	
	
	/**
	 * Get a loaded {@link GLTexture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public GLTexture getTexture(String key)
	{
		final GLTexture tx = textures.get(key);
		
		if (tx == null) throw new RuntimeException("There's no texture called " + key + "!");
		
		return tx;
	}
	
	
	/**
	 * Get a {@link TxSheet} for key
	 * 
	 * @param key sheet key
	 * @return the sheet
	 */
	public TxSheet getSheet(String key)
	{
		final TxSheet sh = sheets.get(key);
		
		if (sh == null) throw new RuntimeException("There's no sheet called " + key + "!");
		
		return sh;
	}
	
}
