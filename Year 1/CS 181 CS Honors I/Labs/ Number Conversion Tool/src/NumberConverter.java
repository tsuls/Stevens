
public class NumberConverter 
{
	/**
	 * Converts a given decimal number to binary
	 * @param the String representation of a decimal number to convert to binary
	 * @return Returns a String representation of the given decimal number, converted to binary.
	 */
	public String DecToBin(String input)
	{
		int num2 = Integer.parseInt(input); //Convert to int for easier division 
		
		if(num2/2 == 0 || num2 == 0) //If the number is even or is 0
		{		
			return  "" + num2%2; //Return the remainder of the number and 2. The "" will convert the int back to String.
		}
		else //If not
		{
			return DecToBin("" + num2/2) + (num2 % 2);/*Run Method again, Dividing the number by Two and keeping the remainder. 
			 										   *Return the Final number in the reverse order. The "" will convert the int back to String*/
					
		}
	}
	/**
	 * Converts a given Octal number to binary
	 * @param the String representation of a Octal number to convert to binary
	 * @return Returns a String representation of the given Octal number, converted to binary.
	 */
	public String OctToBin(String input) 
	{
		int stop = input.length(); //Hold the length
		String convert = "";
		
		for (int i = 0; i < stop; i++) //Keep going until the input String has been looped through
		{
			String back = DecToBin(input.substring(0,1)); //Grab the current first letter
			
			switch(back.length()) //In the case of the length not being a 3 bit group, make one
			{
				case 1: back = "00" + back;
						break;
				
				case 2: back = "0" + back;
						break;
			}
			
			convert = convert + back; //Add the converted group
			input = input.substring(1); //Knock off the converted number
		}
		return convert;
	}
	/**
	 * Converts a given Hex number to binary
	 * @param the String representation of a Hex number to convert to binary
	 * @return Returns a String representation of the given Hex number, converted to binary.
	 */
	public String HexToBin(String input)
	{
		String hexString = "0123456789ABCDEF"; //For reference
		int stop = input.length(); //Hold the length
		String convert = "";
		
		for(int i = 0; i < stop; i ++) //Keep going until the input String has been looped through
		{
			String back = DecToBin("" + hexString.indexOf(input.substring(0, 1))); //Get the Corresponding Hex Digit
			
			switch(back.length()) //In the case of the length not being a 3 bit group, make one
			{
				case 1: back = "000" + back;
						break;
				
				case 2: back = "00" + back;
						break;
				
				case 3: back = "0" + back;
						break;
			}
			convert = convert + back; //Add the converted group
			input = input.substring(1); //Knock off the converted number
		}
		return convert;		
	}
	
	/**
	 * Converts a given binary number to decimal
	 * @param the String representation of a binary number to convert to decimal
	 * @return Returns a String representation of the given binary number, converted to decimal.
	 */
	public  String BinToDec(String input)
	{
		int pos = 0; //The position of the first character in any given string
		int num = Integer.parseInt(input.substring(pos,pos+1)); //Grab the first Character in that string.
		
		if(pos < input.length()-1) /*if the position is less than the length - 1 (Because Length starts counting at One and 
									*my Position & Substring start counting at 0)*/
		{
			return (Integer.parseInt(BinToDec(input.substring(pos+1))) + (num * ((int) Math.pow(2, input.length()-1)))) + ""; //Run The Method again, moving working with a substring of the rest of the number after the current position. 
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
	 * Converts a given binary number to Octal
	 * @param the String representation of a binary number to convert to Octal
	 * @return Returns a String representation of the given binary number, converted to Octal.
	 */ 	
	public String BinToOct(String input)
	{	
		if(input.length() <= 3) //If there are no more groups of 3
		{
			return BinToDec(input); //Return the rest of the string converted
		}
		else
		{
			return BinToOct(input.substring(0, input.length()-3))  +  BinToDec(input.substring(input.length()-3,input.length())); //Keep calling the method, converting groups of three 
		}
	}
	/**
	 * Converts a given binary number to Hex
	 * @param the String representation of a binary number to convert to Hex
	 * @return Returns a String representation of the given binary number, converted to Hex.
	 */
	public String BinToHex(String input)
	{
		String hexString = "0123456789ABCDEF"; //For reference
		
		if(input.length() <= 4) //If there are no more groups of 4
		{
			return "" + hexString.substring((Integer.parseInt(BinToDec(input))),(Integer.parseInt(BinToDec(input)))+1); //Return the rest of the string converted to the corresponding Hex Digit
		}
		else
		{
			return BinToHex(input.substring(0, input.length()-4)) + hexString.substring((Integer.parseInt(BinToDec(input.substring(input.length()-4,input.length())))),((Integer.parseInt(BinToDec(input.substring(input.length()-4,input.length())))))+1); //Keep calling the method, converting groups of four 
		}
	}
}
	
	
	
