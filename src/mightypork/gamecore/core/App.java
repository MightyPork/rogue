package mightypork.gamecore.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mightypork.gamecore.backend.Backend;
import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.core.plugins.AppPlugin;
import mightypork.gamecore.render.GraphicsModule;
import mightypork.gamecore.resources.audio.AudioModule;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.eventbus.clients.DelegatingList;
import mightypork.utils.eventbus.events.DestroyEvent;
import mightypork.utils.logging.Log;


/**
 * Game base class & static subsystem access
 * 
 * @author MightyPork
 */
public class App extends BusNode {
	
	private static App instance;
	
	private final Backend backend;
	private final EventBus eventBus = new EventBus();
	private boolean started = false;
	
	protected final DelegatingList plugins = new DelegatingList();
	protected final List<InitTask> initializers = new ArrayList<>();
	
	
	/**
	 * Create an app with given backend.
	 * 
	 * @param backend
	 */
	public App(Backend backend) {
		if (App.instance != null) {
			throw new IllegalStateException("App already initialized");
		}
		
		// store current instance in static field
		App.instance = this;
		
		// join the bus
		this.eventBus.subscribe(this);
		
		// create plugin registry attached to bus
		this.eventBus.subscribe(this.plugins);
		
		// initialize and use backend
		this.backend = backend;
		this.eventBus.subscribe(backend);
		this.backend.initialize();
	}
	
	
	/**
	 * Add a plugin to the app. Plugins can eg. listen to bus events and react
	 * to them.
	 * 
	 * @param plugin the added plugin.
	 */
	public void addPlugin(AppPlugin plugin)
	{
		if (started) {
			throw new IllegalStateException("App already started, cannot add plugins.");
		}
		
		plugin.initialize(this);
		plugins.add(plugin);
	}
	
	
	/**
	 * Add an initializer to the app.
	 * 
	 * @param initializer
	 */
	public void addInitTask(InitTask initializer)
	{
		if (started) {
			throw new IllegalStateException("App already started, cannot add initializers.");
		}
		
		initializers.add(initializer);
	}
	
	
	/**
	 * Get current backend
	 * 
	 * @return the backend
	 */
	public Backend getBackend()
	{
		return backend;
	}
	
	
	/**
	 * Initialize the App and start operating.<br>
	 * This method should be called after adding all required initializers and
	 * plugins.
	 */
	public final void start()
	{
		if (started) {
			throw new IllegalStateException("Already started.");
		}
		started = true;
		
		// pre-init hook, just in case anyone wanted to have one.
		Log.f2("Calling pre-init hook...");
		preInit();
		
		Log.i("=== Starting initialization sequence ===");
		
		// sort initializers by order.
		List<InitTask> orderedInitializers = InitTasks.inOrder(initializers);
		
		for (InitTask initializer : orderedInitializers) {
			Log.f1("Running init task \"" + initializer.getName() + "\"...");
			initializer.bind(this);
			initializer.init();
			initializer.run();
		}
		
		Log.i("=== Initialization sequence completed ===");
		
		// user can now start the main loop etc.
		Log.f2("Calling post-init hook...");
		postInit();
	}
	
	
	/**
	 * Hook called before the initialization sequence starts.
	 */
	@Stub
	protected void preInit()
	{
	}
	
	
	/**
	 * Hook called after the initialization sequence is finished.
	 */
	@Stub
	protected void postInit()
	{
	}
	
	
	public static void shutdown()
	{
		if (instance == null) throw new IllegalStateException("App is not running.");
		
		Log.i("Shutting down subsystems...");
		
		// TODO send some shutdown notify event
		
		try {
			final EventBus bus = bus();
			if (bus != null) {
				bus.send(new DestroyEvent());
				bus.destroy();
			}
		} catch (final Throwable e) {
			Log.e(e);
		}
		
		Log.i("App terminated.");
		System.exit(0);
	}
	
	
	/**
	 * Get the currently running App instance.
	 * 
	 * @return app instance
	 */
	public static App instance()
	{
		return instance;
	}
	
	
	/**
	 * Get graphics module from the backend
	 * 
	 * @return backend
	 */
	public static GraphicsModule gfx()
	{
		return instance.backend.getGraphics();
	}
	
	
	/**
	 * Get audio module from the backend
	 * 
	 * @return backend
	 */
	public static AudioModule audio()
	{
		return instance.backend.getAudio();
	}
	
	
	/**
	 * Get event bus instance.
	 * 
	 * @return event bus
	 */
	public static EventBus bus()
	{
		return instance.eventBus;
	}
	
	
	/**
	 * Get the main config, if initialized.
	 * 
	 * @return main config
	 * @throws IllegalArgumentException if there is no such config.
	 */
	public static Config cfg()
	{
		return cfg("main");
	}
	
	
	/**
	 * Get a config by alias.
	 * 
	 * @param alias config alias
	 * @return the config
	 * @throws IllegalArgumentException if there is no such config.
	 */
	public static Config cfg(String alias)
	{
		return Config.forAlias(alias);
	}
}
