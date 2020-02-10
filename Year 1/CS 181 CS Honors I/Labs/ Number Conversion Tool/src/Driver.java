/**
 * Main Method
 * @Author Tyler Sulsenti
 * I Pledge My Honor that i have abided by the Stevens Honor Systems
 */
public class Driver 
{
	public static void main (String[] args)
	{
		NumberConverter n = new NumberConverter();
		
		System.out.println("Enter a Number To convert: ");
		String num = args[0];
	
		System.out.println("Enter the target letter to convert to: ");
		String target = args[1];
	
		String ltr = num.substring(0,1);
		String ans = "";
		
		if(ltr.equals("i")) //Input is Decimal
		{
			ans = n.DecToBin(num.substring(1)); //Convert to Binary
			if(target.equals("i")) //Convert based upon the user's input
			{
				System.out.println("Your converted number is: " + num);
			}
			if(target.equals("b"))
			{
				System.out.println("Your converted number is: b" + ans);
			}
			if(target.equals("o"))
			{
				System.out.println("Your converted number is: o" + n.BinToOct(ans));
			}
			if(target.equals("x"))
			{
				System.out.println("Your converted number is: x" + n.BinToHex(ans));
			}
		}
		else if(ltr.equals("b")) //Input is Binary
		{
			ans = num.substring(1);
			if(target.equals("i"))//Convert based upon the user's input
			{
				System.out.println("Your converted number is: i" + n.BinToDec(ans) );
			}
			if(target.equals("b"))
			{
				System.out.println("Your converted number is: " + num);
			}
			if(target.equals("o"))
			{
				System.out.println("Your converted number is: o" + n.BinToOct(ans));
			}
			if(target.equals("x"))
			{
				System.out.println("Your converted number is: x" + n.BinToHex(ans));
			}
		}
		else if(ltr.equals("o")) //Input is Octal
		{
			ans = n.OctToBin(num.substring(1)); //Convert to Binary
			if(target.equals("i"))//Convert based upon the user's input
			{
				System.out.println("Your converted number is: i" + n.BinToDec(ans) );
			}
			if(target.equals("b"))
			{
				System.out.println("Your converted number is: b" + ans);
			}
			if(target.equals("o"))
			{
				System.out.println("Your converted number is: " + num);
			}
			if(target.equals("x"))
			{
				System.out.println("Your converted number is: x" + n.BinToHex(ans));
			}
		}
		else if(ltr.equals("x")) //Input is Hex
		{
			ans = n.HexToBin(num.substring(1)); //Convert to Binary
			if(target.equals("i"))//Convert based upon the user's input
			{
				System.out.println("Your converted number is: i" + n.BinToDec(ans) );
			}
			if(target.equals("b"))
			{
				System.out.println("Your converted number is: b" + ans);
			}
			if(target.equals("o"))
			{
				System.out.println("Your converted number is: o" + n.BinToOct(ans));
			}
			if(target.equals("x"))
			{
				System.out.println("Your converted number is: " + num);
			}
		}	
	}
}
