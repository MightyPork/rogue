package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.rect.caching.RectCache;
import mightypork.rogue.Res;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;


/**
 * Button in the ingame nav
 * 
 * @author MightyPork
 */
public class InvSlot extends ClickableComponent {
	
	private final TxQuad txBase, txSelected;
	
	protected boolean selected = false;
	protected int index;
	
	private final RectCache itemRect;
	private final RectCache uiRect;
	
	private final InvSlot[] slots;
	
	private final TextPainter rbTxP;
	private final TextPainter rtTxP;
	
	private final RectCache rbTxRect;
	private final RectCache rtTxRect;
	
	private final Rect usesRect;
	private final Num hAlpha = Num.make(0.7);
	
	
	public InvSlot(int index, InvSlot[] allSlots)
	{
		super();
		this.txBase = Res.getTxQuad("inv.slot.base");
		this.txSelected = Res.getTxQuad("inv.slot.selected");
		
		this.index = index;
		this.slots = allSlots;
		
		this.uiRect = getRect().shrink(height().perc(16)).cached();
		this.itemRect = uiRect.shrink(Num.ZERO, height().perc(14), height().perc(14), Num.ZERO).cached();
		
		this.usesRect = uiRect.topLeft().startRect().grow(Num.ZERO, uiRect.width().perc(40), Num.ZERO, uiRect.height().perc(8));
		
		//@formatter:off
		this.rbTxRect = uiRect.bottomEdge()
				.moveY(uiRect.height().perc(1*(30/7D)))
				.growUp(uiRect.height().perc(30)).cached();
		//@formatter:on
		
		rbTxP = new TextPainter(Res.getFont("tiny"), AlignX.RIGHT, RGB.WHITE);
		rbTxP.setRect(rbTxRect);
		rbTxP.setShadow(RGB.BLACK_70, rbTxP.getRect().height().div(7).toVectXY());
		
		//@formatter:off
		this.rtTxRect = uiRect.topEdge()
				.growDown(uiRect.height().perc(30)).cached();
		//@formatter:on
		
		rtTxP = new TextPainter(Res.getFont("tiny"), AlignX.RIGHT, RGB.GREEN);
		rtTxP.setRect(rtTxRect);
		rtTxP.setShadow(RGB.BLACK_70, rtTxP.getRect().height().div(7).toVectXY());
		
		setAction(new Action() {
			
			@Override
			protected void execute()
			{
				for (final InvSlot sl : slots) {
					sl.selected = false;
				}
				
				selected = true;
			}
			
		});
	}
	
	
	@Override
	public void updateLayout()
	{
		uiRect.poll();
		itemRect.poll();
		rbTxRect.poll();
		rtTxRect.poll();
	}
	
	
	@Override
	protected void renderComponent()
	{
		TxQuad bg;
		
		if (selected) {
			bg = txSelected;
		} else {
			bg = txBase;
		}
		
		Render.quadTextured(this, bg);
		
		final PlayerFacade pl = WorldProvider.get().getPlayer();
		
		final Item itm = pl.getInventory().getItem(index);
		if (itm != null && !itm.isEmpty()) {
			itm.render(itemRect);
			if (itm.getAmount() > 1) {
				rbTxP.setText("" + itm.getAmount());
				rbTxP.setColor(RGB.WHITE);
				rbTxP.render();
			}
			
			if (pl.getSelectedWeaponIndex() == index) {
				rbTxP.setText("*");
				rbTxP.setColor(RGB.YELLOW);
				rbTxP.render();
			}
			
			if (itm.getType() == ItemType.FOOD) {
				rtTxP.setText(Calc.toString(itm.getFoodPoints() / 2D));
				rtTxP.setColor(RGB.GREEN);
				rtTxP.render();
			} else if (itm.getType() == ItemType.WEAPON) {
				
				final int atk = itm.getAttackPoints();
				rtTxP.setText((atk >= 0 ? "+" : "") + atk);
				rtTxP.setColor(RGB.CYAN);
				rtTxP.render();
			}
			
			
			if (itm.isDamageable()) {
				Color.pushAlpha(hAlpha);
				
				Render.quadColor(usesRect, RGB.BLACK);
				
				final double useRatio = (itm.getRemainingUses() / (double) itm.getMaxUses());
				
				final Color barColor = (useRatio > 0.6 ? RGB.GREEN : useRatio > 0.2 ? RGB.ORANGE : RGB.RED);
				
				Render.quadColor(usesRect.shrinkRight(usesRect.width().value() * (1 - useRatio)), barColor);
				
				Color.popAlpha();
			}
		}
	}
	
}
