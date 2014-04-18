package mightypork.util.files.ion;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import mightypork.util.logging.Log;


/**
 * Universal data storage system
 * 
 * @author MightyPork
 */
public class Ion {
	
	/*
	 *  0-19 ... primitive and Java built-in types 
	 * 20-39 ... technical marks
	 * 40-99 ... built-in ION types
	 */
	
	// primitives 0..19
	/** Null mark (for internal use) */
	private static final short NULL = 0;
	/** Boolean mark (for internal use) */
	private static final short BOOLEAN = 1;
	/** Byte mark (for internal use) */
	private static final short BYTE = 2;
	/** Character mark (for internal use) */
	private static final short CHAR = 3;
	/** Short mark (for internal use) */
	private static final short SHORT = 4;
	/** Integer mark (for internal use) */
	private static final short INT = 5;
	/** Long mark (for internal use) */
	private static final short LONG = 6;
	/** Float mark (for internal use) */
	private static final short FLOAT = 7;
	/** Double mark (for internal use) */
	private static final short DOUBLE = 8;
	/** String mark (for internal use) */
	private static final short STRING = 9;
	
	// technical 20..39
	
	/**
	 * Entry mark - general purpose, marks an entry in sequence of objects. Used
	 * to indicate that the sequence continues wityh another element.
	 */
	public static final short ENTRY = 20;
	
	/**
	 * Start mark - general purpose, marks start of a sequence of stored
	 * objects.
	 */
	public static final short START = 21;
	
	/**
	 * End mark - general purpose, marks end of sequence of stored objects.
	 */
	public static final short END = 22;
	
	/**
	 * Length mark, indicating length of something (such as array) - general
	 * purpose
	 */
	public static final short LENGTH = 23;
	
	// built in 40..99
	/** Map mark (built-in data structure) */
	static final short DATA_BUNDLE = 40;
	
	/** List mark (built-in data structure) */
	static final short DATA_LIST = 41;
	
	/** Ionizables<Mark, Class> */
	private static Map<Short, Class<?>> customIonizables = new HashMap<>();
	
	// buffers and helper arrays for storing to streams.
	private static ByteBuffer bi = ByteBuffer.allocate(Integer.SIZE / 8);
	private static ByteBuffer bd = ByteBuffer.allocate(Double.SIZE / 8);
	private static ByteBuffer bf = ByteBuffer.allocate(Float.SIZE / 8);
	private static ByteBuffer bc = ByteBuffer.allocate(Character.SIZE / 8);
	private static ByteBuffer bl = ByteBuffer.allocate(Long.SIZE / 8);
	private static ByteBuffer bs = ByteBuffer.allocate(Short.SIZE / 8);
	private static byte[] ai = new byte[Integer.SIZE / 8];
	private static byte[] ad = new byte[Double.SIZE / 8];
	private static byte[] af = new byte[Float.SIZE / 8];
	private static byte[] ac = new byte[Character.SIZE / 8];
	private static byte[] al = new byte[Long.SIZE / 8];
	private static byte[] as = new byte[Short.SIZE / 8];
	
	/**
	 * Indicates whether range checking should be performed when registering
	 * marks.
	 */
	private static boolean markRangeChecking;
	
	static {
		markRangeChecking = false;
		
		registerIonizable(Ion.DATA_BUNDLE, IonDataBundle.class);
		registerIonizable(Ion.DATA_LIST, IonDataList.class);
		
		markRangeChecking = true;
	}
	
	
	/**
	 * Register new {@link Ionizable} for direct reconstructing.
	 * 
	 * @param mark mark to be used. Numbers 0..99 are reserved. Mark is of type
	 *            Short, using values out of the short range will raise an
	 *            exception.
	 * @param objClass class of the registered Ionizable
	 */
	public static void registerIonizable(int mark, Class<?> objClass)
	{
		// negative marks are allowed.
		if (mark > Short.MAX_VALUE) throw new IllegalArgumentException("Mark too high (max " + Short.MAX_VALUE + ").");
		if (mark < Short.MIN_VALUE) throw new IllegalArgumentException("Mark too low (min " + Short.MIN_VALUE + ").");
		
		short m = (short) mark;
		
		if (markRangeChecking && m >= 0 && m < 100) {
			throw new IllegalArgumentException("Marks 0..99 are reserved.");
		}
		
		if (customIonizables.containsKey(m)) {
			throw new IllegalArgumentException("Mark " + m + " is already in use.");
		}
		
		customIonizables.put(m, objClass);
	}
	
	
	/**
	 * Load an object from file.
	 * 
	 * @param file file
	 * @return the loaded object
	 * @throws IOException on failure
	 */
	public static Object fromFile(File file) throws IOException
	{
		try(InputStream in = new FileInputStream(file)) {
			
			final Object obj = fromStream(in);
			return obj;
			
		} catch (final IOException e) {
			throw new IOException("Error loading ION file.", e);
		}
	}
	
	
	
