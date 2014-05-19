package mightypork.gamecore.resources;


import mightypork.gamecore.resources.audio.SoundBank;
import mightypork.gamecore.resources.fonts.FontBank;
import mightypork.gamecore.resources.textures.TextureBank;


/**
 * Resource binder; used by apps to specify what resources are to be loaded.
 * 
 * @author MightyPork
 */
public interface ResourceSetup {
	
	/**
	 * Add fonts to load.
	 * 
	 * @param fonts font registry
	 */
	void addFonts(FontBank fonts);
	
	
	/**
	 * Add sounds to load.
	 * 
	 * @param sounds sound registry
	 */
	void addSounds(SoundBank sounds);
	
	
	/**
	 * Add textures to load
	 * 
	 * @param textures texture registry
	 */
	void addTextures(TextureBank textures);
}
