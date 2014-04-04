package mightypork.rogue;


import mightypork.rogue.audio.EffectPlayer;
import mightypork.rogue.audio.LoopPlayer;
import mightypork.rogue.audio.SoundBank;
import mightypork.rogue.gui.screens.screenBouncy.ScreenTestAnim;
import mightypork.rogue.gui.screens.screenTextures.ScreenTextureTest;
import mightypork.rogue.render.textures.TextureBank;
import mightypork.rogue.render.textures.TxQuad;

import org.newdawn.slick.opengl.Texture;


public class Res {
	
	private static TextureBank textures;
	private static SoundBank sounds;
	private static boolean initialized = false;
	
	
	public static void load(App app)
	{
		if (initialized) return;
		initialized = true;
		
		textures = new TextureBank(app);
		sounds = new SoundBank(app);
		
		loadSounds(app);
		loadTextures(app);
		loadFonts(app);
		loadScreens(app);
	}
	
	
	private static void loadFonts(App app)
	{
		
	}
	
	
	private static void loadTextures(App app)
	{
		textures.loadTexture("test.kitten", "/res/img/kitten.png");
	}
	
	
	private static void loadSounds(App app)
	{
		sounds.addEffect("gui.shutter", "/res/audio/shutter.ogg", 1, 1);
		
		sounds.addLoop("test.wilderness", "/res/audio/wilderness.ogg", 1, 1, 3, 3);
	}
	
	
	private static void loadScreens(App app)
	{
		app.screens.add("test.anim", new ScreenTestAnim(app));
		app.screens.add("test.texture", new ScreenTextureTest(app));
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
	
}
