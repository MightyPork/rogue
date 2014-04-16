package mightypork.util.files.ion;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import mightypork.util.math.Calc;


/**
 * Universal data storage system
 * 
 * @author MightyPork
 */
public class Ion {
	
	/** Ionizables<Mark, Class> */
	private static Map<Byte, Class<?>> customIonizables = new HashMap<>();
	
	// register default ionizables
	static {
		try {
			registerIonizable(IonMarks.MAP, IonMap.class);
			registerIonizable(IonMarks.LIST, IonList.class);
		} catch (final IonException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Register new Ionizable for direct reconstructing.
	 * 
	 * @param mark byte mark to be used, see {@link IonMarks} for reference.
	 * @param objClass class of the registered Ionizable
	 * @throws IonException
	 */
	public static void registerIonizable(byte mark, Class<?> objClass) throws IonException
	{
		if (customIonizables.containsKey(mark)) {
			throw new IonException("IonMark " + mark + " is already used.");
		}
		customIonizables.put(mark, objClass);
	}
	
	
	/**
	 * Load Ion object from file.
	 * 
	 * @param file file path
	 * @return the loaded object
	 * @throws IonException
	 */
	public static Object fromFile(String file) throws IonException
	{
		return fromFile(new File(file));
	}
	
	
	/**
	 * Load Ion object from file.
	 * 
	 * @param file file
	 * @return the loaded object
	 * @throws IonException on failure
	 */
	public static Object fromFile(File file) throws IonException
	{
		try(InputStream in = new FileInputStream(file)) {
			
			final Object obj = fromStream(in);
			return obj;
			
		} catch (final IOException e) {
			throw new IonException("Error loading ION file.", e);
		}
	}
	
	
	/**
	 * Load Ion object from stream.
	 * 
	 * @param in input stream
	 * @return the loaded object
	 * @throws IonException
	 */
	public static Object fromStream(InputStream in) throws IonException
	{
		return readObject(in);
	}
	
	
	/**
	 * Store Ion object to file.
	 * 
	 * @param path file path
	 * @param obj object to store
	 * @throws IonException
	 */
	public static void toFile(String path, Object obj) throws IonException
	{
		toFile(new File(path), obj);
	}
	
	
	/**
	 * Store Ion object to file.
	 * 
	 * @param path file path
	 * @param obj object to store
	 * @throws IonException
	 */
	public static void toFile(File path, Object obj) throws IonException
	{
		try(OutputStream out = new FileOutputStream(path)) {
			final String f = path.toString();
			final File dir = new File(f.substring(0, f.lastIndexOf(File.separator)));
			
			if (!dir.mkdirs()) throw new IOException("Could not create file.");
			
			toStream(out, obj);
			
			out.flush();
			out.close();
		} catch (final Exception e) {
			throw new IonException("Error writing to ION file.", e);
		}
	}
	
	
	/**
	 * Store Ion object to output stream.
	 * 
	 * @param out output stream *
	 * @param obj object to store
	 * @throws IonException
	 */
	public static void toStream(OutputStream out, Object obj) throws IonException
	{
		writeObject(out, obj);
	}
	
	
	/**
	 * Read single ionizable or primitive object from input stream
	 * 
	 * @param in input stream
	 * @return the loaded object
	 * @throws IonException
	 */
	public static Object readObject(InputStream in) throws IonException
	{
		try {
			final int bi = in.read();
			if (bi == -1) throw new IonException("Unexpected end of stream.");
			final byte b = (byte) bi;
			if (customIonizables.containsKey(b)) {
				Ionizable ion;
				try {
					ion = ((Ionizable) customIonizables.get(b).newInstance());
				} catch (final InstantiationException e) {
					throw new IonException("Cound not instantiate " + customIonizables.get(b).getSimpleName(), e);
				} catch (final IllegalAccessException e) {
					throw new IonException("Cound not instantiate " + customIonizables.get(b).getSimpleName(), e);
				}
				ion.ionRead(in);
				return ion;
			}
			
			switch (b) {
				case IonMarks.BOOLEAN:
					return BinaryUtils.readBoolean(in);
				case IonMarks.BYTE:
					return BinaryUtils.readByte(in);
				case IonMarks.CHAR:
					return BinaryUtils.readChar(in);
				case IonMarks.SHORT:
					return BinaryUtils.readShort(in);
				case IonMarks.INT:
					return BinaryUtils.readInt(in);
				case IonMarks.LONG:
					return BinaryUtils.readLong(in);
				case IonMarks.FLOAT:
					return BinaryUtils.readFloat(in);
				case IonMarks.DOUBLE:
					return BinaryUtils.readDouble(in);
				case IonMarks.STRING:
					final String s = BinaryUtils.readString(in);
					return s;
				default:
					throw new IonException("Invalid Ion mark " + Integer.toHexString(bi));
			}
		} catch (final IOException e) {
			throw new IonException("Error loading ION file: ", e);
		}
	}
	
	
	/**
	 * Write single ionizable or primitive object to output stream
	 * 
	 * @param out output stream
	 * @param obj stored object
	 * @throws IonException
	 */
	public static void writeObject(OutputStream out, Object obj) throws IonException
	{
		try {
			if (obj instanceof Ionizable) {
				out.write(((Ionizable) obj).ionMark());
				((Ionizable) obj).ionWrite(out);
				return;
			}
			
			if (obj instanceof Boolean) {
				out.write(IonMarks.BOOLEAN);
				BinaryUtils.writeBoolean(out, (Boolean) obj);
				return;
			}
			
			if (obj instanceof Byte) {
				out.write(IonMarks.BYTE);
				BinaryUtils.writeByte(out, (Byte) obj);
				return;
			}
			
			if (obj instanceof Character) {
				out.write(IonMarks.CHAR);
				BinaryUtils.writeChar(out, (Character) obj);
				return;
			}
			
			if (obj instanceof Short) {
				out.write(IonMarks.SHORT);
				BinaryUtils.writeShort(out, (Short) obj);
				return;
			}
			
			if (obj instanceof Integer) {
				out.write(IonMarks.INT);
				BinaryUtils.writeInt(out, (Integer) obj);
				return;
			}
			
			if (obj instanceof Long) {
				out.write(IonMarks.LONG);
				BinaryUtils.writeLong(out, (Long) obj);
				return;
			}
			
			if (obj instanceof Float) {
				out.write(IonMarks.FLOAT);
				BinaryUtils.writeFloat(out, (Float) obj);
				return;
			}
			
			if (obj instanceof Double) {
				out.write(IonMarks.DOUBLE);
				BinaryUtils.writeDouble(out, (Double) obj);
				return;
			}
			
			if (obj instanceof String) {
				out.write(IonMarks.STRING);
				BinaryUtils.writeString(out, (String) obj);
				return;
			}
			
			throw new IonException(Calc.cname(obj) + " can't be stored in Ion storage.");
			
		} catch (final IOException e) {
			throw new IonException("Could not store " + obj, e);
		}
	}
	
}
