package mightypork.util.files.ion;




/**
 * Binary ion object, with a mark = saveable / loadable on it's own
 * 
 * @author MightyPork
 */
public interface IonBinary extends IonBinaryHeadless {
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public short getIonMark();
}
