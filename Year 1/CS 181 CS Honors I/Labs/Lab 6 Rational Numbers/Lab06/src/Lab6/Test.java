/*
 * Test.java
 */

package Lab6;


/**
 * Tests that the Rational program properly compiles
 * Does not test that methods actually work (that's your job)
 *
 * @author Justin Barish
 * @version 1.0
 * @since 20161025
 *
 */


/* NOTE!!! THIS IS NOT A TEST PROGRAM. IT IS NOT ALL SUFFICIENT TO TEST YOUR METHODS.
 * THIS ONLY IS TO CHECK IF YOUR PROGRAM CONTAINS ALL OF THE NEEDED METHODS TO COMPILE. 
 * PUT THIS FILE IN THE SAME FOLDER AS YOUR PROGRAM, AND COMPILE BOTH. RUN THIS PROGRAM.
 * AS LONG AS IT COMPILED, YOU ARE (MOST LIKELY) GOING TO GET FULL COMPILATION POINTS.
 */
public class Test {
    public static void main(String[] args){
	//System.out.println(Rational.NAME);
	Rational T = new Rational(12,7);
	Rational S = new Rational(2,9);
//	Rational T = new Rational();
	/*NOTE that this line should cause it to not compile b/c it is private
	System.out.println(R.numerator);*/
	
	/*BUT, this works*/
	/*System.out.println(S.getNumerator());
	System.out.println(S.getDenominator());
	System.out.println(S.toString());*/

        /*NOTE that these lines should cause it to not compile b/c it is private
	S.reduceTerms();
	Rational.computeGCD(1,2);*/
	

	/*The following methods should not change the value of S. 
	 * You must not multiply s by 5, rather retun the result of S*5. 
	 * Subtle, but HUGE difference */
	//System.out.println(S.mul(5));
	//System.out.println(S.div(5));
    //System.out.println(S.getInv());
	
	System.out.println(T.computeLCM(9, 7));
	
	
	System.out.println(S + " + " + T + " = " + S.add(T));
	System.out.println(S + " - " + T + " = " + S.sub(T));
	System.out.println(S + " * " + T + " = " + S.mul(T));
	System.out.println(S + " / " + T + " = " + S.div(T));
	/*System.out.println(S.doubleValue());
	System.out.println(S.intValue());
	System.out.println(S.longValue());
	System.out.println(S.compareTo(T));*/
    }
}
