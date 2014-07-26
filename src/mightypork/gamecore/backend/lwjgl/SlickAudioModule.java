package mightypork.gamecore.backend.lwjgl;


import java.nio.FloatBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;

import mightypork.gamecore.resources.audio.AudioModule;
import mightypork.gamecore.resources.audio.DeferredAudio;
import mightypork.gamecore.util.BufferHelper;
import mightypork.utils.logging.Log;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.var.VectVar;


/**
 * SlickUtil-based audio module
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class SlickAudioModule extends AudioModule {
	
	private static final Vect INITIAL_LISTENER_POS = Vect.ZERO;
	private static final int MAX_SOURCES = 256;
	
	private VectVar listener = Vect.makeVar();
	private static boolean soundSystemInited = false;
	
	
	public SlickAudioModule() {
		
		if (!soundSystemInited) {
			soundSystemInited = true;
			
			try {
				SoundStore.get().setMaxSources(MAX_SOURCES);
				SoundStore.get().init();
				setListenerPos(INITIAL_LISTENER_POS);
			} catch (final Throwable t) {
				Log.e("Error initializing sound system.", t);
			}
		}
	}
	
	
	@Override
	public void setListenerPos(Vect pos)
	{
		listener.setTo(pos);
		final FloatBuffer buf3 = BufferHelper.alloc(3);
		final FloatBuffer buf6 = BufferHelper.alloc(6);
		buf3.clear();
		BufferHelper.fill(buf3, (float) pos.x(), (float) pos.y(), (float) pos.z());
		AL10.alListener(AL10.AL_POSITION, buf3);
		buf3.clear();
		BufferHelper.fill(buf3, 0, 0, 0);
		AL10.alListener(AL10.AL_VELOCITY, buf3);
		buf6.clear();
		BufferHelper.fill(buf6, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
		AL10.alListener(AL10.AL_ORIENTATION, buf6);
	}
	
	
	@Override
	public Vect getListenerPos()
	{
		return listener;
	}
	
	
	@Override
	protected void deinitSoundSystem()
	{
		SoundStore.get().clear();
		AL.destroy();
	}
	
	
	@Override
	protected DeferredAudio doCreateResource(String res)
	{
		return new SlickAudio(res);
	}
	
}
