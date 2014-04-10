package mightypork.rogue;


import mightypork.gamecore.audio.SoundBank;
import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.control.AppAccess;
import mightypork.gamecore.control.BaseApp;
import mightypork.gamecore.render.fonts.FontBank;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.fonts.Glyphs;
import mightypork.gamecore.render.fonts.impl.DeferredFont;
import mightypork.gamecore.render.textures.DeferredTexture;
import mightypork.gamecore.render.textures.FilterMode;
import mightypork.gamecore.render.textures.TextureBank;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.gamecore.render.textures.WrapMode;

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
	public static void load(BaseApp app)
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
		DeferredFont font;
		
		font = new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", Glyphs.basic, 16);
		font.setAntialias(true);
		font.setFilter(FilterMode.NEAREST);
		fonts.loadFont("default", font);
		
	}
	
	
	private static void loadTextures()
	{
		DeferredTexture texture;
		
		texture = new DeferredTexture("/res/img/kitten.png");
		texture.setFilter(FilterMode.LINEAR);
		texture.setWrap(WrapMode.CLAMP);
		textures.loadTexture("test.kitten", texture);
		
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
