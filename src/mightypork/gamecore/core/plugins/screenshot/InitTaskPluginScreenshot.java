package mightypork.gamecore.core.plugins.screenshot;


import mightypork.gamecore.core.InitTask;
import mightypork.gamecore.core.WorkDir;


/**
 * Register screenshot plugin to the App.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskPluginScreenshot extends InitTask {
	
	private String screenshotDir;
	
	
	/**
	 * Initialize to use the "screenshots" directory
	 */
	public InitTaskPluginScreenshot() {
		this("screenshots");
	}
	
	
	/**
	 * Initialize to use the given directory for saving.
	 * 
	 * @param dir screenshot dir (relative to workdir)
	 */
	public InitTaskPluginScreenshot(String dir) {
		this.screenshotDir = dir;
	}
	
	
	/**
	 * Set screenshot directory
	 * 
	 * @param dir screenshot dir (relative to workdir)
	 */
	public void setScreenshotDir(String dir)
	{
		this.screenshotDir = dir;
	}
	
	
	@Override
	public void run()
	{
		WorkDir.addPath("_screenshot_dir", screenshotDir);
		app.addPlugin(new ScreenshotPlugin());
	}
	
	
	@Override
	public String getName()
	{
		return "plugin_screenshot";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "workdir" };
	}
	
}
