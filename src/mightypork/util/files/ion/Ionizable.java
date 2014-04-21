package mightypork.util.files.ion;


/**
 * <p>
 * Data object that can be reconstructed by Ion based on it's mark. Such object
 * MUST provide an implicit constructor.
 * </p>
 * <p>
 * All {@link Ionizable}s must be registered to {@link Ion}, otherwise they
 * can't be written/loaded using the mark.
 * </p>
 * 
 * @author MightyPork
 */
public interface Ionizable extends Streamable {
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public short getIonMark();
}
