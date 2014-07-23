package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.utils.annotations.Stub;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.animation.NumAnimated;
import mightypork.utils.math.timing.TimedTask;


/**
 * Layer that smoothly appears/disappears when shown/hidden
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class FadingLayer extends ScreenLayer {
	
	private final NumAnimated numa;
	private final TimedTask hideTimer = new TimedTask() {
		
		@Override
		public void run()
		{
			FadingLayer.super.hide();
			fadingOut = false;
			onHideFinished();
		}
	};
	
	private final TimedTask showTimer = new TimedTask() {
		
		@Override
		public void run()
		{
			fadingIn = false;
			onShowFinished();
		}
	};
	
	private boolean fadingIn = false;
	private boolean fadingOut = false;
	
	
	/**
	 * Create with default fading time and effect
	 * 
	 * @param screen
	 */
	public FadingLayer(Screen screen) {
		this(screen, new NumAnimated(1, Easing.QUADRATIC_OUT, 0.3));
	}
	
	
	/**
	 * @param screen
	 * @param easingAnim the animation num
	 */
	public FadingLayer(Screen screen, NumAnimated easingAnim) {
		super(screen);
		
		numa = easingAnim;
		
		updated.add(numa);
		updated.add(hideTimer);
		updated.add(showTimer);
		
		setAlpha(numa);
	}
	
	
	/**
	 * Called after the fade-out was completed
	 */
	@Stub
	protected void onHideFinished()
	{
	}
	
	
	/**
	 * Called after the fade-in was completed
	 */
	@Stub
	protected void onShowFinished()
	{
	}
	
	
	/**
	 * Show with fading
	 */
	@Override
	public void show()
	{
		if (fadingIn) return;
		
		if (!isVisible() || fadingOut) {
			super.show();
			numa.fadeIn();
			hideTimer.stop();
			showTimer.start(numa.getDefaultDuration());
			
			fadingOut = false;
			fadingIn = true;
		}
	}
	
	
	/**
	 * Hide without fading
	 */
	public void hideImmediate()
	{
		hideTimer.stop();
		numa.setTo(0);
		super.hide();
		onHideFinished();
	}
	
	
	/**
	 * Show without fading
	 */
	public void showImmediate()
	{
		hideTimer.stop();
		numa.setTo(1);
		super.show();
		onShowFinished();
	}
	
	
	/**
	 * Hide with fading
	 */
	@Override
	public void hide()
	{
		if (fadingOut) return;
		
		if (isVisible()) {
			numa.fadeOut();
			hideTimer.start(numa.getDefaultDuration());
			
			fadingOut = true;
			fadingIn = false;
		}
	}
	
}
