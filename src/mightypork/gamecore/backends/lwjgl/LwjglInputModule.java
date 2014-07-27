package mightypork.gamecore.backends.lwjgl;


import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.UserQuitRequest;
import mightypork.gamecore.input.InputModule;
import mightypork.gamecore.input.Keys;
import mightypork.gamecore.input.events.KeyEvent;
import mightypork.gamecore.input.events.MouseButtonEvent;
import mightypork.gamecore.input.events.MouseMotionEvent;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.constraints.vect.Vect;
import mightypork.utils.math.constraints.vect.var.VectVar;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * Input system
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class LwjglInputModule extends InputModule implements Updateable {
	
	/** Current mouse position */
	private static final Vect mousePos = new Vect() {
		
		@Override
		public double x()
		{
			if (!Mouse.isInsideWindow()) return Integer.MIN_VALUE;
			
			return Mouse.getX();
		}
		
		
		@Override
		public double y()
		{
			if (!Mouse.isInsideWindow()) return Integer.MIN_VALUE;
			// flip Y axis
			return Display.getHeight() - Mouse.getY();
		}
	};
	
	
	@Override
	public void destroy()
	{
		Mouse.destroy();
		Keyboard.destroy();
	}
	
	
	@Override
	protected void initDevices()
	{
		try {
			Mouse.create();
			Keyboard.create();
			Keyboard.enableRepeatEvents(false);
		} catch (final LWJGLException e) {
			throw new RuntimeException("Failed to initialize input devices.", e);
		}
	}
	
	private final VectVar mouseMove = Vect.makeVar();
	private final VectVar mouseLastPos = Vect.makeVar();
	
	
	@Override
	public synchronized void update(double delta)
	{
		// was destroyed or not initialized
		if (!Display.isCreated()) return;
		if (!Mouse.isCreated()) return;
		if (!Keyboard.isCreated()) return;
		
		Display.processMessages();
		
		// sum the moves
		mouseMove.reset();
		mouseLastPos.reset();
		boolean wasMouse = false;
		while (Mouse.next()) {
			onMouseEvent(mouseMove, mouseLastPos);
			wasMouse = true;
		}
		
		if (wasMouse && !mouseMove.isZero()) {
			App.bus().send(new MouseMotionEvent(mouseLastPos, mouseMove));
		}
		
		while (Keyboard.next()) {
			onKeyEvent();
		}
		
		if (Display.isCloseRequested()) {
			App.bus().send(new UserQuitRequest());
		}
	}
	
	
	private void onMouseEvent(VectVar moveSum, VectVar lastPos)
	{
		final int button = Mouse.getEventButton();
		final boolean down = Mouse.getEventButtonState();
		
		final VectVar pos = Vect.makeVar(Mouse.getEventX(), Mouse.getEventY());
		final VectVar move = Vect.makeVar(Mouse.getEventDX(), Mouse.getEventDY());
		
		final int wheeld = Mouse.getEventDWheel();
		
		// flip Y axis
		pos.setY(Display.getHeight() - pos.y());
		
		if (button != -1 || wheeld != 0) {
			App.bus().send(new MouseButtonEvent(pos.freeze(), button, down, wheeld));
		}
		
		moveSum.setTo(moveSum.add(move));
		lastPos.setTo(pos);
	}
	
	
	private void onKeyEvent()
	{
		final int key = Keyboard.getEventKey();
		final boolean down = Keyboard.getEventKeyState();
		final char c = Keyboard.getEventCharacter();
		
		App.bus().send(new KeyEvent(key, c, down));
	}
	
	
	@Override
	public Vect getMousePos()
	{
		return mousePos;
	}
	
	
	@Override
	public boolean isMouseInside()
	{
		return Mouse.isInsideWindow();
	}
	
	
	@Override
	public void grabMouse(boolean grab)
	{
		Mouse.setGrabbed(grab);
	}
	
	
	@Override
	public boolean isKeyDown(int key)
	{
		return Keyboard.isKeyDown(key);
	}
	
	
	@Override
	public boolean isMouseButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
	
	
	@Override
	public int getActiveModKeys()
	{
		int mods = 0;
		
		if (isKeyDown(Keys.L_ALT) || isKeyDown(Keys.R_ALT)) {
			mods |= Keys.MOD_ALT;
		}
		
		if (isKeyDown(Keys.L_SHIFT) || isKeyDown(Keys.R_SHIFT)) {
			mods |= Keys.MOD_SHIFT;
		}
		
		if (isKeyDown(Keys.L_CONTROL) || isKeyDown(Keys.R_CONTROL)) {
			mods |= Keys.MOD_CONTROL;
		}
		
		if (isKeyDown(Keys.L_META) || isKeyDown(Keys.R_META)) {
			mods |= Keys.MOD_META;
		}
		
		return mods;
	}
}
