package mightypork.rogue;


import mightypork.gamecore.audio.SoundBank;
import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.loading.AsyncResourceLoader;
import mightypork.gamecore.render.fonts.DeferredFont;
import mightypork.gamecore.render.fonts.FontBank;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.fonts.DeferredFont.FontStyle;
import mightypork.gamecore.render.textures.TextureBank;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.gamecore.render.textures.FilteredTexture.Filter;
import mightypork.gamecore.render.textures.FilteredTexture.Wrap;

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
		
		AsyncResourceLoader.launch(app);
		
		textures = new TextureBank(app);
		sounds = new SoundBank(app);
		fonts = new FontBank(app);
		
		loadSounds();
		loadTextures();
		loadFonts();
	}
	
	
	private static void loadFonts()
	{
		fonts.loadFont("default", new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", null, 32, FontStyle.PLAIN, true));
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
