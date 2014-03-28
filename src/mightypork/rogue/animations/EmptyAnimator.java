package mightypork.rogue.animations;


/**
 * Empty animation (no effect)
 * 
 * @author MightyPork
 */
public class EmptyAnimator implements GUIRenderer {

	/**
	 * New empty animation
	 */
	public EmptyAnimator() {}


	@Override
	public void updateGui()
	{}


	@Override
	public void render(double delta)
	{}


	@Override
	public void onFullscreenChange()
	{}
}
