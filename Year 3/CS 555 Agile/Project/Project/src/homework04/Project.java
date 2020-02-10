package homework04;

import java.io.*;
import java.time.*;
import java.util.*;


public class Project 
{	
	HashMap<Integer,List<String>> validTags;
	HashSet<String> ids;
	HashSet<String> famIds;
	Hashtable<String, String> dates;
	Hashtable<String, String> names;
	public List<String[]> indiDetails;
	public List<String[]> famDetails;
	String tagsFilePath;
	String projFilePath;
	String[] saveIndi;
	String[] saveFam;	
	int counter;
	String prevTag;
	String famPrevTag;
	
	
	public Project(String tagsFilePath, String projFilePath)
	{
		this.tagsFilePath = tagsFilePath;
		this.projFilePath = projFilePath;
		this.indiDetails = new ArrayList<String[]>();
		this.famDetails = new ArrayList<String[]>();
		this.saveIndi = new String[9];
		this.saveFam = new String[8];
		this.counter = 0;
		this.prevTag = "";
		this.famPrevTag = "";
		this.dates = new Hashtable<String,String>();
		this.ids = new HashSet<String>();
		this.famIds = new HashSet<String>();
		this.names = new Hashtable<String, String>();
		initDates();
	}
	
	private void initDates()
	{
		dates.put("JAN", "01");
		dates.put("FEB", "02");
		dates.put("MAR", "03");
		dates.put("APR", "04");
		dates.put("MAY", "05");
		dates.put("JUN", "06");
		dates.put("JUL", "07");
		dates.put("AUG", "08");
		dates.put("SEP", "09");
		dates.put("OCT", "10");
		dates.put("NOV", "11");
		dates.put("DEC", "12");	
	}
	
	public boolean checkValidTag(int id, String tag)
	{
		//Unsupported id
		if(validTags.get(id) == null)
		{
			return false;
		}
		
		return validTags.get(id).contains(tag);
	}
	
	public String getTag(String projLine)
	{
		String tag = "";
		
		//Handle INDI
		if(projLine.contains("INDI") || (projLine.contains("FAM") && (!projLine.contains("FAMC") && !projLine.contains("FAMS"))))
		{
			if(projLine.contains("INDI"))
			{
				//index of where INDI starts
				int index = projLine.indexOf("INDI");
				//Get INDI
				tag = projLine.substring(index, index + "INDI".length() );		
			}
					
			//Handle FAM and determine if tag is FAM or FAMC/S
			if(projLine.contains("FAM") && (!projLine.contains("FAMC") && !projLine.contains("FAMS")))
			{
				//index of where FAM starts
				int index = projLine.indexOf("FAM");
				//Get FAM
				tag = projLine.substring(index, index + "FAM".length());
			}
		}
		else
		{
	    	try 
	    	{
	    		 tag = projLine.substring(2).substring(0,projLine.substring(2).indexOf(" "));
	    	}
	    	catch(StringIndexOutOfBoundsException e)
	    	{
	    		 tag = projLine.substring(2).substring(0);
	    	}
		}
		
		return tag;
	}
	
	private void printArr()
	{
		for(int i = 0; i < saveIndi.length; i++)
		{
			System.out.println(saveIndi[i]);
		}
	}
	
	private void printArr(String[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			System.out.println(arr[i]);
		}
		System.out.println("----------------------------");
	}
	
	private void printList(List<String[]> list)
	{
		//System.out.println(details.size());
		for(int i = 0; i < list.size(); i++)
		{
			printArr(list.get(i));
		}
	}
	
	public void fillIndiList(String tag, String data)
	{
		if(tag.equals("INDI"))
		{
			if(!ids.contains(data))
			{
				saveIndi = new String[9];
			}
			else
			{
				ids.add(data);
			}

			saveIndi[0] =  data;
			
			//set defaults
			saveIndi[5] = "True"; //Alive
			saveIndi[6] = "N/A"; //Death
			saveIndi[7] = "N/A"; //Child
			saveIndi[8] = "N/A"; //Spouse
		}

		if(tag.equals("NAME"))
		{
			saveIndi[1] = data;
			if(saveIndi[0] != null)
				names.put(saveIndi[0],saveIndi[1]);
		}
		if(tag.equals("SEX"))
		{
			saveIndi[2] = data;
		}
		if(tag.equals("DATE"))
		{
			//HANDLE AGE
			if(prevTag.equals("BIRT"))
			{
				//Save the date
				String[] dataArr = getFormattedDate(data);
				saveIndi[3] = dataArr[4];
				
				//automated Test
				checkDates(dataArr);
				
				//Handle age
				LocalDate today = LocalDate.now(); //Todays date                         
				LocalDate birthday = LocalDate.of(Integer.parseInt(dataArr[3]), Integer.parseInt(dataArr[2]), Integer.parseInt(dataArr[1]));  //Birth date
				Period p = Period.between(birthday, today);
				
				//Age will be the caluclated year
				saveIndi[4] = "" + p.getYears();
			}
		
			if(prevTag.equals("DEAT"))
			{
				//Save the date
				String[] dataArr = getFormattedDate(data);
				checkDates(dataArr);
    			
    			saveIndi[5] = "False"; //Dead
				saveIndi[6] = dataArr[4];; //Death date
			}
		}
		if(tag.equals("FAMS")) //Spouses
		{
			saveIndi[7] = "{" + data + "}";
		}
		
		if(tag.equals("FAMC")) //Children
		{
			saveIndi[8] = "{" + data + "}";
			indiDetails.add(saveIndi);
		}
		
		prevTag = tag;
	}
	
