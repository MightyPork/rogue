package mightypork.gamecore.resources;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.eventbus.BusAccess;
import mightypork.gamecore.eventbus.events.Destroyable;
import mightypork.gamecore.logging.Log;


/**
 * Asynchronous resource loading thread.
 * 
 * @author MightyPork
 */
public class AsyncResourceLoader extends Thread implements ResourceLoader, Destroyable {
	
	private final ExecutorService exs = Executors.newCachedThreadPool();
	
	private final LinkedBlockingQueue<DeferredResource> toLoad = new LinkedBlockingQueue<>();
	private volatile boolean stopped;
	private BusAccess app;
	private volatile boolean mainLoopQueuing = true;
	
	
	@Override
	public synchronized void init(BusAccess app)
	{
		this.app = app;
		app.getEventBus().subscribe(this);
		setDaemon(true);
		super.start();
	}
	
	
	public void enableMainLoopQueuing(boolean yes)
	{
		mainLoopQueuing = yes;
	}
	
	
	public AsyncResourceLoader()
	{
		super("Deferred loader");
	}
	
	
	@Override
	public void loadResource(final DeferredResource resource)
	{
		if (resource.isLoaded()) return;
		
		// textures & fonts needs to be loaded in main thread
		if (resource.getClass().isAnnotationPresent(MustLoadInMainThread.class)) {
			
			if (!mainLoopQueuing) {
				Log.f3("<LOADER> Cannot load async: " + Log.str(resource));
			} else {
				Log.f3("<LOADER> Delegating to main thread:\n    " + Log.str(resource));
				
				app.getEventBus().send(new MainLoopRequest(new Runnable() {
					
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
				final DeferredResource def = toLoad.take();
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
