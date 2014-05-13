package mightypork.rogue.screens.select_world;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.ClickableWrapper;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.events.CrossfadeRequest;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.Utils;
import mightypork.gamecore.util.ion.Ion;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.GameStateManager.GameState;
import mightypork.rogue.Res;
import mightypork.rogue.events.GameStateRequest;
import mightypork.rogue.screens.LoaderRequest;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;


public class WorldSlot extends ConstraintLayout {
	
	private final StringProvider lblStrp = new StringProvider() {
		
		@Override
		public String getString()
		{
			return label;
		}
	};
	
	private File file;
	private String label;
	
	private IonBundle worldBundle;
	
	
	public WorldSlot(AppAccess app, File worldFile) {
		super(app);
		
		this.file = worldFile;
		
		final Rect innerRect = shrink(height().perc(5));
		
		final QuadPainter qp = new QuadPainter(RGB.BLACK.withAlpha(new Num() {
			
			@Override
			public double value()
			{
				return isMouseOver() ? 0.2 : 0.1;
			}
		}));
		
		qp.setRect(innerRect);
		add(qp);
		
		final GridLayout gridl = new GridLayout(app, 1, 8);
		gridl.setRect(innerRect.shrink(width().perc(10), Num.ZERO));
		add(gridl);
		
		TextPainter tp;
		ClickableWrapper btn;
		final GLFont font = Res.getFont("thick");
		
		tp = new TextPainter(font, AlignX.LEFT, RGB.WHITE, lblStrp);
		tp.setPaddingHPerc(0, 20);
		
		gridl.put(btn = new ClickableWrapper(tp), 0, 0, 1, 7);
		btn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				String msg;
				
				if (worldBundle != null) {
					msg = "Loading world...";
				} else {
					msg = "Creating world...";
				}
				
				getEventBus().send(new LoaderRequest(true, msg));
				
				Utils.runAsThread(new Runnable() {
					
					@Override
					public void run()
					{
						try {
							final World w = new World();
							w.setSaveFile(file);
							w.load(worldBundle);
							WorldProvider.get().setWorld(w);
						} catch (final Exception e) {
							WorldProvider.get().createWorld(Double.doubleToLongBits(Math.random()));
						}
						
						getEventBus().send(new LoaderRequest(false));
						//getEventBus().send(new GameStateRequest(GameState.PLAY_WORLD));

						getEventBus().send(new ScreenRequest("game"));
					}
				});
				
			}
		});
		
		tp = new TextPainter(font, AlignX.LEFT, RGB.RED, "X");
		tp.setPaddingHPerc(0, 20);
		gridl.put(btn = new ClickableWrapper(tp), 0, 7, 1, 1);
		btn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				file.delete();
				refresh();
			}
		});
		
		refresh();
	}
	
	
	public void refresh()
	{
		if (!file.exists()) {
			label = "<empty>";
		} else {
			try {
				worldBundle = Ion.fromFile(file);
				final int lvl = worldBundle.get("meta.last_level", -1);
				
				if (lvl == -1) throw new RuntimeException(); // let the catch block handle it
					
				label = "Floor " + (lvl + 1);
			} catch (final IOException e) {
				label = "<corrupt>";
			}
		}
	}
	
}
