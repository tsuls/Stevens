package Pong;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;	//for mouse events

public class MyPanel extends JPanel 
{
	private MyRectangle rect;
	private MyCircle circle;
	private int rX,rY;
	private int velocity;
	private boolean contain;
	private javax.swing.Timer timer;
	private int width, height;
	private int score;
	private JLabel theLabel;
	private final int MAXVEL = 6;
	private int startDir;
	
	public MyPanel (Color backGroundColor, int inWidth, int inHeight)
	{
		setBackground(backGroundColor);
		score = 0;
		startDir = 45;
		theLabel = new JLabel();
	    theLabel.setForeground(Color.WHITE);
		width = inWidth;
		height = inHeight;
		rect = new MyRectangle((int) (width*.75),133, 20, 100, Color.WHITE);
		circle = new MyCircle(width/50,height/2,15,Color.WHITE);
		
		contain = false;
		rX = 0;
		rY = 0;
		timer = new javax.swing.Timer(5, new MoveListener());
		timer.start();
		circle.setDirection(startDir);
		velocity = 2;
		circle.setVelocity(velocity);
		addMouseListener(new PanelListener());
		addMouseMotionListener(new PanelMotionListener());
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		setRect();
		
		
		
		this.add(theLabel,BorderLayout.NORTH);
		theLabel.setText("Hits:" + score);
		theLabel.setFont(new Font("Serif", Font.BOLD, 40));

		
		int draw = 1;
		for(int i = 0; i <= getHeight(); i = i + 20 )
			{
				switch(draw)
				{	
					case 1: g.drawLine(getWidth()/2, i, getWidth()/2, i + 20);
							draw = 2;
							break;
					
					case 2: draw = 1;
							break;		
				}
			}
		
		rect.fill(g);
		circle.fill(g);
	
	}
	public void setCircle()
	{
		circle.setX(width/20);
		circle.setY(getHeight()/2);
		circle.setDirection(startDir);
	}
	public void setRect()
	{
		rect.setLX((int) (getWidth() *.75));
	}
	private class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			contain = rect.containsPoint(e.getX(), e.getY());
			rX = e.getX();
			rY = e.getY();
			repaint();
		}
	}
	
	private class PanelMotionListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e)
		{
			if(contain)
			{
				rect.move(0, e.getY()-rY);
				rY = e.getY();
				repaint();
			}
		}
	}
	
	private class MoveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a) 
		{			
			boolean x = circle.getRight() >= rect.getLX() && circle.getRight() <= rect.getLX() + rect.getWidth();
			boolean y = circle.getBottom() >= rect.getLY() && circle.getTop() <= rect.getLY() + rect.getHeight();
			boolean rectLeft = circle.getRight() == rect.getLX() && y;
			
			boolean rectTop = circle.getCX() >= rect.getLX() && circle.getCX() <= rect.getLX() + rect.getWidth() && circle.getBottom() == rect.getLY();								
			boolean rectBottom = circle.getCX() >= rect.getLX() && circle.getCX() <= rect.getLX() + rect.getWidth() && circle.getTop() == rect.getLY() + rect.getHeight();													
			
			boolean right = circle.getRight() >= getWidth();
			boolean left = circle.getLeft() <= 0;
			boolean top = circle.getTop() <= 0;
			boolean bottom = circle.getBottom() >= getHeight();
			 

			if(top || bottom || rectTop || rectBottom)
			{
				if(rectTop || rectBottom)
				{
					score++;
					if(velocity < MAXVEL && score % 5 == 0)
					{
						velocity++;
						circle.setVelocity(velocity);
					}
				}
				
				circle.setDirection(360 - circle.getDir());
			}
			else if(right || left || rectLeft)
			{
				if(rectLeft)
				{
					score++;
					if(velocity < MAXVEL && score % 5 == 0)
					{
						velocity++;
						circle.setVelocity(velocity);
					}
				}
				if(right)
				{
					setCircle();
					score = 0;
					velocity = 2;
					circle.setVelocity(velocity);
				}
				if(circle.getDir() >= 0 && circle.getDir() <= 180)
				{
					circle.setDirection(180 - circle.getDir());
				}
				else
				{
					circle.setDirection(360 - (circle.getDir() - 180));
				}
			}
			circle.move();
			repaint();
		}
	}
} 