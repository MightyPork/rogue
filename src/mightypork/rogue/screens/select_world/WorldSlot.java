package mightypork.rogue.screens.select_world;


import java.io.File;
import java.io.IOException;

import mightypork.gamecore.app.AppAccess;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.logging.Log;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.fonts.GLFont;
import mightypork.gamecore.util.ion.Ion;
import mightypork.gamecore.util.ion.IonBundle;
import mightypork.gamecore.util.math.color.pal.RGB;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.strings.StringProvider;
import mightypork.rogue.events.LoadingOverlayRequest;
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
	
	private TextButton loadBtn;
	
	private TextButton delBtn;
	
	
	public WorldSlot(AppAccess app, File worldFile)
	{
		super(app);
		
		this.file = worldFile;
		
		final Rect innerRect = shrink(height().perc(5));
		
		final QuadPainter qp = new QuadPainter(RGB.BLACK.withAlpha(new Num() {
			
			@Override
			public double value()
			{
				return isMouseOver() ? 0.15 : 0.1;
			}
		}));
		
		qp.setRect(innerRect);
		add(qp);
		
		final GridLayout gridl = new GridLayout(app, 1, 8);
		final Num shrinkH = width().perc(8);
		final Num shrinkV = height().perc(10);
		gridl.setRect(innerRect.shrink(shrinkH, shrinkH, shrinkV, shrinkV.half()));
		add(gridl);
		
		final GLFont font = Res.getFont("thick");
		
		gridl.put(loadBtn = new TextButton(font, "", RGB.WHITE), 0, 0, 1, 7);
		loadBtn.textPainter.setVPaddingPercent(20);
		loadBtn.textPainter.setAlign(AlignX.LEFT);
		loadBtn.textPainter.setText(lblStrp);
		loadBtn.disableHoverEffect();
		
		loadBtn.setAction(new Action() {
			
			@Override
			protected void execute()
			{
				String msg;
				
				if (worldBundle != null) {
					msg = "Loading world...";
				} else {
					msg = "Creating world...";
				}
				
				getEventBus().send(new LoadingOverlayRequest(msg, new Runnable() {
					
					@Override
					public void run()
					{
						World w;
						
						if (worldBundle == null) {
							
							try {
								w = WorldProvider.get().createWorld(Double.doubleToLongBits(Math.random()));
								w.setSaveFile(file);
								
								WorldProvider.get().saveWorld();
								
								getEventBus().send(new ScreenRequest("game"));
								
							} catch (final Exception e) {
								Log.e("Could not create & save the world.", e);
							}
							
						} else {
							
							try {
								w = new World();
								w.setSaveFile(file);
								w.load(worldBundle);
								WorldProvider.get().setWorld(w);
								
								getEventBus().send(new ScreenRequest("game"));
								
							} catch (final IOException e) {
								Log.e("Could not load the world.", e);
							}
						}
						
					}
				}));
				
			}
		});
		
		gridl.put(delBtn = new TextButton(font, "X", RGB.RED), 0, 7, 1, 1);
		delBtn.textPainter.setVPaddingPercent(20);
		delBtn.textPainter.setAlign(AlignX.RIGHT);
		delBtn.disableHoverEffect();
		
		delBtn.setAction(new Action() {
			
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
		delBtn.setVisible(false);
		delBtn.setEnabled(false);
		
		if (!file.exists()) {
			label = "<empty>";
			worldBundle = null;
		} else {
			try {
				worldBundle = Ion.fromFile(file);
				final int lvl = worldBundle.get("meta.last_level", -1);
				
				if (lvl == -1) throw new RuntimeException(); // let the catch block handle it
				
				label = "Level " + (lvl + 1);
				delBtn.setVisible(true);
				delBtn.setEnabled(true);
				
			} catch (final Exception e) {
				Log.w("Error loading world save.", e);
				label = "<corrupt>";
			}
		}
	}
	
}
