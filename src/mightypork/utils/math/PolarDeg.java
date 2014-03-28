package mightypork.utils.math;


import mightypork.utils.math.Calc.Deg;
import mightypork.utils.math.Calc.Rad;
import mightypork.utils.math.coord.Coord;


/**
 * Polar coordinate in degrees
 * 
 * @author MightyPork
 */
public class PolarDeg {

	/** angle in degrees */
	public double angle = 0;
	/** distance in units */
	public double distance = 0;


	/**
	 * Polar coordinate in degrees
	 * 
	 * @param angle angle in degrees
	 * @param distance distance from origin
	 */
	public PolarDeg(double angle, double distance) {
		this.angle = angle;
		this.distance = distance;
	}


	/**
	 * Make polar from coord
	 * 
	 * @param coord coord
	 * @return polar
	 */
	public static PolarDeg fromCoord(Coord coord)
	{
		return new PolarDeg(Rad.toDeg(Math.atan2(coord.y, coord.x)), Math.sqrt(Calc.square(coord.x) + Calc.square(coord.y)));
	}


	/**
	 * Make polar from coords
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @return polar
	 */
	public static PolarDeg fromCoord(double x, double y)
	{
		return PolarDeg.fromCoord(new Coord(x, y));
	}


	/**
	 * Make polar from coords
	 * 
	 * @param x x coord
	 * @param z y coord
	 * @return polar
	 */
	public static PolarDeg fromCoordXZ(double x, double z)
	{
		return PolarDeg.fromCoordXZ(new Coord(x, 0, z));
	}


	/**
	 * Get coord from polar
	 * 
	 * @return coord
	 */
	public Coord toCoord()
	{
		return new Coord(distance * Math.cos(Deg.toRad(angle)), distance * Math.sin(Deg.toRad(angle)));
	}


	/**
	 * Get X,0,Y coord from polar
	 * 
	 * @return coord
	 */
	public Coord toCoordXZ()
	{
		return new Coord(distance * Math.cos(Deg.toRad(angle)), 0, distance * Math.sin(Deg.toRad(angle)));
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
	public static PolarDeg fromCoordXZ(Coord coord)
	{
		return fromCoord(coord.x, coord.z);
	}
}
