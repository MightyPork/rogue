package mightypork.rogue.world.entity.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.util.math.Calc;
import mightypork.util.math.constraints.rect.Rect;
import mightypork.util.math.constraints.vect.Vect;


public class SimpleLeftRightMobRenderer extends EntityRenderer {
	
	private final TxSheet sheet;
	
	
	public SimpleLeftRightMobRenderer(Entity entity, String sheetKey)
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
