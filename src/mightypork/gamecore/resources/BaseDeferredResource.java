package mightypork.gamecore.resources;


import java.io.IOException;

import mightypork.gamecore.eventbus.events.Destroyable;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.logging.LogAlias;


/**
 * Deferred resource abstraction.
 * 
 * @author MightyPork
 */
@LogAlias(name = "Resource")
public abstract class BaseDeferredResource implements DeferredResource, Destroyable {
	
	private final String resource;
	private volatile boolean loadFailed = false;
	private volatile boolean loadAttempted = false;
	
	
	/**
	 * @param resource resource path / name; this string is later used in
	 *            loadResource()
	 */
	public BaseDeferredResource(String resource)
	{
		this.resource = resource;
	}
	
	
	@Override
	public synchronized final void load()
	{
		if (loadFailed) return;
		if (loadAttempted) return;
		
		loadAttempted = true;
		loadFailed = false;
		
		try {
			if (resource == null) {
				throw new NullPointerException("Resource string cannot be null for non-null resource.");
			}
			
			Log.f3("<RES> Loading: " + this);
			loadResource(resource);
			Log.f3("<RES> Loaded: " + this);
		} catch (final Throwable t) {
			loadFailed = true;
			Log.e("<RES> Failed to load: " + this, t);
		}
	}
	
	
	@Override
	public synchronized final boolean isLoaded()
	{
		return loadAttempted && !loadFailed;
	}
	
	
	/**
	 * Check if the resource is loaded; if not, try to do so.
	 * 
	 * @return true if it's loaded now.
	 */
	public synchronized final boolean ensureLoaded()
	{
		if (isLoaded()) {
			return true;
		} else {
			if (loadFailed) return false;
			
			Log.f3("<RES> (!) Loading on access: " + this);
			load();
		}
		
		return isLoaded();
	}
	
	
	/**
	 * Load the resource. Called from load() - once only.
	 * 
	 * @param resource the path / name of a resource
	 * @throws IOException when some problem prevented the resource from being
	 *             loaded.
	 */
	protected abstract void loadResource(String resource) throws IOException;
	
	
	@Override
	public abstract void destroy();
	
	
	@Override
	public String toString()
	{
		return Log.str(getClass()) + "(\"" + resource + "\")";
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof BaseDeferredResource)) return false;
		final BaseDeferredResource other = (BaseDeferredResource) obj;
		if (resource == null) {
			if (other.resource != null) return false;
		} else if (!resource.equals(other.resource)) return false;
		return true;
	}
}
