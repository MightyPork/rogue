package mightypork.rogue;


import mightypork.rogue.fonts.DeferredFont;
import mightypork.rogue.fonts.DeferredFont.FontStyle;
import mightypork.rogue.fonts.FontBank;
import mightypork.rogue.fonts.GLFont;
import mightypork.rogue.sound.SoundBank;
import mightypork.rogue.sound.players.EffectPlayer;
import mightypork.rogue.sound.players.LoopPlayer;
import mightypork.rogue.texture.FilteredTexture.Filter;
import mightypork.rogue.texture.FilteredTexture.Wrap;
import mightypork.rogue.texture.TextureBank;
import mightypork.rogue.texture.TxQuad;

import org.newdawn.slick.opengl.Texture;


/**
 * Static resource repository
 * 
 * @author MightyPork
 */
public class Res {
	
	private static TextureBank textures;
	private static SoundBank sounds;
	private static FontBank fonts;
	
	private static boolean initialized = false;
	
	
	/**
	 * Load on behalf of given {@link AppAccess}
	 * 
	 * @param app app access
	 */
	public static void load(App app)
	{
		if (initialized) return;
		initialized = true;
		
		textures = new TextureBank(app);
		sounds = new SoundBank(app);
		fonts = new FontBank(app);
		
		loadSounds();
		loadTextures();
		loadFonts();
	}
	
	
	private static void loadFonts()
	{
		fonts.loadFont("PolygonPixel_16", new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", null, 32, FontStyle.PLAIN, true));
	}
	
	
	private static void loadTextures()
	{
		textures.loadTexture("test.kitten", "/res/img/kitten.png", Filter.LINEAR, Filter.NEAREST, Wrap.CLAMP);
	}
	
	
	private static void loadSounds()
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
		
		sounds.addLoop("test.wilderness", "/res/audio/wilderness.ogg", 1, 1, 3, 3);
	}
	
	
	public static TxQuad getTxQuad(String key)
	{
		return textures.getTxQuad(key);
	}
	
	
	public static Texture getTexture(String key)
	{
		return textures.getTexture(key);
	}
	
	
	public static LoopPlayer getLoop(String key)
	{
		return sounds.getLoop(key);
	}
	
	
	public static EffectPlayer getEffect(String key)
	{
		return sounds.getEffect(key);
	}
	
	
	public static GLFont getFont(String key)
	{
		return fonts.getFont(key);
	}
	
}
