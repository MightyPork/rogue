package mightypork.rogue.screens.game;


import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.components.layout.FlowColumnLayout;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.impl.FadingLayer;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Trigger;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.screens.game.ScreenGame.GScrState;
import mightypork.rogue.world.PlayerFacade;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;
import mightypork.rogue.world.item.Item;
import mightypork.rogue.world.item.ItemType;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.string.StringProvider;


public class LayerInv extends FadingLayer {
	
	private static final int SLOT_COUNT = 8;
	private static final int SLOT_ROW = 4;
	
	private final KeyStroke keyUse = Config.getKeyStroke("game.inv.use");
	private final KeyStroke keyDrop = Config.getKeyStroke("game.inv.drop");
	private final KeyStroke keyClose = Config.getKeyStroke("general.close");
	
	private final StringProvider contextStrProv = new StringProvider() {
		
		@Override
		public String getString()
		{
			String s = keyClose + "-close";
			
			final int selected = getSelectedSlot();
			if (selected != -1) {
				
				final PlayerFacade pl = WorldProvider.get().getPlayer();
				
				final Item itm = pl.getInventory().getItem(selected);
				if (itm != null && !itm.isEmpty()) {
					s = keyDrop + "-drop," + s;
					
					if (itm.getType() == ItemType.FOOD) {
						s = keyUse + "-eat," + s;
					}
					
					if (itm.getType() == ItemType.WEAPON) {
						s = keyUse + "-equip," + s;
					}
				}
			} else {
				s = "Click-select," + s;
			}
			
			return s;
		}
	};
	
	private final InvSlot[] slots = new InvSlot[SLOT_COUNT];
	private ScreenGame gscreen;
	
	
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
	
	
	@Override
	protected void onHideFinished()
	{
		if (gscreen.getState() == GScrState.INV) {
			gscreen.actionToggleInv.run();
		}
	}
	
	
	public LayerInv(final ScreenGame screen) {
		super(screen);
		this.gscreen = screen;
		
		final Rect fg = root.shrink(root.height().perc(15));
		
		// darker down to cover console.
		final QuadPainter qp = new QuadPainter(RGB.BLACK_30, RGB.BLACK_30, RGB.BLACK_90, RGB.BLACK_90);
		
		qp.setRect(root);
		root.add(qp);
		
		int pos = 0;
		
		final GridLayout gl = new GridLayout(root, fg, 10, 1);
		root.add(gl);
		
		final TextPainter txp = new TextPainter(Res.getFont("thick"), AlignX.CENTER, RGB.YELLOW, "Inventory");
		gl.put(txp, pos, 0, 1, 1);
		txp.setVPaddingPercent(5);
		pos += 1;
		
		final FlowColumnLayout row1 = new FlowColumnLayout(root, null, AlignX.LEFT);
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
		
		final FlowColumnLayout row2 = new FlowColumnLayout(root, null, AlignX.LEFT);
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
		txp2.setVPaddingPercent(25);
		
		bindKey(keyClose, Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				hide();
			}
		});
		
		bindKey(keyUse, Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				if (WorldProvider.get().getPlayer().isDead()) return;
				
				useSelectedItem();
			}
		});
		
		bindKey(keyDrop, Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				final int selected = getSelectedSlot();
				if (selected != -1) {
					WorldProvider.get().getPlayer().dropItem(selected);
				}
			}
		});
		
		setupGridWalkKeys();
	}
	
	
	protected void useSelectedItem()
	{
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
					world.getConsole().msgEquipWeapon(pl.getSelectedWeapon());
					
				}
			}
		}
	}
	
	
	private void setupGridWalkKeys()
	{
		
		bindKey(Config.getKeyStroke("game.inv.move.left"), Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				final int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel - 1)) % SLOT_COUNT);
			}
		});
		
		bindKey(Config.getKeyStroke("game.inv.move.right"), Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				final int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel + 1)) % SLOT_COUNT);
			}
		});
		
		bindKey(Config.getKeyStroke("game.inv.move.up"), Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				final int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((SLOT_COUNT + (sel - SLOT_ROW)) % SLOT_COUNT);
			}
		});
		
		bindKey(Config.getKeyStroke("game.inv.move.down"), Trigger.RISING, new Runnable() {
			
			@Override
			public void run()
			{
				if (!isEnabled()) return;
				
				final int sel = getSelectedSlot();
				if (sel == -1) {
					selectSlot(0);
					return;
				}
				
				selectSlot((sel + SLOT_ROW) % SLOT_COUNT);
			}
		});
	}
	
	
	@Override
	public int getZIndex()
	{
		return 300;
	}
	
}
