package Lab7;

public class IntegerOverflowException extends Exception
{
	protected int[] operands;
	protected boolean operator;
	
	
	public IntegerOverflowException(int[] a, boolean b)
	{
		operands = a;
		operator = b;
	}	

	
	/**
	 * gets the operands variable
	 * 
	 * @return operands
	 */
	public int[] getOperands()
	{
		return operands;
	}
	
	/**
	 * gets the operator variable 
	 * 
	 * @return operator
	 */
	public boolean getOperator()
	{
		return operator;
	}
	
	/**
	 * gets the error message
	 * 
	 * @return the error message;
	 */
	public String getMessage()
	{
		if(operands.length <= 1)
		{
			if(operator) //abs
			{
				return "Exception When trying to take the absolute value of " + operands[0];
			}
			else //negate
			{
				return "Exception When trying to negate " + operands[0];
			}
				
		}
		else
		{
			if(operator) //add or sub
			{
				return "Exception when trying to add or subtract operands " + operands[0] + " and " + operands[1];
			}
			else //mul or div
			{
				return "Exception when trying to multiply or divide operands " + operands[0] + " and " + operands[1];
			}
		}
			
	}
}
