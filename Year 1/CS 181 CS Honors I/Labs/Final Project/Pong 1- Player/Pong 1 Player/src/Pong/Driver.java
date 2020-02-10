package Pong;

import javax.swing.*;    

import java.awt.*;      

public class Driver
{

 public static void main(String[] args)
 {     
    JFrame theJFrame = new JFrame();
	  theJFrame.setTitle("Pong");
    theJFrame.setSize(400, 400);
    theJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    MyPanel thePanel = new MyPanel(Color.BLACK, 400, 400);
    
    JLabel theLabel = new JLabel("Hits: " );
    theLabel.setForeground(Color.WHITE);
    theLabel.setSize(50, 50);
   
    
    theJFrame.add(thePanel);  
    theJFrame.setVisible(true);
 }
}


