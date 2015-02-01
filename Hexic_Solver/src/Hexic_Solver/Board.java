package Hexic_Solver;

import java.util.ArrayList;
import java.util.Random;

public class Board
{
	private Random generator;
	private static final int columns = 5;
	private static final int rows = 17;
	private Tile[][] tile;
	private int score;
	private int level;
	private int comborem;
		
	public Board()
	{
		score = 0;
		level = 1;
		comborem = 50;
		
		tile = new Tile[rows][columns];
		generator = new Random();
		for(int x=0;x<columns;x++)
		{
			for(int y=0;y<rows;y++)
			{
				if(x>0 && (y%2)==1) // x > 0 and y is odd
				{
					short typeItCantBe = 100;
					if(y>1 && (tile[y-2][x].getType() == tile[y-1][x].getType()))
					{
						typeItCantBe = tile[y-2][x].getType();
					}
					
					short typeItCantBe2 = 100;
					if(tile[y-1][x-1].getType() == tile[y+1][x-1].getType())
					{
						typeItCantBe2 = tile[y-1][x-1].getType();
					}
					else if(y>2 && (tile[y-1][x-1].getType() == tile[y-2][x].getType()))
					{
						typeItCantBe2 = tile[y-1][x-1].getType();
					}
					else
					{}
					
					short newType = (short) generator.nextInt(5);
					
					
					while(newType==typeItCantBe || newType==typeItCantBe2) // need to change later
						newType = (short) generator.nextInt(5);
					
					
					tile[y][x] = new Tile(newType);
				}
				else // x is 0 or y is even
				{
					short numOfTypesItCantBe = 0;
					short typeItCantBe = 100;
					if(y>1 && (tile[y-2][x].getType() == tile[y-1][x].getType()))
					{
						typeItCantBe = tile[y-2][x].getType();
						numOfTypesItCantBe++;
					}
					short newType = (short) generator.nextInt(5-numOfTypesItCantBe);
					if(numOfTypesItCantBe > 0)
						if(newType >= typeItCantBe)
							newType++;
					tile[y][x] = new Tile(newType);
				}
			}
		}
		/*
		// builds a star cluster
		tile[0][0].setType((short) 0);
		tile[1][0].setType((short) 0);
		tile[3][0].setType((short) 0);
		tile[4][0].setType((short) 0);
		tile[1][1].setType((short) 0);
		tile[3][1].setType((short) 0);
		*/
	}
	
	// will be made private
	public boolean clearingNeeded()
	{
		boolean retbool = false;
		ArrayList<Position> list = new ArrayList<Position>();
		for(int x=0;x<columns && !retbool;x++)
		{
			for(int y=0;y<rows && !retbool;y++)
			{
				if(tile[y][x]!=null)
					clearable(false,x,y,list);
				if(!list.isEmpty())
					retbool = true;
			}
		}
		return retbool;
	}
	
	public void clearThings()
	{
		// Checks Board
		ArrayList<Position> list = new ArrayList<Position>();
		for(int x=0;x<columns;x++)
		{
			for(int y=0;y<rows;y++)
			{
				if(tile[y][x]!=null)
					clearable(true,x,y,list);
			}
		}
		int tmpX;
		int tmpY;
		int tilesCleared = 0;
		for (Position p : list)
		{
			tmpX = p.getX();
			tmpY = p.getY();
			
			if(tile[tmpY][tmpX]!=null)
			{
				tile[tmpY][tmpX] = null;
				tilesCleared++;
			}
			//System.out.println("x="+tmpX+", y="+tmpY);
		}
		score += level*tilesCleared*100;
		comborem -= tilesCleared;
		dropDown();
		
		/*
		for(int x=0;x<columns-5;x++)
		{
			for(int y=0;y<rows;y++)
			{
				if(tile[y][x]!=null)
					clearable(x,y,list);
			}
		}
		*/
		
		for(int x=0;x<columns;x++)
		{
			for(int y=0;y<rows;y++)
			{
				if(tile[y][x]==null)
					tile[y][x] = new Tile((short) generator.nextInt(5), (short) 0);
			}
		}
	}
	
	
	
