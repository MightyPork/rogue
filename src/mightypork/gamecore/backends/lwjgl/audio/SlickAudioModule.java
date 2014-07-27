package mightypork.gamecore.backends.lwjgl.audio;


import java.nio.FloatBuffer;

import mightypork.gamecore.audio.AudioModule;
import mightypork.gamecore.audio.DeferredAudio;
import mightypork.gamecore.backends.lwjgl.BufferHelper;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.var.VectVar;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.SoundStore;


/**
 * SlickUtil-based audio module
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class SlickAudioModule extends AudioModule {
	
	private final VectVar listenerPos = Vect.makeVar();
	
	
	@Override
	public void init()
	{
		SoundStore.get().setMaxSources(256);
		SoundStore.get().init();
		setListenerPos(Vect.ZERO);
	}
	
	
	@Override
	public void setListenerPos(Vect pos)
	{
		listenerPos.setTo(pos);
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
		return listenerPos;
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
