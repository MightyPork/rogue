//package mightypork.utils.math.constraints.builder;
//
//
//import mightypork.utils.math.constraints.ConstraintFactory;
//import mightypork.utils.math.constraints.RectBound;
//import mightypork.utils.math.constraints.VectBound;
//import mightypork.utils.math.rect.Rect;
//import mightypork.utils.math.vect.Vect;
//
//
//public class Bounds {
//
//	public RectBB box(Object side) {
//		return wrap(ConstraintFactory.box(side));
//	}
//	public RectBB box(VectBound origin, Object width, Object height){
//		return wrap(ConstraintFactory.box(origin, width, height));		
//	}
//	
//	public RectBB box(Object width, Object height){
//		return wrap(ConstraintFactory.box(width, height));		
//	}
//	
//	public RectBB box(Object x, Object y, Object width, Object height){
//		return wrap(ConstraintFactory.box(Rect.ZERO, x,y,width,height));			
//	}
//	
//	public RectBB wrap(RectBound rb) {
//		return new RectBB(rb);
//	}
//	
//	public static class RectBB {
//		
//		private final RectBound parent;
//				
//		public RectBB(RectBound parent) {
//			this.parent = parent;
//		}
//		
//	}
//}
