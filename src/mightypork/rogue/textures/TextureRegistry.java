package mightypork.rogue.textures;


import java.util.HashMap;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.Subsystem;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.math.coord.Rect;

import org.newdawn.slick.opengl.Texture;


/**
 * Texture loader and quad registry
 * 
 * @author MightyPork
 */
public class TextureRegistry extends Subsystem implements Destroyable {
	
	public TextureRegistry(AppAccess app) {
		super(app);
	}
	
	private HashMap<String, MultiTexture> textures = new HashMap<String, MultiTexture>();
	
	private HashMap<String, TxQuad> quads = new HashMap<String, TxQuad>();
	
	private MultiTexture lastTx;
	
	
	/**
	 * Load a {@link Texture} from resource
	 * 
	 * @param key texture key
	 * @param resourcePath texture resource path
	 */
	public void loadTexture(String key, String resourcePath)
	{
		MultiTexture tx = new MultiTexture(resourcePath);
		textures.put(key, tx);
		lastTx = tx;
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
		MultiTexture tx = textures.get(textureKey);
		if (tx == null) throw new RuntimeException("Texture with key " + textureKey + " not defined!");
		
		TxQuad txquad = tx.getQuad(quad);
		
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
		MultiTexture tx = lastTx;
		if (tx == null) throw new RuntimeException("There's no texture loaded yet, can't define quads!");
		
		TxQuad txquad = tx.getQuad(quad);
		
		quads.put(quadKey, txquad);
	}
	
	
	/**
	 * Get a {@link TxQuad} for key
	 * 
	 * @param key quad key
	 * @return the quad
	 */
	public TxQuad getQuad(String key)
	{
		TxQuad q = quads.get(key);
		
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
		Texture t = textures.get(key);
		
		if (t == null) throw new RuntimeException("There's no texture called " + key + "!");
		
		return t;
	}
	
	
	@Override
	protected void deinit()
	{
		for (Texture tx : textures.values()) {
			tx.release();
		}
		
		textures.clear();
		quads.clear();
	}
	
}
