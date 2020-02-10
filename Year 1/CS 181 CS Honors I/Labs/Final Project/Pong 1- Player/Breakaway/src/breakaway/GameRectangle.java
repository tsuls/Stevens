//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;
import javax.swing.*;
import java.awt.*;

public class GameRectangle
{
	int height, width, lX, lY;
	Color color;
	
	public GameRectangle(int x, int y, int w, int h, Color c)
	{
		height = h;
		width = w;
		lX = x;
		lY = y;
		color = c;
	}
	
	/**
	 * Fills the object so it can be painted on the screen	
	 * 
	 * @param g		graphics element for painting
	 */
	public void fill(Graphics g)
	{
		g.setColor(color);
		g.fillRect(lX, lY, width, height);
	}
	
	/**
	 * Moves the object to the given coordinates
	 * 
	 * @param x		the new X coordinate
	 * @param y		the new Y coordinate
	 */
	public void move(int x, int y)
	{
		lX = lX+x;
		lY = lY+y;
	}
	
	/**
	 * Gets the current height of the object
	 * 
	 * @return	the current height of the object
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Gets the current width of the object
	 * 
	 * @return		the current width of the object
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Gets the current left corner X coordinate of the object
	 * 
	 * @return		the current left corner X coordinate of the object
	 */
	public int getLX()
	{
		return lX;
	}
	
	/**
	 * Gets the current left corner Y coordinate of the object
	 * 
	 * @return		the current left corner Y coordinate of the object
	 */
	public int getLY()
	{
		return lY;
	}
	
	/**
	 * Gets the current middle X coordinate of the object
	 * 
	 * @return		the current middle X coordinate of the object
	 */
	public int getMidX()
	{
		return lX +  (width/2);
	}
	
	/**
	 * Gets the current middle Y coordinate of the object
	 * 
	 * @return		the current middle Y coordinate of the object
	 */
	public int getMidY()
	{
		return lY + (height/2);
	}
	
	/**
	 * Sets the left Y coordinate of the object
	 * 
	 * @param in		the new Y coordinate
	 */
	public void setLY(int in)
	{
		lY = in;
	}

	/**
	 * Sets the left X coordinate of the object
	 * 
	 * @param in		the new X coordinate
	 */
	public void setLX(int in) 
	{
		lX = in;	
	}
	
	/**
	 * Sets the width of the current object
	 * 
	 * @param in		the new width
	 */
	public void setWidth(int in)
	{
		width = in; 
	}
	
	/**
	 * Sets the height of the current object
	 * 
	 * @param in		the new height
	 */
	public void setHeight(int in)
	{
		height = in;
	}
	
	/**
	 * Gets a rectangle that bounds the object
	 * 
	 * @return	a rectangle that bounds the object
	 */
	public Rectangle getBounds() 
	{
		return new Rectangle(getLX(), getLY(), getWidth(), getHeight());
	}
}