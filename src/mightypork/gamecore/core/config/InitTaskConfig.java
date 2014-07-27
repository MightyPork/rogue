package mightypork.gamecore.core.config;


import mightypork.gamecore.core.InitTask;
import mightypork.utils.annotations.Stub;


/**
 * Initialize config. To apply this initializer, you must extend it. That
 * ensures that the workdir initializer has already finished when the code is
 * executed (such as resolving a file path for the config file).
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskConfig extends InitTask {
	
	/**
	 * Add a config with given alias
	 * 
	 * @param alias config alias
	 * @param config config to add
	 */
	protected void addConfig(String alias, Config config)
	{
		Config.register(alias, config);
	}
	
	
	/**
	 * Initialize the main config.
	 * 
	 * @return the main config.
	 */
	protected abstract Config buildConfig();
	
	
	/**
	 * Initialize extra configs.<br>
	 * the addConfig() method can be used to register configs.
	 */
	@Stub
	protected void buildExtraConfigs()
	{
	}
	
	
	// locked to encourage the use of the build* methods.
	@Override
	public final void init()
	{
	}
	
	
	@Override
	public final void run()
	{
		addConfig("main", buildConfig());
		buildExtraConfigs();
	}
	
	
	@Override
	public String getName()
	{
		return "config";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "workdir" };
	}
	
}