	private void clearable(boolean changeBoard, int x, int y, ArrayList<Position> list)
	{
		// only check for if(y>0 && (x>0 || y%0)) // not true anymore
		
		int typeIn = tile[y][x].getType();
		int typeBelow = -1;
		if(y>1)
			typeBelow = tile[y-2][x].getType();
		int typeLeftDown = -1;
		if(y>0 && (x>0 || y%2==0))
		{
			if(y%2==0)
				typeLeftDown = tile[y-1][x].getType();
			else
				typeLeftDown = tile[y-1][x-1].getType();
		}
		
		if(y>1 && (x>0 || y%2==0) && typeIn==typeBelow && typeLeftDown==typeBelow)
		{
			
			list.add(new Position(x,y));
			list.add(new Position(x,y-2));
			if(y%2==0)
				list.add(new Position(x,y-1));
			else
				list.add(new Position(x-1,y-1));
			//System.out.println("clearable 1: x="+x+", y="+y+", shape: \7");
		}
		if(y>2 && y%2==1 && typeIn==typeBelow && tile[y-1][x].getType()==typeBelow)
		{
			list.add(new Position(x,y));
			list.add(new Position(x,y-2));
			list.add(new Position(x,y-1));
			//System.out.println("clearable 2: x="+x+", y="+y+", shape: \7");
		}
		if(x>0 && y>0 && y%2==1 && typeIn==typeLeftDown && tile[y+1][x-1].getType()==typeLeftDown)
		{
			list.add(new Position(x,y));
			list.add(new Position(x-1,y-1));
			list.add(new Position(x-1,y+1));
			//System.out.println("clearable 3: x="+x+", y="+y+", shape: |>");
		}
		if(x>0 && y>2 && y%2==1 && typeIn==typeBelow && tile[y-3][x-1].getType()==typeBelow &&
				tile[y-2][x-1].getType()==typeBelow && tile[y][x-1].getType()==typeBelow &&
				tile[y+1][x-1].getType()==typeBelow)
		{
			list.add(new Position(x,y));
			list.add(new Position(x,y-2));
			list.add(new Position(x-1,y-3));
			list.add(new Position(x-1,y-2));
			list.add(new Position(x-1,y));
			list.add(new Position(x-1,y+1));
			// change center to star
			if(changeBoard)
				tile[y-1][x-1].setType((short) 7);
			//System.out.println("clearable 4 - star!: x="+x+", y="+y);
		}
		if(x>0 && y>4 && y%2==1 && typeIn==typeLeftDown && tile[y-3][x-1].getType()==typeLeftDown &&
				tile[y-4][x].getType()==typeLeftDown && tile[y-3][x].getType()==typeLeftDown &&
				tile[y-1][x].getType()==typeLeftDown)
		{
			list.add(new Position(x,y));
			list.add(new Position(x,y-1));
			list.add(new Position(x,y-3));
			list.add(new Position(x,y-4));
			list.add(new Position(x-1,y-3));
			list.add(new Position(x-1,y-1));
			// change center to star
			if(changeBoard)
				tile[y-2][x].setType((short) 7);
			//System.out.println("clearable 5 - star!: x="+x+", y="+y);
		}
	}
	
	private void dropDown()
	{
		int tmpPointer = 0;
		for(int x=0;x<columns;x++)
		{
			for(int y=0;y<rows;y++)
			{
				if(tile[y][x]==null)
				{
					tmpPointer = y;
					for(int i=(y+2);i<rows;i+=2)
					{
						if(tile[i][x]!=null)
						{
							tile[tmpPointer][x] = new Tile(tile[i][x].getType(),tile[i][x].getDetail());
							tile[i][x] = null;
							tmpPointer +=2;
						}
					}
				}
			}
		}
	}
	
	
	
	public void rotateCluster(String rotation, int x, int y)
	{
		Tile tmpTile;
		if(rotation.equalsIgnoreCase("Left"))
		{
			if(x%2==0) // x is even
			{
				tmpTile = tile[y][x/2];
				if(y%2==0) // y is even
				{
					tile[y][x/2] = tile[y+1][x/2];
					tile[y+1][x/2] = tile[y+2][x/2];
					tile[y+2][x/2] = tmpTile;
				}
				else // y is odd
				{
					tile[y][x/2] = tile[y+2][x/2];
					tile[y+2][x/2] = tile[y+1][x/2];
					tile[y+1][x/2] = tmpTile;
				}
			}
			else // x is odd
			{
				if(y%2==0) // y is even
				{
					tmpTile = tile[y][x/2];
					tile[y][x/2] = tile[y+2][x/2];
					tile[y+2][x/2] = tile[y+1][x/2+1];
					tile[y+1][x/2+1] = tmpTile;
				}
				else // y is even
				{
					tmpTile = tile[y][x/2+1];
					tile[y][x/2+1] = tile[y+1][x/2];
					tile[y+1][x/2] = tile[y+2][x/2+1];
					tile[y+2][x/2+1] = tmpTile;
				}
			}
		}
		else if(rotation.equalsIgnoreCase("Right"))
		{
			if(x%2==0)
			{
				tmpTile = tile[y][x/2];
				if(y%2==0)
				{
					tile[y][x/2] = tile[y+2][x/2];
					tile[y+2][x/2] = tile[y+1][x/2];
					tile[y+1][x/2] = tmpTile;
					
				}
				else
				{
					tile[y][x/2] = tile[y+1][x/2];
					tile[y+1][x/2] = tile[y+2][x/2];
					tile[y+2][x/2] = tmpTile;
				}
			}
			else
			{
				if(y%2==0)
				{
					tmpTile = tile[y][x/2];
					tile[y][x/2] = tile[y+1][x/2+1];
					tile[y+1][x/2+1] = tile[y+2][x/2];
					tile[y+2][x/2] = tmpTile;
					
				}
				else
				{
					tmpTile = tile[y][x/2+1];
					tile[y][x/2+1] = tile[y+2][x/2+1];
					tile[y+2][x/2+1] = tile[y+1][x/2];
					tile[y+1][x/2] = tmpTile;
				}
			}
		}
		else
			System.out.println("Bad Rotation Call.");
	}
	
