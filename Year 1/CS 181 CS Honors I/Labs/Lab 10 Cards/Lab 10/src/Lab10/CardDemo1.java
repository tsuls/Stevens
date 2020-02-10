//File   : GUI-lowlevel/cards1/cards/CardDemo1
//Purpose: Basic GUI to show dragging cards.
//       Illustrates how to load images from files.
//Author : Fred Swartz - 2007-02-19 - Placed in public domain.
//
//Enhancements:
//     * This really doesn't have a user interface beyond dragging.
//       It doesn't do anything, and therefore has no model.
//       Make it play a game.
//     * Needs to have a Deck class to shuffle, deal, ... Cards.
//       Persumably based on ArrayList<Card>.
//     * Perhaps a Suit and Face class would be useful.
//     * Like Deck, there would also be a class for Hand.
//     * May need Player class too.

package Lab10;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//////////////////////////////////////////////////////////////class CardDemoGUI

 class CardDemo1 extends JFrame
 {
 //=================================================================== fields
 
 private static Card[] _deck = new Card[52];
 private static JButton resetButton;
 
 //===================================================================== main
 public static void main(String[] args) 
 {
     //Schedule a job for the event dispatch thread:
     //creating and showing this application's GUI.
     javax.swing.SwingUtilities.invokeLater(new Runnable() 
     {
	    public void run() {
	        createAndShowGUI();
	    }
	});
 }

 /**
  * Create the GUI and show it.  For thread safety,
  * this method should be invoked from the
  * event-dispatching thread.
  */
 private static void createAndShowGUI() 
 {

     CardDemo1 window = new CardDemo1();
     CardTable table = new CardTable(_deck);
     resetButton = new JButton("Reset");
     resetButton.addActionListener(new ActionListener()
     {
         public void actionPerformed(ActionEvent actionEvent) 
         {
       	  	table.reset();
         }
    });
     window.setTitle("Card Demo 1");
     window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     window.setLayout(new BorderLayout());
     window.add(table);
     window.add(resetButton,BorderLayout.SOUTH);
     window.pack();
     window.setLocationRelativeTo(null);
     window.setVisible(true);
 }
 
 //============================================================== constructor
 public CardDemo1() 
 {  
	
	//  resetButton.addActionListener(this);
	 int n = 0;         // Which card.
     int xPos = 0;      // Where it should be placed initially.
     int yPos = 0;
     
     //... Read in the cards using particular file name conventions.
     //    Images for the backs and Jokers are ignored here.
     String suits = "shdc";
     String faces = "a23456789tjqk";
    
     for (int suit=0; suit < suits.length(); suit++) 
     {
         for (int face=0; face < faces.length(); face++) 
         {
             //... Get the image from the images subdirectory.
             String imagePath = "./src/Lab10/images/" + faces.charAt(face) +
                     suits.charAt(suit) + ".gif";
            
             
             ImageIcon img = new ImageIcon(imagePath);
             
           
             //... Create a card and add it to the deck.
             Card card = new Card(img);
             card.moveTo(xPos, yPos);
             card.setInitPos(xPos, yPos);
             _deck[n] = card;
             
             //... Update local vars for next card.
             xPos += 12;
             yPos += 4;
             n++;
         }
     }
 }

 /*ActionListener actionListenerButton = new ActionListener()
 {
      public void actionPerformed(ActionEvent actionEvent) 
      {
    	  System.out.println("FUCK");
      }
 };*/
}
