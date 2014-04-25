package mightypork.gamecore.loading;


import mightypork.util.control.Destroyable;
import mightypork.util.logging.Log;
import mightypork.util.logging.LogAlias;


/**
 * Deferred resource abstraction.
 * 
 * @author MightyPork
 */
@LogAlias(name = "Resource")
public abstract class DeferredResource implements Deferred, Destroyable {
	
	private final String resource;
	private volatile boolean loadFailed = false;
	private volatile boolean loadAttempted = false;
	
	
	/**
	 * @param resource resource path / name; this string is later used in
	 *            loadResource()
	 */
	public DeferredResource(String resource)
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
			if (resource == null) { throw new NullPointerException("Resource string cannot be null for non-null resource."); }
			
			Log.f3("<RES> Loading: " + this);
			loadResource(resource);
			Log.f3("<RES> Loaded: " + this);
		} catch (final Exception e) {
			loadFailed = true;
			Log.e("<RES> Failed to load: " + this, e);
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
	 * @throws Exception when some problem prevented the resource from being
	 *             loaded.
	 */
	protected abstract void loadResource(String resource) throws Exception;
	
	
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
		if (!(obj instanceof DeferredResource)) return false;
		final DeferredResource other = (DeferredResource) obj;
		if (resource == null) {
			if (other.resource != null) return false;
		} else if (!resource.equals(other.resource)) return false;
		return true;
	}
}
