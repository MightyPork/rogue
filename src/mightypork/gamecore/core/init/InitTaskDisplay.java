package mightypork.gamecore.core.init;


import mightypork.gamecore.core.InitTask;
import mightypork.gamecore.graphics.GraphicsModule;


/**
 * Setup main window.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskDisplay extends InitTask {
	
	private int width = 800, height = 600, fps = 60;
	private boolean resizable, fullscreen;
	private String title = "Game";
	
	
	/**
	 * Set initial window size
	 * 
	 * @param width width (px)
	 * @param height height (px)
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * Set whether the window should be resizable
	 * 
	 * @param resizable true for resizable
	 */
	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}
	
	
	/**
	 * Set window title
	 * 
	 * @param title title text
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	
	/**
	 * Set desired framerate.
	 * 
	 * @param fps FPS
	 */
	public void setTargetFps(int fps)
	{
		this.fps = fps;
	}
	
	
	/**
	 * Set whether the window should start in fullscreen
	 * 
	 * @param fullscreen true for fullscreen
	 */
	public void setFullscreen(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
	}
	
	
	@Override
	public void run()
	{
		GraphicsModule gfx = app.getBackend().getGraphics();
		
		gfx.setSize(width, height);
		gfx.setResizable(resizable);
		gfx.setTitle(title);
		gfx.setTargetFps(fps);
		
		if (fullscreen) gfx.setFullscreen(true);
	}
	
	
	@Override
	public String getName()
	{
		return "display";
	}
	
}
