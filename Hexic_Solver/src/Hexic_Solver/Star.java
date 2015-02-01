package Hexic_Solver;

import java.awt.Polygon;

public class Star extends Polygon
{
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Position pos;
	
	public Star(int xIn, int yIn, int wIn, int hIn, Position pIn)
	{
		super();
		set(xIn,yIn,wIn,hIn);
		addPoint(x+width/4,y);
		addPoint(x+width/2, y+height/6);
		addPoint(x+3*width/4,y);
		addPoint(x+3*width/4,y+height/3);
		addPoint(x+width,y+height/2);
		addPoint(x+3*width/4,y+2*height/3);
		addPoint(x+3*width/4,y+height);
		addPoint(x+width/2, y+5*height/6);
		addPoint(x+width/4,y+height);
		addPoint(x+width/4,y+2*height/3);
		addPoint(x,y+height/2);
		addPoint(x+width/4,y+height/3);
		pos = pIn;
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
	public Position getPos()
	{
		return pos;
	}
}