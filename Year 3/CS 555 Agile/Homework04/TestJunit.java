package homework04;

import org.junit.Test;

import project3.Project;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class TestJunit 
{
	@Test
	
	public void testAdd()
	{
		Project project03 = new Project("resources/tags.txt","resources/Project02.ged");
		
		try 
		{
			project03.run();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		LocalDate curDate = LocalDate.now();
		Period p;
		
		assertNotNull(project03);
		assertNotNull(project03.indiDetails);
		assertNotNull(project03.famDetails);
		
		for(int i = 0; i < project03.indiDetails.size(); i++)
		{
			//Birth
			String date = project03.indiDetails.get(i)[3];
			String year = date.substring(0,date.indexOf("-"));
			String month = date.substring(date.indexOf("-") + 1).substring(0, date.substring(date.indexOf("-") + 1).indexOf("-"));
			String day = date.substring(date.indexOf("-") + 1).substring(date.substring(date.indexOf("-") + 1).indexOf("-") + 1);
			
			p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now());
			
			assertFalse(p.isNegative());
			assertFalse(p.isZero());
			assertNotNull(date);
			assertNotNull(p);
			
			//Death
			date = project03.indiDetails.get(i)[6];
			//If the person didn't die, don't run the test
			if(!date.equals("N/A"))
			{
				year = date.substring(0,date.indexOf("-"));
				month = date.substring(date.indexOf("-") + 1).substring(0, date.substring(date.indexOf("-") + 1).indexOf("-"));
				day = date.substring(date.indexOf("-") + 1).substring(date.substring(date.indexOf("-") + 1).indexOf("-") + 1);
				
				p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now());
				
				assertFalse(p.isNegative());
				assertFalse(p.isZero());
				assertNotNull(date);
				assertNotNull(p);
			}
			
		}
		
		for(int i = 0; i < project03.famDetails.size(); i++)
		{
			//Marriage
			String date = project03.famDetails.get(i)[1];
			//If the person isn't married, don't run the test
			if(!date.equals("N/A"))
			{
				String year = date.substring(0,date.indexOf("-"));
				String month = date.substring(date.indexOf("-") + 1).substring(0, date.substring(date.indexOf("-") + 1).indexOf("-"));
				String day = date.substring(date.indexOf("-") + 1).substring(date.substring(date.indexOf("-") + 1).indexOf("-") + 1);
				
				p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now());
				
				assertFalse(p.isNegative());
				assertFalse(p.isZero());
				assertNotNull(date);
				assertNotNull(p);
			}
			
			//Divorce
			 date = project03.famDetails.get(i)[2];
			//If the person isn't married, don't run the test
			if(!date.equals("N/A"))
			{
				String year = date.substring(0,date.indexOf("-"));
				String month = date.substring(date.indexOf("-") + 1).substring(0, date.substring(date.indexOf("-") + 1).indexOf("-"));
				String day = date.substring(date.indexOf("-") + 1).substring(date.substring(date.indexOf("-") + 1).indexOf("-") + 1);
				
				p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now());
				
				assertFalse(p.isNegative());
				assertFalse(p.isZero());
				assertNotNull(date);
				assertNotNull(p);
			}
		}
	}
}
