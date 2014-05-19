package mightypork.rogue;


import mightypork.gamecore.resources.ResourceSetup;
import mightypork.gamecore.resources.audio.SoundBank;
import mightypork.gamecore.resources.fonts.FontBank;
import mightypork.gamecore.resources.fonts.Glyphs;
import mightypork.gamecore.resources.fonts.impl.DeferredFont;
import mightypork.gamecore.resources.textures.FilterMode;
import mightypork.gamecore.resources.textures.GLTexture;
import mightypork.gamecore.resources.textures.QuadGrid;
import mightypork.gamecore.resources.textures.TextureBank;
import mightypork.gamecore.resources.textures.WrapMode;
import mightypork.gamecore.util.math.constraints.rect.Rect;


public class RogueResources implements ResourceSetup {
	
	
	@Override
	public void addFonts(FontBank fonts)
	{
		DeferredFont font;
		
		//fonts.loadFont("polygon_pixel", new DeferredFont("/res/font/PolygonPixel5x7Standard.ttf", Glyphs.basic, 16));
		fonts.addFont("press_start", font = new DeferredFont("/res/font/PressStart2P.ttf", Glyphs.basic, 16));
		
		fonts.addFont("battlenet", font = new DeferredFont("/res/font/battlenet.ttf", Glyphs.basic, 16));
		font.setDiscardRatio(3 / 16D, 2 / 16D);
		
		fonts.addFont("tinyutf", font = new DeferredFont("/res/font/TinyUnicode2.ttf", Glyphs.basic, 16));
		font.setDiscardRatio(5 / 16D, 3 / 16D);
		
		// aliases
		fonts.addAlias("thick", "press_start");
		fonts.addAlias("thin", "battlenet");
		fonts.addAlias("tiny", "tinyutf");
	}
	
	
	@Override
	public void addSounds(SoundBank sounds)
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
	}
	
	
	@Override
	public void addTextures(TextureBank textures)
	{
		GLTexture texture;
		QuadGrid grid;
		
		// gui
		texture = textures.addTexture("/res/img/gui.png", FilterMode.NEAREST, WrapMode.CLAMP);
		
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
		textures.add("nav.button.fg.menu", grid.makeQuad(3, 6));
		textures.add("nav.button.fg.help", grid.makeQuad(4, 6));
		textures.add("nav.button.fg.map", grid.makeQuad(5, 6));
		textures.add("nav.button.fg.pause", grid.makeQuad(6, 6));
		textures.add("nav.button.fg.magnify", grid.makeQuad(7, 6));
		textures.add("nav.button.fg.save", grid.makeQuad(7, 5));
		textures.add("nav.button.fg.load", grid.makeQuad(6, 5));
		
		textures.add("inv.slot.base", grid.makeQuad(0, 5));
		textures.add("inv.slot.selected", grid.makeQuad(1, 5));
		
		
		// sprites
		texture = textures.addTexture("/res/img/sprites.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		textures.add("sprite.player", grid.makeSheet(0, 0, 4, 1));
		textures.add("sprite.rat.gray", grid.makeSheet(0, 1, 4, 1));
		textures.add("sprite.rat.brown", grid.makeSheet(0, 2, 4, 1));
		textures.add("sprite.rat.boss", grid.makeSheet(0, 3, 4, 1));
		textures.add("sprite.zzz", grid.makeQuad(0, 7)); // sleep thingy
		
		
		// logo
		texture = textures.addTexture("/res/img/logo.png", FilterMode.NEAREST, WrapMode.CLAMP);
		textures.add("logo", texture.makeQuad(Rect.make(0, 0, 0.543, 0.203)));
		grid = texture.grid(8, 8);
		textures.add("death", grid.makeQuad(0, 2));
		
		
		// tiles
		texture = textures.addTexture("tiles", "/res/img/tiles.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		
		textures.add("tile.brick.floor", grid.makeSheet(0, 1, 5, 1));
		textures.add("tile.brick.wall", grid.makeSheet(0, 0, 8, 1));
		
		textures.add("tile.brick.door.closed", grid.makeQuad(1, 2));
		textures.add("tile.brick.door.open", grid.makeQuad(2, 2));
		textures.add("tile.brick.door.secret", grid.makeSheet(0, 3, 2, 1));
		
		textures.add("tile.brick.passage", grid.makeSheet(3, 2, 4, 1));
		textures.add("tile.brick.stairs.up", grid.makeQuad(0, 6));
		textures.add("tile.brick.stairs.down", grid.makeQuad(1, 6));
		textures.add("tile.extra.chest.closed", grid.makeQuad(0, 4));
		textures.add("tile.extra.chest.open", grid.makeQuad(1, 4));
		
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
		textures.add("tile.ufog.full", grid.makeQuad(6, 7));
		
		
		texture = textures.addTexture("items", "/res/img/items.png", FilterMode.NEAREST, WrapMode.CLAMP);
		grid = texture.grid(8, 8);
		textures.add("item.meat", grid.makeQuad(0, 0));
		textures.add("item.club", grid.makeQuad(1, 0));
		textures.add("item.sword", grid.makeQuad(2, 0));
		textures.add("item.axe", grid.makeQuad(3, 0));
		textures.add("item.stone", grid.makeQuad(4, 0));
		textures.add("item.bone", grid.makeQuad(5, 0));
		textures.add("item.cheese", grid.makeQuad(6, 0));
		textures.add("item.sandwich", grid.makeQuad(7, 0));
		textures.add("item.heart", grid.makeQuad(0, 1));
		textures.add("item.knife", grid.makeQuad(1, 1));
		textures.add("item.twig", grid.makeQuad(2, 1));
	}
	
}
