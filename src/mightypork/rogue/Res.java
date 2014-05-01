package mightypork.rogue;


import mightypork.gamecore.app.BaseApp;
import mightypork.gamecore.resources.audio.SoundBank;
import mightypork.gamecore.resources.audio.players.EffectPlayer;
import mightypork.gamecore.resources.audio.players.LoopPlayer;
import mightypork.gamecore.resources.fonts.FontBank;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.resources.fonts.Glyphs;
import mightypork.gamecore.resources.fonts.impl.DeferredFont;
import mightypork.gamecore.resources.textures.*;


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
		fonts.loadFont("press_start", new DeferredFont("/res/font/PressStart2P.ttf", Glyphs.latin, 16));
		
		// aliases based on concrete usage
		fonts.addAlias("default", "polygon_pixel");
		fonts.addAlias("main_menu_button", "press_start");
		fonts.addAlias("main_menu_title", "press_start");
	}
	
	
	private static void loadTextures()
	{
		GLTexture texture;
		QuadGrid tiles;
		
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
		
		// sprites
		texture = textures.loadTexture("mob", "/res/img/dudes.png", FilterMode.NEAREST, WrapMode.CLAMP);
		tiles = texture.grid(8, 8);
		textures.addSheet("sprite.player", tiles.makeSheet(0, 0, 4, 1));
		textures.addSheet("sprite.rat", tiles.makeSheet(0, 1, 4, 1));
		
		// small sheet
		texture = textures.loadTexture("tiles", "/res/img/tiles16.png", FilterMode.NEAREST, WrapMode.CLAMP);
		tiles = texture.grid(8, 8);
		
		textures.addSheet("tile.brick.floor", tiles.makeSheet(0, 1, 5, 1));
		textures.addSheet("tile.brick.wall", tiles.makeSheet(0, 0, 8, 1));
		
		textures.addSheet("tile.brick.door.locked", tiles.makeSheet(1, 2, 1, 1));//TODO unique tx
		textures.addSheet("tile.brick.door.closed", tiles.makeSheet(1, 2, 1, 1));
		textures.addSheet("tile.brick.door.open", tiles.makeSheet(2, 2, 1, 1));
		textures.addSheet("tile.brick.door.secret", tiles.makeSheet(0, 3, 2, 1));
		
		textures.addSheet("tile.brick.passage", tiles.makeSheet(3, 2, 4, 1));
		
		textures.addQuad("tile.shadow.n", tiles.makeQuad(0, 7));
		textures.addQuad("tile.shadow.s", tiles.makeQuad(0, 7).flipY());
		textures.addQuad("tile.shadow.w", tiles.makeQuad(1, 7));
		textures.addQuad("tile.shadow.e", tiles.makeQuad(1, 7).flipX());
		
		textures.addQuad("tile.shadow.nw", tiles.makeQuad(2, 7));
		textures.addQuad("tile.shadow.ne", tiles.makeQuad(2, 7).flipX());
		textures.addQuad("tile.shadow.sw", tiles.makeQuad(2, 7).flipY());
		textures.addQuad("tile.shadow.se", tiles.makeQuad(2, 7).flipY().flipX());
		
		// unexplored fog
		textures.addQuad("tile.ufog.n", tiles.makeQuad(3, 7));
		textures.addQuad("tile.ufog.s", tiles.makeQuad(3, 7).flipY());
		textures.addQuad("tile.ufog.w", tiles.makeQuad(4, 7));
		textures.addQuad("tile.ufog.e", tiles.makeQuad(4, 7).flipX());
		
		textures.addQuad("tile.ufog.nw", tiles.makeQuad(5, 7));
		textures.addQuad("tile.ufog.ne", tiles.makeQuad(5, 7).flipX());
		textures.addQuad("tile.ufog.sw", tiles.makeQuad(5, 7).flipY());
		textures.addQuad("tile.ufog.se", tiles.makeQuad(5, 7).flipY().flipX());
	}
	
	
	private static void loadSounds()
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
	}
	
	
	public static TxQuad getTxQuad(String key)
	{
		return textures.getQuad(key);
	}
	
	
	public static GLTexture getTexture(String key)
	{
		return textures.getTexture(key);
	}
	
	
	public static TxSheet sheet(String key)
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
