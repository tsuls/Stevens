package Lab8;
//I pledge my honor that i have abided by the stevens honor system
//Tyler Sulsenti



public class InsertionSort 
{
	public static final String NAME = "Tyler Sulsenti";
	
	/**
	 * Sorts a given array in a given order
	 * 
	 * @param a	the array to be sorted
	 * @param order		the order to sort
	 * @return	a sorted array in the given order
	 */
	public int[] sort(int[] a,String order)
	{
		if(order.equals("asc"))
		{
			for(int i = 0; i < a.length; i++)
			{
				int key = a[i];
				int curIndex = i;
				while(curIndex > 0 && a[curIndex-1] > key)
				{
					a[curIndex] = a[curIndex-1];
					curIndex--;
				}			
				a[curIndex] = key;		
			}
		}
		else if(order.equals("desc"))
		{
			for(int i = 0; i < a.length; i++)
			{
				int key = a[i];
				int curIndex = i;
				while(curIndex > 0 && a[curIndex-1] < key)
				{
					a[curIndex] = a[curIndex-1];
					curIndex--;
				}
				
				a[curIndex] = key;			
			}
		}
		return a;
	}
}
