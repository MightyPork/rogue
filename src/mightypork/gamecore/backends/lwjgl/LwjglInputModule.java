package mightypork.gamecore.backends.lwjgl;


import mightypork.gamecore.core.App;
import mightypork.gamecore.input.InputModule;
import mightypork.gamecore.input.Key;
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
 * Lwjgl Input Module.
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
	
	
	@Override
	protected void initKeyCodes()
	{
		Keys.NONE.setCode(Keyboard.KEY_NONE);
		
		Keys.NUM_1.setCode(Keyboard.KEY_1);
		Keys.NUM_2.setCode(Keyboard.KEY_2);
		Keys.NUM_3.setCode(Keyboard.KEY_3);
		Keys.NUM_4.setCode(Keyboard.KEY_4);
		Keys.NUM_5.setCode(Keyboard.KEY_5);
		Keys.NUM_6.setCode(Keyboard.KEY_6);
		Keys.NUM_7.setCode(Keyboard.KEY_7);
		Keys.NUM_8.setCode(Keyboard.KEY_8);
		Keys.NUM_9.setCode(Keyboard.KEY_9);
		Keys.NUM_0.setCode(Keyboard.KEY_0);
		
		Keys.Q.setCode(Keyboard.KEY_Q);
		Keys.W.setCode(Keyboard.KEY_W);
		Keys.E.setCode(Keyboard.KEY_E);
		Keys.R.setCode(Keyboard.KEY_R);
		Keys.T.setCode(Keyboard.KEY_T);
		Keys.Y.setCode(Keyboard.KEY_Y);
		Keys.U.setCode(Keyboard.KEY_U);
		Keys.I.setCode(Keyboard.KEY_I);
		Keys.O.setCode(Keyboard.KEY_O);
		Keys.P.setCode(Keyboard.KEY_P);
		Keys.A.setCode(Keyboard.KEY_A);
		Keys.S.setCode(Keyboard.KEY_S);
		Keys.D.setCode(Keyboard.KEY_D);
		Keys.F.setCode(Keyboard.KEY_F);
		Keys.G.setCode(Keyboard.KEY_G);
		Keys.H.setCode(Keyboard.KEY_H);
		Keys.J.setCode(Keyboard.KEY_J);
		Keys.K.setCode(Keyboard.KEY_K);
		Keys.L.setCode(Keyboard.KEY_L);
		Keys.Z.setCode(Keyboard.KEY_Z);
		Keys.X.setCode(Keyboard.KEY_X);
		Keys.C.setCode(Keyboard.KEY_C);
		Keys.V.setCode(Keyboard.KEY_V);
		Keys.B.setCode(Keyboard.KEY_B);
		Keys.N.setCode(Keyboard.KEY_N);
		Keys.M.setCode(Keyboard.KEY_M);
		
		Keys.MINUS.setCode(Keyboard.KEY_MINUS);
		Keys.EQUALS.setCode(Keyboard.KEY_EQUALS);
		Keys.SLASH.setCode(Keyboard.KEY_SLASH);
		Keys.BACKSLASH.setCode(Keyboard.KEY_BACKSLASH);
		Keys.BRACKET_LEFT.setCode(Keyboard.KEY_LBRACKET);
		Keys.BRACKET_RIGHT.setCode(Keyboard.KEY_RBRACKET);
		Keys.SEMICOLON.setCode(Keyboard.KEY_SEMICOLON);
		Keys.APOSTROPHE.setCode(Keyboard.KEY_APOSTROPHE);
		Keys.GRAVE.setCode(Keyboard.KEY_GRAVE);
		Keys.COMMA.setCode(Keyboard.KEY_COMMA);
		Keys.PERIOD.setCode(Keyboard.KEY_PERIOD);
		
		Keys.SPACE.setCode(Keyboard.KEY_SPACE);
		Keys.BACKSPACE.setCode(Keyboard.KEY_BACK);
		Keys.TAB.setCode(Keyboard.KEY_TAB);
		Keys.ESCAPE.setCode(Keyboard.KEY_ESCAPE);
		
		Keys.APPS.setCode(Keyboard.KEY_APPS);
		Keys.POWER.setCode(Keyboard.KEY_POWER);
		Keys.SLEEP.setCode(Keyboard.KEY_SLEEP);
		//Keys.MENU.setCode(Keyboard.KEY_MENU); // not defined
		
		Keys.F1.setCode(Keyboard.KEY_F1);
		Keys.F2.setCode(Keyboard.KEY_F2);
		Keys.F3.setCode(Keyboard.KEY_F3);
		Keys.F4.setCode(Keyboard.KEY_F4);
		Keys.F5.setCode(Keyboard.KEY_F5);
		Keys.F6.setCode(Keyboard.KEY_F6);
		Keys.F7.setCode(Keyboard.KEY_F7);
		Keys.F8.setCode(Keyboard.KEY_F8);
		Keys.F9.setCode(Keyboard.KEY_F9);
		Keys.F10.setCode(Keyboard.KEY_F10);
		Keys.F11.setCode(Keyboard.KEY_F11);
		Keys.F12.setCode(Keyboard.KEY_F12);
		Keys.F13.setCode(Keyboard.KEY_F13);
		Keys.F14.setCode(Keyboard.KEY_F14);
		Keys.F15.setCode(Keyboard.KEY_F15);
		
		Keys.CAPS_LOCK.setCode(Keyboard.KEY_CAPITAL);
		Keys.SCROLL_LOCK.setCode(Keyboard.KEY_SCROLL);
		Keys.NUM_LOCK.setCode(Keyboard.KEY_NUMLOCK);
		
		Keys.NUMPAD_MINUS.setCode(Keyboard.KEY_SUBTRACT);
		Keys.NUMPAD_PLUSS.setCode(Keyboard.KEY_ADD);
		Keys.NUMPAD_0.setCode(Keyboard.KEY_NUMPAD0);
		Keys.NUMPAD_1.setCode(Keyboard.KEY_NUMPAD1);
		Keys.NUMPAD_2.setCode(Keyboard.KEY_NUMPAD2);
		Keys.NUMPAD_3.setCode(Keyboard.KEY_NUMPAD3);
		Keys.NUMPAD_4.setCode(Keyboard.KEY_NUMPAD4);
		Keys.NUMPAD_5.setCode(Keyboard.KEY_NUMPAD5);
		Keys.NUMPAD_6.setCode(Keyboard.KEY_NUMPAD6);
		Keys.NUMPAD_7.setCode(Keyboard.KEY_NUMPAD7);
		Keys.NUMPAD_8.setCode(Keyboard.KEY_NUMPAD8);
		Keys.NUMPAD_9.setCode(Keyboard.KEY_NUMPAD9);
		Keys.NUMPAD_DECIMAL.setCode(Keyboard.KEY_DECIMAL);
		Keys.NUMPAD_ENTER.setCode(Keyboard.KEY_NUMPADENTER);
		Keys.NUMPAD_DIVIDE.setCode(Keyboard.KEY_DIVIDE);
		Keys.NUMPAD_MULTIPLY.setCode(Keyboard.KEY_MULTIPLY);
		
		Keys.CONTROL_LEFT.setCode(Keyboard.KEY_LCONTROL);
		Keys.CONTROL_RIGHT.setCode(Keyboard.KEY_RCONTROL);
		Keys.ALT_LEFT.setCode(Keyboard.KEY_LMENU);
		Keys.ALT_RIGHT.setCode(Keyboard.KEY_RMENU);
		Keys.SHIFT_LEFT.setCode(Keyboard.KEY_LSHIFT);
		Keys.SHIFT_RIGHT.setCode(Keyboard.KEY_RSHIFT);
		Keys.META_LEFT.setCode(Keyboard.KEY_LMETA);
		Keys.META_RIGHT.setCode(Keyboard.KEY_RMETA);
		
		Keys.UP.setCode(Keyboard.KEY_UP);
		Keys.DOWN.setCode(Keyboard.KEY_DOWN);
		Keys.LEFT.setCode(Keyboard.KEY_LEFT);
		Keys.RIGHT.setCode(Keyboard.KEY_RIGHT);
		
		Keys.HOME.setCode(Keyboard.KEY_HOME);
		Keys.END.setCode(Keyboard.KEY_END);
		
		Keys.PAGE_UP.setCode(Keyboard.KEY_PRIOR);
		Keys.PAGE_DOWN.setCode(Keyboard.KEY_NEXT);
		
		Keys.RETURN.setCode(Keyboard.KEY_RETURN);
		Keys.PAUSE.setCode(Keyboard.KEY_PAUSE);
		Keys.INSERT.setCode(Keyboard.KEY_INSERT);
		Keys.DELETE.setCode(Keyboard.KEY_DELETE);
		Keys.SYSRQ.setCode(Keyboard.KEY_SYSRQ);
	}
	
	
	@Override
	public void destroy()
	{
		Mouse.destroy();
		Keyboard.destroy();
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
			App.shutdown();
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
	public boolean isKeyDown(Key key)
	{
		return key.isDefined() && Keyboard.isKeyDown(key.getCode());
	}
	
	
	@Override
	public boolean isMouseButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
}
