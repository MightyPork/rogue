package mightypork.gamecore.backends.lwjgl;


import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


/**
 * Calc subclass with buffer utils.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class BufferHelper {
	
	/**
	 * Create java.nio.FloatBuffer of given floats, and flip it.
	 * 
	 * @param obj floats or float array
	 * @return float buffer
	 */
	public static FloatBuffer mkFillBuff(float... obj)
	{
		return (FloatBuffer) BufferUtils.createFloatBuffer(obj.length).put(obj).flip();
	}
	
	
	/**
	 * Fill java.nio.FloatBuffer with floats or float array
	 * 
	 * @param buff
	 * @param obj
	 */
	public static void fill(FloatBuffer buff, float... obj)
	{
		buff.put(obj);
		buff.flip();
	}
	
	
	/**
	 * Create new java.nio.FloatBuffer of given length
	 * 
	 * @param count elements
	 * @return the new java.nio.FloatBuffer
	 */
	public static FloatBuffer alloc(int count)
	{
		return BufferUtils.createFloatBuffer(count);
	}
	
}
