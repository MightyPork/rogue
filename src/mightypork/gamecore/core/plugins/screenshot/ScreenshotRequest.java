package mightypork.gamecore.core.plugins.screenshot;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Event used to request screenshot capture.
 * 
 * @author MightyPork
 */
@SingleReceiverEvent
public class ScreenshotRequest extends BusEvent<ScreenshotPlugin> {
	
	@Override
	protected void handleBy(ScreenshotPlugin handler)
	{
		handler.takeScreenshot();
	}
	
}
