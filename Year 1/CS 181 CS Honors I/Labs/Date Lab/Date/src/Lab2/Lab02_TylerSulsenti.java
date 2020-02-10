/*
 * Lab02_TylerSulsenti.java
 *
 * I Pledge my Honor that i have abided by the Stevens Honor System
 * Tyler Sulsenti
 *
 */

package Lab2;

/**
 * ENTER A BRIEF DESCRIPTION OF WHAT YOUR PROGRAM DOES HERE
 *
 * IN THE LINES BELOW, FILL IN FIELDS WITH APPROPRIATE RESPONSES. DELETE THIS LINE.
 * @author NAME
 * @version 1.0
 * @since YYYYMMDD
 * 
 */

/*
 * Your program can only have ONE class
 * Change the class declaration line to add_YOURNAME after Lab02.
 * IMPORTANT: THE DECLARATION LINE MUST BE public class Lab02_yourname{
 * MAKE SURE YOU KEEP THE BRACKET ON THE SAME LINE AS THE CLASS DECLARATION!!
 * You may delete this comment block after you read it.
 */
public class Lab02_TylerSulsenti
{
    
    public static final String NAME = "Tyler Sulsenti";

    /**
     * Given a string, in the format of yyyymmdd, extracts the day of the 
     * week the date falls on
     *
     * @param s  the string, in the format of yyyymmdd to be processed
     * @return   the day of the week, as a String
     */
    public static String getDay(String s)
    {
    	int w, d = 0, m = 0, y = 0;
    	int yyyy = Integer.parseInt(s.substring(0,4));
    	int mm = Integer.parseInt(s.substring(4,6)); 
    	int dd = Integer.parseInt(s.substring(6));
    	
    	if(!dateValid(s)) //check if valid
    	{
    		return "The Given Date(s) are invalid";
    	} 	
    	
    	if(mm >= 3 && mm <= 12) //Setting up for calculation 
    	{
    		d = dd;
    		m = mm;
    		y = yyyy;  		
    	}
    	else if(mm >= 1 && mm <= 2)
    	{
    		d = dd;
    		m = mm + 12;
    		y = yyyy - 1;
    	}
    	
    	w = (d - 1 + ((13*(m+1))/5) + y + (y/4) - (y/100) + (y/400)) % 7;
    	switch(w)
    	{
    		case 0: return "Sunday";
    		
    		case 1: return "Monday";
    		
    		case 2: return "Tuesday";
    		
    		case 3: return "Wednesday";
    		
    		case 4: return "Thursday";
    		
    		case 5: return "Friday";
    		
    		case 6: return "Saturday";
    	}
		return null;
    }

   /**
    * Given a string date and a number, computes the day of the week n days 
    * after (or before) the date given
    *
    * @param s  the string, in the format of yyyymmdd to be processed
    * @param n  the number of days after (or before if negative) 
    *           the specified date
    * @return   the new date,as a String in yyyymmdd format
    */
    public static String newDate(String s, int n)
    {
    	String yyyy = s.substring(0,4);
    	String mm = s.substring(4,6); 
    	String dd = s.substring(6);
    	int maxDays = getMaxDays(Integer.parseInt(mm), Integer.parseInt(yyyy));
    	int maxMonths = 12;
    	
    	if(!dateValid(s)) //check if valid
    	{
    		return "The Given Date(s) are invalid";
    	}
    	
    	if(Integer.parseInt(dd) + n <= maxDays) //if new date will be in the given month
    	{
    		if(mm.length() == 1) //formatting
        	{
        		mm = "0" + mm;
        	}
        	if(dd.length() == 1)
        	{
        		dd = "0" + dd;
        	}
    		
    		return yyyy + mm + (Integer.parseInt(dd) + n); // add
    	}
    	else
    	{
    		while(n > 0)
    		{
    			if(Integer.parseInt(dd) == maxDays) // Checks to see if going into next month
    			{
    				dd = "01"; //reset days if so
    				
    				if(Integer.parseInt(mm) + 1 > maxMonths) // Checks to see if going into next year
    				{
    					mm = "01"; //reset month if so
    					yyyy = "" + (Integer.parseInt(yyyy) + 1); //increment year
    				}
    				else
    				{
    					mm = "" + (Integer.parseInt(mm) + 1); //increment month if not incrementing year
    				}
    				
    				n--;
    			}
    			else
    			{
    				dd = "" + (Integer.parseInt(dd) + 1); //Increment day if not incrementing month 
    				n--;
    			}
    		}   		
    	}
    	
    	if(mm.length() == 1) //formatting
    	{
    		mm = "0" + mm;
    	}
    	if(dd.length() == 1)
    	{
    		dd = "0" + dd;
    	}
    	return yyyy + mm + dd;
    }

