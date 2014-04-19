package mightypork.gamecore.render.textures;


import java.util.Random;


/**
 * Basic sprite sheet
 * 
 * @author MightyPork
 */
public class TxSheet {
	
	private final TxQuad original;
	private final TxQuad[] sprites;
	private final int width;
	
	private final Random rand = new Random();
	private final Random randForSeed = new Random();
	private final int count;
	
	
	public TxSheet(TxQuad tx, int width, int height)
	{
		this.original = tx;
		this.width = width;
		this.count = width * height;
		
		this.sprites = new TxQuad[count];
	}
	
	
	/**
	 * @return number of quads
	 */
	public int getQuadCount()
	{
		return count;
	}
	
	
	/**
	 * Get quad of index
	 * 
	 * @param index index
	 * @return the quad
	 */
	public TxQuad getQuad(int index)
	{
		if (index < 0 || index > count) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index + ", allowed: 0.." + count);
		}
		
		// lazy - init only when needed
		if (sprites[index] == null) {
			final int x = index % width;
			final int y = index / width;
			
			final double origW = original.uvs.width().value();
			final double origH = original.uvs.height().value();
			
			sprites[index] = new TxQuad(original.tx, original.uvs.move(x * origW, y * origH));
		}
		
		return sprites[index];
	}
	
	
	/**
	 * Get entirely random TxQuad from this sheet
	 * 
	 * @return the picked quad
	 */
	public TxQuad getRandomQuad()
	{
		return getQuad(rand.nextInt(count));
	}
	
	
	/**
	 * Get random TxQuad from this sheet
	 * 
	 * @param seed random number generator seed
	 * @return the picked quad
	 */
	public TxQuad getRandomQuad(long seed)
	{
		randForSeed.setSeed(seed);
		return getQuad(randForSeed.nextInt(count));
	}
	
	
	/**
	 * Get random TxQuad from this sheet
	 * 
	 * @param seed random number generator seed (double will be converted to
	 *            long)
	 * @return the picked quad
	 */
	public TxQuad getRandomQuad(double seed)
	{
		return getRandomQuad(Double.doubleToLongBits(seed));
	}
}
