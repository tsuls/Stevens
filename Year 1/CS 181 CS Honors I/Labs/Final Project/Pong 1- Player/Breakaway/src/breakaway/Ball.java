//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ball
{
	private double cX;
	private double cY;
	private int radius,direction;
	private double velocity;
	private Color color;
	private JLabel powerUpLabel;
	
	public Ball(int x, int y,int rad, Color clr)
	{
		cX = x;
		cY = y;
		color = clr;
		radius = rad;
		velocity = 2;
	}
	
	public Ball(int x, int y,int rad, Color clr, JLabel text)
	{
		cX = x;
		cY = y;
		color = clr;
		radius = rad;
		velocity = 2;
		powerUpLabel = text;
	}
	
	/**
	 * Fills the object so it can be painted on the screen	
	 * 
	 * @param g		graphics element for painting
	 */
	public void fill(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int)cX - radius, (int)cY - radius, radius * 2, radius * 2);
		if(powerUpLabel != null)
		{
			powerUpLabel.setFont(new Font("Helvetica", Font.BOLD, radius));
			powerUpLabel.setForeground(Color.WHITE);
			powerUpLabel.setSize(35,30);
			powerUpLabel.setLocation((int)cX - radius, (int)cY - 15);
		}
	}
	
	/**
	 * Helper method to move the circle to the coordinates 
	 * 
	 * @param d		new center X coordinate
	 * @param e		new center Y coordinate
	 */
	public void move(double d, double e)
	{
		cX = cX + d;
		cY = cY + e;
	}

	/**
	 * Takes into account the velocity and direction of the ball to move it
	 */
	public void move()
	{
		
		move (velocity * Math.cos(Math.toRadians(direction)),
			(velocity * Math.sin(Math.toRadians(direction))));
	}
	/**
	 * Returns the JLabel on this object
	 * 
	 * @return		the JLabel on this object
	 */
	public JLabel getLabel()
	{
		return powerUpLabel;
	}
	
	/**
	 * Sets the direction of the ball
	 * 
	 * @param degrees	the new direction
	 */	
	public void setDirection(int degrees)
	{
		direction = degrees;
	}		
	
	/**
	 * Sets the velocity of the ball
	 * 
	 * @param d		the new velocity
	 */
	public void setVelocity(double d)
	{
		 velocity = d;
	}
	
	/**
	 * Sets the center X coordinate of the ball
	 * 
	 * @param initCX	the new center X coordinate
	 */
	public void setX(double initCX)
	{
		cX = initCX;
	}
	
	/**
	 * Sets the center Y coordinate of the ball
	 * 
	 * @param y		the new center Y coordinate
	 */
	public void setY(double y)
	{
		cY = y;
	}
	
	/**
	 * Gets the leftmost coordinate of the ball
	 * 
	 * @return		the leftmost coordinate of the ball
	 */
	public double getLeft()
	{
		return cX - radius;
	}
	
	/**
	 * Gets the rightmost coordinate of the ball
	 * 
	 * @return		the rightmost coordinate of the ball
	 */
	public double getRight()
	{
		return cX + radius;
	}
	
	/**
	 * Gets the topmost coordinate of the ball
	 * 
	 * @return		the topmost coordinate of the ball
	 */
	public double getTop()
	{
		return cY - radius;
	}
	/**
	 * Gets the bottom most coordinate of the ball
	 * 
	 * @return		the bottom most coordinate of the ball
	 */
	public double getBottom()
	{
		return cY + radius;
	}
	/**
	 * Gets the current direction of the ball
	 * 
	 * @return		the current direction of the ball
	 */
	public int getDir()
	{
		return direction;
	}
	
	/**
	 * Gets the current center Y coordinate of the ball
	 * 
	 * @return		the current center Y coordinate of the ball
	 */
	public double getCY()
	{
		return cY;
	}
	
	/**
	 * Gets the current center X coordinate of the ball
	 * 
	 * @return		the current center X coordinate of the ball
	 */
	public double getCX()
	{
		return cX;
	}
	
	/**
	 * Gets the current radius of the ball
	 * 
	 * @return		the current radius of the ball
	 */
	public int getRadius()
	{
		return radius;
	}
	
	/**
	 * Gets the current color of the ball
	 * 
	 * @return		the current color of the ball
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Gets a rectangle that bounds the ball
	 * 
	 * @return	a rectangle that bounds the ball
	 */
	public Rectangle getBounds() 
	{
		return new Rectangle((int)getLeft(),(int) getTop(), (int) radius*2, (int) radius*2);
	}

	/**
	 * Gets the current velocity of the ball
	 * 
	 * @return		the current velocity of the ball
	 */
	public double getVelocity() 
	{
		return velocity;
	}
}