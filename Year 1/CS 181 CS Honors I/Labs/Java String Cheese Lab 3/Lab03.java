/*
 * Lab03.java
 *
 * REPLACE THIS LINE WITH THE PLEDGE
 * REPLACE THIS LINE WITH YOUR NAME
 *
 */

package Lab3; /*DO NOT CHANGE!!!!!!*/

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
public class Lab03{
    
    public static final String NAME = ""; /*<<<insert your name here*/

    /**
     * Given a binary periodic string, return an double value
     * Accepts any input ("._" should return 0)
     * 
     * @param s  the binay string, in the format of D.A_P
     * @return   the double value of the binary string
     */
    public static double binaryPeriodic(String s){

	return 0.0;
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
    public static String longestSubstring(String s1, String s2){
	return "";
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
    public static String threeNPlusOne(int n){
	return "";
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
	 */
    }
    
}
