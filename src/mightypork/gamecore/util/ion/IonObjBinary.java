package mightypork.gamecore.util.ion;


/**
 * Binary ion object, with a mark = saveable / loadable on it's own
 * 
 * @author MightyPork
 */
public interface IonObjBinary extends IonObjBlob {
	
	/**
	 * Get Ion mark byte.
	 * 
	 * @return Ion mark byte.
	 */
	public short getIonMark();
}
