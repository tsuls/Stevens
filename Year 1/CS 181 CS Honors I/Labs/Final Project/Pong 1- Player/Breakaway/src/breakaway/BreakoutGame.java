//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;

public class BreakoutGame 
{
	/**
	 * Main method
	 */
	public static void main(String[] args)
	{
		JFrame theJFrame = new JFrame();
		theJFrame.setTitle("BreakAway");
	    theJFrame.setSize(400, 400);
	    theJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    theJFrame.setResizable(false);
	    	    
	    GamePanel gamePanel = new GamePanel(Color.BLACK, 400, 400);
		gamePanel.setLayout(null);
		gamePanel.setSize(400,400);
	    
	    theJFrame.add(gamePanel);
	        
	    theJFrame.setVisible(true);
	    
	}
}