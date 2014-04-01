package mightypork.rogue.display.rendering;


import mightypork.rogue.bus.UpdateReceiver;
import mightypork.rogue.display.Screen;


public abstract class ScreenLayer extends UpdateReceiver implements Renderable {
	
	private Screen screen;

	public ScreenLayer(Screen screen) {
		super(screen);
		this.screen = screen;
	}
	
	
	@Override
	public abstract void render();
	
	
	@Override
	public abstract void update(double delta);
	
	protected Screen screen() {
		return screen;
	}
	
}
