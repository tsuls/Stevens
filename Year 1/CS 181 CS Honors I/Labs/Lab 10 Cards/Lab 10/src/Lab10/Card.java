//File   : GUI-lowlevel/cards1/cards/Card.java
//Purpose: Represents one card.
//Author : Fred Swartz - February 19, 2007 - Placed in public domain.
//
//Enhancements:
//       * Needs to have Suit and Face value.
package Lab10;


import java.awt.*;
import javax.swing.*;

/////////////////////////////////////////////////////////////////////////// Card
 class Card {
 //=================================================================== fields
 private ImageIcon _image;
 private ImageIcon back;
 private String    imgStr;
 private int       _x;
 private int       _y;
 private int 	   initX;
 private int	   initY;
 private boolean   isFlip;
 
 //============================================================== constructor
 public Card(ImageIcon image) 
 {
     _image = image;
     back = new ImageIcon("./src/Lab10/images/b.gif");
     isFlip = false;
     imgStr = _image.toString();
 }
 
 //=================================================================== moveTo
 public void moveTo(int x, int y) {
     _x = x;
     _y = y;
 }
 
 //================================================================= contains
 public boolean contains(int x, int y) {
     return (x > _x && x < (_x + getWidth()) && 
             y > _y && y < (_y + getHeight()));
 }
 
 //================================================================= getWidth
 public int getWidth() {
     return _image.getIconWidth();
 }
 
 //================================================================ getHeight
 public int getHeight() {
     return _image.getIconHeight();
 }
 
 //===================================================================== getX
 public int getX() {
     return _x;
 }
 
 //===================================================================== getY
 public int getY() {
     return _y;
 }
 //=====================================================================
 /**
  * Changes this card's image
  * 
  * @param imgPath	the path of the new image
  */
 private void changeImg(String imgPath)
 {
	 _image = new ImageIcon(imgPath);
 }
//=====================================================================
 /**
  * Flips this card between the back and original image
  */
 public void flip()
 {
	 if(isFlip == false)
	 {
		 changeImg(back.toString());
		 isFlip = true;
	 }
	 else if(isFlip == true)
	 {
		 changeImg(imgStr);
		 isFlip = false;
	 }
 }
//=====================================================================
 /**
  * Sets the initial positions for the Card
  * 
  * @param x		the initial X POS
  * @param y		the initial Y POS
  */
 public void setInitPos(int x, int y)
 {
	 initX = x;
	 initY = y;
 }
//=====================================================================
 /**
  * Resets the Card's position and image to its initials
  */
 public void reset()
 {
	 moveTo(initX, initY);
	 changeImg(imgStr); 
 }
 //===================================================================== draw
 public void draw(Graphics g, Component c) {
     _image.paintIcon(c, g, _x, _y);
 }
}
