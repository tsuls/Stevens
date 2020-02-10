/*
 * Test.java
 */
package Lab7;

/**
 * Tests that the CheckedInteger program properly compiles
 * Does not test that methods actually work (that's your job)
 *
 * @author Justin Barish
 * @version 1.0
 * @since 20161107
 *
 */


/* NOTE!!! THIS IS NOT A TEST PROGRAM. IT IS NOT ALL SUFFICIENT TO TEST YOUR METHODS.
 * THIS ONLY IS TO CHECK IF YOUR PROGRAM CONTAINS ALL OF THE NEEDED METHODS TO COMPILE. 
 * PUT THIS FILE IN THE SAME FOLDER AS YOUR PROGRAM, AND COMPILE BOTH. RUN THIS PROGRAM.
 * AS LONG AS IT COMPILED, YOU ARE (MOST LIKELY) GOING TO GET FULL COMPILATION POINTS.
 */
public class Test {
    
    
    public static void main (String[] args)
    {
	CheckedInteger i = new CheckedInteger();
	CheckedInteger j = new CheckedInteger(1);
	CheckedInteger k = new CheckedInteger(new CheckedInteger());
	CheckedInteger a;	
	System.out.println(CheckedInteger.NAME);
	

	/* None of the methods should change the value of i.
	 * Rather, they must return a NEW checkedInteger. 
	 */
	String s= i.toString();
	/*if the try block is commented out, your code 
	 * should not compile becuase your errors must be checked
	 */

	try {
	    a = i.abs();
	    
	    a = i.add(1);
	    a = i.add (j);
	    a = i.negate();
	    a = i.sub(1);
	    a = i.sub(j);
	    a = i.mul(1);
	    a = i.mul(j);
	}catch (IntegerOverflowException ioe){
	    ioe.getOperands();
	    System.out.println(ioe.getOperator());
	    System.out.println(ioe.getMessage());
	}
	int c = j.compareTo(i);
	a = i.div(1);
	a = i.div(j);
	int in = a.intValue();
	long l= a.longValue();
	float f = a.floatValue();
	double d = a.doubleValue();
	
    }
}