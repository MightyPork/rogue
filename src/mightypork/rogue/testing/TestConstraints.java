package mightypork.rogue.testing;


import mightypork.rogue.display.constraints.Bounding;
import mightypork.rogue.display.constraints.Constraint;
import mightypork.utils.math.coord.Coord;
import mightypork.utils.math.coord.Rect;


public class TestConstraints {
	
	public static void main(String[] args)
	{
		Bounding context = new Bounding() {
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(new Coord(0, 0), new Coord(400, 300));
			}
		};
		
		class Navbar extends Constraint {
			
			private double height;
			
			
			public Navbar(Bounding context, double height) {
				super(context);
				this.height = height;
			}
			
			
			@Override
			public Rect getRect()
			{
				return Rect.fromSize(origin().setY(size().y - height), size().setY(height));
			}
		}
		
		class TileHorizontal extends Constraint {
			
			private int count;
			private int tile;
			
			
			public TileHorizontal(Bounding context, int tileCount, int aTile) {
				super(context);
				this.count = tileCount;
				setTile(aTile);
			}
			
			
			public void setTile(int aTile)
			{
				if (aTile > count) throw new IndexOutOfBoundsException("Tile count exceeded: " + aTile + " max: " + count);
				this.tile = aTile;
			}
			
			
			@Override
			public Rect getRect()
			{
				Coord size = size().mul(1D / count, 1);
				return Rect.fromSize(origin().add(size.x * tile, 0), size);
			}
		}
		
		Navbar nb = new Navbar(context, 100);
		
		TileHorizontal tile = new TileHorizontal(nb, 5, 0);
		
		for (int i = 0; i < 5; i++) {
			tile.setTile(i);
			
			System.out.println(tile.getRect());
		}
		
		System.out.println("nb:" + nb.getRect());
		
		System.out.println("ctx:" + context.getRect());
	}
	
}
