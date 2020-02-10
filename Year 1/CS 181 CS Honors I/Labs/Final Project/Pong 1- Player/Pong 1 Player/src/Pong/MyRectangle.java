package Pong;

import javax.swing.*;
import java.awt.*;

public class MyRectangle
{
	private int lX, lY;
	Color color;
	private int width,height;

	
	public MyRectangle(int x, int y, int w, int h, Color c1)
	{
		color = c1;
		width = w;
		height = h;
		lX = x;
		lY = y;
	}

	public void fill(Graphics g)
	{
		g.fillRect(lX, lY, width, height);
	}
	public boolean containsPoint(int x, int y)
	{	
		int xRange = lX + width;
		int yRange = lY + height;
		
		if(x >= lX && x <= xRange)	
		{
			if(y >= lY && y <= yRange)
			{
				return true;
			}
		}
		return false;
	
	}

	public void move(int x, int y)
	{
		lX = lX+x;
		lY = lY+y;
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width;
	}
	public int getLX()
	{
		return lX;
	}
	public int getLY()
	{
		return lY;
	}
	public void setLY(int in)
	{
		lY = in;
	}

	public void setLX(int in) 
	{
		lX = in;	
	}
	
}