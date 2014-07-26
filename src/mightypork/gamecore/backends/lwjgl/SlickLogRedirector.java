package mightypork.gamecore.backends.lwjgl;


import java.util.logging.Level;

import mightypork.utils.logging.writers.LogWriter;


/**
 * Used to redirect slick log into main logger.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
class SlickLogRedirector implements org.newdawn.slick.util.LogSystem {
	
	LogWriter writer;
	
	
	/**
	 * @param log log to redirect into
	 */
	public SlickLogRedirector(LogWriter log) {
		this.writer = log;
	}
	
	
	@Override
	public void error(String msg, Throwable e)
	{
		writer.log(Level.SEVERE, msg, e);
	}
	
	
	@Override
	public void error(Throwable e)
	{
		writer.log(Level.SEVERE, null, e);
	}
	
	
	@Override
	public void error(String msg)
	{
		writer.log(Level.SEVERE, msg);
	}
	
	
	@Override
	public void warn(String msg)
	{
		writer.log(Level.WARNING, msg);
	}
	
	
	@Override
	public void warn(String msg, Throwable e)
	{
		writer.log(Level.WARNING, msg, e);
	}
	
	
	@Override
	public void info(String msg)
	{
		writer.log(Level.INFO, msg);
	}
	
	
	@Override
	public void debug(String msg)
	{
		writer.log(Level.FINEST, msg);
	}
	
}
