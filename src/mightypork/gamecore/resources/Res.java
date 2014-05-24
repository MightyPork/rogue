package mightypork.gamecore.resources;


import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.resources.audio.SoundRegistry;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;
import mightypork.gamecore.resources.fonts.FontRegistry;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.resources.textures.GLTexture;
import mightypork.gamecore.resources.textures.TextureRegistry;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;


/**
 * Static resource repository
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public final class Res {
	
	private static TextureRegistry textures;
	private static SoundRegistry sounds;
	private static FontRegistry fonts;
	
	private static boolean initialized = false;
	
	
	/**
	 * Load on behalf of given base app
	 * 
	 * @param app app access
	 */
	public static void init(AppAccess app)
	{
		if (initialized) return;
		initialized = true;
		
		textures = new TextureRegistry(app);
		sounds = new SoundRegistry(app);
		fonts = new FontRegistry(app);
	}
	
	
	public static GLTexture getTexture(String key)
	{
		return textures.getTexture(key);
	}
	
	
	/**
	 * Get a texture sheet by key
	 * 
	 * @param key
	 * @return sheet
	 */
	public static TxSheet getTxSheet(String key)
	{
		return textures.getSheet(key);
	}
	
	
	/**
	 * Get a texture quad by key
	 * 
	 * @param key
	 * @return quad
	 */
	public static TxQuad getTxQuad(String key)
	{
		return textures.getQuad(key);
	}
	
	
	public static LoopPlayer getSoundLoop(String key)
	{
		return sounds.getLoop(key);
	}
	
	
	public static EffectPlayer getSoundEffect(String key)
	{
		return sounds.getEffect(key);
	}
	
	
	public static GLFont getFont(String key)
	{
		return fonts.getFont(key);
	}
	
	
	public static void load(ResourceSetup binder)
	{
		binder.addFonts(fonts);
		binder.addTextures(textures);
		binder.addSounds(sounds);
	}
	
}
