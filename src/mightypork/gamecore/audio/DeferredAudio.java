package mightypork.gamecore.audio;


import mightypork.gamecore.core.App;
import mightypork.gamecore.resources.BaseDeferredResource;
import mightypork.utils.annotations.Alias;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract deferred audio, to be extended in backend.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Audio")
public abstract class DeferredAudio extends BaseDeferredResource implements IAudio {
	
	/**
	 * Create audio
	 * 
	 * @param resourceName resource to load (when needed)
	 */
	public DeferredAudio(String resourceName) {
		super(resourceName);
	}
	
	
	@Override
	public void play(double pitch, double gain, boolean loop)
	{
		play(pitch, gain, loop, App.audio().getListenerPos());
	}
	
	
	@Override
	public void play(double pitch, double gain, boolean loop, double x, double y)
	{
		play(pitch, gain, loop, x, y, App.audio().getListenerPos().z());
	}
	
	
	@Override
	public void play(double pitch, double gain, boolean loop, Vect pos)
	{
		play(pitch, gain, loop, pos.x(), pos.y(), pos.z());
	}
}
