package mightypork.utils.string.validation;


/**
 * Utility interface for string filters (accepting filepaths and similar)
 * 
 * @author MightyPork
 */
public interface StringFilter {
	
	public boolean accept(String entry);
}
