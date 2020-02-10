package bookstore;

public class PaperbackBook extends Book 
{	
	public PaperbackBook(String t, String a, Integer c) 
	{
		super(t,a,c,Medium.Paperback);		
	}

	public boolean isForSale()
	{
		return true;
	}
	
	public String toString()
	{
		return super.toString() + ".";
	}
}
