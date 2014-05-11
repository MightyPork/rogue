package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.ClickableComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.rect.caching.RectCache;
import mightypork.rogue.Res;
import mightypork.rogue.world.World.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;


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
	
	private final InvSlot[] slots;
	
	private final TextPainter txt;
	
	private final RectCache txtRect;
	
	
	public InvSlot(int index, InvSlot[] allSlots)
	{
		super();
		this.txBase = Res.txq("inv.slot.base");
		this.txSelected = Res.txq("inv.slot.selected");
		
		this.index = index;
		this.slots = allSlots;
		
		this.itemRect = getRect().shrink(height().perc(16)).cached();
		
		//@formatter:off
		this.txtRect = itemRect.bottomEdge()
				.move(itemRect.height().perc(5).neg(), itemRect.height().perc(10).neg())
				.growUp(itemRect.height().perc(35)).cached();
		//@formatter:on
		
		txt = new TextPainter(Res.getFont("thin"), AlignX.RIGHT, RGB.WHITE);
		txt.setRect(txtRect);
		txt.setShadow(RGB.BLACK_60, txt.getRect().height().div(8).toVectXY());
		
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
		itemRect.poll();
		txtRect.poll();
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
				txt.setText("" + itm.getAmount());
				txt.setColor(RGB.WHITE);
				txt.render();
			}
			
			if (pl.getSelectedWeapon() == index) {
				txt.setText("*");
				txt.setColor(RGB.YELLOW);
				txt.render();
			}
			
			
		}
	}
	
}
