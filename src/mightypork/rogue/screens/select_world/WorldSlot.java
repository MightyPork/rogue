package mightypork.rogue.screens.select_world;


import java.io.File;

import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.ConstraintLayout;
import mightypork.gamecore.gui.components.layout.GridLayout;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.events.LoadingOverlayRequest;
import mightypork.rogue.world.World;
import mightypork.rogue.world.WorldProvider;
import mightypork.utils.ion.Ion;
import mightypork.utils.ion.IonDataBundle;
import mightypork.utils.logging.Log;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.string.StringProvider;


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
	
	private IonDataBundle worldBundle;
	
	private TextButton loadBtn;
	
	private TextButton delBtn;
	
	
	public WorldSlot(File worldFile)
	{
		
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
		
		final GridLayout gridl = new GridLayout(1, 8);
		final Num shrinkH = width().perc(8);
		final Num shrinkV = height().perc(10);
		gridl.setRect(innerRect.shrink(shrinkH, shrinkH, shrinkV, shrinkV.half()));
		add(gridl);
		
		final IFont font = Res.font("thick");
		
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
				
				App.bus().send(new LoadingOverlayRequest(msg, new Runnable() {
					
					@Override
					public void run()
					{
						World w;
						
						if (worldBundle == null) {
							
							try {
								w = WorldProvider.get().createWorld(Double.doubleToLongBits(Math.random()));
								w.setSaveFile(file);
								
								WorldProvider.get().saveWorld();
								
								App.bus().send(new ScreenRequest("game"));
								
							} catch (final Exception t) {
								Log.e("Could not create & save the world.", t);
							}
							
						} else {
							
							try {
								w = new World();
								w.setSaveFile(file);
								w.load((IonDataBundle) worldBundle.get("world"));
								WorldProvider.get().setWorld(w);
								
								App.bus().send(new ScreenRequest("game"));
								
							} catch (final Exception e) {
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
				Log.f3("Trying to delete: " + file);
				if (!file.delete()) Log.w("Could not delete save file: " + file);
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
			
			delBtn.setVisible(true);
			delBtn.setEnabled(true);
			
			try {
				worldBundle = Ion.fromFile(file);
				final int lvl = worldBundle.get("level", -1);
				
				if (lvl == -1) throw new RuntimeException("Invalid save format."); // let the catch block handle it

				label = "Level " + (lvl + 1);
			} catch (final Exception e) {
				Log.w("Error loading world save.", e);
				label = "<corrupt>";
			}
		}
	}
	
}
