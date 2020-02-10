package Project4;

import org.junit.Test;

import project3.Project;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class TestJunit 
{
	Project project04 = new Project("resources/tags.txt","resources/Project04.ged");
	@Test
	
	public void testAdd()
	{	
		try 
		{
			project04.run();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		LocalDate curDate = LocalDate.now();
		Period p;
		
		assertNotNull(project04);
		assertNotNull(project04.indiDetails);
		assertNotNull(project04.famDetails);
		
		for(int i = 0; i < project04.famDetails.size(); i++)
		{	
			//Get Divorce Date
			String date = project04.famDetails.get(i)[2];
					
			if(!date.equals("N/A"))
			{
				String dyear = date.substring(0,date.indexOf("-"));
				String dmonth = date.substring(date.indexOf("-") + 1).substring(0, date.substring(date.indexOf("-") + 1).indexOf("-"));
				String dday = date.substring(date.indexOf("-") + 1).substring(date.substring(date.indexOf("-") + 1).indexOf("-") + 1);
				
				//Get Husband ID
				String husID = project04.famDetails.get(i)[3];
				//Get Wife ID
				String wifID = project04.famDetails.get(i)[5];
				
				//Get Husband & Wife Death Date
				String husDeat = getDeathDate(husID);
				String wifDeat = getDeathDate(wifID);
				
					//Check Husband
					if(!husDeat.equals("N/A"))
					{
						String year = husDeat.substring(0,husDeat.indexOf("-"));
						String month = husDeat.substring(husDeat.indexOf("-") + 1).substring(0, husDeat.substring(husDeat.indexOf("-") + 1).indexOf("-"));
						String day = husDeat.substring(husDeat.indexOf("-") + 1).substring(husDeat.substring(husDeat.indexOf("-") + 1).indexOf("-") + 1);
						
						
						p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), 
								LocalDate.of(Integer.parseInt(dyear), Integer.parseInt(dmonth), Integer.parseInt(dday)));
						
						assertFalse(p.isNegative());
						assertFalse(p.isZero());
					}
					if(!wifDeat.equals("N/A"))
					{
						String year = wifDeat.substring(0,wifDeat.indexOf("-"));
						String month = wifDeat.substring(wifDeat.indexOf("-") + 1).substring(0, wifDeat.substring(wifDeat.indexOf("-") + 1).indexOf("-"));
						String day = wifDeat.substring(wifDeat.indexOf("-") + 1).substring(wifDeat.substring(wifDeat.indexOf("-") + 1).indexOf("-") + 1);
						
						
						p = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), 
								LocalDate.of(Integer.parseInt(dyear), Integer.parseInt(dmonth), Integer.parseInt(dday)));
						
						assertFalse(p.isNegative());
						assertFalse(p.isZero());
					}
				}
				
			}
			
		}
	
	
	public String getDeathDate(String ID)
	{
		for(int i = 0; i < project04.indiDetails.size(); i++)	
		{
			if(project04.indiDetails.get(i)[0].equals(ID))
			{
				return project04.indiDetails.get(i)[6];
			}
		}
		return "N/A";
	}
	

}
