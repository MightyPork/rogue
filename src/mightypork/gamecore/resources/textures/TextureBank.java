package mightypork.gamecore.resources.textures;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.app.LightAppModule;
import mightypork.gamecore.resources.events.ResourceLoadRequest;
import mightypork.gamecore.util.error.KeyAlreadyExistsException;


/**
 * Texture storage and quad/sheet registry. Quads and Sheets are interchangeable
 * once registered.
 * 
 * @author MightyPork
 */
public class TextureBank extends LightAppModule {
	
	private final Map<String, GLTexture> textures = new HashMap<>();
	private final Map<String, TxSheet> sheets = new HashMap<>();
	
	
	/**
	 * @param app app access
	 */
	public TextureBank(AppAccess app)
	{
		super(app);
	}
	
	
	/**
	 * Load a texture from resource, with the resource-path as a name.
	 * 
	 * @param resourcePath resource path of the texture
	 * @param filter
	 * @param wrap
	 * @return texture reference
	 */
	public GLTexture loadTexture(String resourcePath, FilterMode filter, WrapMode wrap)
	{
		return loadTexture(resourcePath, resourcePath, filter, wrap);
	}
	
	
	/**
	 * Load a texture from resource
	 * 
	 * @param key texture key
	 * @param resourcePath resource path of the texture
	 * @param filter
	 * @param wrap
	 * @return texture reference
	 */
	public GLTexture loadTexture(String key, String resourcePath, FilterMode filter, WrapMode wrap)
	{
		if (textures.containsKey(key)) throw new KeyAlreadyExistsException();
		
		final DeferredTexture texture = new DeferredTexture(resourcePath);
		texture.setFilter(filter);
		texture.setWrap(wrap);
		
		getEventBus().send(new ResourceLoadRequest(texture));
		
		textures.put(key, texture);
		
		return texture;
	}
	
	
	/**
	 * Add already created quad to the quad registry
	 * 
	 * @param quadKey key
	 * @param quad quad to add
	 */
	public void add(String quadKey, TxQuad quad)
	{
		if (sheets.containsKey(quadKey)) throw new KeyAlreadyExistsException();
		
		sheets.put(quadKey, quad.makeSheet(1, 1));
	}
	
	
	/**
	 * Add an already created sheet
	 * 
	 * @param sheetKey key
	 * @param sheet sheet to add
	 */
	public void add(String sheetKey, TxSheet sheet)
	{
		if (sheets.containsKey(sheetKey)) throw new KeyAlreadyExistsException();
		
		sheets.put(sheetKey, sheet);
	}
	
	
	/**
	 * Get a {@link TxQuad} for key; if it was added as sheet, the first quad
	 * ofthe sheet is returned.
	 * 
	 * @param key quad key
	 * @return the quad
	 */
	public TxQuad getQuad(String key)
	{
		return getSheet(key).getQuad(0); // get the first
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
		
		if (tx == null) throw new RuntimeException("There's no texture called \"" + key + "\"!");
		
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
		
		if (sh == null) {
			throw new RuntimeException("There's no sheet called  \"" + key + "\"!");
		}
		
		return sh;
	}
	
}
