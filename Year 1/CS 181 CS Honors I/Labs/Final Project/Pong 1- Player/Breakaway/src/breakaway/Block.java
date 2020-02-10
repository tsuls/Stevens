//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import javax.swing.*;
import java.awt.*;


public class Block extends GameRectangle 
{
	
	public Block(int x, int y, int w, int h, int l, Ball b) 
	{
		super(x, y, w, h, getColorType(l));	
	}
	
	public void fill(Graphics g)
	{
		super.fill(g);
		g.setColor(Color.BLACK);
		g.drawRect(super.getLX(), super.getLY(), super.getWidth(), super.getHeight());		
	}
	
	/**
	 * Gets a color determined by the given int
	 * 
	 * @param layer		the int to determine the color
	 * @return			the color that corresponds to the given int
	 */
	public static Color getColorType(int layer)
	{
		Color c = Color.WHITE;
		
		switch(layer)
		{
			case 1:	c = Color.RED;
					break;
			
			case 2:	c = Color.ORANGE;
				break;
			
			case 3:	c = Color.ORANGE;
				break;
			
			case 4:	c = Color.YELLOW;
				break;
			
			case 5:	c = Color.GREEN;
				break;
			
			case 6:	c = Color.CYAN;
				break;
			
			case 7:	c = Color.BLUE;
				break;
			
			case 8:	c = Color.PINK;
				break;
			
			case 9:	c = Color.MAGENTA;
				break;
			
			case 10: c = Color.MAGENTA;		
		}		
		return c;
	}
}