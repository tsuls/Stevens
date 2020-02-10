/*
 * Lab03.java
 *
 * REPLACE THIS LINE WITH THE PLEDGE
 * REPLACE THIS LINE WITH YOUR NAME
 *
 */

package Lab3; /*DO NOT CHANGE!!!!!!*/

import java.util.ArrayList;

/**
 * ENTER A BRIEF DESCRIPTION OF WHAT YOUR PROGRAM DOES HERE
 *
 * @author Tyler Sulsenti
 * @version 1.0
 * @since 20160921
 * 
 */

public class Lab03_SulsentiTyler{
    
    public static final String NAME = "Tyler Sulsenti";

    /**
     * Given a binary periodic string, return an double value
     * Accepts any input ("._" should return 0)
     * 
     * @param s  the binay string, in the format of D.A_P
     * @return   the double value of the binary string
     */
    public static double binaryPeriodic(String s)
    {
    	String af = s.substring(s.indexOf(".")+1);
    	String bef = s.substring(0,s.indexOf("."));
    	double x = 0;
    	double ans = 0;
    	if(s.equals("._"))
    	{
    		return 0.0;
    	}
    	
    	if(af.length() == 4)//case 1
    	{
        	int k = af.length();
        	int afterDecimal = Integer.parseInt(binToDec(af));
        	x = afterDecimal/((Math.pow(2.0, k))-1);
        	ans = x;
    	}
    	else if(af.length() > 4 && Integer.parseInt(bef) > 0)//case 3
    	{
    		int z = Integer.parseInt(binToDec(bef));
    		int u = Integer.parseInt(af.substring(0,1));
    		int l = 1;
    		int k = af.length()-1;
        	int afterDecimal = Integer.parseInt(binToDec(af.substring(1)));
        	x = afterDecimal/((Math.pow(2.0, k))-1);
        	double y = ((x + u)/Math.pow(2, l))+z;
        	ans = y;
    	}
    	else if(af.length() > 4) //case 2
    	{
    		int afterDecimal = Integer.parseInt(binToDec(af.substring(1)));
    		int k = af.length()-1;
    		x = afterDecimal/((Math.pow(2.0, k))-1);
    		int l = 1;
    		int u = Integer.parseInt(af.substring(0,1));
    		
    		double y = (x + u)/Math.pow(2, l);
    		ans = y;   		
    	} 	
    	return ans;
    }
    
   /**
    * Given a string, find the longest substring fowards and backwards
    * More details in lab description
    * Accepts any input (including empty strings)
    *
    * @param s1  String 1 to be processed
    * @param s2  String 2 to be processed
    * @return    a SPACE deliminated string with 4 values:
    *            longest substring when both strings forward,
    *            longest substring s1 backward, longest substring s2 backward
    *            longest substring both backward.
    *            Example return is "car car rac rac"
    */
    public static String longestSubstring(String s1, String s2)
    {	
    	String[] s1Subs = getAllSubs(s1); //Get all possible substrings
    	String[] s2Subs = getAllSubs(s2);
    	sortSubsByLen(s1Subs);//sort them by length
    	sortSubsByLen(s2Subs);
    	String fin = "";
    	String x;

    	x = findEqual(s1Subs, s2Subs);
    	if(x != null)
    	{
    		fin = fin + x;
    	}
    	
    	s1 = reverseString(s1);//Reverse the first only
    	s1Subs = getAllSubs(s1);//Get all possible substrings
    	sortSubsByLen(s1Subs);//sort them by length

    	x = findEqual(s1Subs, s2Subs);
    	if(x != null)
    	{
    		fin = fin + " " + x;
    	}
    	
    	s1 = reverseString(s1);//Change the first back to normal. Reverse the second only.
    	s2 = reverseString(s2);
    	s1Subs = getAllSubs(s1); //Get all possible substrings
    	s2Subs = getAllSubs(s2);
    	sortSubsByLen(s1Subs);//sort them by length
    	sortSubsByLen(s2Subs);
    	x = findEqual(s1Subs, s2Subs);
    	if(x != null)
    	{
    		fin = fin + " " + x;
    	}
    	
    	s1 = reverseString(s1);//Reverse the first again.
    	s1Subs = getAllSubs(s1); //Get all possible substrings
    	sortSubsByLen(s1Subs);//sort them by length
    	x = findEqual(s1Subs, s2Subs);
    	if(x != null)
    	{
    		fin = fin + " " + x;
    	}
    			
    	return fin;  	
    }

