package mightypork.util.files.ion;


/**
 * Ionizable Arraylist
 * 
 * @author MightyPork
 */
public class IonDataList extends IonList<Object> {
	
	public Ionizable getIonizable(int index)
	{
		return (Ionizable) getOfType(index, Ionizable.class);
	}
	
	
	public boolean getBoolean(int index)
	{
		return (Boolean) getOfType(index, Boolean.class);
	}
	
	
	public byte getByte(int index)
	{
		return (Byte) getOfType(index, Byte.class);
	}
	
	
	public char getChar(int index)
	{
		return (Character) getOfType(index, Character.class);
	}
	
	
	public short getShort(int index)
	{
		return (Short) getOfType(index, Short.class);
	}
	
	
	public int getInt(int index)
	{
		return (Integer) getOfType(index, Integer.class);
	}
	
	
	public long getLong(int index)
	{
		return (Long) getOfType(index, Long.class);
	}
	
	
	public float getFloat(int index)
	{
		return (Float) getOfType(index, Float.class);
	}
	
	
	public double getDouble(int index)
	{
		return (Double) getOfType(index, Double.class);
	}
	
	
	public String getString(int index)
	{
		return (String) getOfType(index, String.class);
	}
	
	
	public void addIonizable(Ionizable o)
	{
		super.add(o);
	}
	
	
	public void addBoolean(boolean o)
	{
		super.add(o);
	}
	
	
	public void addByte(byte o)
	{
		super.add(o);
	}
	
	
	public void addChar(char o)
	{
		super.add(o);
	}
	
	
	public void addShort(short o)
	{
		super.add(o);
	}
	
	
	public void addInt(int o)
	{
		super.add(o);
	}
	
	
	public void addLong(long o)
	{
		super.add(o);
	}
	
	
	public void addFloat(float o)
	{
		super.add(o);
	}
	
	
	public void addDouble(double o)
	{
		super.add(o);
	}
	
	
	public void addString(String o)
	{
		super.add(o);
	}
	
	
	public Object getOfType(int index, Class<?> type)
	{
		final Object o = super.get(index);
		if (o == null || !o.getClass().isAssignableFrom(type)) {
			throw new RuntimeException("Incorrect object type");
		}
		return o;
	}
	
	
	@Override
	public short getIonMark()
	{
		return Ion.DATA_LIST;
	}
	
}
