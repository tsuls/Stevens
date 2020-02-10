package Lab8;
//I pledge my honor that i have abided by the stevens honor system
//Tyler Sulsenti


public class QuickSort 
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
		quickSortHelper(a, 0, a.length-1, order);
		return a;
	}
	
	/**
	 * assists in the recursive quicksorting of an array
	 * 
	 * @param a	the array to be sorted
	 * @param low the first index in the array
	 * @param high	the last index in the array
	 * @param order	the order to sort in
	 */
	public void quickSortHelper(int[] a, int low, int high, String order)
	{
		if(low < high)
		{
			if(order.equals("asc"))
			{
				int pivot = partitionAsc(a, low, high);
				quickSortHelper(a, low, pivot - 1, order);
				quickSortHelper(a, pivot + 1, high, order);
			}
			else if(order.equals("desc"))
			{
				int pivot = partitionDesc(a, low, high);
				quickSortHelper(a, low, pivot - 1, order);
				quickSortHelper(a, pivot + 1, high, order);
			}
		}
	}
	
	/**
	 * partitions the array so it can be sorted in ascending order
	 * 
	 * @param a		the array to be sorted
	 * @param low	the first index in the array
	 * @param high	the last index in the array
	 * @return	the sorted value
	 */
	public int partitionAsc(int[] a, int low, int high)
	{
		int pivot = a[low];
		int curLeft = low + 1;
		int curRight = high;
		boolean fin = false;
		
		while(!fin)
		{
			while(curLeft <= curRight && a[curLeft] <= pivot)
			{
				curLeft++;
			}
			while(a[curRight] >= pivot && curRight >= curLeft)
			{
				curRight--;
			}
			if(curRight < curLeft)
			{
				fin = true;
			}
			else
			{
				int x = a[curLeft];
				a[curLeft] = a[curRight];
				a[curRight] = x;
			}
		}
		int y = a[low];
		a[low] = a[curRight];
		a[curRight] = y;
		return curRight;		
	}
	
	
	/**
	 * partitions the array so it can be sorted in descending order
	 * 
	 * @param a		the array to be sorted
	 * @param low	the first index in the array
	 * @param high	the last index in the array
	 * @return	the sorted value
	 */
	public int partitionDesc(int[] a, int low, int high)
	{
		int pivot = a[low];
		int curLeft = low + 1;
		int curRight = high;
		boolean fin = false;
		
		while(!fin)
		{
			while(curLeft <= curRight && a[curLeft] >= pivot)
			{
				curLeft++;
			}
			while(a[curRight] <= pivot && curRight >= curLeft)
			{
				curRight--;
			}
			if(curRight < curLeft)
			{
				fin = true;
			}
			else
			{
				int x = a[curLeft];
				a[curLeft] = a[curRight];
				a[curRight] = x;
			}
		}
		int y = a[low];
		a[low] = a[curRight];
		a[curRight] = y;
		return curRight;
	}
}
