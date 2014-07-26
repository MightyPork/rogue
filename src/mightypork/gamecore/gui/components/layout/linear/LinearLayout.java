package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.gui.components.LinearComponent;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.batch.NumSum;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.proxy.VectAdapter;


/**
 * Layout that aligns elements while taking into account their actual
 * dimensions.<br>
 * Useful eg. for buttons that stretch based on text length.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LinearLayout extends LayoutComponent {
	
	public LinearLayout(AlignX align) {
		this.align = align;
	}
	
	
	public LinearLayout(RectBound context, AlignX align) {
		super(context);
		this.align = align;
	}
	
	private final NumSum totalWidth = new NumSum();
	
	private final Vect leftAlignOrigin = LinearLayout.this.origin();
	private final Vect centerAlignOrigin = LinearLayout.this.topCenter().sub(totalWidth.half(), Num.ZERO);
	private final Vect rightAlignOrigin = LinearLayout.this.topRight().sub(totalWidth, Num.ZERO);
	
	private final Vect leftMostOrigin = new VectAdapter() {
		
		@Override
		protected Vect getSource()
		{
			switch (align) {
				default:
				case LEFT:
					return leftAlignOrigin;
				case CENTER:
					return centerAlignOrigin;
				case RIGHT:
					return rightAlignOrigin;
			}
		}
	};
	
	private Vect nextOrigin = leftMostOrigin;
	
	private AlignX align = AlignX.LEFT;
	
	
	public void add(DynamicWidthComponent dwcomp)
	{
		add(new LinearWrapper(dwcomp));
	}
	
	
	public void add(LinearComponent lincomp)
	{
		lincomp.setHeight(height());
		lincomp.setOrigin(nextOrigin);
		nextOrigin = nextOrigin.add(lincomp.width(), Num.ZERO);
		totalWidth.addSummand(lincomp.width());
		attach(lincomp);
	}
	
	
	public void setAlign(AlignX align)
	{
		this.align = align;
	}
	
	
	/**
	 * Add a gap.
	 * 
	 * @param heightPercent percent of height for gap width
	 */
	public void gap(double heightPercent)
	{
		add(new LinearGap(heightPercent));
	}
}
