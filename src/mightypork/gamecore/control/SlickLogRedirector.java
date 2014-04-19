package mightypork.gamecore.control;


import java.util.logging.Level;

import mightypork.util.logging.writers.LogWriter;

import org.newdawn.slick.util.LogSystem;


/**
 * Used to redirect slick log into main logger.
 * 
 * @author MightyPork
 */
public class SlickLogRedirector implements LogSystem {
	
	LogWriter l;
	
	
	/**
	 * @param log log to redirect into
	 */
	public SlickLogRedirector(LogWriter log)
	{
		this.l = log;
	}
	
	
	@Override
	public void error(String msg, Throwable e)
	{
		l.log(Level.SEVERE, msg, e);
	}
	
	
	@Override
	public void error(Throwable e)
	{
		l.log(Level.SEVERE, null, e);
	}
	
	
	@Override
	public void error(String msg)
	{
		l.log(Level.SEVERE, msg);
	}
	
	
	@Override
	public void warn(String msg)
	{
		l.log(Level.WARNING, msg);
	}
	
	
	@Override
	public void warn(String msg, Throwable e)
	{
		l.log(Level.WARNING, msg, e);
	}
	
	
	@Override
	public void info(String msg)
	{
		l.log(Level.INFO, msg);
	}
	
	
	@Override
	public void debug(String msg)
	{
		l.log(Level.FINEST, msg);
	}
	
}
