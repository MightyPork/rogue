package mightypork.util.ion;


import java.io.*;

import mightypork.util.logging.Log;


/**
 * Universal data storage system (main API class)
 * 
 * @author MightyPork
 */
public class Ion {
	
	// marks for object saving
	/** Null mark */
	static final int NULL = 0;
	/** Boolean mark */
	static final int BOOLEAN = 1;
	/** Byte mark */
	static final int BYTE = 2;
	/** Character mark */
	static final int CHAR = 3;
	/** Short mark */
	static final int SHORT = 4;
	/** Integer mark */
	static final int INT = 5;
	/** Long mark */
	static final int LONG = 6;
	/** Float mark */
	static final int FLOAT = 7;
	/** Double mark */
	static final int DOUBLE = 8;
	/** String mark */
	static final int STRING = 9;
	/** Boolean array mark */
	static final int BOOLEAN_ARRAY = 10;
	/** Byte array mark */
	static final int BYTE_ARRAY = 11;
	/** Character array mark */
	static final int CHAR_ARRAY = 12;
	/** Short array mark */
	static final int SHORT_ARRAY = 13;
	/** Integer array mark */
	static final int INT_ARRAY = 14;
	/** Long array mark */
	static final int LONG_ARRAY = 15;
	/** Float array mark */
	static final int FLOAT_ARRAY = 16;
	/** Double array mark */
	static final int DOUBLE_ARRAY = 17;
	/** String array mark */
	static final int STRING_ARRAY = 18;
	/** Entry mark - start of map or sequence entry */
	static final int ENTRY = 19;
	/** End mark - end of sequence or map */
	static final int END = 20;
	/** Map mark (built-in data structure) */
	static final int DATA_BUNDLE = 21;
	/** Sequence wrapper for bundle */
	static final int SEQUENCE_WRAPPER = 22;
	/** Map wrapper for bundle */
	static final int MAP_WRAPPER = 23;
	
	// technical 20..39
	
	/** Ionizables<Mark, Class> */
	@SuppressWarnings("rawtypes")
	private static Class[] registered = new Class[256];
	
	private static boolean reservedMarkChecking;
	
	static {
		reservedMarkChecking = false;
		
		// register built-ins
		registerBinary(DATA_BUNDLE, IonBundle.class);
		registerBinary(SEQUENCE_WRAPPER, IonSequenceWrapper.class);
		registerBinary(MAP_WRAPPER, IonMapWrapper.class);
		
		reservedMarkChecking = true;
	}
	
	
	/**
	 * Register new {@link IonBinary} class for writing/loading.
	 * 
	 * @param mark mark to be used 50..255, unless internal
	 * @param objClass class of the registered object
	 */
	public static void registerBinary(int mark, Class<? extends IonBinary> objClass)
	{
		// negative marks are allowed.
		if (mark > 255) throw new IllegalArgumentException("Mark must be < 256.");
		if (mark < 0) throw new IllegalArgumentException("Mark must be positive.");
		
		if (reservedMarkChecking && mark < 50) {
			throw new IllegalArgumentException("Marks 0..49 are reserved.");
		}
		
		if (registered[mark] != null) {
			throw new IllegalArgumentException("Mark " + mark + " is already in use.");
		}
		
		try {
			objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("Class " + Log.str(objClass) + " doesn't have an implicit constructor.");
		}
		
		registered[mark] = objClass;
	}
	
	
	/**
	 * Load binary from file and cast.
	 */
	public static <T extends IonBinary> T fromFile(String path) throws IOException
	{
		return fromFile(new File(path));
	}
	
	
	/**
	 * Load binary from file and cast.
	 */
	public static <T extends IonBinary> T fromFile(File file) throws IOException
	{
		try(InputStream in = new FileInputStream(file)) {
			return fromStream(in);
		}
	}
	
	
	/**
	 * Load bundled from file and unwrap.
	 */
	public static <T extends IonBundled> T fromFile(String path, Class<? extends T> objClass) throws IOException
	{
		return fromFile(new File(path), objClass);
	}
	
	
	/**
	 * Load bundled from file and unwrap.
	 */
	public static <T extends IonBundled> T fromFile(File file, Class<? extends T> objClass) throws IOException
	{
		try(InputStream in = new FileInputStream(file)) {
			
			return fromStream(in, objClass);
			
		}
	}
	
	
	public static void toFile(String path, IonBundled obj) throws IOException
	{
		toFile(new File(path), obj);
	}
	
	
	/**
	 * Wrap bundled and save to file.
	 */
	public static void toFile(File file, IonBundled obj) throws IOException
	{
		toFile(file, wrap(obj));
	}
	
	
	/**
	 * Write binary to file with mark.
	 */
	public static void toFile(String path, IonBinary obj) throws IOException
	{
		toFile(new File(path), obj);
	}
	
	
	/**
	 * Write binary to file with mark.
	 */
	public static void toFile(File file, IonBinary obj) throws IOException
	{
		try(OutputStream out = new FileOutputStream(file)) {
			
			toStream(out, obj);
			
			out.flush();
			out.close();
		} catch (final Exception e) {
			throw new IOException("Error writing to ION file.", e);
		}
	}
	
	
	/**
	 * Load object from stream based on mark, try to cast.
	 */
	public static <T> T fromStream(InputStream in) throws IOException
	{
		final IonInput inp = new IonInput(in);
		
		return (T) inp.readObject();
	}
	
	
	/**
	 * Load bundled object from stream, unwrap.
	 */
	public static <T extends IonBundled> T fromStream(InputStream in, Class<? extends T> objClass) throws IOException
	{
		return unwrap((IonBundle)new IonInput(in).readObject(), objClass);
	}
	
	
	/**
	 * Write object to output with a mark.
	 */
	public static void toStream(OutputStream out, IonBinary obj) throws IOException
	{
		new IonOutput(out).writeObject(obj);
	}
	
	
	/**
	 * Create new bundle and write the object to it.
	 */
	public static IonBundle wrap(IonBundled content) throws IOException
	{
		final IonBundle ib = new IonBundle();
		content.save(ib);
		return ib;
	}
	
	
	/**
	 * Try to unwrap an object from bundle. The object class must have implicit
	 * accessible constructor.
	 * 
	 * @param bundle unwrapped bundle
	 * @param objClass class of desired object
	 * @return the object unwrapped
	 * @throws IOException
	 */
	public static <T extends IonBundled> T unwrap(IonBundle bundle, Class<? extends T> objClass) throws IOException
	{
		try {
			final T inst = objClass.newInstance();
			inst.load(bundle);
			return inst;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IOException("Could not instantiate " + Log.str(objClass) + ".");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	static Class<? extends IonBinary> getClassForMark(int mark)
	{
		return registered[mark];
	}
	
	
	/**
	 * @return true if the mark is for a registered {@link IonBinary} object
	 */
	static boolean isMarkForBinary(int mark)
	{
		return registered[mark] != null;
	}
	
	
	/**
	 * Make sure object is registered in the table
	 * 
	 * @throws IOException if not registered or class mismatch
	 */
	static void assertRegistered(IonBinary obj) throws IOException
	{
		final int mark = obj.getIonMark();
		
		final Class<? extends IonBinary> clz = Ion.getClassForMark(mark);
		
		if (clz == null) {
			throw new IOException("Not registered - mark: " + mark + ", class: " + Log.str(obj.getClass()));
		}
		
		if (clz != obj.getClass()) {
			throw new IOException("Class mismatch - mark: " + mark + ", class: " + Log.str(obj.getClass()));
		}
	}
	
}