	private boolean rotatable(int x, int y)
	{
		if(y>1 && y<15)
		{
			if(x>0 && x<4)
			{
				return true;
			}
			else if(x==0)
			{
				if(y%2==0)
					return true;
			}
			else if(x==4)
			{
				if(y%2==1)
					return true;
			}
		}
		return false;
	}
	
	/***********************************************
	 * Under Construction
	 * 
	 * Will make star pieces rotatable
	 * when it is complete
	 * 
	 ***********************************************/
	
	public void rotateStar(String rotation, int x, int y)
	{
		if(rotatable(x,y))
		{
			Tile tmpTile = tile[y-2][x];
			if(rotation.equalsIgnoreCase("Left"))
			{
				if(y%2==0)
				{
					tile[y-2][x] = tile[y-1][x];
					tile[y-1][x] = tile[y+1][x];
					tile[y+1][x] = tile[y+2][x];
					tile[y+2][x] = tile[y+1][x+1];
					tile[y+1][x+1] = tile[y-1][x+1];
					tile[y-1][x+1] = tmpTile;
				}
				else
				{
					tile[y-2][x] = tile[y-1][x-1];
					tile[y-1][x-1] = tile[y+1][x-1];
					tile[y+1][x-1] = tile[y+2][x];
					tile[y+2][x] = tile[y+1][x];
					tile[y+1][x] = tile[y-1][x];
					tile[y-1][x] = tmpTile;
				}
			}
			else if(rotation.equalsIgnoreCase("Right"))
			{
				if(y%2==0)
				{
					tile[y-2][x] = tile[y-1][x+1];
					tile[y-1][x+1] = tile[y+1][x+1];
					tile[y+1][x+1] = tile[y+2][x];
					tile[y+2][x] = tile[y+1][x];
					tile[y+1][x] = tile[y-1][x];
					tile[y-1][x] = tmpTile;
				}
				else
				{
					tile[y-2][x] = tile[y-1][x];
					tile[y-1][x] = tile[y+1][x];
					tile[y+1][x] = tile[y+2][x];
					tile[y+2][x] = tile[y+1][x-1];
					tile[y+1][x-1] = tile[y-1][x-1];
					tile[y-1][x-1] = tmpTile;
				}
			}
			else
				System.out.println("Bad Rotation Call.");
		}
	}
	
	
	public int getScore() 
	{
		return score;
	}
	public int getLevel() 
	{
		return level;
	}
	public int getComborem() 
	{
		return comborem;
	}
	public int getNumOfColumns()
	{
		return columns;
	}
	public int getNumOfRows()
	{
		return rows;
	}
	public Tile getTile(int x, int y)
	{
		return tile[y][x];
	}
	
	
	
}
/*
 * 
----(0,16)---(1,16)----(2,16)---(3,16)---(4,16)
(0,15)---(1,15)---(2,15)---(3,15)---(4,15)----
----(0,14)---(1,14)----(2,14)---(3,14)---(4,14)
(0,13)---(1,13)---(2,13)---(3,13)---(4,13)----
----(0,12)---(1,12)---(2,12)---(3,12)---(4,12)
(0,11)---(1,11)---(2,11)---(3,11)---(4,11)----
----(0,10)---(1,10)---(2,10)---(3,10)---(4,10)
(0,9)----(1,9)----(2,9)----(3,9)----(4,9)----
----(0,8)----(1,8)----(2,8)----(3,8)----(4,8)
(0,7)----(1,7)----(2,7)----(3,7)----(4,7)----
----(0,6)----(1,6)----(2,6)----(3,6)----(4,6)
(0,5)----(1,5)----(2,5)----(3,5)----(4,5)----
----(0,4)----(1,4)----(2,4)----(3,4)----(4,4)
(0,3)----(1,3)----(2,3)----(3,3)----(4,3)----
----(0,2)----(1,2)----(2,2)----(3,2)----(4,2)
(0,1)----(1,1)----(2,1)----(3,1)----(4,1)----
----(0,0)----(1,0)----(2,0)----(3,0)----(4,0)

 */
