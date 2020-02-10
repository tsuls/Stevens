package Stevens.CS181.Lab5;
/**
 * @author Tyler Sulsenti
 *
 *I Pledge my honor that i have abided by the stevens honor system	
 *Tyler Sulsenti
 */
import java.util.ArrayList;

public class FourDice 
{
	private Die d1;
	private Die d2;
	private Die d3;
	private Die d4;
	
	public FourDice()
	{
		d1 = new Die();
		d2 = new Die();
		d3 = new Die();
		d4 = new Die(); 
	}
	
	/**
     * Rolls the dice
     * 
     * @param a int count of the rolls
     */
	public void roll()
	{
		d1.roll();
		d2.roll();
		d3.roll();
		d4.roll();
	}
	
	/**
     * Determines if there is one 1 in the roll
     * 
     * @return   true if there is one 1 in the roll, false if not
     */
	public boolean oneOne()
	{
		ArrayList<Die> dieList = createDieList(d1,d2,d3,d4);
		for(int i = 0; i < dieList.size(); i++)
		{
			if(dieList.get(i).getFaceValue() == 1)
			{
				dieList.remove(i);
				i--;
			}
		}
		return(dieList.size() == 3);
	}
	
	/**
     * Determines if there are two 1s in the roll
     * 
     * @return   true if there are two 1s in the roll, false if not
     */
	public boolean twoOnes()
	{
		ArrayList<Die> dieList = createDieList(d1,d2,d3,d4);
		for(int i = 0; i < dieList.size(); i++)
		{
			if(dieList.get(i).getFaceValue() == 1)
			{
				dieList.remove(i);
				i--;
			}
		}
		return(dieList.size() == 2);
	}
	
	/**
     * Determines if there is are three 1s in the roll
     * 
     * @return   true if there are three 1s in the roll, false if not
     */
	public boolean threeOnes()
	{
		ArrayList<Die> dieList = createDieList(d1,d2,d3,d4);
		for(int i = 0; i < dieList.size(); i++)
		{
			if(dieList.get(i).getFaceValue() == 1)
			{
				dieList.remove(i);
				i--;
			}
		}
		return(dieList.size() == 1);
	}
	
	/**
     * Determines if there are four 1s in the roll
     * 
     * @return   true if there are four 1s in the roll, false if not
     */
	public boolean fourOnes()
	{
		return (d1.getFaceValue() == 1) & (d2.getFaceValue() == 1) & 
				(d3.getFaceValue() == 1) & (d4.getFaceValue() == 1);
	}
	
	/**
     * Determines the current total of the dice rolled
     * 
     * @return   the int value of the total of the dice rolled
     */
	public int getDiceTotal()
	{
		int sum = 0;
		
		if(d1.getFaceValue() != 1)
		{
			sum += d1.getFaceValue();
		}
		
		if(d2.getFaceValue() != 1)
		{
			sum += d2.getFaceValue();
		}
		
		if(d3.getFaceValue() != 1)
		{
			sum += d3.getFaceValue();
		}
		
		if(d4.getFaceValue() != 1)
		{
			sum += d4.getFaceValue();
		}
		
		return sum;
	}
	
	/**
     * Creates an ArrayList of all the dice
     * 
     * @param	 d1 a Die to add to the list
     * @param	 d2 a Die to add to the list
     * @param	 d3 a Die to add to the list
     * @param	 d4 a Die to add to the list
     * 
     * @return   An ArrayList of the given die
     */
	protected ArrayList<Die> createDieList(Die d1, Die d2, Die d3, Die d4)
	{
		ArrayList<Die> dieList = new ArrayList<Die>();
		dieList.add(d1);
		dieList.add(d2);
		dieList.add(d3);
		dieList.add(d4);
		return dieList;	
	}
	
	/**
     * Returns a String representation of this class
     * 
     * @return   a String representation of this class
     */
	 public String toString()
	 { 
	    return "(" + d1 + "," + d2 + "," + d3 + "," + d4 + ")"; 
	 }
}
