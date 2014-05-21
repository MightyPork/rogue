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
	
	private final ExecutorService exs = Executors.newFixedThreadPool(2);
	
	private final LinkedBlockingQueue<LazyResource> toLoad = new LinkedBlockingQueue<>();
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
	public void loadResource(final LazyResource resource)
	{
		if (resource.isLoaded()) return;
		
		// textures & fonts needs to be loaded in main thread
		if (resource.getClass().isAnnotationPresent(TextureBasedResource.class)) {
			
			if (!mainLoopQueuing) {
				// just let it be
			} else {
				Log.f3("(loader) Delegating to main thread: " + Log.str(resource));
				
				app.getEventBus().send(new MainLoopRequest(new Runnable() {
					
					@Override
					public void run()
					{
						resource.load();
					}
				}, false));
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
				final LazyResource def = toLoad.take();
				if (def == null) continue;
				
				if (!def.isLoaded()) {
					
					Log.f3("(loader) Scheduling... " + Log.str(def));
					
					exs.submit(new Runnable() {
						
						@Override
						public void run()
						{
							if(!def.isLoaded()) {
								def.load();
							}
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
		Log.f3("Stopping resource loader thread.");
		stopped = true;
		exs.shutdownNow();
	}
	
}
