package mightypork.gamecore.resources;


import mightypork.gamecore.util.math.Calc;


public class Profiler {
	
	public static long begin()
	{
		return System.currentTimeMillis();
	}
	
	
	public static double end(long begun)
	{
		return endLong(begun) / 1000D;
	}
	
	
	public static long endLong(long begun)
	{
		return System.currentTimeMillis() - begun;
	}
	
	
	public static String endStr(long begun)
	{
		return Calc.toString(end(begun)) + " s";
	}
	
}
