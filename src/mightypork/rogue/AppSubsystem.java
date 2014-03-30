package mightypork.rogue;


import java.util.HashSet;
import java.util.Set;

import mightypork.rogue.display.DisplaySystem;
import mightypork.rogue.display.events.UpdateEvent;
import mightypork.rogue.input.InputSystem;
import mightypork.rogue.sounds.SoundSystem;
import mightypork.utils.logging.Log;
import mightypork.utils.patterns.Destroyable;
import mightypork.utils.patterns.subscription.MessageBus;
import mightypork.utils.time.Updateable;


public abstract class AppSubsystem implements AppAccess, UpdateEvent.Listener, Updateable, Destroyable {

	private AppAccess app;
	private boolean wantUpdates;
	private boolean destroyed = false;

	/** Subsystem children subscribing to MessageBus */
	private Set<Object> childSubscribers = new HashSet<Object>();


	/**
	 * Create a subsystem
	 * 
	 * @param app app instance access
	 * @param joinBus whether to initially join msgbus
	 */
	public AppSubsystem(AppAccess app, boolean joinBus) {
		this.app = app;

		// add to subscriber group
		childSubscribers.add(this);

		enableEvents(joinBus);

		enableUpdates(true);

		init();
	}


	/**
	 * Set whether events should be received.<br>
	 * This includes {@link UpdateEvent}, so disabling events also disables
	 * updates.
	 * 
	 * @param enable
	 */
	protected final void enableEvents(boolean enable)
	{
		assertLive();

		// this & child subscribers
		for (Object o : childSubscribers) {
			if (enable) {
				app.msgbus().addSubscriber(o);
			} else {
				app.msgbus().removeSubscriber(o);
			}
		}
	}


	/**
	 * Set whether to receive {@link UpdateEvent}s (delta timing, one each
	 * frame).<br>
	 * 
	 * @param enable
	 */
	protected final void enableUpdates(boolean enable)
	{
		assertLive();

		wantUpdates = enable;
	}


	@Override
	public final void receive(UpdateEvent event)
	{
		assertLive();

		if (wantUpdates) update(event.getDeltaTime());
	}


//	/**
//	 * @return app instance
//	 */
//	protected AppAccess app()
//	{
//		assertLive();
//
//		return app;
//	}

	@Override
	public void update(double delta)
	{
		Log.w("Subsystem " + getClass().getSimpleName() + " receives updates, but does not override the update() method.");
	}


	/**
	 * Initialize the subsystem<br>
	 * (called during construction)
	 */
	protected abstract void init();


	/**
	 * Deinitialize the subsystem<br>
	 * (called during destruction)<br>
	 * <br>
	 * All child eventbus subscribers will be removed from the eventbus.
	 */
	protected abstract void deinit();


	/**
	 * Add a child subscriber to the {@link MessageBus}.<br>
	 * Child subscribers are removed when subsystem is destroyed, and can be
	 * connected/disconnected using the <code>enableEvents()</code> method.
	 * 
	 * @param client
	 * @return true on success
	 */
	public final boolean addChildSubscriber(Object client)
	{
		assertLive();

		if (client == null) return false;

		msgbus().addSubscriber(client);
		childSubscribers.add(client);

		return true;
	}


	public final void removeChildSubscriber(Object client)
	{
		assertLive();

		childSubscribers.remove(client);
		msgbus().removeSubscriber(client);
	}


	@Override
	public final void destroy()
	{
		assertLive();

		deinit();

		enableEvents(false); // remove all subscribers from bus
		app = null;

		destroyed = true;
	}


	@Override
	public MessageBus msgbus()
	{
		assertLive();

		return app.msgbus();
	}


	@Override
	public SoundSystem soundsys()
	{
		assertLive();

		return app.soundsys();
	}


	@Override
	public InputSystem input()
	{
		assertLive();

		return app.input();
	}


	@Override
	public DisplaySystem disp()
	{
		assertLive();

		return app.disp();
	}


	private void assertLive()
	{
		if (destroyed) throw new IllegalStateException("Subsystem already destroyed.");
	}
}
