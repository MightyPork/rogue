package mightypork.utils.math.coord;


/**
 * Simple integer coordinate class<br>
 * Unlike Coord, this is suitable for using in array indices etc.
 * 
 * @author MightyPork
 */
public class CoordI {

	/** X coordinate */
	public int x = 0;
	/** Y coordinate */
	public int y = 0;
	/** Z coordinate */
	public int z = 0;


	/**
	 * Create CoordI as copy of other
	 * 
	 * @param other coord to copy
	 */
	public CoordI(CoordI other) {
		setTo(other);
	}


	/**
	 * Integer 2D Coord
	 * 
	 * @param x x coord
	 * @param y y coord
	 */
	public CoordI(int x, int y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * Integer 3D Coord
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 */
	public CoordI(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	/**
	 * Empty cobnstructor 0,0,0
	 */
	public CoordI() {
		x = 0;
		y = 0;
		z = 0;
	}


	/**
	 * Add other coordI coordinates in a copy
	 * 
	 * @param other coordI to add
	 * @return copy modified
	 */
	public CoordI add(CoordI other)
	{
		return copy().add_ip(other);
	}


	/**
	 * Add coords in copy
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return the copy
	 */
	public CoordI add(int x, int y)
	{
		return copy().add_ip(x, y, 0);
	}


	/**
	 * Add coords in copy
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 * @return the copy
	 */
	public CoordI add(int x, int y, int z)
	{
		return copy().add_ip(x, y, z);
	}


	/**
	 * Add other coordI coordinates in place
	 * 
	 * @param move coordI to add
	 * @return this
	 */
	public CoordI add_ip(CoordI move)
	{
		x += move.x;
		y += move.y;
		z += move.z;
		return this;
	}


	/**
	 * Add coords in place
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return this
	 */
	public CoordI add_ip(int x, int y)
	{
		this.x += x;
		this.y += y;
		return this;
	}


	/**
	 * Add coords in place
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 * @return this
	 */
	public CoordI add_ip(int x, int y, int z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}


	/**
	 * Get copy
	 * 
	 * @return copy
	 */
	public CoordI copy()
	{
		return new CoordI(x, y, z);
	}


	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (obj instanceof CoordI) return ((CoordI) obj).x == x && ((CoordI) obj).y == y && ((CoordI) obj).z == z;
		return false;
	}


	@Override
	public int hashCode()
	{
		return x ^ y ^ z;
	}


	/**
	 * Middle of this and other coordinate, rounded to CoordI - integers
	 * 
	 * @param other other coordI
	 * @return middle CoordI
	 */
	public CoordI midTo(CoordI other)
	{
		return new CoordI((x + other.x) / 2, (y + other.y) / 2, (z + other.z) / 2);
	}


	/**
	 * Multiply in copy 2D
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return the copy
	 */
	public CoordI mul(double x, double y)
	{
		return copy().mul_ip(x, y);
	}


	/**
	 * Multiply in copy
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 * @return the copy
	 */
	public CoordI mul(double x, double y, double z)
	{
		return copy().mul_ip(x, y, z);
	}


	/**
	 * Multiply in place 2D
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return this
	 */
	public CoordI mul_ip(double x, double y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}


	/**
	 * Multiply in place
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 * @return this
	 */
	public CoordI mul_ip(double x, double y, double z)
	{
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}


	/**
	 * Set to coords from other coord
	 * 
	 * @param other source coord
	 */
	public void setTo(CoordI other)
	{
		if (other == null) {
			setTo(0, 0, 0);
			return;
		}
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}


	/**
	 * Set coords to
	 * 
	 * @param x x coord to set
	 * @param y y coord to set
	 */
	public void setTo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}


	/**
	 * Set coords to
	 * 
	 * @param x x coord to set
	 * @param y y coord to set
	 * @param z z coord to set
	 */
	public void setTo(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	/**
	 * Subtract other coordI coordinates in a copy
	 * 
	 * @param other coordI to subtract
	 * @return copy subtracted
	 */
	public CoordI sub(CoordI other)
	{
		return copy().sub_ip(other);
	}


	/**
	 * Subtract x,y in a copy
	 * 
	 * @param x x to subtract
	 * @param y y to subtract
	 * @return copy subtracted
	 */
	public CoordI sub(int x, int y)
	{
		return copy().sub_ip(new CoordI(x, y));
	}


	/**
	 * Subtract x,y,z in a copy
	 * 
	 * @param x x to subtract
	 * @param y y to subtract
	 * @param z z to subtract
	 * @return copy subtracted
	 */
	public CoordI sub(int x, int y, int z)
	{
		return copy().sub_ip(new CoordI(x, y, z));
	}


	/**
	 * Subtract other coordI coordinates in place
	 * 
	 * @param move coordI to subtract
	 * @return this
	 */
	public CoordI sub_ip(CoordI move)
	{
		x -= move.x;
		y -= move.y;
		z -= move.z;
		return this;
	}


	/**
	 * Sub coords in place
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return this
	 */
	public CoordI sub_ip(int x, int y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}


	/**
	 * Sub coords in place
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param z z coord
	 * @return this
	 */
	public CoordI sub_ip(int x, int y, int z)
	{
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}


	/**
	 * Convert to double Coord
	 * 
	 * @return coord with X and y from this CoordI
	 */
	public Coord toCoord()
	{
		return new Coord(x, y);
	}


	@Override
	public String toString()
	{
		return "[ " + x + " ; " + y + " ; " + z + " ]";
	}

}
