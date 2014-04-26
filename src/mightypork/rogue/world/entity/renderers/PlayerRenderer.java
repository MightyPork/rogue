package mightypork.rogue.world.entity.renderers;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.render.textures.TxQuad;
import mightypork.gamecore.render.textures.TxSheet;
import mightypork.rogue.Res;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityPos;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.util.math.Calc;
import mightypork.util.math.constraints.rect.Rect;


public class PlayerRenderer extends EntityRenderer {
	
	TxSheet sheet;
	
	
	public PlayerRenderer(String sheetKey)
	{
		this.sheet = Res.getTxSheet(sheetKey);
	}
	
	
	@Override
	public void render(Entity entity, MapRenderContext context)
	{
		TxQuad q = sheet.getQuad(Calc.frag(entity.getPosition().getProgress()));
		
		if (entity.renderData.lastXDir == -1) q = q.flipX();
		
		final EntityPos pos = entity.getPosition();
		
		final Rect tileRect = context.getRectForTile(pos.getCoord());
		final double w = tileRect.width().value();
		
		Rect spriteRect = tileRect.move(pos.visualXOffset() * w, pos.visualYOffset() * w);
		spriteRect = spriteRect.shrink(w * 0.1);
		
		Render.quadTextured(spriteRect, q);
	}
}
