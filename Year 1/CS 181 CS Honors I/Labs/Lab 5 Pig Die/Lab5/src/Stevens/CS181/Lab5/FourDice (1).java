package Stevens.CS181.Lab5;

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
	
	public void roll()
	{
		d1.roll();
		d2.roll();
		d3.roll();
		d4.roll();
	}
	
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
	
	public boolean ThreeOnes()
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
	
	
	public boolean fourOnes()
	{
		return (d1.getFaceValue() == 1) & (d2.getFaceValue() == 1) & 
				(d3.getFaceValue() == 1) & (d4.getFaceValue() == 1);
	}
	
	public int getDiceTotal()
	{
		return d1.getFaceValue() + d2.getFaceValue() + d3.getFaceValue() + d4.getFaceValue();
	}
	
	protected ArrayList<Die> createDieList(Die d1, Die d2, Die d3, Die d4)
	{
		ArrayList<Die> dieList = new ArrayList<Die>();
		dieList.add(d1);
		dieList.add(d2);
		dieList.add(d3);
		dieList.add(d4);
		return dieList;	
	}
	
	 public String toString()
	 { 
	    return "(" + d1 + "," + d2 + "," + d3 + "," + d4 + ")"; 
	 }
}
