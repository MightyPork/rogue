package mightypork.test;


import mightypork.utils.math.constraints.num.Num;
import mightypork.utils.math.constraints.num.mutable.NumVar;
import mightypork.utils.math.constraints.rect.Rect;


public class TestTiled {
	
	public static void main(String[] args)
	{
//		{
//			RectVar area = Rect.makeVar(0, 0, 100, 100);
//			
//			TiledRect tiled = area.tiles(5, 5).oneBased();
//			
//			System.out.println(tiled.span(1, 1, 1, 1));
//			System.out.println(tiled.span(1, 1, 3, 1));
//		}
		
//		{
//			RectVar area = Rect.makeVar(0, 0, 100, 100);
//			TiledRect tiled = area.columns(4);
//			
//			System.out.println(tiled.column(2));
//			
//			
//			
//		}
//		
		{
			Rect abox;
			final Rect b = Rect.make(100, 100, 100, 10);
			final NumVar pos = Num.makeVar(1);
			
			abox = b.leftEdge().growRight(b.height());
			abox = abox.move(b.width().sub(b.height()).mul(pos), Num.ZERO);
			//abox = abox.shrink(b.height().perc(10));
			
			System.out.println(abox);
		}
	}
}
