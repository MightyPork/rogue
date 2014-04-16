package mightypork.util.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import mightypork.util.constraints.vect.Vect;
import mightypork.util.constraints.vect.VectConst;
import mightypork.util.math.Range;
import mightypork.util.objects.Convert;


/**
 * Property manager with advanced formatting and value checking.<br>
 * Methods starting with put are for filling. Most of the others are shortcuts
 * to getters.
 * 
 * @author MightyPork
 */
public class PropertyManager {
	
	private abstract class Property<T> {
		
		public String comment;
		public String key;
		
		public T value;
		public T defaultValue;
		
		
		public Property(String key, T defaultValue, String comment) {
			super();
			this.comment = comment;
			this.key = key;
			this.defaultValue = defaultValue;
			this.value = defaultValue;
		}
		
		
		public abstract void parse(String string);
		
		
		@Override
		public String toString()
		{
			return Convert.toString(value);
		}
	}
	
	private class BooleanProperty extends Property<Boolean> {
		
		public BooleanProperty(String key, Boolean defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toBoolean(string, defaultValue);
		}
	}
	
	private class IntegerProperty extends Property<Integer> {
		
		public IntegerProperty(String key, Integer defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toInteger(string, defaultValue);
		}
	}
	
	private class DoubleProperty extends Property<Double> {
		
		public DoubleProperty(String key, Double defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toDouble(string, defaultValue);
		}
	}
	
	private class StringProperty extends Property<String> {
		
		public StringProperty(String key, String defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toString(string, defaultValue);
		}
	}
	
	private class RangeProperty extends Property<Range> {
		
		public RangeProperty(String key, Range defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toRange(string, defaultValue);
		}
	}
	
	private class CoordProperty extends Property<Vect> {
		
		public CoordProperty(String key, Vect defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		
		@Override
		public void parse(String string)
		{
			value = Convert.toVect(string, defaultValue);
		}
	}
	
	/** put newline before entry comments */
	private boolean cfgNewlineBeforeComments = true;
	/** Put newline between sections. */
	private boolean cfgSeparateSections = true;
	/** Force save, even if nothing changed (used to save changed comments) */
	private boolean cfgForceSave;
	
	private final File file;
	private String fileComment = "";
	
