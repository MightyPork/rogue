package mightypork.gamecore.control.bus;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import mightypork.gamecore.control.bus.events.Event;
import mightypork.gamecore.control.bus.events.types.DelayedEvent;
import mightypork.gamecore.control.bus.events.types.ImmediateEvent;
import mightypork.gamecore.control.bus.events.types.SingleReceiverEvent;
import mightypork.gamecore.control.bus.events.types.UnloggedEvent;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.utils.logging.Log;


/**
 * An event bus, accommodating multiple {@link EventChannel}s.
 * 
 * @author MightyPork
 */
final public class EventBus implements Destroyable {
	
	/** Message channels */
	private final BufferedHashSet<EventChannel<?, ?>> channels = new BufferedHashSet<EventChannel<?, ?>>();
	
	/** Registered clients */
	private final BufferedHashSet<Object> clients = new BufferedHashSet<Object>();
	
	/** Messages queued for delivery */
	private final DelayQueue<DelayQueueEntry> sendQueue = new DelayQueue<DelayQueueEntry>();
	
	/** Queue polling thread */
	private final QueuePollingThread busThread;
	
	/** Whether the bus was destroyed */
	private boolean dead = false;
	
	public boolean logSending = false;
	
	
	/**
	 * Make a new bus and start it's queue thread.
	 */
	public EventBus() {
		busThread = new QueuePollingThread();
		busThread.start();
	}
	
	
	private boolean shallLog(Event<?> event)
	{
		if (!logSending) return false;
		if (event.getClass().isAnnotationPresent(UnloggedEvent.class)) return false;
		
		return true;
	}
	
	
	/**
	 * Add a {@link EventChannel} to this bus.<br>
	 * If a channel of matching types is already added, it is returned instead.
	 * 
	 * @param channel channel to be added
	 * @return the channel that's now in the bus
	 */
	public EventChannel<?, ?> addChannel(EventChannel<?, ?> channel)
	{
		assertLive();
		
		// if the channel already exists, return this instance instead.
		for (final EventChannel<?, ?> ch : channels) {
			if (ch.equals(channel)) {
				Log.w("<bus> Channel of type " + Log.str(channel) + " already registered.");
				return ch;
			}
		}
		
		channels.add(channel);
		
		return channel;
	}
	
	
	/**
	 * Add a channel for given event and client type.
	 * 
	 * @param eventClass event type
	 * @param clientClass client type
	 * @return the created channel instance
	 */
	public <F_EVENT extends Event<F_CLIENT>, F_CLIENT> EventChannel<?, ?> addChannel(Class<F_EVENT> eventClass, Class<F_CLIENT> clientClass)
	{
		assertLive();
		
		final EventChannel<F_EVENT, F_CLIENT> channel = EventChannel.create(eventClass, clientClass);
		return addChannel(channel);
	}
	
	
	/**
	 * Remove a {@link EventChannel} from this bus
	 * 
	 * @param channel true if channel was removed
	 */
	public void removeChannel(EventChannel<?, ?> channel)
	{
		assertLive();
		
		channels.remove(channel);
	}
	
	
	/**
	 * Send based on annotation.
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
		
		if (shallLog(event)) Log.f3("<bus> Qu " + Log.str(event) + ", t = +" + delay + "s");
		
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
		
		if (shallLog(event)) Log.f3("<bus> Di " + Log.str(event));
		
		dispatch(event);
	}
	
	
	/**
	 * Send immediately.<br>
	 * Should be used for real-time events that require immediate response, such
	 * as timing events.
	 * 
	 * @param event event
	 */
	private void dispatch(Event<?> event)
	{
		assertLive();
		
		synchronized (this) {
			channels.setBuffering(true);
			clients.setBuffering(true);
			
			boolean sent = false;
			boolean accepted = false;
			
			final boolean singular = event.getClass().isAnnotationPresent(SingleReceiverEvent.class);
			
			for (final EventChannel<?, ?> b : channels) {
				if (b.canBroadcast(event)) {
					accepted = true;
					sent |= b.broadcast(event, clients);
				}
				
				if (sent && singular) break;
			}
			
			if (!accepted) Log.e("<bus> Not accepted by any channel: " + Log.str(event));
			if (!sent && shallLog(event)) Log.w("<bus> Not delivered: " + Log.str(event));
			
			channels.setBuffering(false);
			clients.setBuffering(false);
		}
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
	}
	
	
	public boolean isClientValid(Object client)
	{
		assertLive();
		
		if (client == null) return false;
		
		for (final EventChannel<?, ?> ch : channels) {
			if (ch.isClientValid(client)) {
				return true;
			}
		}
		
		return false;
	}
	
	private class DelayQueueEntry implements Delayed {
		
		private final long due;
		private Event<?> evt = null;
		
		
		public DelayQueueEntry(double seconds, Event<?> event) {
			super();
			this.due = System.currentTimeMillis() + (long) (seconds * 1000);
			this.evt = event;
		}
		
		
		@Override
		public int compareTo(Delayed o)
		{
			return -Long.valueOf(o.getDelay(TimeUnit.MILLISECONDS)).compareTo(getDelay(TimeUnit.MILLISECONDS));
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
	
	private class QueuePollingThread extends Thread {
		
		public boolean stopped = false;
		
		
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
	 * Make sure the bus is not destroyed.
	 * 
	 * @throws IllegalStateException if the bus is dead.
	 */
	private void assertLive() throws IllegalStateException
	{
		if (dead) throw new IllegalStateException("EventBus is dead.");
	}
	
}