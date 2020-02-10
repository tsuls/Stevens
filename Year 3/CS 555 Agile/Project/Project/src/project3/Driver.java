package project3;

import java.io.IOException;

public class Driver 
{
	public static void main(String[] args) throws IOException
	{
		Project project03 = new Project("resources/tags.txt","resources/Project02.ged");
		
		project03.run();
	}
}
