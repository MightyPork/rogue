package mightypork.gamecore.util.math.constraints.rect.proxy;


import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.gamecore.util.math.constraints.vect.proxy.VectAdapter;


/**
 * Rect proxy with abstract method for plugging in / generating rect
 * dynamically.
 * 
 * @author Ondřej Hruška
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
