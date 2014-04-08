package mightypork.rogue.util;


import mightypork.utils.logging.LogInstance;

import org.newdawn.slick.util.LogSystem;


public class SlickLogRedirector implements LogSystem {
	
	LogInstance l;
	
	
	public SlickLogRedirector(LogInstance log) {
		this.l = log;
	}
	
	
	@Override
	public void error(String msg, Throwable e)
	{
		l.e(msg, e);
	}
	
	
	@Override
	public void error(Throwable e)
	{
		l.e(e);
	}
	
	
	@Override
	public void error(String msg)
	{
		l.e(msg);
	}
	
	
	@Override
	public void warn(String msg)
	{
		l.w(msg);
	}
	
	
	@Override
	public void warn(String msg, Throwable e)
	{
		l.e(msg, e);
	}
	
	
	@Override
	public void info(String msg)
	{
		l.i(msg);
	}
	
	
	@Override
	public void debug(String msg)
	{
		l.f3(msg);
	}
	
}
