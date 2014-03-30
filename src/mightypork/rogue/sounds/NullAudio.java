package mightypork.rogue.sounds;


public class NullAudio extends AudioX {

	public NullAudio() {
		super("");
	}


	@Override
	public boolean load()
	{
		return false;
	}

}
