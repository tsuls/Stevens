//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import java.awt.Color;
import java.awt.Graphics;

public class Life
{
	int cX,cY;
	int radius,direction;
	int velocity;
	Color color;
	
	public Life(int x, int y,int rad, Color clr)
	{
		cX = x;
		cY = y;
		color = clr;
		radius = rad;
	}

	/**
	 * Fills the object so it can be painted on the screen	
	 * 
	 * @param g		graphics element for painting
	 */
	public void fill(Graphics g)
	{
		g.fillOval(cX - radius, cY - radius, radius * 2, radius * 2);
		g.setColor(color);
	}
}