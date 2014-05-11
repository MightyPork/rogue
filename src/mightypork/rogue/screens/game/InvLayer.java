package mightypork.rogue.screens.game;


import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.layout.HorizontalFixedFlowLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.World.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;


public class InvLayer extends ScreenLayer {
	
	private final StringProvider contextStrProv = new StringProvider() {
		
		@Override
		public String getString()
		{
			String s = "Esc - close";
			
			final int selected = getSelectedSlot();
			if (selected != -1) {
				
				final PlayerFacade pl = WorldProvider.get().getPlayer();
				
				final Item itm = pl.getInventory().getItem(selected);
				if (itm != null && !itm.isEmpty()) {
					s = "D - drop, " + s;
					
					if (itm.getType() == ItemType.FOOD) {
						s = "E - eat, " + s;
					}
					
					if (itm.getType() == ItemType.WEAPON) {
						s = "E - equip, " + s;
					}
				}
			}
			
			return s;
		}
	};
	
	private final InvSlot[] slots = new InvSlot[8];
	
	
	private int getSelectedSlot()
	{
		for (final InvSlot sl : slots) {
			if (sl.selected) {
				// stuff
				return sl.index;
			}
		}
		return -1;
	}
	
	
	public InvLayer(final ScreenGame screen)
	{
		super(screen);
		
		final Rect fg = root.shrink(root.height().perc(15));
		
		final QuadPainter qp = new QuadPainter(Color.rgba(0, 0, 0, 0.5));
		qp.setRect(root);
		root.add(qp);
		
		int pos = 0;
		
		final GridLayout gl = new GridLayout(root, fg, 21, 1);
		root.add(gl);
		
		final TextPainter txp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.YELLOW, "Inventory");
		gl.put(txp, pos, 0, 2, 1);
		pos += 3;
		
		final HorizontalFixedFlowLayout row1 = new HorizontalFixedFlowLayout(root, null, AlignX.LEFT);
		row1.setElementWidth(row1.height());
		final ConstraintLayout cl1 = new ConstraintLayout(root);
		row1.setRect(cl1.axisV().grow(cl1.height().mul(2), Num.ZERO));
		cl1.add(row1);
		
		gl.put(cl1, pos, 0, 8, 1);
		pos += 8;
		
		row1.add(slots[0] = new InvSlot(0, slots));
		row1.add(slots[1] = new InvSlot(1, slots));
		row1.add(slots[2] = new InvSlot(2, slots));
		row1.add(slots[3] = new InvSlot(3, slots));
		
		
		final HorizontalFixedFlowLayout row2 = new HorizontalFixedFlowLayout(root, null, AlignX.LEFT);
		row2.setElementWidth(row2.height());
		final ConstraintLayout cl2 = new ConstraintLayout(root);
		row2.setRect(cl2.axisV().grow(cl2.height().mul(2), Num.ZERO));
		cl2.add(row2);
		gl.put(cl2, pos, 0, 8, 1);
		pos += 8;
		
		row2.add(slots[4] = new InvSlot(4, slots));
		row2.add(slots[5] = new InvSlot(5, slots));
		row2.add(slots[6] = new InvSlot(6, slots));
		row2.add(slots[7] = new InvSlot(7, slots));
		
		final TextPainter txp2 = new TextPainter(Res.getFont("thin"), AlignX.CENTER, RGB.WHITE, contextStrProv);
		gl.put(txp2, pos + 1, 0, 1, 1);
		
		setVisible(false);
		
		bindKey(new KeyStroke(Keys.ESCAPE), new Runnable() {
			
			@Override
			public void run()
			{
				if (!isVisible()) return;
				
				screen.setState(GScrState.WORLD);
			}
		});
		
		bindKey(new KeyStroke(Keys.E), new Runnable() {
			
			@Override
			public void run()
			{
				if (!isVisible()) return;
				
				final int selected = getSelectedSlot();
				if (selected != -1) {
					final PlayerFacade pl = WorldProvider.get().getPlayer();
					final Item itm = pl.getInventory().getItem(selected);
					if (itm != null && !itm.isEmpty()) {
						
						if (itm.getType() == ItemType.FOOD) {
							if (pl.eatFood(itm)) {
								if (itm.consume()) {
									pl.getInventory().setItem(selected, null);
								}
							}
						}
						
						if (itm.getType() == ItemType.WEAPON) {
							pl.selectWeapon(selected);
						}
					}
				}
			}
		});
		
		bindKey(new KeyStroke(Keys.D), new Runnable() {
			
			@Override
			public void run()
			{
				if (!isVisible()) return;
				
				final int selected = getSelectedSlot();
				if (selected != -1) {
					final PlayerFacade pl = WorldProvider.get().getPlayer();
					final Item itm = pl.getInventory().getItem(selected);
					if (itm != null && !itm.isEmpty()) {
						
						final Item piece = itm.split(1);
						if (itm.isEmpty()) pl.getInventory().setItem(selected, null);
						
						if (!pl.getLevel().getTile(pl.getCoord()).dropItem(piece)) {
							pl.getInventory().addItem(piece); // add back
						}
					}
				}
			}
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 200;
	}
	
}
