package mightypork.utils.math.rect;


import mightypork.utils.math.vect.Vect;
import mightypork.utils.math.vect.VectAdapter;
import mightypork.utils.math.vect.VectView;


/**
 * Rect proxy with abstract method for plugging in / generating rect
 * dynamically.
 * 
 * @author MightyPork
 */
public abstract class RectAdapter extends RectView {
	
	private VectAdapter originAdapter = new VectAdapter() {
		
		@Override
		protected Vect getSource()
		{
			return RectAdapter.this.getSource().origin();
		}
	};
	
	private VectAdapter sizeAdapter = new VectAdapter() {
		
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
	public VectView origin()
	{
		return originAdapter;
	}
	
	
	@Override
	public VectView size()
	{
		return sizeAdapter;
	}
	
}