	private void fillFamList(String tag, String data)
	{
		if(tag.equals("FAM"))
		{
			if(!famIds.contains(data))
			{
				saveFam = new String[8];
			}
			else
			{
				famIds.add(data);
			}

			saveFam[0] =  data;
			
			//set defaults
			saveFam[1] = "N/A"; //Married
			saveFam[2] = "N/A"; //Divorced
			saveFam[3] = "N/A"; //HusbandID
			saveFam[4] = "N/A"; //HusbandName
			saveFam[5] = "N/A"; //WifeID
			saveFam[6] = "N/A"; //WifeName
			saveFam[7] = "N/A"; //Children

		}
		
		if(tag.equals("HUSB"))
		{
			//Husband ID
			saveFam[3] = data;
			//Get Husband Name
			saveFam[4] = names.get(data);
		}
		
		if(tag.equals("WIFE"))
		{
			//Wife ID
			saveFam[5] = data;
			//Get Wife Name
			saveFam[6] = names.get(data);
		}
		
		if(tag.equals("CHIL"))
		{
			saveFam[7] = "{" + data + "}";
		}
		
		if(tag.equals("DATE"))
		{
			if(famPrevTag.equals("MARR"))
			{
				String[] dataArr = getFormattedDate(data);	
				checkDates(dataArr);
				saveFam[1] = dataArr[4];
				famDetails.add(saveFam);
			}
			else if(famPrevTag.equals("DIV"))
			{
				String[] dataArr = getFormattedDate(data);	
				checkDates(dataArr);
				saveFam[2] = dataArr[4];
			}
		}
		
		famPrevTag = tag;
	}
	//Takes in a date formatted in DD-MMM-YYYY and returns a String array
	//where 0 is the original date, 1 is the day, 2 is the month in MM, 3 is the year and 4 is the new formatted date in YYYY-MM-DD
	private String[] getFormattedDate(String data)
	{
		//Store the data
		String[] rtn = new String[5];
		rtn[0] = data;
		//Get Day
		//Find where the day number ends
		int daySplitIndex = data.indexOf(" ");
		String day = data.substring(0,daySplitIndex);
		rtn[1] = day;
				
		//Get Month
		//Find out where the day ends and take into account the space (1)
		int monthIndex = daySplitIndex + 1;
		//Get the remaining String for other units.
		String month = data.substring(monthIndex);
		String year = month;
		//Find out where month ends in the string
		int monthSplitIndex = month.indexOf(" ");
		//Get the month
		month = month.substring(0,  monthSplitIndex);
		//Get the number of the month
		month = dates.get(month);
		rtn[2] = month;
		
		//Get Year
		//Get the rest of the string and split it where the month ended. Include +1 for the space
		year = year.substring(monthSplitIndex + 1);
		rtn[3] = year;
		rtn[4] = year + "-" + month + "-" + day;
			
		return  rtn;
	}
	
	//Returns the components of a date formatted in YYYY MM DD into an array where 0 is YYYY, 1 is MM and 2 is DD
	private String[] getComponents(String date)
	{
		String[] rtn = new String[3]; 
	
		int yearIndex = date.indexOf("-");
		String year = date.substring(0,yearIndex);
		rtn[0] = year;
		
		String month = date.substring(yearIndex + 1);
		String day = month;
		int monthIndex = month.indexOf("-");
		month = month.substring(0,monthIndex);
		rtn[1] = month;
		
		day = day.substring(monthIndex + 1);
		rtn[2] = day;
		
		return rtn;
	}
	
