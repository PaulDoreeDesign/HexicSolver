package Hexic_Solver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
//import java.awt.geom.Rectangle2D;
import java.awt.GradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
//import javax.swing.SwingUtilities;

public class SimpleGraph
{
	private Board board;
	private Color[] color;
	private int numOfColumns;
	private int numOfRows;
	
	private int circleRows;
	private int circleColumns;
	
	private int initialX;
	private int initialY;
	private int tileWidth;
	private int tileHeight;
	private int spacing;
	
	
	private ArrayList<Ellipse2D> circleList;
	
	private ArrayList<Hexagon> hexList;
	private ArrayList<Star> starList;
	//private ArrayList<Polygon> pearlUpList;
	//private ArrayList<Polygon> pearlDownList;
	
	
	public SimpleGraph(Board bIn)
	{
		board = bIn;
		color = new Color[9];
		color[0] = new Color(216,52,16);  // red
	    color[1] = new Color(239,205,32); // yellow
	    color[2] = new Color(176,212,24); // green
	    color[3] = new Color(84,169,242); // blue
	    color[4] = new Color(108,16,221); // violet
	    color[5] = new Color(220,40,208); // pink
	    color[6] = new Color(6,101,68); // emerald
	    color[7] = new Color(190,190,190); // star
	    color[8] = new Color(101,84,27); // black
	    
	    initialX = 150;
		initialY = 500;
		tileWidth = 60; // was 50 and 43
		tileHeight = 52; // h = w*sqrt(3)/2
		spacing = 1;
		
		numOfColumns = board.getNumOfColumns(); // 5
		numOfRows = board.getNumOfRows(); // 17
		
		circleColumns = 2*numOfColumns-1; // 9
		circleRows = numOfRows-2; // 15
		
		hexList = new ArrayList<Hexagon>();
		createHexGrid();
		circleList = new ArrayList<Ellipse2D>();
		createCircles();
		starList = new ArrayList<Star>();
	}
	
	public void setBoard(Board bIn)
	{
		board = bIn;
	}
	
	private void createHexGrid()
	{
		int Xcoordinate = initialX;
		int Ycoordinate = initialY;
		for(int x=0;x<numOfColumns;x++)
		{
			for(int y=0;y<numOfRows;y++)
			{
				if(y%2==0)
					Xcoordinate += 3*tileWidth/4 + spacing;
				else
					Xcoordinate -= 3*tileWidth/4 + spacing;
				hexList.add(new Hexagon(Xcoordinate, Ycoordinate, tileWidth, tileHeight));
				Ycoordinate -= tileHeight/2 + spacing;
			}
			Ycoordinate = initialY;
			Xcoordinate += 3*tileWidth/4 + spacing;
		}
	}
	public void draw(Graphics2D g2)
	{
		drawBackground(g2);
		drawGridBorder(g2);
		drawBoard(g2);
		//drawHexGrid(g2);
	}
	
	
	private void drawHexGrid(Graphics2D g2)
	{
		int a = 0;
		Tile tmpTile;
		Color tmpColor;
		GradientPaint tmpPaint;
		for (Hexagon h : hexList)
		{
			g2.setColor(Color.black);
	        g2.draw(h);
	        tmpTile = board.getTile(a%17, a/17);
	        if(tmpTile!=null)
	        {
	        	tmpColor = color[tmpTile.getType()];
	        	tmpPaint = new GradientPaint(h.getX() + h.getWidth()/2, h.getY() + h.getHeight()/2, tmpColor,
	        			h.getX() + 9*h.getWidth()/4, h.getY() + 3*h.getHeight(), Color.black, true);
	        	g2.setPaint(tmpPaint);
	        	g2.fill(h);
			}
	        a++;
	    }
	}
	
	private void drawGridBorder(Graphics2D g2)
	{
		int bgX = initialX - tileWidth/8;
		int bgY = initialY - 33*tileHeight/4 - 8*spacing;
		int bgWidth = 8*tileWidth + 9*spacing;
		int bgHeight = 38*tileHeight/4;
		
		g2.setColor(new Color(34,34,34));
		g2.fillRoundRect(bgX, bgY, bgWidth, bgHeight, 20, 20);
	}
	
	private void drawBoard(Graphics2D g2)
	{
		Hexagon hex;
		Star star;
		int Xcoordinate = initialX;
		int Ycoordinate = initialY;
		Tile tempTile;
		Color[] tempColor = new Color[2];
		GradientPaint tempPaint;
		
		for(int x=0;x<numOfColumns;x++)
		{
			for(int y=0;y<numOfRows;y++)
			{
				if(y%2==0)
					Xcoordinate += 3*tileWidth/4 + spacing;
				else
					Xcoordinate -= 3*tileWidth/4 + spacing;
				hex = new Hexagon(Xcoordinate, Ycoordinate, tileWidth, tileHeight);
				
				g2.setColor(Color.black);
				g2.draw(hex);
				
				//System.out.println("hmm loop");
				if(board.getTile(x, y)!=null)
				{
					tempTile = board.getTile(x,y);
					tempColor[0] = color[tempTile.getType()];
				}
				else
					tempColor[0] = Color.white;
				tempColor[1] = Color.black;
				tempPaint = new GradientPaint(Xcoordinate + tileWidth/2, Ycoordinate + tileHeight/2, tempColor[0],
						Xcoordinate + 9*tileWidth/4, Ycoordinate + 3*tileHeight, tempColor[1], true);
				g2.setPaint(tempPaint);
				g2.fill(hex);
				
				if(board.getTile(x, y)!=null)
				{
					tempTile = board.getTile(x,y);
					if(tempTile.getType()==7)
					{
						star = new Star(Xcoordinate, Ycoordinate, tileWidth, tileHeight, (new Position(x,y)));
						g2.setColor(Color.black);
						g2.draw(star);
						starList.add(star);
					}
				}
				Ycoordinate -= tileHeight/2 + spacing;
			}
			Ycoordinate = initialY;
			Xcoordinate += 3*tileWidth/4 + spacing;
		}
	}
	
