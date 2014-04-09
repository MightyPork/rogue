package mightypork.gamecore.loading;


import mightypork.gamecore.control.interf.Destroyable;
import mightypork.utils.logging.Log;
import mightypork.utils.logging.LoggedName;


/**
 * Deferred resource abstraction.<br>
 * Resources implementing {@link NullResource} will be treated as fake and not
 * attempted to load.
 * 
 * @author MightyPork
 */
@LoggedName(name = "Resource")
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
		if (loadAttempted) {
			Log.w("<RES> Already loaded @ load():\n    " + this);
			(new IllegalStateException()).printStackTrace();
			//return;
		}
		
		loadAttempted = true;
		
		loadFailed = false;
		
		if (isNull()) return;
		try {
			if (resource == null) {
				throw new NullPointerException("Resource string cannot be null for non-null resource.");
			}
			
			Log.f3("<RES> Loading:\n    " + this);
			loadResource(resource);
			Log.f3("<RES> Loaded:\n    " + this);
		} catch (final Exception e) {
			loadFailed = true;
			Log.e("<RES> Failed to load:\n    " + this, e);
		}
	}
	
	
	@Override
	public synchronized final boolean isLoaded()
	{
		if (isNull()) return false;
		
		return loadAttempted && !loadFailed;
	}
	
	
	/**
	 * Check if the resource is loaded; if not, try to do so.
	 * 
	 * @return true if it's loaded now.
	 */
	public synchronized final boolean ensureLoaded()
	{
		if (isNull()) return false;
		
		if (isLoaded()) {
			return true;
		} else {
			Log.w("<RES> First use, not loaded yet - loading directly\n    " + this);
			(new IllegalStateException()).printStackTrace();
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
		if (!(obj instanceof BaseDeferredResource)) return false;
		final BaseDeferredResource other = (BaseDeferredResource) obj;
		if (resource == null) {
			if (other.resource != null) return false;
		} else if (!resource.equals(other.resource)) return false;
		return true;
	}
	
	
	private boolean isNull()
	{
		return this instanceof NullResource;
	}
}
