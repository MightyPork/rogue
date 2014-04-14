package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAdapter;


/**
 * Rect proxy with abstract method for plugging in / generating rect
 * dynamically.
 * 
 * @author MightyPork
 */
public abstract class RectAdapter extends Rect {
	
	// adapters are needed in case the vect returned from source changes
	// (is replaced). This way, references to origin and rect will stay intact.
	
	private final VectAdapter originAdapter = new VectAdapter() {
		
		@Override
		protected Vect getSource()
		{
			return RectAdapter.this.getSource().origin();
		}
	};
	
	private final VectAdapter sizeAdapter = new VectAdapter() {
		
		@Override
		protected Vect getSource()
		{
			return RectAdapter.this.getSource().size();
		}
	};
	
	
	/**
	 * @return the proxied coord
	 */
	protected abstract Rect getSource();
	
	
	@Override
	public Vect origin()
	{
		return originAdapter;
	}
	
	
	@Override
	public Vect size()
	{
		return sizeAdapter;
	}
	
}
