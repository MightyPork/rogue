package mightypork.gamecore.core.config;


import mightypork.gamecore.input.KeyStroke;


/**
 * Key options - restricted access to {@link Config} for keys
 */
public class KeyOpts {
	
	public void add(String cfgKey, String dataString)
	{
		add(cfgKey, dataString, null);
	}
	
	
	/**
	 * @param cfgKey key in config file
	 * @param dataString string representing the keystroke (format for
	 *            {@link KeyStroke})
	 * @param comment optional comment
	 */
	public void add(String cfgKey, String dataString, String comment)
	{
		final KeyProperty kprop = new KeyProperty(Config.prefixKey(cfgKey), KeyStroke.createFromDataString(dataString), comment);
		Config.strokes.put(Config.prefixKey(cfgKey), kprop);
		Config.cfg.putProperty(kprop);
	}
}
