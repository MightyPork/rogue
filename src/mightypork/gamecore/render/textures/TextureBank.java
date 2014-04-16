package mightypork.gamecore.render.textures;


import java.util.HashMap;

import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.AppAdapter;
import mightypork.gamecore.control.events.ResourceLoadRequest;
import mightypork.util.constraints.rect.Rect;

import org.newdawn.slick.opengl.Texture;


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
	
	private final HashMap<String, DeferredTexture> textures = new HashMap<>();
	
	private final HashMap<String, TxQuad> quads = new HashMap<>();
	
	private DeferredTexture lastTx;
	
	
	/**
	 * Load a {@link Texture}
	 * 
	 * @param key texture key
	 * @param texture texture to load
	 */
	public void loadTexture(String key, DeferredTexture texture)
	{
		getEventBus().send(new ResourceLoadRequest(texture));
		
		textures.put(key, texture);
		lastTx = texture;
		
		makeQuad(key, Rect.ONE);
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
	public void loadTexture(String key, String resourcePath, FilterMode filter_min, FilterMode filter_mag, WrapMode wrap)
	{
		final DeferredTexture texture = new DeferredTexture(resourcePath);
		texture.setFilter(filter_min, filter_mag);
		texture.setWrap(wrap);
		
		loadTexture(key, texture);
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
