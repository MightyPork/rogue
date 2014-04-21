package mightypork.gamecore.loading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.gamecore.control.events.MainLoopTaskRequest;
import mightypork.gamecore.control.events.ResourceLoadRequest;
import mightypork.util.annotations.FactoryMethod;
import mightypork.util.control.Destroyable;
import mightypork.util.control.eventbus.BusAccess;
import mightypork.util.logging.Log;


/**
 * Asynchronous resource loading thread.
 * 
 * @author MightyPork
 */
public class AsyncResourceLoader extends Thread implements ResourceLoadRequest.Listener, Destroyable {
	
	/**
	 * Start a new loader thread.
	 * 
	 * @param app app access
	 * @return the launched thread
	 */
	@FactoryMethod
	public static AsyncResourceLoader launch(BusAccess app)
	{
		final AsyncResourceLoader loader = new AsyncResourceLoader(app);
		loader.setDaemon(true);
		loader.start();
		return loader;
	}
	
	private final ExecutorService exs = Executors.newCachedThreadPool();
	
	private final LinkedBlockingQueue<Deferred> toLoad = new LinkedBlockingQueue<>();
	private volatile boolean stopped;
	private final BusAccess app;
	private volatile boolean mainLoopQueuing = false;
	
	
	public void enableMainLoopQueuing(boolean yes)
	{
		mainLoopQueuing = yes;
	}
	
	
	/**
	 * @param app app acceess
	 */
	public AsyncResourceLoader(BusAccess app)
	{
		super("Deferred loader");
		this.app = app;
		app.getEventBus().subscribe(this);
	}
	
	
	@Override
	public void loadResource(final Deferred resource)
	{
		if (resource.isLoaded()) return;
		
		// textures & fonts needs to be loaded in main thread
		if (resource.getClass().isAnnotationPresent(MustLoadInMainThread.class)) {
			
			if (!mainLoopQueuing) {
				Log.f3("<LOADER> Cannot load async: " + Log.str(resource));
			} else {
				Log.f3("<LOADER> Delegating to main thread:\n    " + Log.str(resource));
				
				app.getEventBus().send(new MainLoopTaskRequest(new Runnable() {
					
					@Override
					public void run()
					{
						resource.load();
					}
				}));
			}
			
			return;
		}
		
		toLoad.add(resource);
	}
	
	
	@Override
	public void run()
	{
		Log.f3("Asynchronous resource loader started.");
		
		while (!stopped) {
			
			try {
				final Deferred def = toLoad.take();
				if (def == null) continue;
				
				if (!def.isLoaded()) {
					
					Log.f3("<LOADER> Loading: " + Log.str(def));
					
					exs.submit(new Runnable() {
						
						@Override
						public void run()
						{
							def.load();
						}
					});
				}
				
			} catch (final InterruptedException ignored) {
				//
			}
			
		}
	}
	
	
	// apparently, destroy method exists on thread :/
	@SuppressWarnings("deprecation")
	@Override
	public void destroy()
	{
		Log.i("Stopping resource loader thread.");
		stopped = true;
		exs.shutdownNow();
	}
	
}
