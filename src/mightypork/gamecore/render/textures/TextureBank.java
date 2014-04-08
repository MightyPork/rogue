package mightypork.gamecore.render.textures;


import java.util.HashMap;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppAdapter;
import mightypork.gamecore.control.bus.events.ResourceLoadRequest;
import mightypork.gamecore.render.textures.FilteredTexture.Filter;
import mightypork.gamecore.render.textures.FilteredTexture.Wrap;
import mightypork.utils.math.coord.Rect;

import org.newdawn.slick.opengl.Texture;


/**
 * Texture loader and quad registry
 * 
 * @author MightyPork
 */
public class TextureBank extends AppAdapter {
	
	public TextureBank(AppAccess app) {
		super(app);
	}
	
	private final HashMap<String, DeferredTexture> textures = new HashMap<String, DeferredTexture>();
	
	private final HashMap<String, TxQuad> quads = new HashMap<String, TxQuad>();
	
	private DeferredTexture lastTx;
	
	
	/**
	 * Load a {@link Texture} from resource, with filters LINEAR and wrap CLAMP
	 * 
	 * @param key texture key
	 * @param resourcePath texture resource path
	 */
	public void loadTexture(String key, String resourcePath)
	{
		loadTexture(key, resourcePath, Filter.LINEAR, Filter.NEAREST, Wrap.CLAMP);
	}
	
	
	/**
	 * Load a {@link Texture} from resource
	 * 
	 * @param key texture key
	 * @param resourcePath texture resource path
	 * @param filter_min min filter (when rendered smaller)
	 * @param filter_mag mag filter (when rendered larger)
	 * @param wrap texture wrapping
	 */
	public void loadTexture(String key, String resourcePath, Filter filter_min, Filter filter_mag, Wrap wrap)
	{
		final DeferredTexture tx = new DeferredTexture(resourcePath);
		tx.setFilter(filter_min, filter_mag);
		tx.setWrap(wrap);
		
		bus().send(new ResourceLoadRequest(tx));
		
		textures.put(key, tx);
		lastTx = tx;
		
		makeQuad(key, Rect.one());
	}
	
	
	/**
	 * Create a {@link TxQuad} in a texture
	 * 
	 * @param quadKey quad key
	 * @param textureKey texture key
	 * @param quad quad rectangle (absolute pixel coordinates) *
	 */
	public void makeQuad(String quadKey, String textureKey, Rect quad)
	{
		final DeferredTexture tx = textures.get(textureKey);
		if (tx == null) throw new RuntimeException("Texture with key " + textureKey + " not defined!");
		
		final TxQuad txquad = tx.getQuad(quad);
		
		quads.put(quadKey, txquad);
	}
	
	
	/**
	 * Create a {@link TxQuad} in the last loaded texture
	 * 
	 * @param quadKey quad key
	 * @param quad quad rectangle (absolute pixel coordinates)
	 */
	public void makeQuad(String quadKey, Rect quad)
	{
		final DeferredTexture tx = lastTx;
		if (tx == null) throw new RuntimeException("There's no texture loaded yet, can't define quads!");
		
		final TxQuad txquad = tx.getQuad(quad);
		
		quads.put(quadKey, txquad);
	}
	
	
	/**
	 * Get a {@link TxQuad} for key
	 * 
	 * @param key quad key
	 * @return the quad
	 */
	public TxQuad getTxQuad(String key)
	{
		final TxQuad q = quads.get(key);
		
		if (q == null) throw new RuntimeException("There's no quad called " + key + "!");
		
		return q;
	}
	
	
	/**
	 * Get a loaded {@link Texture}
	 * 
	 * @param key texture key
	 * @return the texture
	 */
	public Texture getTexture(String key)
	{
		final Texture t = textures.get(key);
		
		if (t == null) throw new RuntimeException("There's no texture called " + key + "!");
		
		return t;
	}
	
}
