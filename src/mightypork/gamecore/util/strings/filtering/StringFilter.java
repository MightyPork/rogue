package mightypork.gamecore.util.strings.filtering;


/**
 * Utility interface for string filters (accepting filepaths and similar)
 * 
 * @author MightyPork
 */
public interface StringFilter {
	
	public boolean isValid(String entry);
}
