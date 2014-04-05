package mightypork.utils.control.bus;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.logging.Log;


/**
 * An event bus, accommodating multiple {@link EventChannel}s.
 * 
 * @author MightyPork
 */
final public class EventBus implements Destroyable {
	
	/** Message channels */
	private BufferedHashSet<EventChannel<?, ?>> channels = new BufferedHashSet<EventChannel<?, ?>>();
	
	/** Registered clients */
	private BufferedHashSet<Object> clients = new BufferedHashSet<Object>();
	
	/** Messages queued for delivery */
	private DelayQueue<DelayedMessage> sendQueue = new DelayQueue<DelayedMessage>();
	
	/** Queue polling thread */
	private QueuePollingThread busThread;
	
	/** Log all */
	private boolean logging = false;
	
	/** Whether the bus was destroyed */
	private boolean dead = false;
	
	
	/**
	 * Make a new bus and start it's queue thread.
	 */
	public EventBus() {
		busThread = new QueuePollingThread();
		busThread.start();
	}
	
	
	/**
	 * Enable a level of logging.
	 * 
	 * @param level 0 none, 1 warning only, 2 all
	 */
	public void enableLogging(boolean level)
	{
		assertLive();
		
		logging = level;
		
		for (EventChannel<?, ?> ch : channels) {
			ch.enableLogging(logging);
		}
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
		for (EventChannel<?, ?> ch : channels) {
			if (ch.equals(channel)) {
				Log.w("<bus> Channel of type " + Log.str(channel) + " already registered.");
				return ch;
			}
		}
		
		channels.add(channel);
		channel.enableLogging(logging);
		if (logging) Log.f3("<bus> Added chanel: " + Log.str(channel));
		
		return channel;
	}
	
	
	/**
	 * Add a channel for given message and client type.
	 * 
	 * @param messageClass message type
	 * @param clientClass client type
	 * @return the created channel instance
	 */
	public <F_EVENT extends Event<F_CLIENT>, F_CLIENT> EventChannel<?, ?> addChannel(Class<F_EVENT> messageClass, Class<F_CLIENT> clientClass)
	{
		assertLive();
		
		EventChannel<F_EVENT, F_CLIENT> channel = EventChannel.create(messageClass, clientClass);
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
	 * Add message to a queue
	 * 
	 * @param message message
	 */
	public void queue(Event<?> message)
	{
		assertLive();
		
		schedule(message, 0);
	}
	
	
	/**
	 * Add message to a queue, scheduled for given time.
	 * 
	 * @param message message
	 * @param delay delay before message is dispatched
	 */
	public void schedule(Event<?> message, double delay)
	{
		assertLive();
		
		DelayedMessage dm = new DelayedMessage(delay, message);
		
		if (logging) Log.f3("<bus> + [ Queuing: " + Log.str(message) + " ]");
		
		sendQueue.add(dm);
	}
	
	
	/**
	 * Send immediately.<br>
	 * Should be used for real-time events that require immediate response, such
	 * as timing events.
	 * 
	 * @param message message
	 */
	public void send(Event<?> message)
	{
		assertLive();
		
		synchronized (this) {
			channels.setBuffering(true);
			clients.setBuffering(true);
			
			if (logging) Log.f3("<bus> - [ Sending: " + Log.str(message) + " ]");
			
			boolean sent = false;
			boolean channelAccepted = false;
			
			for (EventChannel<?, ?> b : channels) {
				if (b.canBroadcast(message)) channelAccepted = true;
				sent |= b.broadcast(message, clients);
			}
			
			// more severe
			if (!channelAccepted) Log.w("<bus> Not accepted by any channel: " + Log.str(message));
			
			// less severe
			if (logging && !sent) Log.w("<bus> Not delivered to any client: " + Log.str(message));
			
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
		
		if (logging) Log.f3("<bus> ADDING CLIENT " + client);
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
		if (logging) Log.f3("<bus> REMOVING CLIENT " + client);
		
	}
	
	
	public boolean isClientValid(Object client)
	{
		assertLive();
		
		if (client == null) return false;
		
		for (EventChannel<?, ?> ch : channels) {
			if (ch.isClientValid(client)) {
				return true;
			}
		}
		
		return false;
	}
	
	private class DelayedMessage implements Delayed {
		
		private long due;
		private Event<?> theMessage = null;
		
		
		public DelayedMessage(double seconds, Event<?> theMessage) {
			super();
			this.due = System.currentTimeMillis() + (long) (seconds * 1000);
			this.theMessage = theMessage;
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
		
		
		public Event<?> getMessage()
		{
			return theMessage;
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
			DelayedMessage dm;
			
			while (!stopped) {
				dm = null;
				
				try {
					dm = sendQueue.take();
				} catch (InterruptedException ignored) {
					//
				}
				
				if (dm != null) {
					send(dm.getMessage());
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
