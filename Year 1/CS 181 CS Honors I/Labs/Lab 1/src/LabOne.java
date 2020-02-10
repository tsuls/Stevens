import java.util.Scanner;
/**
 * Tyler Sulsenti
 * CS 181
 * I pledge my Honor that i have abided by the Stevens Honor System.
 */
public class LabOne
{
	/**
	 * Main Method
	 */
	public static void main (String args[])
	{
		Scanner in = new Scanner(System.in);
		LabOne l = new LabOne();
		
		
		System.out.println("Please enter number to Convert");
		String input = in.nextLine();
		
		String ltr = input.substring(0,1);
		
		if(ltr.equals("i"))
		{
			System.out.println("Your Converted Number is: b" + BinaryToDecimal(input.substring(1)));
		}
		else if(ltr.equals("b"))
		{
			System.out.println("Your Converted Number is: i" + DecimalToBinary(input.substring(1)));
		}
		
		System.out.println("Please Enter the number you want to find the Square Root of: ");
		double inNum = in.nextDouble();
		System.out.println("The Square Root of " + inNum + " is " + SquareRoot(inNum));
		
		System.out.println("Please enter coeffecient a: ");
		int a = in.nextInt();
	
		System.out.println("Please enter coeffecient b: ");
		int b = in.nextInt();
		
		System.out.println("Please enter coeffecient c: ");
		int c = in.nextInt();
		
		System.out.println(findQuadRoot(a,b,c));		

	}
	/**
	 * Converts a given binary number to decimal
	 * @param the String representation of a binary number to convert to decimal
	 * @return Returns a String representation of the given binary number, converted to decimal.
	 */
	public static  String BinaryToDecimal(String input) //Receive String Input
	{
			int num = Integer.parseInt(input); //Convert number To an int
			
			if(num/2 == 0 || num == 0) //If the number is even or is 0
			{
				return "" + num % 2; //Return the remainder of the number and 2. The "" will convert the int back to String.
			}
			else //If not
			{
				return BinaryToDecimal("" + num/2) + (num % 2);	/*Run Method again, Dividing the number by Two and keeping the remainder. 
																 *Return the Final number in the reverse order. The "" will convert the int back to String*/	
			}
	}
	/**
	 * Converts a given decimal number to binary
	 * @param the String representation of a decimal number to convert to binary
	 * @return Returns a String representation of the given decimal number, converted to binary.
	 */
	public static String DecimalToBinary(String input)
	{
		int pos = 0; //The position of the first character in any given string
		int num = Integer.parseInt(input.substring(pos,pos+1)); //Grab the first Character in that string.
		
		if(pos < input.length()-1) /*if the position is less than the length - 1 (Because Length starts counting at One and 
									*my Position & Substring start counting at 0)*/
		{
			return (Integer.parseInt(DecimalToBinary(input.substring(pos+1))) + (num * ((int) Math.pow(2, input.length()-1)))) + ""; //Run The Method again, moving working with a substring of the rest of the number after the current position. 
																																	   //Convert the returned number to an int value so it may be used as such.
																																	   // To that, Concatenate (Since we are returning a String), the product of our current number at our current position by 
																																	   // and 2, our binary base, to the power of the length of our current substring minus one, since we must start counting from the power of 0.
		}
		else
		{
			return "" + (num * ((int) Math.pow(2, input.length()-1))); //Else, When the current position reaches the length - 1, perform the algorithm on the final int-converted substring and then convert back to String using  + "".
		}
		
	}
	
	/**
	 * Finds the Square Root of the input "num"
	 * @param A double Positive Number
	 * @return the Square root of the input num. NaN if the input is ) or below
	 */
	public static String SquareRoot(double num)
	{
		double initX = 10; //An initial guess
		double nextX = 0; //declare the next Guess's variable
		
		int iterator = 20; //number of divisions
		if(num <= 0)
		{
			return "" + Double.NaN;
		}
		
		else
		{
			for(int i = 0; i < iterator; i++)
			{
				double numerator = ((Math.pow(initX, 2)) - num); //Function in Newton's method
				double denom = 2*initX; //Derivative of that function.
			
				nextX = initX - (numerator/denom); // Applying Newton's method to find the next guess
			
				initX = nextX; // Setting up for next Iteration
			}
		}	
		return String.format("%.10f",nextX); //nextX will have a number that is the square root or close when it is finished looping, to Ten decimal Places.	
	}
	
	/**
	 * Find the quadratic roots of the coefficients given
	 * @param the coefficient, A, of AX^2 in a quadratic equation
	 * @param the coefficient, B, of BX in a quadratic equation
	 * @param the coefficient, C of C in a quadratic equation
	 * @return Returns a String containing the two roots of the quadratic equation.
	 */
	public static String findQuadRoot(int a, int b, int c)
	{
		int r1 = (int) (((b) - Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a));
		
		int r2 = (int) (((b) + Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a));
		
		return "The roots are r1: " + r1 + " and r2: " + r2;
	}

}
