package mightypork.rogue.audio;


public class NullAudio extends DeferredAudio {
	
	public NullAudio() {
		super("");
	}
	
	
	@Override
	public void load()
	{
	}
	
	
	@Override
	public boolean isLoaded()
	{
		return true;
	}
	
	
	@Override
	protected boolean ensureLoaded()
	{
		return false;
	}
	
}
