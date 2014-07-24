package mightypork.gamecore.resources;


import java.io.IOException;

import mightypork.utils.annotations.Alias;
import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.logging.Log;
import mightypork.utils.math.timing.Profiler;
import mightypork.utils.string.StringUtil;


/**
 * Deferred resource abstraction.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Resource")
public abstract class BaseDeferredResource implements DeferredResource, Destroyable {
	
	private final String resource;
	private volatile boolean loadFailed = false;
	private volatile boolean loadAttempted = false;
	
	
	/**
	 * @param resource resource path / name; this string is later used in
	 *            loadResource()
	 */
	public BaseDeferredResource(String resource) {
		this.resource = resource;
	}
	
	
	@Override
	public synchronized final void load()
	{
		if (!loadFailed && loadAttempted) return;
		
//		
//		if (loadFailed) return;
//		if (loadAttempted) return;
//		
		
		loadAttempted = true;
		loadFailed = false;
		
		try {
			if (resource == null) {
				throw new NullPointerException("Resource string cannot be null for non-null resource.");
			}
			
			final long time = Profiler.begin();
			Log.f3("(res) + Load: " + this);
			loadResource(resource);
			Log.f3("(res) - Done: " + this + " in " + Profiler.endStr(time));
			
		} catch (final Throwable t) {
			loadFailed = true;
			Log.e("(res) Failed to load: " + this, t);
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
			
			Log.f3("(res) !! Loading on access: " + this);
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
		return StringUtil.fromLastChar(resource, '/');
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
