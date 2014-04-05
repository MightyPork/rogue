package mightypork.rogue.texture;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.rogue.AppAccess;
import mightypork.rogue.Deferred;
import mightypork.rogue.bus.events.MainLoopTaskRequest;
import mightypork.rogue.bus.events.ResourceLoadRequest;
import mightypork.utils.control.interf.Destroyable;
import mightypork.utils.logging.Log;


/**
 * Asynchronous resource loading thread.
 * 
 * @author MightyPork
 */
public class DeferredLoader extends Thread implements ResourceLoadRequest.Listener, Destroyable {
	
	public static void launch(AppAccess app)
	{
		(new DeferredLoader(app)).start();
	}
	
	private ExecutorService exs = Executors.newCachedThreadPool();
	
	private LinkedBlockingQueue<Deferred> toLoad = new LinkedBlockingQueue<Deferred>();
	private boolean stopped;
	private AppAccess app;
	
	
	public DeferredLoader(AppAccess app) {
		super("Deferred loader");
		this.app = app;
	}
	
	
	@Override
	public void loadResource(Deferred resource)
	{
		toLoad.add(resource);
	}
	
	
	@Override
	public void run()
	{
		while (!stopped) {
			
			try {
				final Deferred def = toLoad.take();
				if (def == null) continue;
				
				if (!def.isLoaded()) {
					
					// texture needs to be loaded in main thread, unfortunately.
					// -> delegate to MainLoop
					if (def instanceof DeferredTexture) {
						app.bus().queue(new MainLoopTaskRequest(new Runnable() {
							
							@Override
							public void run()
							{
								Log.f3("<DEFERRED> Loading \"" + Log.str(def) + "\"");
								def.load();
							}
						}));
						
						continue;
					}
					
					Log.f3("<DEFERRED> Loading \"" + Log.str(def) + "\"");
					
					exs.submit(new Runnable() {
						
						@Override
						public void run()
						{
							def.load();
						}
					});
				}
				
			} catch (InterruptedException ignored) {
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
