package mightypork.rogue.world.entity.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.constraints.rect.Rect;
import mightypork.gamecore.util.math.constraints.vect.Vect;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.render.MapRenderContext;


/**
 * Renderer for a walking mob with only one strip (right sided), which is
 * flipped for walking left.
 * 
 * @author MightyPork
 */
public class EntityRendererMobLR extends EntityRenderer {
	
	private final TxSheet sheet;
	
	
	public EntityRendererMobLR(Entity entity, String sheetKey)
	{
		super(entity);
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(MapRenderContext context)
	{
		TxQuad q = sheet.getQuad(Calc.frag(entity.pos.getProgress()));
		
		if (entity.pos.lastXDir == -1) q = q.flipX();
		
		final Rect tileRect = context.getRectForTile(entity.pos.getCoord());
		final double w = tileRect.width().value();
		final Vect visualPos = entity.pos.getVisualPos();
		
		Rect spriteRect = Rect.make(visualPos.x() * w, visualPos.y() * w, w, w);
		spriteRect = spriteRect.shrink(w * 0.1);
		
		Render.quadTextured(spriteRect, q);
	}
}