   /**
    * Given two dates, computes the number of days inbetween them.
    *
    * @param start  the start date, in the format of yyyymmdd 
    * @param end    the end date
    * @return       the number of days between the two dates
    */
	public static int daysBetween(String start, String end)
     {
    	String[] dates = getLaterDate(start,end);    //The dates	
    	
    	if(dates == null) // if they are the same
    	{
    		return 0;
    	}
    	
    	start = dates[0];
    	end = dates[1];
    	
    	String yyyyS = start.substring(0,4);
    	String mmS = start.substring(4,6); 
    	String ddS = start.substring(6);
    	
    	String yyyyE = end.substring(0,4);
    	String mmE = end.substring(4,6); 
    	String ddE = end.substring(6);
   	
    	int ans = 0;
    	
    	if(!dateValid(start) || !dateValid(end)) //Check if valid
    	{
    		System.out.println("The Given Date(s) are invalid");
    		return -1;
    	}
    	
    	if(mmS.equals(mmE)) //if same month
    	{
    		ans = Integer.parseInt(ddS) - Integer.parseInt(ddE);//subtract
    		if(ans < 0)
    		{
    			ans = Math.abs(ans);
    		}
    		return ans;
    	}
    	while(start.equals(end) == false)//while start and end are not the same
    	{
    		if(ddS.equals("01")) //Check for previous month
    		{
    			ddS = "" + getMaxDays((Integer.parseInt(mmS) - 1), Integer.parseInt(yyyyS)); //Go back to the prevoid month's max days
    			if(Integer.parseInt(mmS) - 1 <= 0) //Check for previous year
    			{
    				mmS = "12"; //go back to the last month
    				yyyyS = "" + (Integer.parseInt(yyyyS) - 1); //decrement year
    			}
    			else
    			{
    				mmS = "" + (Integer.parseInt(mmS) - 1); //decrement month if not decrementing year
    			}
    			ans++;//count
    		}
    		else
    		{
    			ddS = "" + (Integer.parseInt(ddS) - 1); //decrement day if not decrementing month
    			ans++;//count
    		}
    	    		
    		if(mmS.length() == 1)//format
        	{
        		mmS = "0" + mmS;
        	}
        	if(ddS.length() == 1)
        	{
        		ddS = "0" + ddS;
        	}
    		start = yyyyS+mmS+ddS;//decrement start. Bring it to the end string
    	}
		return ans;
    }
	 /**
	    * Takes in two dates and sorts them into which date comes later in time
	    * 
	    * @param one  the string, in the format of yyyymmdd to be sorted
	    * @param two the string, in the format of yyyymmdd to be sorted
	    * @return   an array of the two dates, with the date later in time coming first
	    */
    public static String[] getLaterDate(String one, String two)
    {
    	String[] dates = new String[2];
    	String yyyyO = one.substring(0,4);
    	String mmO = one.substring(4,6); 
    	String ddO = one.substring(6);
    	
    	String yyyyT = two.substring(0,4);
    	String mmT = two.substring(4,6); 
    	String ddT = two.substring(6);
    	
    	if(Integer.parseInt(yyyyO) > Integer.parseInt(yyyyT)) //compare years
    	{
    		dates[0] = one;
    		dates[1] = two;
    		return dates;
    	}
    	else if(Integer.parseInt(yyyyO) < Integer.parseInt(yyyyT))
    	{
    		dates[0] = two;
    		dates[1] = one;
    		return dates;
    	}
    	else 
    	{
    		if(Integer.parseInt(mmO) > Integer.parseInt(mmT))//compare months
    		{
    			dates[0] = one;
        		dates[1] = two;
        		return dates;
    		}
    		else if(Integer.parseInt(mmO) < Integer.parseInt(mmT))
    		{	
    			dates[0] = two;
    			dates[1] = one;
    			return dates;
    		}
    		else
    		{
    			if(Integer.parseInt(ddO) > Integer.parseInt(ddT))//compare days
    			{
    				dates[0] = one;
    	    		dates[1] = two;
    	    		return dates;
    			}
    			else if(Integer.parseInt(ddO) < Integer.parseInt(ddT))
    			{
    				dates[0] = two;
    	    		dates[1] = one;
    	    		return dates;
    			}
    			else     			{
    				return null;//they are the same
    			}
    		}
    	}
    }
    /**
	    * Gets the max days for the given month in the given year
	    * 
	    * @param mm  the given month
	    * @param yyyy the given year
	    * @return   returns the max number of days in the given month in the given year
	    */
    public static int getMaxDays(int mm, int yyyy)
    {
    	int maxDays = 0;
    	switch(mm) //check each month
    	{
    		case 1: maxDays = 31;
    				break;
    			
    		case 2: if(yyyy % 4 == 0 && yyyy % 100 != 0 || yyyy % 400 == 0) //check leap year
    				{
    					maxDays = 29;
    				}
    				else
    				{
    					maxDays = 28;
    				}	
    				break;
    				
    		case 3: maxDays = 31;
    				break;
    				
    		case 4: maxDays = 30;
    				break;
    		
    		case 5: maxDays = 31;
    				break;
    				
    		case 6: maxDays = 30;
    				break;
    				
    		case 7: maxDays = 31;
    				break;
    				
    		case 8: maxDays = 31;
    				break;
    				
    		case 9: maxDays = 30;
    				break;
    				
    		case 10: maxDays = 31;
    				 break;
    				 
    		case 11: maxDays = 30;
    				 break;
    				 
    		case 12: maxDays = 31;
    			     break; 		
    	}
    	return maxDays;
    }
    /**
	    * Takes in a date and determines if its valid
	    * 
	    * @param date the date to check
	    * @return true if the date is valid false if not
	    */
    public static boolean dateValid(String date)
    {
    	String yyyy = date.substring(0,4);
    	String mm = date.substring(4,6); 
    	String dd = date.substring(6);
    	
    	if(Integer.parseInt(yyyy) <= 0)//check year
    	{
    		return false;
    	}
    	else if(Integer.parseInt(mm) <= 0 || Integer.parseInt(mm) >= 13)//check month
    	{
    		return false;
    	}
    	else if(Integer.parseInt(dd) <= 0 || Integer.parseInt(dd) >= getMaxDays((Integer.parseInt(mm)), Integer.parseInt(yyyy))) //check day
    		
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public static void main(String[] args)
    {
    	System.out.println("The Day of the week for the Date inputed is " + getDay("20160929"));
    	System.out.println("The new Date is: " + newDate("20160929", 1000));
    	System.out.println("Number of Days in between: " + daysBetween("20160910", "20161020"));    	
    }
    
}
