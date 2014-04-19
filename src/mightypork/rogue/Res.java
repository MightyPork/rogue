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
import mightypork.gamecore.render.textures.*;
import mightypork.util.constraints.rect.Rect;

import org.newdawn.slick.opengl.Texture;


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
		loadFonts();
		loadTextures();
	}
	
	
	private static void loadFonts()
	{
		DeferredFont font;
		
		font = new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", Glyphs.basic, 16);
		fonts.loadFont("polygon_pixel", font);
		
		font = new DeferredFont("/res/font/PressStart2P.ttf", Glyphs.basic, 16);
		fonts.loadFont("press_start", font);
		
		fonts.addAlias("default", "polygon_pixel");
		fonts.addAlias("main_menu_button", "press_start");
		fonts.addAlias("main_menu_title", "press_start");
	}
	
	
	private static void loadTextures()
	{
		DeferredTexture texture;
		
		texture = new DeferredTexture("/res/img/kitten.png");
		texture.setFilter(FilterMode.LINEAR);
		texture.setWrap(WrapMode.CLAMP);
		textures.loadTexture("test.kitten", texture);
		
		texture = new DeferredTexture("/res/img/gui1.png");
		texture.setFilter(FilterMode.NEAREST);
		texture.setWrap(WrapMode.CLAMP);
		textures.loadTexture("gui1", texture);
		QuadGrid guiGrid = texture.grid(4, 4);
		
		//@formatter:off
		textures.addQuad("item_frame", guiGrid.makeQuad(0, 0));
		textures.addQuad("sword", guiGrid.makeQuad(1, 0));
		textures.addQuad("meat", guiGrid.makeQuad(2, 0));
		
		textures.addQuad("heart_on", guiGrid.makeQuad(.0, 1, .5, .5));
		textures.addQuad("heart_off", guiGrid.makeQuad(.5, 1, .5, .5));
		
		textures.addQuad("xp_on", guiGrid.makeQuad(0, 1.5, .5, .5));
		textures.addQuad("xp_off", guiGrid.makeQuad(.5, 1.5, .5, .5));
		
		textures.addQuad("panel", guiGrid.makeQuad(0, 3.75, 4, .25));
		//@formatter:off
	}
	
	
	private static void loadSounds()
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
		
		sounds.addLoop("test.wilderness", "/res/audio/wilderness.ogg", 1, 1, 3, 3);
	}
	
	
	public static TxQuad getTxQuad(String key)
	{
		return textures.getQuad(key);
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
