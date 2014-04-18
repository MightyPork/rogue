package mightypork.util.files.ion;

import java.io.IOException;


/**
 * Ionizable Arraylist
 * 
 * @author MightyPork
 */
public class IonDataList extends IonList<Object> {
	
	public Ionizable getIonizable(int index) throws IOException
	{
		return (Ionizable) getOfType(index, Ionizable.class);
	}
	
	
	public boolean getBoolean(int index) throws IOException
	{
		return (Boolean) getOfType(index, Boolean.class);
	}
	
	
	public byte getByte(int index) throws IOException
	{
		return (Byte) getOfType(index, Byte.class);
	}
	
	
	public char getChar(int index) throws IOException
	{
		return (Character) getOfType(index, Character.class);
	}
	
	
	public short getShort(int index) throws IOException
	{
		return (Short) getOfType(index, Short.class);
	}
	
	
	public int getInt(int index) throws IOException
	{
		return (Integer) getOfType(index, Integer.class);
	}
	
	
	public long getLong(int index) throws IOException
	{
		return (Long) getOfType(index, Long.class);
	}
	
	
	public float getFloat(int index) throws IOException
	{
		return (Float) getOfType(index, Float.class);
	}
	
	
	public double getDouble(int index) throws IOException
	{
		return (Double) getOfType(index, Double.class);
	}
	
	
	public String getString(int index) throws IOException
	{
		return (String) getOfType(index, String.class);
	}
	
	
	public void addIonizable(Ionizable o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addBoolean(boolean o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addByte(byte o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addChar(char o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addShort(short o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addInt(int o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addLong(long o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addFloat(float o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addDouble(double o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public void addString(String o) throws IOException
	{
		assertNotNull(o);
		add(o);
	}
	
	
	public Object getOfType(int index, Class<?> type) throws IOException
	{
		try {
			final Object o = super.get(index);
			if (o == null || !o.getClass().isAssignableFrom(type)) {
				throw new IOException("Incorrect object type");
			}
			return o;
		} catch (final IndexOutOfBoundsException e) {
			throw new IOException("Out of bounds");
		}
	}
	
	
	private static void assertNotNull(Object o) throws IOException
	{
		if (o == null) throw new IOException("Cannot store null");
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_LIST;
	}
	
}
