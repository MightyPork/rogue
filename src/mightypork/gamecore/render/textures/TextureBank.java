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
	
	/**
	 * @param app app access
	 */
	public TextureBank(AppAccess app) {
		super(app);
	}
	
	private final Map<String, GLTexture> textures = new HashMap<>();
	
	private final Map<String, TxQuad> quads = new HashMap<>();
	
	private final Map<String, TxSheet> sheets = new HashMap<>();
	
	
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
	 * Make a quad from texture, and add it to quads registry.
	 * 
	 * @param quadKey key
	 * @param texture source texture
	 * @param uvs rect
	 * @return the created quad
	 */
	public TxQuad makeQuad(String quadKey, GLTexture texture, Rect uvs)
	{
		TxQuad quad = texture.makeQuad(uvs);
		addQuad(quadKey, quad);
		return quad;
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
	 * make a sprite sheet originating at given quad, spanning right and down.
	 * 
	 * @param sheetKey key
	 * @param origin starting quad
	 * @param width sheet width (multiplies of origin width)
	 * @param height sheet height (multiplies of origin height)
	 * @return the created sheet
	 */
	public TxSheet makeSheet(String sheetKey, TxQuad origin, int width, int height)
	{
		TxSheet sheet = origin.makeSheet(width, height);
		
		addSheet(sheetKey, sheet);
		
		return sheet;
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
		final TxQuad q = quads.get(key);
		
		if (q == null) throw new RuntimeException("There's no quad called " + key + "!");
		
		return q;
	}
	
	
	/**
	 * Get a loaded {@link GLTexture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public GLTexture getTexture(String key)
	{
		final GLTexture t = textures.get(key);
		
		if (t == null) throw new RuntimeException("There's no texture called " + key + "!");
		
		return t;
	}
	
}
