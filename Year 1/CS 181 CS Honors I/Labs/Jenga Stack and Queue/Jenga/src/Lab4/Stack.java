package Lab4;

import java.util.EmptyStackException;

public class Stack
{
	protected static final String NAME = "Tyler Sulsenti";
	private int[] arr;
	
	public Stack()
	{
		arr = new int[0];
	}
	
	public Stack(int[] a)
	{
		arr = a;
	}
	
	 /**
     * Returns the current size of the Stack
     * 
     * @return   an int representing the current size of the Stack
     */
	public int size()
	{
		return arr.length;//Current size
	}
	
	/**
     * Pushes the input int on to the top of the stack
     * 
     * @param toPush	the int to push on to the top of the Stack
     */
	public void push(int toPush)
	{
		int[] temp = new int[size()+1]; //Temporary array to expand the size and allow dynamic growth
		temp[0] = toPush;	//Places element on top of the Stack
		for(int i = 1; i < temp.length;i++)
		{
			temp[i] = arr[i-1];
		}
		arr = temp;
	}
	
	 /**
     * Removes the element from the top of the stack
     * 
     * @return   the element that was removed;
     */
	public int pop()
	{
		empty(); //Checks if empty
		int[] temp = new int[size()-1]; //Temporary array to shrink the size and allow dynamic growth
		int popVal = arr[0]; //Capture the element that is to be removed
		for(int i = 1; i < size(); i++)
		{
			temp[i-1] = arr[i];
		}
		arr = temp;
		return popVal; //Return it
	}
	
	/**
     * Determines if the Stack is empty or not
     * 
     * @return   throws an EmptyStackException if the stack is Empty, returns false if it is not.
     */
	public boolean empty()
	{
		if(arr.length == 0) //Check the current size of the Stack
		{
			throw new EmptyStackException(); //If empty
		}
		return false;
	}
	
	  /**
     * Shows the element that is on the top of the stack
     * 
     * @return   the element on top of the stack
     */
	public int peek()
	{
		empty();//Check if empty
		return arr[0];//Return the element that is on top of the stack
	}
}
