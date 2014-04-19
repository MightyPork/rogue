package mightypork.util.files.ion;


/**
 * Ionizable HashMap<String, Object> with getters and setters for individual
 * supported types.
 * 
 * @author MightyPork
 */
public class IonDataBundle extends IonMap<String, Object> {
	
	public boolean getBoolean(String key)
	{
		return (Boolean) getOfType(key, Boolean.class);
	}
	
	
	public byte getByte(String key)
	{
		return (Byte) getOfType(key, Byte.class);
	}
	
	
	public char getChar(String key)
	{
		return (Character) getOfType(key, Character.class);
	}
	
	
	public short getShort(String key)
	{
		return (Short) getOfType(key, Short.class);
	}
	
	
	public int getInt(String key)
	{
		return (Integer) getOfType(key, Integer.class);
	}
	
	
	public long getLong(String key)
	{
		return (Long) getOfType(key, Long.class);
	}
	
	
	public float getFloat(String key)
	{
		return (Float) getOfType(key, Float.class);
	}
	
	
	public double getDouble(String key)
	{
		return (Double) getOfType(key, Double.class);
	}
	
	
	public String getString(String key)
	{
		return (String) getOfType(key, String.class);
	}
	
	
	public void putBoolean(String key, boolean num)
	{
		super.put(key, num);
	}
	
	
	public void putByte(String key, int num)
	{
		super.put(key, (byte) num);
	}
	
	
	public void putChar(String key, char num)
	{
		super.put(key, num);
	}
	
	
	public void putShort(String key, int num)
	{
		super.put(key, num);
	}
	
	
	public void putInt(String key, int num)
	{
		super.put(key, num);
	}
	
	
	public void putLong(String key, long num)
	{
		super.put(key, num);
	}
	
	
	public void putFloat(String key, double num)
	{
		super.put(key, (float) num);
	}
	
	
	public void putDouble(String key, double num)
	{
		super.put(key, num);
	}
	
	
	public void putString(String key, String num)
	{
		super.put(key, num);
	}
	
	
	public Object getOfType(String key, Class<?> type)
	{
		final Object o = super.get(key);
		if (o == null || !o.getClass().isAssignableFrom(type)) {
			throw new RuntimeException("Incorrect object type");
		}
		return o;
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_BUNDLE;
	}
	
}
