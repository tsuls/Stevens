package bookstore;
public class AudioBook extends Book 
{
	private final Integer numDiscs;

	public AudioBook(String t, String a, int c, Integer n) 
	{
		super(t, a, c, Medium.Audio);
		numDiscs = n;
	}
	
	public String getMedium()
	{
		return super.getMedium() + " " + numDiscs;
	}
	
	public boolean isForSale()
	{
		return false;
	}
	
	public String toString()
	{
		return super.toString() + ":{" + numDiscs + "} discs";
	}

}
