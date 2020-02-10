
//Tyler Sulsenti
//Brett Conetta
//Stephen Prospero
//Michael Morano
import java.awt.*;

public class MyCircle
{
	int cX,cY;
	int radius,direction;
	int velocity;
	Color color;
	
	public MyCircle(int x, int y,int rad, Color clr)
	{
		cX = x;
		cY = y;
		color = clr;
		radius = rad;
	}

	public void fill(Graphics g)
	{
		g.fillOval(cX - radius, cY - radius, radius * 2, radius * 2);
	}
	public void move(int x, int y)
	{
		cX = cX + x;
		cY = cY + y;
	}

	public void move()
	{
		
		move ((int)(velocity * Math.cos(Math.toRadians(direction))),
			(int)(velocity * Math.sin(Math.toRadians(direction))));
	}

	public void setDirection(int degrees)
	{
		direction = degrees;
	}		
	public void setVelocity(int v)
	{
		 velocity = v;
	}
	public void setX(int x)
	{
		cX = x;
	}
	
	public void setY(int y)
	{
		cY = y;
	}
	public int getLeft()
	{
		return cX - radius;
	}
	public int getRight()
	{
		return cX + radius;
	}
	public int getTop()
	{
		return cY - radius;
	}
	public int getBottom()
	{
		return cY + radius;
	}
	public void reverseDir()
	{
		setDirection(180);
	}
	public int getDir()
	{
		return direction;
	}

	public int getCY()
	{
		return cY;
	}
	public int getCX()
	{
		return cX;
	}
}