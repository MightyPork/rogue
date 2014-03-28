package mightypork.utils.files.ion;


/**
 * Ionizable Arraylist
 * 
 * @author MightyPork
 */
public class IonList extends AbstractIonList<Object> implements Ionizable {

	public Ionizable getIonizable(int index) throws IonException
	{
		return (Ionizable) getCheckType(index, Ionizable.class);
	}


	public boolean getBoolean(int index) throws IonException
	{
		return (Boolean) getCheckType(index, Boolean.class);
	}


	public byte getByte(int index) throws IonException
	{
		return (Byte) getCheckType(index, Byte.class);
	}


	public char getChar(int index) throws IonException
	{
		return (Character) getCheckType(index, Character.class);
	}


	public short getShort(int index) throws IonException
	{
		return (Short) getCheckType(index, Short.class);
	}


	public int getInt(int index) throws IonException
	{
		return (Integer) getCheckType(index, Integer.class);
	}


	public long getLong(int index) throws IonException
	{
		return (Long) getCheckType(index, Long.class);
	}


	public float getFloat(int index) throws IonException
	{
		return (Float) getCheckType(index, Float.class);
	}


	public double getDouble(int index) throws IonException
	{
		return (Double) getCheckType(index, Double.class);
	}


	public String getString(int index) throws IonException
	{
		return (String) getCheckType(index, String.class);
	}


	public void addIonizable(Ionizable o) throws IonException
	{
		addCheckNull(o);
	}


	public void addBoolean(boolean o) throws IonException
	{
		addCheckNull(o);
	}


	public void addByte(byte o) throws IonException
	{
		addCheckNull(o);
	}


	public void addChar(char o) throws IonException
	{
		addCheckNull(o);
	}


	public void addShort(short o) throws IonException
	{
		addCheckNull(o);
	}


	public void addInt(int o) throws IonException
	{
		addCheckNull(o);
	}


	public void addLong(long o) throws IonException
	{
		addCheckNull(o);
	}


	public void addFloat(float o) throws IonException
	{
		addCheckNull(o);
	}


	public void addDouble(double o) throws IonException
	{
		addCheckNull(o);
	}


	public void addString(String o) throws IonException
	{
		addCheckNull(o);
	}


	public Object getCheckType(int index, Class<?> type) throws IonException
	{
		try {
			Object o = super.get(index);
			if (o == null || !o.getClass().isAssignableFrom(type)) {
				throw new IonException("Incorrect object type");
			}
			return o;
		} catch (IndexOutOfBoundsException e) {
			throw new IonException("Out of bounds");
		}
	}


	private void addCheckNull(Object o) throws IonException
	{
		if (o == null) throw new IonException("Cannot store null");
	}


	@Override
	public byte ionMark()
	{
		return IonMarks.LIST;
	}

}
