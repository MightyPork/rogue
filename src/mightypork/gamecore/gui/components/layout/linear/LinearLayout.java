package mightypork.gamecore.gui.components.layout.linear;


import mightypork.gamecore.core.modules.AppAccess;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.DynamicWidthComponent;
import mightypork.gamecore.gui.components.LayoutComponent;
import mightypork.gamecore.gui.components.LinearComponent;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.batch.NumSum;
import mightypork.utils.math.constraints.rect.RectBound;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.proxy.VectAdapter;


public class LinearLayout extends LayoutComponent {
	
	public LinearLayout(AppAccess app, AlignX align) {
		super(app);
		this.align = align;
	}
	
	
	public LinearLayout(AppAccess app, RectBound context, AlignX align) {
		super(app, context);
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
