package mightypork.rogue.world.entity.render;


import mightypork.gamecore.render.Render;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.gamecore.util.math.Calc;
import mightypork.gamecore.util.math.Easing;
import mightypork.gamecore.util.math.color.Color;
import mightypork.gamecore.util.math.constraints.num.Num;
import mightypork.gamecore.util.math.constraints.num.mutable.NumVar;
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
	
	protected final Entity entity;
	
	private final NumVar animRedVar = Num.makeVar(0);
	
	private final Color hue = Color.rgb(Num.ONE, animRedVar, animRedVar);
	
	
	public EntityRendererMobLR(Entity entity, String sheetKey)
	{
		this.entity = entity;
		this.sheet = Res.txs(sheetKey);
	}
	
	
	@Override
	public void render(MapRenderContext context)
	{
		final double hurtTime = entity.health.getTimeSinceLastDamage();
		
		TxQuad q = sheet.getQuad(Calc.frag(entity.pos.getProgress()));
		
		if (entity.pos.lastXDir == -1) q = q.flipX();
		
		final Rect tileRect = context.getRectForTile(entity.pos.getCoord());
		final double w = tileRect.width().value();
		final Vect visualPos = entity.pos.getVisualPos();
		
		final double hurtOffset = (1 - Calc.clamp(hurtTime / 0.1, 0, 1)) * (entity.isDead() ? 0.3 : 0.05);
		
		
		Rect spriteRect = Rect.make(visualPos.x() * w, (visualPos.y() - hurtOffset) * w, w, w);
		spriteRect = spriteRect.shrink(w * 0.05);
		
		animRedVar.setTo(hurtTime / 0.3);
		
		Render.pushMatrix();
		
		Render.translate(spriteRect.center());
		
		if (entity.isDead()) {
			Render.rotateZ(Calc.clamp(hurtTime / 0.3, 0, 1) * 90);
		}
		
		final double hw = spriteRect.width().half().value();
		
		Render.quadTextured(
				Vect.ZERO.expand(hw, hw, hw, hw),
				q,
				hue.withAlpha(entity.isDead() ? 1 - Easing.CIRC_IN.get(hurtTime / entity.getDespawnDelay()) : 1));
		Render.popMatrix();
	}
}
