package mightypork.rogue.display.constraints;


public interface Renderable extends WithContext {
	
	public void render();
	
	
	@Override
	public void setContext(RenderContext context);
	
}
