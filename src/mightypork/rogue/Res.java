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
import mightypork.gamecore.util.math.constraints.rect.Rect;


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
		fonts.addAlias("hud", "press_start");
	}
	
	
	private static void loadTextures()
	{
		GLTexture texture;
		QuadGrid grid;
		
		// gui
		texture = textures.loadTexture("/res/img/gui.png", FilterMode.NEAREST, WrapMode.CLAMP);
		
		// small gui elements
		grid = texture.grid(32, 32);
		textures.add("hud.heart.on", grid.makeQuad(0, 0));
		textures.add("hud.heart.off", grid.makeQuad(1, 0));
		textures.add("hud.heart.half", grid.makeQuad(2, 0));
		textures.add("hud.xp.on", grid.makeQuad(0, 1));
		textures.add("hud.xp.off", grid.makeQuad(1, 1));
		
		// nav
		grid = texture.grid(8, 8);
		textures.add("nav.bg", grid.makeQuad(0, 7, 4, 1));
		textures.add("nav.button.bg.base", grid.makeQuad(4, 7));
		textures.add("nav.button.bg.hover", grid.makeQuad(5, 7));
		textures.add("nav.button.bg.down", grid.makeQuad(6, 7));
		
		textures.add("nav.button.fg.eat", grid.makeQuad(0, 6));
		textures.add("nav.button.fg.inventory", grid.makeQuad(1, 6));
		textures.add("nav.button.fg.attack", grid.makeQuad(2, 6));
		textures.add("nav.button.fg.options", grid.makeQuad(3, 6));
		textures.add("nav.button.fg.help", grid.makeQuad(4, 6));
		
		
		// sprites
		texture = textures.loadTexture("/res/img/sprites.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		textures.add("sprite.player", grid.makeSheet(0, 0, 4, 1));
		textures.add("sprite.rat", grid.makeSheet(0, 1, 4, 1));
		
		
		// logo
		texture = textures.loadTexture("/res/img/logo.png", FilterMode.NEAREST, WrapMode.CLAMP);
		textures.add("logo", texture.makeQuad(Rect.make(0, 0, 0.543, 0.203)));
		
		
		// tiles
		texture = textures.loadTexture("tiles", "/res/img/tiles.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		
		textures.add("tile.brick.floor", grid.makeSheet(0, 1, 5, 1));
		textures.add("tile.brick.wall", grid.makeSheet(0, 0, 8, 1));
		
		textures.add("tile.brick.door.closed", grid.makeQuad(1, 2));
		textures.add("tile.brick.door.open", grid.makeQuad(2, 2));
		textures.add("tile.brick.door.secret", grid.makeSheet(0, 3, 2, 1));
		
		textures.add("tile.brick.passage", grid.makeSheet(3, 2, 4, 1));
		textures.add("tile.brick.stairs.up", grid.makeQuad(0, 6));
		textures.add("tile.brick.stairs.down", grid.makeQuad(1, 6));
		
		// shadows
		textures.add("tile.shadow.n", grid.makeQuad(0, 7));
		textures.add("tile.shadow.s", grid.makeQuad(0, 7).flipY());
		textures.add("tile.shadow.w", grid.makeQuad(1, 7));
		textures.add("tile.shadow.e", grid.makeQuad(1, 7).flipX());
		
		textures.add("tile.shadow.nw", grid.makeQuad(2, 7));
		textures.add("tile.shadow.ne", grid.makeQuad(2, 7).flipX());
		textures.add("tile.shadow.sw", grid.makeQuad(2, 7).flipY());
		textures.add("tile.shadow.se", grid.makeQuad(2, 7).flipY().flipX());
		
		// unexplored fog
		textures.add("tile.ufog.n", grid.makeQuad(3, 7));
		textures.add("tile.ufog.s", grid.makeQuad(3, 7).flipY());
		textures.add("tile.ufog.w", grid.makeQuad(4, 7));
		textures.add("tile.ufog.e", grid.makeQuad(4, 7).flipX());
		
		textures.add("tile.ufog.nw", grid.makeQuad(5, 7));
		textures.add("tile.ufog.ne", grid.makeQuad(5, 7).flipX());
		textures.add("tile.ufog.sw", grid.makeQuad(5, 7).flipY());
		textures.add("tile.ufog.se", grid.makeQuad(5, 7).flipY().flipX());
		
		
		texture = textures.loadTexture("items", "/res/img/items.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		textures.add("item.meat", grid.makeQuad(0, 0));
	}
	
	
	private static void loadSounds()
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
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
	public static TxSheet txs(String key)
	{
		return textures.getSheet(key);
	}
	
	
	/**
	 * Get a texture quad by key
	 * 
	 * @param key
	 * @return quad
	 */
	public static TxQuad txq(String key)
	{
		return textures.getQuad(key);
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
