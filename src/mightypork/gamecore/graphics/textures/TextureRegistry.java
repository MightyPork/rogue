package mightypork.gamecore.graphics.textures;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.core.App;
import mightypork.gamecore.resources.loading.ResourceLoadRequest;
import mightypork.utils.exceptions.KeyAlreadyExistsException;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Texture storage and quad/sheet registry. Quads and Sheets are interchangeable
 * once registered.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class TextureRegistry {
	
	private final Map<String, ITexture> textures = new HashMap<>();
	private final Map<String, TxSheet> sheets = new HashMap<>();
	
	
	/**
	 * Load a texture from resource, without a key. This texture will not be
	 * added to the bank.
	 * 
	 * @param resourcePath resource path of the texture
	 * @param filter
	 * @param wrap
	 * @return texture reference
	 */
	public ITexture addTexture(String resourcePath, FilterMode filter, WrapMode wrap)
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
	public ITexture addTexture(String key, String resourcePath, FilterMode filter, WrapMode wrap)
	{
		if (key != null) if (textures.containsKey(key)) throw new KeyAlreadyExistsException();
		
		final DeferredTexture texture = App.gfx().getLazyTexture(resourcePath);
		texture.setFilter(filter);
		texture.setWrap(wrap);
		
		App.bus().send(new ResourceLoadRequest(texture));
		
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
	 * Get a loaded {@link ITexture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public ITexture getTexture(String key)
	{
		final ITexture tx = textures.get(key);
		
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
