package mightypork.util.string.filtering;


/**
 * Utility interface for string filters (accepting filepaths and similar)
 * 
 * @author MightyPork
 */
public interface StringFilter {
	
	public boolean accept(String entry);
}