	/**
	 * Store an object to file.
	 * 
	 * @param path file path
	 * @param obj object to store
	 * @throws IOException
	 */
	public static void toFile(File path, Object obj) throws IOException
	{
		try(OutputStream out = new FileOutputStream(path)) {
			
			writeObject(out, obj);
			
			out.flush();
			out.close();
		} catch (final Exception e) {
			throw new IOException("Error writing to ION file.", e);
		}
	}
	
	/**
	 * Load an object from stream.
	 * 
	 * @param in input stream
	 * @return the loaded object
	 * @throws IOException
	 */
	public static Object fromStream(InputStream in) throws IOException
	{
		return readObject(in);
	}
	
	
	/**
	 * Write an object to a stream.
	 * 
	 * @param out output stream
	 * @param obj written object
	 * @throws IOException
	 */
	public static void toStream(OutputStream out, Object obj) throws IOException
	{
		writeObject(out, obj);
	}
	
	
	/**
	 * Read single object from input stream, preceded by a mark. If a mark is
	 * not present, the behavior is undefined - in case the read bytes happen to
	 * match one of the registered marks, some garbage will be read. Otherwise
	 * an exception will be be thrown.
	 * 
	 * @param in input stream
	 * @return the loaded object
	 * @throws IOException
	 */
	public static Object readObject(InputStream in) throws IOException
	{
		try {
			final short mark = readMark(in);
			if (customIonizables.containsKey(mark)) {
				Ionizable ionObj;
				
				try {
					ionObj = ((Ionizable) customIonizables.get(mark).newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					throw new IOException("Cound not instantiate: " + Log.str(customIonizables.get(mark)), e);
				}
				
				ionObj.loadFrom(in);
				return ionObj;
			}
			
			switch (mark) {
				case Ion.NULL:
					return null;
					
				case Ion.BOOLEAN:
					return readBoolean(in);
					
				case Ion.BYTE:
					return readByte(in);
					
				case Ion.CHAR:
					return readChar(in);
					
				case Ion.SHORT:
					return readShort(in);
					
				case Ion.INT:
					return readInt(in);
					
				case Ion.LONG:
					return readLong(in);
					
				case Ion.FLOAT:
					return readFloat(in);
					
				case Ion.DOUBLE:
					return readDouble(in);
					
				case Ion.STRING:
					final String s = readString(in);
					return s;
				default:
					throw new IOException("Invalid Ion mark: " + mark);
			}
		} catch (final IOException e) {
			throw new IOException("Error loading ION file: ", e);
		}
	}
	
	
	public static void expect(InputStream in, short mark) throws IOException
	{
		if (readMark(in) != mark) throw new IOException("Unexpected mark in ION stream.");
	}
	
	
	public static short readMark(InputStream in) throws IOException
	{
		return readShort(in);
	}
	
	
	public static void writeMark(OutputStream out, short mark) throws IOException
	{
		writeShort(out, mark);
	}
	
	
	/**
	 * Write a single object to output stream, with a mark.
	 * 
	 * @param out output stream
	 * @param obj stored object
	 * @throws IOException
	 */
	public static void writeObject(OutputStream out, Object obj) throws IOException
	{
		try {
			if (obj instanceof Ionizable) {
				writeMark(out, ((Ionizable) obj).getIonMark());
				((Ionizable) obj).saveTo(out);
				return;
			}
			
			if (obj instanceof Boolean) {
				writeMark(out, Ion.BOOLEAN);
				writeBoolean(out, (Boolean) obj);
				return;
			}
			
			if (obj instanceof Byte) {
				writeMark(out, Ion.BYTE);
				writeByte(out, (Byte) obj);
				return;
			}
			
			if (obj instanceof Character) {
				writeMark(out, Ion.CHAR);
				writeChar(out, (Character) obj);
				return;
			}
			
			if (obj instanceof Short) {
				writeMark(out, Ion.SHORT);
				writeShort(out, (Short) obj);
				return;
			}
			
			if (obj instanceof Integer) {
				writeMark(out, Ion.INT);
				writeInt(out, (Integer) obj);
				return;
			}
			
			if (obj instanceof Long) {
				writeMark(out, Ion.LONG);
				writeLong(out, (Long) obj);
				return;
			}
			
			if (obj instanceof Float) {
				writeMark(out, Ion.FLOAT);
				writeFloat(out, (Float) obj);
				return;
			}
			
			if (obj instanceof Double) {
				writeMark(out, Ion.DOUBLE);
				writeDouble(out, (Double) obj);
				return;
			}
			
			if (obj instanceof String) {
				writeMark(out, Ion.STRING);
				writeString(out, (String) obj);
				return;
			}
			
			throw new IOException("Object " + Log.str(obj) + " could not be be ionized.");
			
		} catch (final IOException e) {
			throw new IOException("Could not store: " + obj, e);
		}
	}
	
	
	private static byte[] getBytesBool(boolean bool)
	{
		return new byte[] { (byte) (bool ? 1 : 0) };
	}
	
	
	private static byte[] getBytesByte(byte num)
	{
		return new byte[] { num };
	}
	
	
	private static byte[] getBytesChar(char num)
	{
		synchronized (bc) {
			bc.clear();
			bc.putChar(num);
			return bc.array();
		}
	}
	
	
	private static byte[] getBytesShort(short num)
	{
		synchronized (bs) {
			bs.clear();
			bs.putShort(num);
			return bs.array();
		}
	}
	
	
	private static byte[] getBytesInt(int num)
	{
		synchronized (bi) {
			bi.clear();
			bi.putInt(num);
			return bi.array();
		}
	}
	
	
	private static byte[] getBytesLong(long num)
	{
		synchronized (bl) {
			bl.clear();
			bl.putLong(num);
			return bl.array();
		}
	}
	
	
	private static byte[] getBytesFloat(float num)
	{
		synchronized (bf) {
			bf.clear();
			bf.putFloat(num);
			return bf.array();
		}
	}
	
	
	private static byte[] getBytesDouble(double num)
	{
		synchronized (bd) {
			bd.clear();
			bd.putDouble(num);
			return bd.array();
		}
	}
	
	
	private static byte[] getBytesString(String str)
	{
		final char[] chars = str.toCharArray();
		
		final ByteBuffer bstr = ByteBuffer.allocate((Character.SIZE / 8) * chars.length + (Character.SIZE / 8));
		for (final char c : chars) {
			bstr.putChar(c);
		}
		
		bstr.putChar((char) 0);
		
		return bstr.array();
	}
	
	
	/**
	 * Write a boolean (without a mark)
	 * 
	 * @param out output stream
	 * @param b boolean to write
	 * @throws IOException
	 */
	public static void writeBoolean(OutputStream out, boolean b) throws IOException
	{
		out.write(getBytesBool(b));
	}
	
	
	/**
	 * Write a byte (without a mark)
	 * 
	 * @param out output stream
	 * @param b byte to write
	 * @throws IOException
	 */
	public static void writeByte(OutputStream out, byte b) throws IOException
	{
		out.write(getBytesByte(b));
	}
	
	
	/**
	 * Write a char (without a mark)
	 * 
	 * @param out output stream
	 * @param c char to write
	 * @throws IOException
	 */
	public static void writeChar(OutputStream out, char c) throws IOException
	{
		out.write(getBytesChar(c));
	}
	
	
	/**
	 * Write a short (without a mark)
	 * 
	 * @param out output stream
	 * @param s short to write
	 * @throws IOException
	 */
	public static void writeShort(OutputStream out, short s) throws IOException
	{
		out.write(getBytesShort(s));
	}
	
	
	/**
	 * Write an integer (without a mark)
	 * 
	 * @param out output stream
	 * @param i integer to write
	 * @throws IOException
	 */
	public static void writeInt(OutputStream out, int i) throws IOException
	{
		out.write(getBytesInt(i));
	}
	
	
	/**
	 * Write a long (without a mark)
	 * 
	 * @param out output stream
	 * @param l long to write
	 * @throws IOException
	 */
	public static void writeLong(OutputStream out, long l) throws IOException
	{
		out.write(getBytesLong(l));
	}
	
	
	/**
	 * Write a float (without a mark)
	 * 
	 * @param out output stream
	 * @param f float to write
	 * @throws IOException
	 */
	public static void writeFloat(OutputStream out, float f) throws IOException
	{
		out.write(getBytesFloat(f));
	}
	
	
	/**
	 * Write a double (without a mark)
	 * 
	 * @param out output stream
	 * @param d double to write
	 * @throws IOException
	 */
	public static void writeDouble(OutputStream out, double d) throws IOException
	{
		out.write(getBytesDouble(d));
	}
	
	
	/**
	 * Write a String (without a mark)
	 * 
	 * @param out output stream
	 * @param str String to write
	 * @throws IOException
	 */
	public static void writeString(OutputStream out, String str) throws IOException
	{
		out.write(getBytesString(str));
	}
	
	
	/**
	 * Read a boolean (without a mark)
	 * 
	 * @param in input stream
	 * @return boolean read
	 * @throws IOException
	 */
	public static boolean readBoolean(InputStream in) throws IOException
	{
		return readByte(in) > 0;
	}
	
	
	/**
	 * Read a byte (without a mark)
	 * 
	 * @param in input stream
	 * @return byte read
	 * @throws IOException
	 */
	public static byte readByte(InputStream in) throws IOException
	{
		int b = in.read();
		if (-1 == b) throw new IOException("End of stream.");
		return (byte) b;
	}
	
	
	/**
	 * Read a char (without a mark)
	 * 
	 * @param in input stream
	 * @return char read
	 * @throws IOException
	 */
	public static char readChar(InputStream in) throws IOException
	{
		synchronized (ac) {
			if (-1 == in.read(ac, 0, ac.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(ac);
			return buf.getChar();
		}
	}
	
	
	/**
	 * Read a short (without a mark)
	 * 
	 * @param in input stream
	 * @return short read
	 * @throws IOException
	 */
	public static short readShort(InputStream in) throws IOException
	{
		synchronized (as) {
			if (-1 == in.read(as, 0, as.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(as);
			return buf.getShort();
		}
	}
	
	
	/**
	 * Read a long (without a mark)
	 * 
	 * @param in input stream
	 * @return long read
	 * @throws IOException
	 */
	public static long readLong(InputStream in) throws IOException
	{
		synchronized (al) {
			if (-1 == in.read(al, 0, al.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(al);
			return buf.getLong();
		}
	}
	
	
	/**
	 * Read an integer (without a mark)
	 * 
	 * @param in input stream
	 * @return integer read
	 * @throws IOException
	 */
	public static int readInt(InputStream in) throws IOException
	{
		synchronized (ai) {
			if (-1 == in.read(ai, 0, ai.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(ai);
			return buf.getInt();
		}
	}
	
	
	/**
	 * Read a float (without a mark)
	 * 
	 * @param in input stream
	 * @return float read
	 * @throws IOException
	 */
	public static float readFloat(InputStream in) throws IOException
	{
		synchronized (af) {
			if (-1 == in.read(af, 0, af.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(af);
			return buf.getFloat();
		}
	}
	
	
	/**
	 * Read a double (without a mark)
	 * 
	 * @param in input stream
	 * @return double read
	 * @throws IOException
	 */
	public static double readDouble(InputStream in) throws IOException
	{
		synchronized (ad) {
			if (-1 == in.read(ad, 0, ad.length)) throw new IOException("End of stream.");
			final ByteBuffer buf = ByteBuffer.wrap(ad);
			return buf.getDouble();
		}
	}
	
	
	/**
	 * Read a string (without a mark)
	 * 
	 * @param in input stream
	 * @return string read
	 * @throws IOException
	 */
	public static String readString(InputStream in) throws IOException
	{
		String s = "";
		char c;
		while ((c = readChar(in)) > 0) {
			s += c;
		}
		return s;
	}
	
}
