package mightypork.gamecore.resources;


import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.resources.audio.SoundBank;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;
import mightypork.gamecore.resources.fonts.FontBank;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.resources.textures.GLTexture;
import mightypork.gamecore.resources.textures.TextureBank;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;


/**
 * Static resource repository
 * 
 * @author MightyPork
 */
public final class Res {
	
	private static TextureBank textures;
	private static SoundBank sounds;
	private static FontBank fonts;
	
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
		
		textures = new TextureBank(app);
		sounds = new SoundBank(app);
		fonts = new FontBank(app);
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
		binder.addSounds(sounds);
		binder.addTextures(textures);
	}
	
}
