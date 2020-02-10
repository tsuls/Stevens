package Stevens.CS181.Lab5;
/**
 * @author Tyler Sulsenti
 *
 *I Pledge my honor that i have abided by the stevens honor system	
 *Tyler Sulsenti
 */
public class Die 
{

    public static final int MAX = 6;

    private int faceValue;

    //empty constructor, initial value 1
    public Die() 
    { 
    	faceValue=1;
    }
    
    /**
     * Rolls the dice to generate 1-MAX
     * 
     * @return	 int a random value 1-MAX
     * 
     */
    public int roll() 
    {
    	faceValue = (int)(Math.random () * MAX) +1;
    	return faceValue;
    }

    /**
     * Sets the Face Value of the die
     * 
     * @param	faceValue, current faceValue
     * 
     */
    public void setFaceValue(int faceValue)
    {
    	if (faceValue > 0 && faceValue <= MAX)
    	{
    		this.faceValue = faceValue;
    	}
    }

    /**
     * gets the current face value
     * 
     * @return	 int the current face value
     * 
     */
    public int getFaceValue() 
    { 
    	return faceValue; 
    }

    /**
     * returns a String representation of the object
     * 
     * @param	a String representation of the object
     * 
     */
    public String toString()
    {   
    	return Integer.toString(faceValue); 
    }
}
