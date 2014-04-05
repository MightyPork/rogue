package mightypork.rogue;


import mightypork.rogue.sound.SoundBank;
import mightypork.rogue.sound.players.EffectPlayer;
import mightypork.rogue.sound.players.LoopPlayer;
import mightypork.rogue.texture.TextureBank;
import mightypork.rogue.texture.TxQuad;

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
		
		loadSounds();
		loadTextures();
		loadFonts();
	}
	
	
	private static void loadFonts()
	{
		//
	}
	
	
	private static void loadTextures()
	{
		textures.loadTexture("test.kitten", "/res/img/kitten.png");
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
	
}