	//This automated method will check the two refactors made in the code.
	private void checkDates(String[] data)
	{
		Boolean pass = true;
		String[] newComponents = getComponents(data[4]);
		//Dates must be in YYYY MM DD format
		for(int i = 0; i < data.length; i++)
		{
			
			
			//Check Year
			if(data[3].length() != 4)
			{
				pass = false;
				System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[3].length() + " YEAR IS GREATER THAN THE EXPECTED VALUE OF 4");
			}
			//Check Month length
			if(data[2].length() != 2)
			{
				pass = false;
				System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[2].length() + " MONTH IS GREATER THAN THE EXPECTED VALUE OF 2");
				//Check Month Validity
				if(Integer.parseInt(data[2]) < 1 || Integer.parseInt(data[2]) > 12)
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[2].length() + " MONTH IS NOT IN THE EXPECTED RANGE OF 01-12");
					
				}
			}
			//Check Day length
			if(data[1].length() != 2)
			{
				pass = false;
				System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[1].length() + " DAY IS GREATER THAN THE EXPECTED VALUE OF 2");
				//Check Day Validity
				if(Integer.parseInt(data[1]) < 1 || Integer.parseInt(data[1]) > 31)
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[1].length() + " DAY IS NOT IN THE EXPECTED RANGE OF 01-31");
				}
			}
				
			//Check date validity
			LocalDate origDate = LocalDate.of(Integer.parseInt(data[3]), Integer.parseInt(data[2]), Integer.parseInt(data[1])); //Todays date                         
			LocalDate newDate = LocalDate.of(Integer.parseInt(newComponents[0]), Integer.parseInt(newComponents[1]), Integer.parseInt(newComponents[2]));  //Birth date
			Period p = Period.between(origDate, newDate);
			
			if(!p.isZero())
			{
				pass = false;
				System.out.println("ERROR IN REFACTORED CODE FOR DATES: THE ORIGINAL DATE: " + data[0] + " IS DIFFERENT THAN THE NEW FORMATTED DATE: " + data[4]);
			}
			
		}
		
		if(!pass)
		{
			System.out.println("ERROS IN REFACTORED CODE FOUND PLEASE CHECK ABOVE FOR ERROR DETAILS");
		}
	}
	
	public void run() throws IOException
	{
		BufferedReader proj = null;
		BufferedReader tagsFile = null;
		validTags = new HashMap<Integer,List<String>>();
		List<String> list0 = new ArrayList<String>();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		boolean tagIsValid = false;
		
		String print = "";
	    
	    try 
	    {
	    	tagsFile = new BufferedReader(new FileReader(tagsFilePath));
	    	String txtLine = tagsFile.readLine();
	    	while(txtLine != null)
	    	{
	    		int id = Integer.parseInt(txtLine.substring(0,1));
	    		String tag = txtLine.substring(2);
		    
	    		switch(id)
		    	{
		    		case 0:
		    			list0.add(tag);
		    			break;
		    		
		    		case 1:
		    			list1.add(tag);
		    			break;
		    			
		    		case 2:
		    			list2.add(tag);
		    			break;
		    		
		    		default:
		    			System.out.println("Unsupported ID: " + id);
		    			break;
		    	}
	    	
	    		
	    		txtLine = tagsFile.readLine();
	    	}
	    	validTags.put(0,list0);
	    	validTags.put(1,list1);
	    	validTags.put(2,list2);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Tags File Not Found");
	    	e.printStackTrace();
	    }
	    
	    	
	   try 
	   {
	    	proj = new BufferedReader(new FileReader(projFilePath));
	    	String projLine = proj.readLine();
	    
	    	while(projLine != null)
	    	{	    		
	    		//get tag
	    		String tag = getTag(projLine);
	    		int id = Integer.parseInt(projLine.substring(0,1));
	    		//Check Validity
	    		tagIsValid = checkValidTag(id, tag);
	    		
	    		//Print line
	    		//System.out.println("--> "  + projLine);
	    		
	    		//Prints details
	    		print = print + "<-- ";
	    		
	    		//Print level
	    		String level = projLine.substring(0,1);
	    		print = print + level + "|";
	    		
	    		print = print + tag + "|";
	    		 //******************************************
	    		//Handle INDI and FAM  		
	    		if(tag.equals("INDI"))
	    		{
	    			if(projLine.indexOf("INDI") < 3)
	    			{
	    				tagIsValid = false;
	    			}
	    			else
	    			{
	    				tagIsValid = true;
	    			}
	    		}
	    		
	    		if(tag.equals("FAM"))
	    		{
	    			if(projLine.indexOf("FAM") < 3)
	    			{
	    				tagIsValid = false;
	    			}
	    			else
	    			{
	    				tagIsValid = true;
	    			}
	    		}
	    	
	    		
	    		//Check if tag is valid
	    		if(tagIsValid)
	    		{
	    			print = print + "Y|";
	    		}
	    		else
	    		{
	    			print = print + "N|";
	    		}
	    		
	    		//Print arguments
	    		String arguments = projLine.substring(2).substring(projLine.substring(2).indexOf(" ") + 1);
	    	
	    		if(tag.equals("INDI") || tag.equals("FAM"))
	    		{
	    			String temp = projLine.substring(projLine.indexOf(" ") + 1);
	    			arguments = temp.substring(0,temp.indexOf(" "));
	    		}
	    		print = print + arguments + "\n";
	    		
	    		//System.out.print(print);
	    		print = "";
	    		
	    		fillIndiList(tag, arguments);
	    		fillFamList(tag, arguments);
	    		
	    		projLine = proj.readLine();
	    	}

	    	//Adjust
	    	if(indiDetails.get(0)[0] == null)
	    		indiDetails.remove(0);
	    	if(famDetails.get(0)[0] == null)
	    		famDetails.remove(0);
	    	
	    	printList(indiDetails);
	    	printList(famDetails);
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Project File Not Found");
	    	e.printStackTrace();
	    }   
	    
	}
}
