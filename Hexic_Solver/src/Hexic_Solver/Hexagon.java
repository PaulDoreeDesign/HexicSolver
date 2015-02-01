package Hexic_Solver;

import java.awt.Polygon;

public class Hexagon extends Polygon
{
	private int x;
	private int y;
	private int width;
	private int height;
	public Hexagon(int xIn, int yIn, int wIn, int hIn)
	{
		super();
		set(xIn,yIn,wIn,hIn);
		addPoint(x+width/4,y);
		addPoint(x+3*width/4,y);
		addPoint(x+width,y+height/2);
		addPoint(x+3*width/4,y+height);
		addPoint(x+width/4,y+height);
		addPoint(x,y+height/2);
	}
	private void set(int xIn, int yIn, int wIn, int hIn)
	{
		x = xIn;
		y = yIn;
		width = wIn;
		height = hIn;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}
