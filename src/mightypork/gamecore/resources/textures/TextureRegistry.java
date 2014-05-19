package mightypork.gamecore.resources.textures;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.core.AppAccess;
import mightypork.gamecore.core.AppAccessAdapter;
import mightypork.gamecore.resources.ResourceLoadRequest;
import mightypork.gamecore.util.error.KeyAlreadyExistsException;
import mightypork.gamecore.util.math.constraints.rect.Rect;


/**
 * Texture storage and quad/sheet registry. Quads and Sheets are interchangeable
 * once registered.
 * 
 * @author MightyPork
 */
public class TextureRegistry extends AppAccessAdapter {
	
	private final Map<String, GLTexture> textures = new HashMap<>();
	private final Map<String, TxSheet> sheets = new HashMap<>();
	
	
	/**
	 * @param app app access
	 */
	public TextureRegistry(AppAccess app)
	{
		super(app);
	}
	
	
	/**
	 * Load a texture from resource, without a key. This texture will not be
	 * added to the bank.
	 * 
	 * @param resourcePath resource path of the texture
	 * @param filter
	 * @param wrap
	 * @return texture reference
	 */
	public GLTexture addTexture(String resourcePath, FilterMode filter, WrapMode wrap)
	{
		return addTexture(resourcePath, resourcePath, filter, wrap);
	}
	
	
	/**
	 * Load a texture from resource; if key is not null, the texture will be
	 * added to the bank.
	 * 
	 * @param key texture key, can be null.
	 * @param resourcePath resource path of the texture
	 * @param filter
	 * @param wrap
	 * @return texture reference
	 */
	public GLTexture addTexture(String key, String resourcePath, FilterMode filter, WrapMode wrap)
	{
		if (key != null) if (textures.containsKey(key)) throw new KeyAlreadyExistsException();
		
		final DeferredTexture texture = new DeferredTexture(resourcePath);
		texture.setFilter(filter);
		texture.setWrap(wrap);
		
		getEventBus().send(new ResourceLoadRequest(texture));
		
		if (key != null) {
			textures.put(key, texture);
			add(key, texture.makeQuad(Rect.ONE));
		}
		
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