package mightypork.gamecore.backend.lwjgl;


import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.render.GraphicsModule;
import mightypork.gamecore.resources.audio.AudioModule;


/**
 * Game backend using LWJGL and SlickUtil
 * 
 * @author MightyPork
 */
public class LwjglBackend extends Backend {
	
	private LwjglGraphicsModule graphics;
	private SlickAudioModule audio;
	
	
	@Override
	public void initialize()
	{
		addChildClient(graphics = new LwjglGraphicsModule());
		addChildClient(audio = new SlickAudioModule());
	}
	
	
	@Override
	public GraphicsModule getGraphics()
	{
		return graphics;
	}
	
	
	@Override
	public AudioModule getAudio()
	{
		return audio;
	}
}
