package mightypork.gamecore.loading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.gamecore.control.bus.BusAccess;
import mightypork.gamecore.control.bus.events.ResourceLoadRequest;
import mightypork.gamecore.control.interf.Destroyable;
import mightypork.utils.logging.Log;


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
	 */
	public static void launch(BusAccess app)
	{
		final Thread loader = new AsyncResourceLoader(app);
		loader.setDaemon(true);
		loader.start();
	}
	
	private final ExecutorService exs = Executors.newCachedThreadPool();
	
	private final LinkedBlockingQueue<Deferred> toLoad = new LinkedBlockingQueue<>();
	private volatile boolean stopped;
	@SuppressWarnings("unused")
	private final BusAccess app;
	
	
	/**
	 * @param app app acceess
	 */
	public AsyncResourceLoader(BusAccess app) {
		super("Deferred loader");
		this.app = app;
		app.getEventBus().subscribe(this);
	}
	
	
	@Override
	public void loadResource(final Deferred resource)
	{
		if (resource.isLoaded()) return;
		if (resource instanceof NullResource) return;
		
		// textures & fonts needs to be loaded in main thread
		if (resource.getClass().isAnnotationPresent(MustLoadInMainThread.class)) {
			
			Log.f3("<LOADER> Cannot load async: " + Log.str(resource));
			
//			Log.f3("<LOADER> Delegating to main thread:\n    " + Log.str(resource));
//			
//			app.getEventBus().send(new MainLoopTaskRequest(new Runnable() {
//				
//				@Override
//				public void run()
//				{
//					resource.load();
//				}
//			}));
			
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
	
	
	@Override
	public void destroy()
	{
		stopped = true;
		exs.shutdownNow();
	}
	
}
