package mightypork.rogue;


import mightypork.gamecore.audio.SoundBank;
import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.control.BaseApp;
import mightypork.gamecore.render.fonts.FontBank;
import mightypork.gamecore.render.fonts.GLFont;
import mightypork.gamecore.render.fonts.Glyphs;
import mightypork.gamecore.render.fonts.impl.DeferredFont;
import mightypork.gamecore.render.textures.*;


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
		fonts.loadFont("polygon_pixel", new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", Glyphs.basic, 16));
		fonts.loadFont("press_start", new DeferredFont("/res/font/PressStart2P.ttf", Glyphs.basic, 16));
		
		// aliases based on concrete usage
		fonts.addAlias("default", "polygon_pixel");
		fonts.addAlias("main_menu_button", "press_start");
		fonts.addAlias("main_menu_title", "press_start");
	}
	
	
	private static void loadTextures()
	{
		GLTexture texture;
		QuadGrid tiles;
		
		// tests
		texture = textures.loadTexture("test.kitten", "/res/img/kitten.png", FilterMode.LINEAR, WrapMode.CLAMP);
		texture = textures.loadTexture("test.kitten2", "/res/img/kitten_npot.png", FilterMode.LINEAR, WrapMode.CLAMP);
		
		// gui
		texture = textures.loadTexture("gui1", "/res/img/gui1.png", FilterMode.NEAREST, WrapMode.CLAMP);
		final QuadGrid gui = texture.grid(4, 4);
		textures.addQuad("item_frame", gui.makeQuad(0, 0));
		textures.addQuad("sword", gui.makeQuad(1, 0));
		textures.addQuad("meat", gui.makeQuad(2, 0));
		textures.addQuad("heart_on", gui.makeQuad(.0, 1, .5, .5));
		textures.addQuad("heart_off", gui.makeQuad(.5, 1, .5, .5));
		textures.addQuad("xp_on", gui.makeQuad(0, 1.5, .5, .5));
		textures.addQuad("xp_off", gui.makeQuad(.5, 1.5, .5, .5));
		textures.addQuad("panel", gui.makeQuad(0, 3.75, 4, .25));
		
		// huge sheet
//		texture = textures.loadTexture("tiles", "/res/img/tiles.png", FilterMode.NEAREST, WrapMode.CLAMP);
//		tiles = texture.grid(32, 32);		
//		textures.addSheet("tile.wall.mossy_bricks", tiles.makeSheet(4, 0, 7, 1));
//		textures.addSheet("tile.wall.small_bricks", tiles.makeSheet(0, 0, 4, 1));
//		textures.addSheet("tile.floor.mossy_bricks", tiles.makeSheet(16, 5, 7, 1));
//		textures.addSheet("tile.floor.rect_bricks", tiles.makeSheet(23, 5, 4, 1));
//		textures.addSheet("tile.wall.sandstone", tiles.makeSheet(0, 3, 10, 1));
//		textures.addSheet("tile.floor.sandstone", tiles.makeSheet(0, 6, 10, 1));
//		textures.addSheet("tile.wall.brown_cobble", tiles.makeSheet(0, 8, 8, 1));
//		textures.addSheet("tile.floor.brown_cobble", tiles.makeSheet(0, 11, 9, 1));
//		textures.addSheet("tile.floor.crystal", tiles.makeSheet(4, 5, 6, 1));
//		textures.addSheet("tile.wall.crystal", tiles.makeSheet(12, 2, 14, 1));
		
		// sprites
		texture = textures.loadTexture("mob", "/res/img/dudes.png", FilterMode.NEAREST, WrapMode.CLAMP);
		tiles = texture.grid(8, 8);
		textures.addSheet("player", tiles.makeSheet(0, 0, 4, 1));
		
		// small sheet
		texture = textures.loadTexture("tiles16", "/res/img/tiles16.png", FilterMode.NEAREST, WrapMode.CLAMP);
		tiles = texture.grid(8, 8);
		
		textures.addSheet("tile16.floor.dark", tiles.makeSheet(0, 1, 5, 1));
		textures.addSheet("tile16.wall.brick", tiles.makeSheet(0, 0, 5, 1));
		
		textures.addQuad("tile16.shadow.n", tiles.makeQuad(0, 7));
		textures.addQuad("tile16.shadow.s", tiles.makeQuad(0, 7).flipY());
		textures.addQuad("tile16.shadow.w", tiles.makeQuad(2, 7));
		textures.addQuad("tile16.shadow.e", tiles.makeQuad(2, 7).flipX());
		
		textures.addQuad("tile16.shadow.nw", tiles.makeQuad(1, 7));
		textures.addQuad("tile16.shadow.ne", tiles.makeQuad(1, 7).flipX());
		textures.addQuad("tile16.shadow.sw", tiles.makeQuad(1, 7).flipY());
		textures.addQuad("tile16.shadow.se", tiles.makeQuad(1, 7).flipY().flipX());
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
	
	
	public static GLTexture getTexture(String key)
	{
		return textures.getTexture(key);
	}
	
	
	public static TxSheet getTxSheet(String key)
	{
		return textures.getSheet(key);
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