   /**
    * Computes the intermediate steps of the 3n+1 conjecture
    * 
    * @requires n to be a positive, non-zero integer 
    *      ^^The requires flag tells you what input conditions
    *        your method requires. So this flag means your method
    *        does not need to handle negative nums or 0. Yay!
    *
    * @param  n  the number to be processed
    * @return    the string of intermediate steps, seperated by SPACES. 
    *            The last character of the string, based on the conjecture
    *            should always be a 1
    */
    public static String threeNPlusOne(int n)
    {
    	String fin = "";
    	while(n != 1) //n will go to 1
    	{
    		if(n % 2 != 0 )//if odd
    		{
    			n = (3*n)+1;
    			fin = fin + n + " ";		
    		}
    		else
    		{
    			n = (n/2); //if even
    			fin = fin + n + " ";
    		}
    	}
    	return fin;
    }
    /**
     * Determines if a given string is already in a given ArrauList
     *
     * @param  myList the list to be scanned
     * @param check the String to look for
     * 
     * @return    true if the string is found in the list. False if its not
     */
    public static boolean inList(ArrayList<String> myList,String check)
    {
    	for(int i = 0; i < myList.size();i++)
    	{
    		if(check.equals(myList.get(i)))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Gets all the possible substrings in a given string
     *
     *
     * @param  s  the String to be used
     * @return   an array containing each possible substring 
     */
    public static String[] getAllSubs(String s)
    {
    	ArrayList<String> subs = new ArrayList<String>(); //Easier to work with
    	for(int i = 0; i < s.length(); i++)
    	{
    		if(!inList(subs,s.substring(i,i+1)))//prevents doubles
    		{
    			subs.add(s.substring(i,i+1)); //adds each indivdual letter
    		}
    	}
    	for(int i = 0; i < s.length(); i++)
    	{
    		if(!inList(subs,s.substring(i)))
    		{
    			subs.add(s.substring(i));//add each sub from the front
    		}
    	}
    	for(int i = s.length(); i >= 0; i--)
    	{
    		if(!inList(subs,s.substring(0,i)))
    		{
    			subs.add(s.substring(0,i));//add each sub from the back
    		}
    	}
    	
    	String[] sub = new String[subs.size()];//easier to sort
    	for(int i = 0; i < subs.size(); i++)
    	{
    		sub[i] = subs.get(i);//convert to array
    	}
    	
    	return sub;
    }
    /**
     * Sorts the Strings in the given array by descending lengths
     *
     *
     * @param  sub the array to sort
     * @return  a sorted array
     */
    public static String[] sortSubsByLen(String[] sub)
    {
    	for(int i = 1; i < sub.length; i++)//Insertion sort
    	{
    		for(int j = i; j > 0; j--)
    		{
    			if(sub[j].length() > sub[j-1].length()) //Sort the array into longest to shortest
    			{
    				String temp = sub[j];
    				sub[j] = sub[j-1];
    				sub[j-1] = temp;
    			}
    		}
    	}
    	return sub;
    }
    /**
     * Finds the first equal String between to given arrays
     *
     *
     * @param  s1Subs first array to be used
     * @param  s2Subs second array to be used
     * @return   the first equal string between the two given arrays. Null if there are none found.
     */
    public static String findEqual(String[] s1Subs, String[] s2Subs)
    {
    	for(int i = 0; i < s1Subs.length; i++)//compare each substring
    	{
    		for(int j = 0; j < s2Subs.length; j++)
    		{
    			if(s1Subs[i].equals(s2Subs[j]))
    			{
    				return s1Subs[i];
    			}
    		}
    	}
    	return null;
    }
    /**
     * Reverses a given string
     *
     *
     * @param  s a string
     * @return   a reversed string of the given string
     */
    public static String reverseString(String s)
    {
    	String rev = "";
    	
    	for(int i = s.length(); i > 0; i--)
    	{
    		rev = rev + s.substring(i-1, i);
    		s = s.substring(0, i);
    	}
    	return rev;
    }
	/**
	 * Converts a given binary number to decimal
	 * @param the String representation of a binary number to convert to decimal
	 * @return Returns a String representation of the given binary number, converted to decimal.
	 */
    public static  String binToDec(String input)
	{
		int pos = 0; //The position of the first character in any given string
		int num = Integer.parseInt(input.substring(pos,pos+1)); //Grab the first Character in that string.
		
		if(pos < input.length()-1) /*if the position is less than the length - 1 (Because Length starts counting at One and 
									*my Position & Substring start counting at 0)*/
		{
			return (Integer.parseInt(binToDec(input.substring(pos+1))) + (num * ((int) Math.pow(2, input.length()-1)))) + ""; //Run The Method again, moving working with a substring of the rest of the number after the current position. 
																																	   //Convert the returned number to an int value so it may be used as such.
																																	   // To that, Concatenate (Since we are returning a String), the product of our current number at our current position by 
																																	   // and 2, our binary base, to the power of the length of our current substring minus one, since we must start counting from the power of 0.
		}
		else
		{
			return "" + (num * ((int) Math.pow(2, input.length()-1))); //Else, When the current position reaches the length - 1, perform the algorithm on the final int-converted substring and then convert back to String using  + "".
		}
	}
    public static void main(String[] args){

	/* IMPORTANT (NEW FOR THIS LAB 3):
	 *
	 * PLEASE COMMENT OUT YOU ENTIRE MAIN FUNCTION BEFORE SUBMITTING. 
	 * THERE SHOULD ADDITIONALLY BE NO PRINTLN STATEMENTS IN ANY OF THE ABOVE METHODS
	 *
	 * This main method is for your testing purpose only. There should
	 * be NO code in this method that enables the above methods to properly function
	 *
	 * Feel free to add additional methods
	 * Any new methods you create you  MUST also write javadocs
	 * However, you CANNOT delete or modify any of the given method headers
	 *
	 * If you have any questions, please ask a CA for clarification
	 * 
	 * Remember that you CANNOT use any java libraries to convert bin->int
	 * Doing so will result in a 0 on the assignment
	 *
	 * You may delete this comment block after you have read it.
	 * Good Luck, and enjoy your String Cheese!
	 * 
	 * 
	 * 
	 */
    	
    	
    	//System.out.println(binaryPeriodic("10.00011"));
    	//System.out.println(longestSubstring("jeans","snake"));
    	System.out.println(threeNPlusOne(7));
    	
    	
    }
    
}
