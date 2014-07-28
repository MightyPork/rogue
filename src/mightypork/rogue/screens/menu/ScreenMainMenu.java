package mightypork.rogue.screens.menu;


import mightypork.gamecore.core.App;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.components.input.TextButton;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.input.Trigger;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;
import mightypork.utils.math.AlignX;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.PAL16;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;


/**
 * Main menu screen
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class ScreenMainMenu extends RogueScreen {

	/**
	 * The layer
	 *
	 * @author Ondřej Hruška (MightyPork)
	 */
	class MenuLayer extends ScreenLayer {

		public MenuLayer(Screen screen)
		{
			super(screen);

			init();
		}


		private void init()
		{
			final Rect menuBox = root.shrink(Num.ZERO, root.height().perc(15)).moveY(root.height().perc(-4));

			final QuadPainter bg = QuadPainter.gradV(Color.fromHex(0x007eb3), PAL16.SEABLUE);
			bg.setRect(root);
			root.add(bg);

			final RowLayout rows = new RowLayout(menuBox, 13);
			rows.enableCaching(true);
			root.add(rows);

			final LinearLayout linlayout = new LinearLayout(root, AlignX.CENTER);
			linlayout.add(new ImagePainter(Res.txQuad("logo")));
			rows.add(linlayout, 4);
			rows.skip(1);

			TextButton btn;

			final IFont btnFont = Res.font("thick");

			// world button
			btn = new TextButton(btnFont, "Play", PAL16.SLIMEGREEN);
			btn.setAction(new Action() {

				@Override
				protected void execute()
				{
					App.bus().send(new RogueStateRequest(RogueState.SELECT_WORLD));
				}
			});
			rows.add(btn, 2);
			rows.skip(1);

			btn = new TextButton(btnFont, "Story", PAL16.CLOUDBLUE);
			btn.setAction(new Action() {

				@Override
				protected void execute()
				{
					App.bus().send(new RogueStateRequest(RogueState.STORY));
				}
			});
			rows.add(btn, 2);
			rows.skip(1);

			// quit button
			btn = new TextButton(btnFont, "Exit", PAL16.BLOODRED);
			btn.setAction(new Action() {

				@Override
				protected void execute()
				{
					App.shutdown();
				}
			});
			rows.add(btn, 2);

			bindKey(App.cfg().getKeyStroke("general.close"), Trigger.RISING, new Runnable() {

				@Override
				public void run()
				{
					App.shutdown();
				}
			});
		}


		@Override
		public int getZIndex()
		{
			return 2;
		}

	}


	public ScreenMainMenu()
	{
		addLayer(new MenuLayer(this));
	}


	@Override
	protected void onScreenEnter()
	{
		super.onScreenEnter();

		App.sound().fadeOutAllLoops();
		Res.loop("music.menu").fadeIn();
	}
}
