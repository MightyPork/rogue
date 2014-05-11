package mightypork.rogue.screens.game;


import java.util.Collection;

import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.BaseComponent;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.resources.fonts.FontRenderer;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumVar;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.rogue.Res;
import mightypork.rogue.world.WorldConsole;
import mightypork.rogue.world.WorldConsole.Entry;
import mightypork.rogue.world.WorldProvider;


public class WorldConsoleRenderer extends BaseComponent {
	
	private final Num rowHeight;
	private final FontRenderer fr;
	
	
	public WorldConsoleRenderer(Num rowHeight) {
		this.rowHeight = rowHeight;
		this.fr = new FontRenderer(Res.getFont("tiny"));
	}
	
	
	@Override
	protected void renderComponent()
	{
		double rh = rowHeight.value();
		
		Rect lowRow = bottomEdge().growUp(rowHeight);
		
		Collection<Entry> entries = WorldProvider.get().getWorld().getConsole().getEntries();
		int cnt = 0;
		
		NumVar alph = Num.makeVar();
		
		Color.pushAlpha(alph);
		
		for (WorldConsole.Entry entry : entries) {
			
			alph.setTo(entry.getAlpha());
			
			Rect rrr = lowRow.moveY(-rh * cnt);
			
			fr.draw(entry.getMessage(), rrr.move(rh / 12, rh / 12), AlignX.LEFT, RGB.BLACK_60);
			fr.draw(entry.getMessage(), rrr, AlignX.LEFT, RGB.WHITE);
			
			cnt++;
		}
		
		Color.popAlpha();
	}
	
}
