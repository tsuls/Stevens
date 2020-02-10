package Lab8;


public class Test 
{
	public static void main (String[] args)
	{
		InsertionSort i = new InsertionSort();
		SelectionSort s = new SelectionSort();
		QuickSort q = new QuickSort(); 
		
		int[] a = new int[1000000];
		
		//Insertion
		//int[] b = i.sort(a, "asc");
		//int[] c = i.sort(a, "desc");
		
		//Selection
		//int[] d = s.sort(a, "asc");
		//int[] e = s.sort(a, "desc");
		
		//Quick
		//int[] f = q.sort(a, "asc");
		//int[] g = q.sort(a, "desc");
		
		//1,000,000
		int[] h = randomizer(a);
		long StartTime = System.nanoTime(); //Start timer
		
		h = i.sort(a, "asc");
		
		long TotalTime = System.nanoTime() - StartTime; //print time
        System.out.println("Total elapsed time (milliseconds) " + "is: "
                + TotalTime + "\n");
		//Display
		/*for(int j = 0; j < h.length; j++)
		{
			System.out.print("[" + h[j] + "]");
		}*/
	}
	
	public static int[] randomizer(int[] a)
	{
		for(int i = 0; i < 10000; i++ )
		{
			a[i] = (int) Math.floor(Math.random() * Integer.MAX_VALUE);
		}
		return a;
	}
}
