package mightypork.rogue.loading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.rogue.AppAccess;
import mightypork.rogue.bus.events.MainLoopTaskRequest;
import mightypork.rogue.bus.events.ResourceLoadRequest;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.logging.Log;


/**
 * Asynchronous resource loading thread.
 * 
 * @author MightyPork
 */
public class AsyncResourceLoader extends Thread implements ResourceLoadRequest.Listener, Destroyable {
	
	public static void launch(AppAccess app)
	{
		(new AsyncResourceLoader(app)).start();
	}
	
	private final ExecutorService exs = Executors.newCachedThreadPool();
	
	private final LinkedBlockingQueue<DeferredResource> toLoad = new LinkedBlockingQueue<DeferredResource>();
	private boolean stopped;
	private final AppAccess app;
	
	
	public AsyncResourceLoader(AppAccess app) {
		super("Deferred loader");
		this.app = app;
		app.bus().subscribe(this);
	}
	
	
	@Override
	public void loadResource(DeferredResource resource)
	{
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
					
					// skip nulls
					if (def instanceof NullResource) continue;
					
					// textures & fonts needs to be loaded in main thread
					if (def.getClass().isAnnotationPresent(MustLoadInMainThread.class)) {
						
						Log.f3("<LOADER> Delegating to main thread:\n    "+Log.str(def));
						
						app.bus().send(new MainLoopTaskRequest(new Runnable() {
							
							@Override
							public void run()
							{
								def.load();
							}
						}));
						
						continue;
					}
					
					Log.f3("<LOADER> Loading async:\n    "+Log.str(def));
					
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