	private final TreeMap<String, Property<?>> entries;
	private final TreeMap<String, String> renameTable;
	private final TreeMap<String, String> overrideValues;
	private SortedProperties props = new SortedProperties();
	
	
	/**
	 * Create property manager from file path and an initial comment.
	 * 
	 * @param file file with the props
	 * @param comment the initial comment. Use \n in it if you want.
	 */
	public PropertyManager(File file, String comment) {
		this.file = file;
		this.entries = new TreeMap<>();
		this.overrideValues = new TreeMap<>();
		this.renameTable = new TreeMap<>();
		this.fileComment = comment;
	}
	
	
	/**
	 * Load, fix and write to file.
	 */
	public void apply()
	{
		boolean needsSave = false;
		if (!file.getParentFile().mkdirs()) {
			if (!file.getParentFile().exists()) {
				throw new RuntimeException("Cound not create config file.");
			}
		}
		
		try (FileInputStream fis = new FileInputStream(file)) {
			props.load(fis);
		} catch (final IOException e) {
			needsSave = true;
			props = new SortedProperties();
		}
		
		props.cfgBlankRowBetweenSections = cfgSeparateSections;
		props.cfgBlankRowBeforeComment = cfgNewlineBeforeComments;
		
		final ArrayList<String> keyList = new ArrayList<>();
		
		// rename keys
		for (final Entry<String, String> entry : renameTable.entrySet()) {
			if (props.getProperty(entry.getKey()) == null) {
				continue;
			}
			props.setProperty(entry.getValue(), props.getProperty(entry.getKey()));
			props.remove(entry.getKey());
			needsSave = true;
		}
		
		// set the override values into the freshly loaded properties file
		for (final Entry<String, String> entry : overrideValues.entrySet()) {
			props.setProperty(entry.getKey(), entry.getValue());
			needsSave = true;
		}
		
		// validate entries one by one, replace with default when needed
		for (final Property<?> entry : entries.values()) {
			keyList.add(entry.key);
			
			final String propOrig = props.getProperty(entry.key);
			
			entry.parse(propOrig);
			
			if (!entry.toString().equals(propOrig)) {
				needsSave = true;
			}
			
			if (entry.comment != null) {
				props.setKeyComment(entry.key, entry.comment);
			}
			
			if (propOrig == null || !entry.toString().equals(propOrig)) {
				props.setProperty(entry.key, entry.toString());
				
				needsSave = true;
			}
		}
		
		// removed unused props
		for (final String propname : props.keySet().toArray(new String[props.size()])) {
			if (!keyList.contains(propname)) {
				props.remove(propname);
				needsSave = true;
			}
			
		}
		
		// save if needed
		if (needsSave || cfgForceSave) {
			try {
				props.store(new FileOutputStream(file), fileComment);
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		overrideValues.clear();
		renameTable.clear();
	}
	
	
	/**
	 * @param newlineBeforeComments put newline before comments
	 */
	public void cfgNewlineBeforeComments(boolean newlineBeforeComments)
	{
		this.cfgNewlineBeforeComments = newlineBeforeComments;
	}
	
	
	/**
	 * @param separateSections do separate sections by newline
	 */
	public void cfgSeparateSections(boolean separateSections)
	{
		this.cfgSeparateSections = separateSections;
	}
	
	
	/**
	 * @param forceSave save even if unchanged.
	 */
	public void cfgForceSave(boolean forceSave)
	{
		this.cfgForceSave = forceSave;
	}
	
	
	/**
	 * Get a property entry (rarely used)
	 * 
	 * @param n key
	 * @return the entry
	 */
	private Property<?> get(String n)
	{
		try {
			return entries.get(n);
		} catch (final Throwable t) {
			return null;
		}
	}
	
	
	/**
	 * Get boolean property
	 * 
	 * @param n key
	 * @return the boolean found, or false
	 */
	public Boolean getBoolean(String n)
	{
		return Convert.toBoolean(get(n).value);
	}
	
	
	/**
	 * Get numeric property
	 * 
	 * @param n key
	 * @return the int found, or null
	 */
	public Integer getInteger(String n)
	{
		return Convert.toInteger(get(n).value);
	}
	
	
	/**
	 * Get numeric property as double
	 * 
	 * @param n key
	 * @return the double found, or null
	 */
	public Double getDouble(String n)
	{
		return Convert.toDouble(get(n).value);
	}
	
	
	/**
	 * Get string property
	 * 
	 * @param n key
	 * @return the string found, or null
	 */
	public String getString(String n)
	{
		return Convert.toString(get(n).value);
	}
	
	
	/**
	 * Get range property
	 * 
	 * @param n key
	 * @return the range found, or null
	 */
	public Range getRange(String n)
	{
		return Convert.toRange(get(n).value);
	}
	
	
	/**
	 * Get coord property
	 * 
	 * @param n key
	 * @return the coord found, or null
	 */
	public VectConst getCoord(String n)
	{
		return Convert.toVect(get(n).value);
	}
	
	
	/**
	 * Add a boolean property
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putBoolean(String n, boolean d, String comment)
	{
		entries.put(n, new BooleanProperty(n, d, comment));
	}
	
	
	/**
	 * Add a numeric property (double)
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putDouble(String n, double d, String comment)
	{
		entries.put(n, new DoubleProperty(n, d, comment));
	}
	
	
	/**
	 * Add a numeric property
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putInteger(String n, int d, String comment)
	{
		entries.put(n, new IntegerProperty(n, d, comment));
	}
	
	
	/**
	 * Add a string property
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putString(String n, String d, String comment)
	{
		entries.put(n, new StringProperty(n, d, comment));
	}
	
	
	/**
	 * Add a coord property
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putCoord(String n, Vect d, String comment)
	{
		entries.put(n, new CoordProperty(n, d, comment));
	}
	
	
	/**
	 * Add a range property
	 * 
	 * @param n key
	 * @param d default value
	 * @param comment the in-file comment
	 */
	public void putRange(String n, Range d, String comment)
	{
		entries.put(n, new RangeProperty(n, d, comment));
	}
	
	
	/**
	 * Rename key before doing "apply"; value is preserved
	 * 
	 * @param oldKey old key
	 * @param newKey new key
	 */
	public void renameKey(String oldKey, String newKey)
	{
		renameTable.put(oldKey, newKey);
		return;
	}
	
	
	/**
	 * Set value saved to certain key; use to save runtime-changed configuration
	 * values.
	 * 
	 * @param key key
	 * @param value the saved value
	 */
	public void setValue(String key, Object value)
	{
		overrideValues.put(key, value.toString());
		return;
	}
	
}
