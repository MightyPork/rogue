package mightypork.utils.files.ion;

/**
 * Byte marks used to structure data in Ion files.
 * 
 * @author MightyPork
 */
public class IonMarks {
	
	/** Null value */
	public static final byte NULL = 0;
	
	/** Boolean value */
	public static final byte BOOLEAN = 1;
	
	/** Byte value */
	public static final byte BYTE = 2;
	
	/** Character value */
	public static final byte CHAR = 3;
	
	/** Short value */
	public static final byte SHORT = 4;
	
	/** Integer value */
	public static final byte INT = 5;
	
	/** Long value */
	public static final byte LONG = 6;
	
	/** Float value */
	public static final byte FLOAT = 7;
	
	/** Double value */
	public static final byte DOUBLE = 8;
	
	/** String value */
	public static final byte STRING = 9;
	
	/** List value (begin) - contains entries, ends with END */
	public static final byte LIST = 10;
	
	/** Map value (begin) - contains entries, ends with END */
	public static final byte MAP = 11;
	
	/**
	 * List / Map entry<br>
	 * In list directly followed by entry value. In map followed by (string) key
	 * and the entry value.
	 */
	public static final byte ENTRY = 12;
	
	/** End of List / Map */
	public static final byte END = 13;
	
}
