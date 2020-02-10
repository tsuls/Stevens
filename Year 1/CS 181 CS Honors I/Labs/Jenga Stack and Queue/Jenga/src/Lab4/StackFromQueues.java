/*
 * StacksFromQueues.java
 *
 * I Pledge my honor that i have abided by the Stevens Honor System.
 * Tyler Sulsenti
 *
 */

package Lab4;

/**
 * Creates a Stack out of Two Queues, no direct use of Arrays
 *
 * @author Tyler Sulsenti
 * @version 1.0
 * @since 2016/10/05
 * 
 */

import java.util.EmptyStackException;

public class StackFromQueues 
{
	protected static final String NAME = "Tyler Sulsenti";
	Queue qData; // a Main Queue for data
	Queue qScratch; //a Scratch Queue for transfers 
	
	public StackFromQueues()
	{
		qData = new Queue();
		qScratch = new Queue();
	}
	public StackFromQueues(int[] a)
	{
		qData = new Queue(a);
		qScratch = new Queue();
	}
	
	 /**
     * Returns the current size of the Stack
     * 
     * @return   an int representing the current size of the Stack
     */
	public int size()
	{
		return qData.size(); //Size of the primary Queue
	}
	
	 /**
     * Pushes the input int on to the top of the stack
     * 
     * @param toPush	the int to push on to the top of the Stack
     */
	public void push(int toPush)
	{
		clearQueue(qScratch); //Ensure the scratch queue is clear
		
		qScratch.enqueue(toPush); //Place the input value into the empty scratch queue to ensure it is first
		
		int qDLen = qData.size(); //Capture the current size of the data queue
		for(int i = 0; i < qDLen; i++)
		{
			qScratch.enqueue(qData.dequeue()); //Move whatever is in the data queue to the scratch queue, thus placing them behind the toPush input vairable
		}
		
		int qSLen = qScratch.size(); //Capture the current size of the scratch queue
		for(int i = 0; i < qSLen; i++)
		{
			qData.enqueue(qScratch.dequeue()); //Move whatever is in the scratch queue back to the data queue to maintain consistency
		}
	}
	
	 /**
     * Removes the element from the top of the stack
     * 
     * @return   the element that was removed;
     */
	public int pop()
	{
		empty(); //Check if the Stack is empty
		return qData.dequeue(); //removes the first element in the data queue
	}
	
	 /**
     * Determines if the Stack is empty or not
     * 
     * @return   throws an EmptyStackException if the stack is Empty, returns false if it is not.
     */
	public boolean empty()
	{
		if(qData.size() == 0) //Check the current size of the data queue
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
		empty();//Check the current size of the data queue
		return qData.peek();//Get the element that is on top of the data queue
	}
	
	 /**
     * Clears all data from a given Queue
     * 
     * @param toClear	the Queue to clear data from
     */
	public void clearQueue(Queue toClear)
	{
		for(int i = 0; i < toClear.size(); i++) //Transverse the queue to clear
		{
			toClear.dequeue(); //Remove all elements from the queue to clear
		}
	}
}
