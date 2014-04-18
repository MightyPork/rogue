package mightypork.util.control.eventbus;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import mightypork.util.control.Destroyable;
import mightypork.util.control.eventbus.clients.DelegatingClient;
import mightypork.util.control.eventbus.events.Event;
import mightypork.util.control.eventbus.events.flags.DelayedEvent;
import mightypork.util.control.eventbus.events.flags.ImmediateEvent;
import mightypork.util.control.eventbus.events.flags.SingleReceiverEvent;
import mightypork.util.control.eventbus.events.flags.UnloggedEvent;
import mightypork.util.logging.Log;


/**
 * An event bus, accommodating multiple EventChannels.<br>
 * Channel will be created when an event of type is first encountered.
 * 
 * @author MightyPork
 */
final public class EventBus implements Destroyable {
	
	/**
	 * Queued event holder
	 */
	private class DelayQueueEntry implements Delayed {
		
		private final long due;
		private final Event<?> evt;
		
		
		public DelayQueueEntry(double seconds, Event<?> event) {
			super();
			this.due = System.currentTimeMillis() + (long) (seconds * 1000);
			this.evt = event;
		}
		
		
		@Override
		public int compareTo(Delayed o)
		{
			return Long.valueOf(getDelay(TimeUnit.MILLISECONDS)).compareTo(o.getDelay(TimeUnit.MILLISECONDS));
		}
		
		
		@Override
		public long getDelay(TimeUnit unit)
		{
			return unit.convert(due - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		
		
		public Event<?> getEvent()
		{
			return evt;
		}
		
	}
	
	/**
	 * Thread handling queued events
	 */
	private class QueuePollingThread extends Thread {
		
		public volatile boolean stopped = false;
		
		
		public QueuePollingThread() {
			super("Queue Polling Thread");
		}
		
		
		@Override
		public void run()
		{
			DelayQueueEntry evt;
			
			while (!stopped) {
				evt = null;
				
				try {
					evt = sendQueue.take();
				} catch (final InterruptedException ignored) {
					//
				}
				
				if (evt != null) {
					dispatch(evt.getEvent());
				}
			}
		}
		
	}
	
	static final String logMark = "<BUS> ";
	
	
	private static Class<?> getEventListenerClass(Event<?> event)
	{
		// BEHOLD, MAGIC!
		final Type[] interfaces = event.getClass().getGenericInterfaces();
		for (final Type interf : interfaces) {
			if (interf instanceof ParameterizedType) {
				if (((ParameterizedType) interf).getRawType() == Event.class) {
					final Type[] types = ((ParameterizedType) interf).getActualTypeArguments();
					for (final Type genericType : types) {
						return (Class<?>) genericType;
					}
				}
			}
		}
		
		throw new RuntimeException("Could not detect event listener type.");
	}
	
	/** Log detailed messages (debug) */
	public boolean detailedLogging = false;
	
	/** Queue polling thread */
	private final QueuePollingThread busThread;
	
	/** Registered clients */
	private final BufferedHashSet<Object> clients = new BufferedHashSet<>();
	
	/** Whether the bus was destroyed */
	private boolean dead = false;
	
	/** Message channels */
	private final BufferedHashSet<EventChannel<?, ?>> channels = new BufferedHashSet<>();
	
	/** Messages queued for delivery */
	private final DelayQueue<DelayQueueEntry> sendQueue = new DelayQueue<>();
	
	
	/**
	 * Make a new bus and start it's queue thread.
	 */
	public EventBus() {
		busThread = new QueuePollingThread();
		busThread.setDaemon(true);
		busThread.start();
	}
	
	
	/**
	 * Halt bus thread and reject any future events.
	 */
	@Override
	public void destroy()
	{
		assertLive();
		
		busThread.stopped = true;
		dead = true;
	}
	
	
	/**
	 * Send based on annotation
	 * 
	 * @param event event
	 */
	public void send(Event<?> event)
	{
		assertLive();
		
		final DelayedEvent adelay = event.getClass().getAnnotation(DelayedEvent.class);
		if (adelay != null) {
			sendDelayed(event, adelay.delay());
			return;
		}
		
		if (event.getClass().isAnnotationPresent(ImmediateEvent.class)) {
			sendDirect(event);
			return;
		}
		
		sendQueued(event);
	}
	
	
	/**
	 * Add event to a queue
	 * 
	 * @param event event
	 */
	public void sendQueued(Event<?> event)
	{
		assertLive();
		
		sendDelayed(event, 0);
	}
	
	
	/**
	 * Add event to a queue, scheduled for given time.
	 * 
	 * @param event event
	 * @param delay delay before event is dispatched
	 */
	public void sendDelayed(Event<?> event, double delay)
	{
		assertLive();
		
		final DelayQueueEntry dm = new DelayQueueEntry(delay, event);
		
		if (shallLog(event)) {
			Log.f3(logMark + "Qu [" + Log.str(event) + "]" + (delay == 0 ? "" : (", delay: " + delay + "s")));
		}
		
		sendQueue.add(dm);
	}
	
	
	/**
	 * Send immediately.<br>
	 * Should be used for real-time events that require immediate response, such
	 * as timing events.
	 * 
	 * @param event event
	 */
	public void sendDirect(Event<?> event)
	{
		assertLive();
		
		if (shallLog(event)) Log.f3(logMark + "Di [" + Log.str(event) + "]");
		
		dispatch(event);
	}
	
	
	public void sendDirectToChildren(DelegatingClient delegatingClient, Event<?> event)
	{
		assertLive();
		
		if (shallLog(event)) Log.f3(logMark + "Di->sub [" + Log.str(event) + "]");
		
		doDispatch(delegatingClient.getChildClients(), event);
	}
	
	
	/**
	 * Connect a client to the bus. The client will be connected to all current
	 * and future channels, until removed from the bus.
	 * 
	 * @param client the client
	 */
	public void subscribe(Object client)
	{
		assertLive();
		
		if (client == null) return;
		
		clients.add(client);
		
		if (detailedLogging) Log.f3(logMark + "Client joined: " + Log.str(client));
	}
	
	
	/**
	 * Disconnect a client from the bus.
	 * 
	 * @param client the client
	 */
	public void unsubscribe(Object client)
	{
		assertLive();
		
		clients.remove(client);
		
		if (detailedLogging) Log.f3(logMark + "Client left: " + Log.str(client));
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean addChannelForEvent(Event<?> event)
	{
		try {
			if (detailedLogging) {
				Log.f2(logMark + "Setting up channel for new event type: " + Log.str(event.getClass()));
			}
			
			final Class<?> listener = getEventListenerClass(event);
			final EventChannel<?, ?> ch = EventChannel.create(event.getClass(), listener);
			
			if (ch.canBroadcast(event)) {
				
				channels.add(ch);
				//channels.flush();
				
				if (detailedLogging) {
					Log.f2("<bus> Created new channel: " + Log.str(event.getClass()) + " -> " + Log.str(listener));
				}
				
				return true;
				
			} else {
				Log.w(logMark + "Could not create channel for event " + Log.str(event.getClass()));
			}
			
		} catch (final Throwable t) {
			Log.w(logMark + "Error while trying to add channel for event.", t);
		}
		
		return false;
	}
	
	
	/**
	 * Make sure the bus is not destroyed.
	 * 
	 * @throws IllegalStateException if the bus is dead.
	 */
	private void assertLive() throws IllegalStateException
	{
		if (dead) throw new IllegalStateException("EventBus is dead.");
	}
	
	
	/**
	 * Send immediately.<br>
	 * Should be used for real-time events that require immediate response, such
	 * as timing events.
	 * 
	 * @param event event
	 */
	private synchronized void dispatch(Event<?> event)
	{
		assertLive();
		
		clients.setBuffering(true);
		doDispatch(clients, event);
		clients.setBuffering(false);
	}
	
	
	/**
	 * Send to a set of clients
	 * 
	 * @param clients clients
	 * @param event event
	 */
	private synchronized void doDispatch(Collection<Object> clients, Event<?> event)
	{
		boolean sent = false;
		boolean accepted = false;
		
		final boolean singular = event.getClass().isAnnotationPresent(SingleReceiverEvent.class);
		
		for (int i = 0; i < 2; i++) { // two tries.
		
			channels.setBuffering(true);
			for (final EventChannel<?, ?> b : channels) {
				if (b.canBroadcast(event)) {
					accepted = true;
					sent |= b.broadcast(event, clients);
				}
				
				if (sent && singular) break;
			}
			channels.setBuffering(false);
			
			if (!accepted) if (addChannelForEvent(event)) continue;
			
			break;
		}
		
		if (!accepted) Log.e(logMark + "Not accepted by any channel: " + Log.str(event));
		if (!sent && shallLog(event)) Log.w(logMark + "Not delivered: " + Log.str(event));
	}
	
	
	private boolean shallLog(Event<?> event)
	{
		if (!detailedLogging) return false;
		if (event.getClass().isAnnotationPresent(UnloggedEvent.class)) return false;
		
		return true;
	}
	
}