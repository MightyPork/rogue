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
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.Res;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.World;
import mightypork.rogue.world.World.PlayerFacade;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;


public class InvLayer extends ScreenLayer {
	
	private static final int SLOT_COUNT = 8;
	private static final int SLOT_ROW = 4;
	
	private final StringProvider contextStrProv = new StringProvider() {
		
		@Override
		public String getString()
		{
			String s = "ESC-close";
			
			final int selected = getSelectedSlot();
			if (selected != -1) {
				
				final PlayerFacade pl = WorldProvider.get().getPlayer();
				
				final Item itm = pl.getInventory().getItem(selected);
				if (itm != null && !itm.isEmpty()) {
					s = "D-drop," + s;
					
					if (itm.getType() == ItemType.FOOD) {
						s = "E-eat," + s;
					}
					
					if (itm.getType() == ItemType.WEAPON) {
						s = "E-equip," + s;
					}
				}
			} else {
				s = "Click-select," + s;
			}
			
			return s;
		}
	};
	
	private final InvSlot[] slots = new InvSlot[SLOT_COUNT];
	
	
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
	
	
	private void selectSlot(int i)
	{
		for (final InvSlot sl : slots) {
			sl.selected = false;
		}
		
		if (i >= 0 && i < SLOT_COUNT) {
			slots[i].selected = true;
		}
	}
	
	
	public InvLayer(final ScreenGame screen) {
		super(screen);
		
		final Rect fg = root.shrink(root.height().perc(15));
		
		final QuadPainter qp = new QuadPainter(RGB.BLACK_30, RGB.BLACK_30, RGB.BLACK_80, RGB.BLACK_80);
		qp.setRect(root);
		root.add(qp);
		
		int pos = 0;
		
		final GridLayout gl = new GridLayout(root, fg, 10, 1);
		root.add(gl);
		
		final TextPainter txp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.YELLOW, "Inventory");
		gl.put(txp, pos, 0, 1, 1);
		txp.setPaddingHPerc(0, 5);
		pos += 1;
		
		final HorizontalFixedFlowLayout row1 = new HorizontalFixedFlowLayout(root, null, AlignX.LEFT);
		row1.setElementWidth(row1.height());
		final ConstraintLayout cl1 = new ConstraintLayout(root);
		row1.setRect(cl1.axisV().grow(cl1.height().mul(2), Num.ZERO));
		cl1.add(row1);
		
		gl.put(cl1, pos, 0, 4, 1);
		pos += 4;
		
		row1.add(slots[0] = new InvSlot(0, slots));
		row1.add(slots[1] = new InvSlot(1, slots));
		row1.add(slots[2] = new InvSlot(2, slots));
		row1.add(slots[3] = new InvSlot(3, slots));
		
		final HorizontalFixedFlowLayout row2 = new HorizontalFixedFlowLayout(root, null, AlignX.LEFT);
		row2.setElementWidth(row2.height());
		final ConstraintLayout cl2 = new ConstraintLayout(root);
		row2.setRect(cl2.axisV().grow(cl2.height().mul(2), Num.ZERO));
		cl2.add(row2);
		gl.put(cl2, pos, 0, 4, 1);
		pos += 4;
		
		row2.add(slots[4] = new InvSlot(4, slots));
		row2.add(slots[5] = new InvSlot(5, slots));
		row2.add(slots[6] = new InvSlot(6, slots));
		row2.add(slots[7] = new InvSlot(7, slots));
		
		final TextPainter txp2 = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.WHITE, contextStrProv);
		gl.put(txp2, pos, 0, 1, 1);
		txp2.setPaddingHPerc(0, 25);
		
		final Runnable leaveInv = new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				screen.setState(GScrState.WORLD);
			}
		};
		
		bindKey(new KeyStroke(Keys.ESCAPE), leaveInv);
		//bindKey(new KeyStroke(Keys.I), leaveInv);
		// TODO needs some rewrite of keys system
		
		bindKey(new KeyStroke(Keys.E), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				if (WorldProvider.get().getPlayer().isDead()) return;
				
				final int selected = getSelectedSlot();
				if (selected != -1) {
					
					final World world = WorldProvider.get().getWorld();
					final PlayerFacade pl = WorldProvider.get().getPlayer();
					final Item itm = pl.getInventory().getItem(selected);
					
					if (itm != null && !itm.isEmpty()) {
						
						final ItemType type = itm.getType();
						
						if (type == ItemType.FOOD) {
							
							if (pl.eatFood(itm)) {
								pl.getInventory().clean();
							}
							
						} else if (type == ItemType.WEAPON) {
							
							if (pl.getSelectedWeaponIndex() == selected) {
								pl.selectWeapon(-1);
							} else {
								pl.selectWeapon(selected);
							}
							world.msgEquipWeapon(pl.getSelectedWeapon());
							
						}
					}
				}
			}
		});
		
		bindKey(new KeyStroke(Keys.D), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
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
		
		setupGridWalkKeys();
	}
	
	
	private void setupGridWalkKeys()
	{
		
		bindKey(new KeyStroke(Keys.LEFT), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel - 1)) % SLOT_COUNT);
			};
		});
		
		bindKey(new KeyStroke(Keys.RIGHT), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel + 1)) % SLOT_COUNT);
			};
		});
		
		bindKey(new KeyStroke(Keys.UP), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel - SLOT_ROW)) % SLOT_COUNT);
			};
		});
		
		bindKey(new KeyStroke(Keys.DOWN), new Runnable() {
			
			@Override
			public void run()
			{
				if(!isEnabled()) return;
				
				int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((sel + SLOT_ROW) % SLOT_COUNT);
			};
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 200;
	}
	
}
