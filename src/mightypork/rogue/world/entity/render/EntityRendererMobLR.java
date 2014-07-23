package mightypork.rogue.world.entity.render;


import mightypork.gamecore.core.modules.App;
import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.textures.TxQuad;
import mightypork.gamecore.resources.textures.TxSheet;
import mightypork.rogue.world.entity.Entity;
import mightypork.rogue.world.entity.EntityRenderer;
import mightypork.rogue.world.level.render.MapRenderContext;
import mightypork.utils.math.Calc;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.color.Color;
import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.var.NumVar;
import mightypork.utils.math.constraints.rect.Rect;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Renderer for a walking mob with only one strip (right sided), which is
 * flipped for walking left.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public class EntityRendererMobLR extends EntityRenderer {
	
	private final TxSheet sheet;
	
	protected final Entity entity;
	
	private final NumVar animRedVar = Num.makeVar(0);
	
	private final Color hue = Color.rgb(Num.ONE, animRedVar, animRedVar);
	
	
	public EntityRendererMobLR(Entity entity, String sheetKey) {
		this.entity = entity;
		this.sheet = Res.getTxSheet(sheetKey);
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
		
		App.gfx().pushGeometry();
		
		App.gfx().translate(spriteRect.center());
		
		if (entity.isDead()) {
			App.gfx().rotateZ(Calc.clamp(hurtTime / 0.3, 0, 1) * 90);
		}
		
		final double hw = spriteRect.width().half().value();
		
		App.gfx().quad(Vect.ZERO.expand(hw, hw, hw, hw), q, hue.withAlpha(entity.isDead() ? 1 - Easing.CIRC_IN.get(hurtTime / entity.getDespawnDelay()) : 1));
		App.gfx().popGeometry();
	}
}
