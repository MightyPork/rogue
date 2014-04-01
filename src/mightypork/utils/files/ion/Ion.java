package mightypork.utils.files.ion;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import mightypork.utils.math.Calc;


/**
 * Universal data storage system
 * 
 * @author MightyPork
 */
public class Ion {
	
	/** Ionizables<Mark, Class> */
	private static Map<Byte, Class<?>> customIonizables = new HashMap<Byte, Class<?>>();
	
	// register default ionizables
	static {
		try {
			registerIonizable(IonMarks.MAP, IonMap.class);
			registerIonizable(IonMarks.LIST, IonList.class);
		} catch (IonException e) {
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
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			Object obj = fromStream(in);
			return obj;
			
		} catch (IOException e) {
			throw new IonException("Error loading ION file.", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		OutputStream out = null;
		try {
			String f = path.toString();
			File dir = new File(f.substring(0, f.lastIndexOf(File.separator)));
			
			dir.mkdirs();
			
			out = new FileOutputStream(path);
			
			toStream(out, obj);
			
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new IonException("Error writing to ION file.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
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
			int bi = in.read();
			if (bi == -1) throw new IonException("Unexpected end of stream.");
			byte b = (byte) bi;
			if (customIonizables.containsKey(b)) {
				Ionizable ion;
				try {
					ion = ((Ionizable) customIonizables.get(b).newInstance());
				} catch (InstantiationException e) {
					throw new IonException("Cound not instantiate " + customIonizables.get(b).getSimpleName(), e);
				} catch (IllegalAccessException e) {
					throw new IonException("Cound not instantiate " + customIonizables.get(b).getSimpleName(), e);
				}
				ion.ionRead(in);
				return ion;
			}
			
			switch (b) {
				case IonMarks.BOOLEAN:
					return StreamUtils.readBoolean(in);
				case IonMarks.BYTE:
					return StreamUtils.readByte(in);
				case IonMarks.CHAR:
					return StreamUtils.readChar(in);
				case IonMarks.SHORT:
					return StreamUtils.readShort(in);
				case IonMarks.INT:
					return StreamUtils.readInt(in);
				case IonMarks.LONG:
					return StreamUtils.readLong(in);
				case IonMarks.FLOAT:
					return StreamUtils.readFloat(in);
				case IonMarks.DOUBLE:
					return StreamUtils.readDouble(in);
				case IonMarks.STRING:
					String s = StreamUtils.readString(in);
					return s;
				default:
					throw new IonException("Invalid Ion mark " + Integer.toHexString(bi));
			}
		} catch (IOException e) {
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
				StreamUtils.writeBoolean(out, (Boolean) obj);
				return;
			}
			
			if (obj instanceof Byte) {
				out.write(IonMarks.BYTE);
				StreamUtils.writeByte(out, (Byte) obj);
				return;
			}
			
			if (obj instanceof Character) {
				out.write(IonMarks.CHAR);
				StreamUtils.writeChar(out, (Character) obj);
				return;
			}
			
			if (obj instanceof Short) {
				out.write(IonMarks.SHORT);
				StreamUtils.writeShort(out, (Short) obj);
				return;
			}
			
			if (obj instanceof Integer) {
				out.write(IonMarks.INT);
				StreamUtils.writeInt(out, (Integer) obj);
				return;
			}
			
			if (obj instanceof Long) {
				out.write(IonMarks.LONG);
				StreamUtils.writeLong(out, (Long) obj);
				return;
			}
			
			if (obj instanceof Float) {
				out.write(IonMarks.FLOAT);
				StreamUtils.writeFloat(out, (Float) obj);
				return;
			}
			
			if (obj instanceof Double) {
				out.write(IonMarks.DOUBLE);
				StreamUtils.writeDouble(out, (Double) obj);
				return;
			}
			
			if (obj instanceof String) {
				out.write(IonMarks.STRING);
				StreamUtils.writeString(out, (String) obj);
				return;
			}
			
			throw new IonException(Calc.cname(obj) + " can't be stored in Ion storage.");
			
		} catch (IOException e) {
			throw new IonException("Could not store " + obj, e);
		}
	}
	
}
