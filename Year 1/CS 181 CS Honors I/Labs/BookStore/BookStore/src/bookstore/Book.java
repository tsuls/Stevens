package bookstore;

public abstract class Book 
{
	private final String author;
	private final int cost;
	private final Medium medium;
	private final String title;
	
	public Book(String t, String a, int c, Medium m)
	{
		title = t; 
		author = a;
		cost = c;
		medium = m;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public double getCost()
	{
		return cost;
	}
	
	public String getMedium()
	{
		return medium + "";
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public abstract boolean isForSale();
	
	public String toString()
	{
		return title + "\n " + author + "\n" + medium; 
	}
}