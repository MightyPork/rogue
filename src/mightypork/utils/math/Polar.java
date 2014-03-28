package mightypork.utils.math;


import mightypork.utils.math.coord.Coord;


/**
 * Polar coordinate
 * 
 * @author MightyPork
 */
public class Polar {

	/** angle in radians */
	public double angle = 0;
	/** distance in units */
	public double distance = 0;


	/**
	 * @param angle angle in radians
	 * @param distance distance from origin
	 */
	public Polar(double angle, double distance) {
		this.angle = angle;
		this.distance = distance;
	}


	/**
	 * Make polar from coord
	 * 
	 * @param coord coord
	 * @return polar
	 */
	public static Polar fromCoord(Coord coord)
	{
		return new Polar(Math.atan2(coord.y, coord.x), Math.sqrt(Calc.square(coord.x) + Calc.square(coord.y)));
	}


	/**
	 * Make polar from coords
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return polar
	 */
	public static Polar fromCoord(double x, double y)
	{
		return Polar.fromCoord(new Coord(x, y));
	}


	/**
	 * Make polar from coords
	 * 
	 * @param x x coord
	 * @param z z coord
	 * @return polar
	 */
	public static Polar fromCoordXZ(double x, double z)
	{
		return Polar.fromCoordXZ(new Coord(x, 0, z));
	}


	/**
	 * Get coord from polar
	 * 
	 * @return coord
	 */
	public Coord toCoord()
	{
		return new Coord(distance * Math.cos(angle), distance * Math.sin(angle));
	}


	/**
	 * Get X,0,Y coord from polar
	 * 
	 * @return coord
	 */
	public Coord toCoordXZ()
	{
		return new Coord(distance * Math.cos(angle), 0, distance * Math.sin(angle));
	}


	@Override
	public String toString()
	{
		return "Polar(theta=" + angle + ", r=" + distance + ")";
	}


	/**
	 * Build polar from X,Z instead of X,Y
	 * 
	 * @param coord cpprd with X,Z
	 * @return polar
	 */
	public static Polar fromCoordXZ(Coord coord)
	{
		return fromCoord(coord.x, coord.z);
	}
}
