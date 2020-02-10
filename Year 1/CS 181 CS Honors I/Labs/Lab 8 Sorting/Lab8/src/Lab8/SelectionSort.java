package Lab8;
//I pledge my honor that i have abided by the stevens honor system
//Tyler Sulsenti


public class SelectionSort
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
				int minDex = i;
				for(int j = i; j < a.length; j++)
				{
					if(a[j] < a[minDex])
					{
						minDex = j;
					}
				}
				int temp = a[i];
				a[i] = a[minDex];
				a[minDex] = temp;		
			}
		}
		else if(order.equals("desc"))
		{
			for(int i = 0; i < a.length; i++)
			{
				int maxDex = i;
				for(int j = i; j < a.length; j++)
				{
					if(a[j] > a[maxDex])
					{
						maxDex = j;
					}
				}
				int temp = a[i];
				a[i] = a[maxDex];
				a[maxDex] = temp;		
			}
		}
		return a;
	}
}
