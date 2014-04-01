package mightypork.utils.files.ion;


/**
 * Ionizable HashMap
 * 
 * @author MightyPork
 */
public class IonMap extends AbstractIonMap<Object> implements Ionizable {
	
	public boolean getBoolean(String key)
	{
		return (Boolean) get(key);
	}
	
	
	public boolean getBool(String key)
	{
		return (Boolean) get(key);
	}
	
	
	public byte getByte(String key)
	{
		return (Byte) get(key);
	}
	
	
	public char getChar(String key)
	{
		return (Character) get(key);
	}
	
	
	public short getShort(String key)
	{
		return (Short) get(key);
	}
	
	
	public int getInt(String key)
	{
		return (Integer) get(key);
	}
	
	
	public long getLong(String key)
	{
		return (Long) get(key);
	}
	
	
	public float getFloat(String key)
	{
		return (Float) get(key);
	}
	
	
	public double getDouble(String key)
	{
		return (Double) get(key);
	}
	
	
	public String getString(String key)
	{
		return (String) get(key);
	}
	
	
	@Override
	public Object get(Object arg0)
	{
		return super.get(arg0);
	}
	
	
	public void putBoolean(String key, boolean num)
	{
		put(key, num);
	}
	
	
	public void putBool(String key, boolean num)
	{
		put(key, num);
	}
	
	
	public void putByte(String key, int num)
	{
		put(key, (byte) num);
	}
	
	
	public void putChar(String key, char num)
	{
		put(key, num);
	}
	
	
	public void putCharacter(String key, char num)
	{
		put(key, num);
	}
	
	
	public void putShort(String key, int num)
	{
		put(key, num);
	}
	
	
	public void putInt(String key, int num)
	{
		put(key, num);
	}
	
	
	public void putInteger(String key, int num)
	{
		put(key, num);
	}
	
	
	public void putLong(String key, long num)
	{
		put(key, num);
	}
	
	
	public void putFloat(String key, double num)
	{
		put(key, (float) num);
	}
	
	
	public void putDouble(String key, double num)
	{
		put(key, num);
	}
	
	
	public void putString(String key, String num)
	{
		put(key, num);
	}
	
	
	@Override
	public byte ionMark()
	{
		return IonMarks.MAP;
	}
	
}
