package mightypork.gamecore.resources;


import mightypork.gamecore.audio.SoundRegistry;
import mightypork.gamecore.graphics.fonts.FontRegistry;
import mightypork.gamecore.graphics.textures.TextureRegistry;


/**
 * Resource binder; used by apps to specify what resources are to be loaded.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public interface ResourceSetup {
	
	/**
	 * Add fonts to load.
	 * 
	 * @param fonts font registry
	 */
	void addFonts(FontRegistry fonts);
	
	
	/**
	 * Add sounds to load.
	 * 
	 * @param sounds sound registry
	 */
	void addSounds(SoundRegistry sounds);
	
	
	/**
	 * Add textures to load
	 * 
	 * @param textures texture registry
	 */
	void addTextures(TextureRegistry textures);
}
