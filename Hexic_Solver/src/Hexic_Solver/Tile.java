package Hexic_Solver;

public class Tile
{
	private short primaryType;
	private short secondaryType;
	
	public Tile(short type, short detail)
	{
		primaryType = type;
		secondaryType = detail;
	}
	public Tile(short type)
	{
		primaryType = type;
		secondaryType = 0;
	}
	public short getType()
	{
		return primaryType;
	}
	public void setType(short type)
	{
		primaryType = type;
	}
	public short getDetail()
	{
		return secondaryType;
	}
}

/*
 * 5 initial colors:  red, yellow, green, blue, violet
 * 2 secondary colors: pink, emerald
 * 
 * colored tile types: blank, star, bomb
 * special pieces: star, black-up, black-down
 */

/*
 * primaryType:
 * 0 = red
 * 1 = yellow
 * 2 = green
 * 3 = blue
 * 4 = violet
 * 5 = pink
 * 6 = emerald
 * 7 = star piece
 * 8 = black (swaps bottom-left, up, bottom-right)
 * 9 = black (swaps up-left, up-right, bottom)
 * 
 * secondaryType for primaryType 0-6
 * 0 = blank
 * 1 = star
 * 2 = bomb
 * 
 * secondaryType for primaryType 7-9
 * always set to 0. or ignore
 */
