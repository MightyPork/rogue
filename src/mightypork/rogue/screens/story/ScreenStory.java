package mightypork.rogue.screens.story;


import mightypork.gamecore.config.Config;
import mightypork.gamecore.gui.Action;
import mightypork.gamecore.gui.AlignX;
import mightypork.gamecore.gui.components.layout.RowLayout;
import mightypork.gamecore.gui.components.layout.linear.LinearLayout;
import mightypork.gamecore.gui.components.painters.ImagePainter;
import mightypork.gamecore.gui.components.painters.TextPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.gamecore.gui.screens.impl.LayerColor;
import mightypork.gamecore.input.Edge;
import mightypork.gamecore.input.KeyStroke;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseButtonHandler;
import mightypork.gamecore.resources.Res;
import mightypork.rogue.RogueStateManager.RogueState;
import mightypork.rogue.events.RogueStateRequest;
import mightypork.rogue.screens.RogueScreen;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.animation.NumAnimated;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.timing.TimedTask;


public class ScreenStory extends RogueScreen implements MouseButtonHandler {
	
	private class LayerSlide extends ScreenLayer {
		
		private final TextPainter tp1;
		private final TextPainter tp2;
		private final ImagePainter img;
		private final NumAnimated layerAlpha = new NumAnimated(0, Easing.QUARTIC_OUT, 0.6);
		private final NumAnimated tx1alpha = new NumAnimated(0, Easing.BOUNCE_OUT, 1);
		private final NumAnimated tx2alpha = new NumAnimated(0, Easing.BOUNCE_OUT, 1);
		private final NumAnimated txProceedAlpha = new NumAnimated(0, Easing.CIRC_OUT, 1);
		
		private String nextImg, nextT1, nextT2;
		
		private final TimedTask ttNextSlide = new TimedTask() {
			
			@Override
			public void run()
			{
				img.setTxQuad(Res.getTxQuad(nextImg));
				if (nextT1 != null) tp1.setText(nextT1);
				if (nextT2 != null) tp2.setText(nextT2);
				
				tx1alpha.setTo(0);
				tx2alpha.setTo(0);
				txProceedAlpha.setTo(0);
				
				layerAlpha.setTo(0);
				layerAlpha.fadeIn();
				ttText1.start(1.5);
			}
		};
		
		private final TimedTask ttText1 = new TimedTask() {
			
			@Override
			public void run()
			{
				if (nextT1 == null) {
					ttText2.run();
				} else {
					tx1alpha.fadeIn();
					ttText2.start(2);
				}
			}
		};
		
		private final TimedTask ttText2 = new TimedTask() {
			
			@Override
			public void run()
			{
				if (nextT2 == null) {
					ttFinish.run();
				} else {
					tx2alpha.fadeIn();
					ttFinish.start(1.2);
				}
			}
		};
		
		private final TimedTask ttFinish = new TimedTask() {
			
			@Override
			public void run()
			{
				txProceedAlpha.fadeIn();
			}
		};
		
		private final Color textColor = Color.fromHex(0x7ad8ff);
		
		
		public LayerSlide(Screen screen) {
			super(screen);
			
			final TextPainter help = new TextPainter(Res.getFont("tiny"), AlignX.CENTER, RGB.WHITE.withAlpha(txProceedAlpha.mul(0.3)), "Space / click to proceed.");
			help.setRect(root.bottomEdge().growUp(root.height().perc(4)));
			help.setVPaddingPercent(5);
			root.add(help);
			
			final Rect contentRect = root.shrink(Num.ZERO, Num.ZERO, root.height().perc(2), root.height().perc(6));
			final RowLayout rl = new RowLayout(root, 9);
			rl.setRect(contentRect);
			root.add(rl);
			
			final LinearLayout ll = new LinearLayout(root, AlignX.CENTER);
			rl.add(ll, 7);
			img = new ImagePainter(Res.getTxQuad("story_1"));
			ll.add(img);
			
			tp1 = new TextPainter(Res.getFont("tiny"), AlignX.CENTER, textColor.withAlpha(tx1alpha), "");
			rl.add(tp1);
			tp1.setVPaddingPercent(19);
			
			tp2 = new TextPainter(Res.getFont("tiny"), AlignX.CENTER, textColor.withAlpha(tx2alpha), "");
			rl.add(tp2);
			tp2.setVPaddingPercent(19);
			
			updated.add(layerAlpha);
			updated.add(txProceedAlpha);
			updated.add(tx1alpha);
			updated.add(tx2alpha);
			
			updated.add(ttText1);
			updated.add(ttText2);
			updated.add(ttFinish);
			updated.add(ttNextSlide);
			
			setAlpha(layerAlpha);
		}
		
		
		@Override
		public int getZIndex()
		{
			return 1;
		}
		
		
		public void showSlide(String image, String text1, String text2)
		{
			ttFinish.stop();
			ttNextSlide.stop();
			ttText1.stop();
			ttText2.stop();
			
			this.nextImg = image;
			this.nextT1 = text1;
			this.nextT2 = text2;
			
			layerAlpha.fadeOut();
			ttNextSlide.start(1);
		}
		
		
		public void reset()
		{
			ttFinish.stop();
			ttNextSlide.stop();
			ttText1.stop();
			ttText2.stop();
			
			layerAlpha.setTo(0);
		}
	}
	
	private LayerSlide slideLayer;
	
	private final Action next = new Action() {
		
		@Override
		protected void execute()
		{
			showSlide(++slide);
		}
	};
	
	private final Action prev = new Action() {
		
		@Override
		protected void execute()
		{
			if (slide > 0) slide--;
			showSlide(slide);
		}
	};
	
	private final Action close = new Action() {
		
		@Override
		protected void execute()
		{
			getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
		}
	};
	
	
	public ScreenStory(AppAccess app) {
		super(app);
		
		addLayer(new LayerColor(this, Color.fromHex(0x040c1e), 0));
		addLayer(slideLayer = new LayerSlide(this));
		
		bindKey(new KeyStroke(Keys.SPACE), Edge.RISING, next);
		bindKey(new KeyStroke(Keys.RIGHT), Edge.RISING, next);
		bindKey(new KeyStroke(Keys.BACKSPACE), Edge.RISING, prev);
		bindKey(new KeyStroke(Keys.LEFT), Edge.RISING, prev);
		bindKey(Config.getKeyStroke("general.close"), Edge.RISING, close);
	}
	
	private int slide = 0;
	
	
	@Override
	protected void onScreenEnter()
	{
		slide = 0;
		slideLayer.reset();
		showSlide(slide);
	}
	
	
	private void showSlide(int slide)
	{
		switch (slide) {
			case 0:
				slideLayer.showSlide("story_1", "Man, it's so hot today!", "Makes me real thirsty, ya know.");
				break;
			
			case 1:
				slideLayer.showSlide("story_2", "'Guess I'll go get some beer", "from the cellar.");
				break;
			
			case 2:
				slideLayer.showSlide("story_3", "Here we go.. HEY GIVE IT BACK!", "I'll hunt you down, thieves!");
				break;
			
			case 3:
				getEventBus().send(new RogueStateRequest(RogueState.MAIN_MENU));
		}
	}
	
	
	@Override
	public void receive(MouseButtonEvent event)
	{
		if (event.isButtonEvent() && event.isUp()) {
			if (event.getButton() == 0) next.run();
			if (event.getButton() == 1) prev.run();
		}
	}
}
