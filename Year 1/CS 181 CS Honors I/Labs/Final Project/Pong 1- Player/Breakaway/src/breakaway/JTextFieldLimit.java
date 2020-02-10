//I pledge my honor that i have abided by the Stevens honor system
//Tyler Sulsenti
//Sara Dos Santos
package breakaway;

import javax.swing.text.AttributeSet;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends JTextField 
{
    private int limit;

    public JTextFieldLimit(int l) 
    {
        super();
        limit = l;
    }

    protected Document createDefaultModel() 
    {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument
    {
    	/**
    	 * Makes it impossible for a user to input a string more characters in length than the limit
    	 */
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException 
        {
            if (str == null)
            {
            	return;
            }

            if ((getLength() + str.length()) <= limit) 
            {
                super.insertString(offset, str, attr);
            }
        }       

    }

}