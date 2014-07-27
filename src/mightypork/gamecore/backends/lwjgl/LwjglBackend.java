package mightypork.gamecore.backends.lwjgl;


import mightypork.gamecore.audio.AudioModule;
import mightypork.gamecore.backends.lwjgl.audio.SlickAudioModule;
import mightypork.gamecore.backends.lwjgl.graphics.LwjglGraphicsModule;
import mightypork.gamecore.core.AppBackend;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.input.InputModule;


/**
 * Game backend using LWJGL and SlickUtil
 * 
 * @author MightyPork
 */
public class LwjglBackend extends AppBackend {
	
	private LwjglGraphicsModule graphics;
	private SlickAudioModule audio;
	private LwjglInputModule input;
	
	
	@Override
	public void initialize()
	{
		addChildClient(graphics = new LwjglGraphicsModule());
		addChildClient(audio = new SlickAudioModule());
		addChildClient(input = new LwjglInputModule());
		
		graphics.init();
		audio.init();
		input.init();
		
		app.addInitTask(new InitTaskRedirectSlickLog());
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
	
	
	@Override
	public InputModule getInput()
	{
		return input;
	}
}