	private void createCircles()
	{
		int radius = 20;
		int Xcoordinate = initialX + 3*tileWidth/4 - radius/2;
		int Ycoordinate = initialY - radius/2;
		
		for(int x=0;x<circleColumns;x++)
		{
			for(int y=0;y<circleRows;y++)
			{
				if(x%2==0)
				{
					if(y%2==0)
						Xcoordinate += tileWidth/4 + spacing;
					else
						Xcoordinate -= tileWidth/4 + spacing;
				}
				else
				{
					if(y%2==0)
						Xcoordinate -= tileWidth/4 + spacing;
					else
						Xcoordinate += tileWidth/4 + spacing;
				}
				circleList.add(new Ellipse2D.Float(Xcoordinate, Ycoordinate, radius,radius));
				Ycoordinate -= tileHeight/2 + spacing;
			}
			Ycoordinate = initialY- radius/2;
			Xcoordinate += 3*tileWidth/4 + spacing;
		}
	}
	/*
	private void drawCircles(Graphics2D g2)
	{
		for (Ellipse2D c : circleList)
		{
			g2.setColor(Color.black);
	        g2.draw(c);
	        g2.setColor(Color.white);
	        g2.fill(c);
	    }
	}
	*/
	private void drawBackground(Graphics2D g2)
	{
		// Background gradient
		GradientPaint tempPaint = new GradientPaint(400, 0, (new Color(144,144,180)), 400, 620, Color.black, true);
		g2.setPaint(tempPaint);
		g2.fillRect(0, 0, 800, 640);
		
		// Hexagon pattern background
		
		int tileW = tileWidth/2;
		int tileH = tileHeight/2;
		int initX = -tileW/2;
		int initY = 620 - 3*tileH/4;
		int Xcoordinate = initX;
		int Ycoordinate = initY;
		
		Hexagon hex;
		g2.setColor(Color.black);
		
		for(int x=0;x<21;x++)
		{
			for(int y=0;y<51;y++)
			{
				if(y%2==0)
					Xcoordinate += 3*tileW/4 + spacing;
				else
					Xcoordinate -= 3*tileW/4 + spacing;
				hex = new Hexagon(Xcoordinate, Ycoordinate, tileW, tileH);
				g2.draw(hex);
				Ycoordinate -= tileH/2 + spacing;
			}
			Ycoordinate = initY;
			Xcoordinate += 3*tileW/4 + spacing;
		}
		// Rounded rectangle behind score
		
		tempPaint = new GradientPaint(400, 0, Color.black, 400, 40, (new Color(0,140,212)), true);
		g2.setPaint(tempPaint);
		g2.fillRoundRect(240, 16, 280, 40, 20, 20);
		
		// spin arrows for bottom right of frame
		
		g2.setColor(Color.black);
		Polygon leftArrow = leftSpinArrow(720,400, 40, 40);
		Polygon rightArrow = rightSpinArrow(720,480, 40, 40);
		//Polygon rightArrow = star(720,480, 60, 52);
		g2.draw(leftArrow);
		g2.draw(rightArrow);
		g2.setColor(new Color(212,212,212));
		g2.fill(leftArrow);
		g2.fill(rightArrow);
		
		// Rounded Rectangles behind Level and Combos Remaining
		
		//g2.setColor(new Color(119,123,119));
		//g2.fillRoundRect(5, 104, 110, 60, 20, 20);
		//g2.fillRoundRect(5, 178, 110, 76, 20, 20);
	}
	
	
	public Ellipse2D isTheMouseInACircle(Point2D p)
	{
		for (Ellipse2D c : circleList)
		{
			if(c.contains(p))
			{
				return c;
			}
	    }
		return null;
	}
	public Star isTheMouseInAStar(Point2D p)
	{
		for (Star s : starList)
		{
			if(s.contains(p))
			{
				return s;
			}
	    }
		return null;
	}
	
	public Position getPosCircle(Ellipse2D cIn)
	{
		int index = circleList.indexOf(cIn);
		return (new Position(index/circleRows,index%circleRows));
	}
	
	private Polygon leftSpinArrow(int x, int y, int width, int height)
	{
		Polygon retPoly = new Polygon();
		retPoly.addPoint(x,y+height/3);
		retPoly.addPoint(x,y+2*height/3);
		retPoly.addPoint(x+width/3,y+2*height/3);
		retPoly.addPoint(x+2*width/9,y+5*height/9);
		retPoly.addPoint(x+4*width/9,y+3*height/9); //
		retPoly.addPoint(x+6*width/9,y+5*height/9);
		retPoly.addPoint(x+7*width/9,y+height);
		retPoly.addPoint(x+width,y+height);
		retPoly.addPoint(x+width,y+5*height/9);
		retPoly.addPoint(x+7*width/9,y+2*height/9);
		retPoly.addPoint(x+4*width/9,y+height/9);
		retPoly.addPoint(x+width/3,y+2*height/9);
		retPoly.addPoint(x+width/9, y+4*height/9);
		return retPoly;
	}
	private Polygon rightSpinArrow(int x, int y, int width, int height)
	{
		Polygon retPoly = new Polygon();
		retPoly.addPoint(x,y);
		retPoly.addPoint(x,y+height/3);
		retPoly.addPoint(x+width/3,y+height/3);
		retPoly.addPoint(x+2*width/9,y+2*height/9);
		
		
		return retPoly;
	}
}
