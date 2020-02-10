#include <stdlib.h>
#include <stdio.h>
#include "Queue.h"

Queue enqueue(Queue q, int toAdd)
{

    int temp[q -> size + 1];    //Dynamic, get a bigger array
  
    for(int i = 0; i <= q->size; i++)
    {
        temp[i] = q -> arr[i];  //Put eveything from old array into the new one;
    }
    
    temp[(q -> size + 1)] = toAdd; // Add the new element to the end of the new arrya
    
        
    Queue nQ = (Queue) malloc(sizeof(struct  queue)); //Set up the new Queue
    nQ -> arr = temp;
    nQ -> size = q -> size + 1;
    
     return nQ; //Return 
}

void display(Queue q)
{
    for(int i = 0; i < q -> size; i++)
    {
        printf("%i\n", q -> arr[i]);
        
    }
}
//Return 1 if empty, 0 if not
int empty(Queue q)
{
	if(q -> size == 0)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

int dequeue(Queue q)
{
    int temp[q ->size - 1];
	if(empty(q) == 0) //NOT empty
	{
        int deVal = q -> arr[0];

		for(int i = 1; i < q -> size; i++)
		{	
			temp[i - 1] = q -> arr[i];
		}
        
        Queue nQ;
        nQ -> arr = temp;
        nQ -> size = q -> size - 1;
        
        q = nQ;
        
		return deVal;
	}
	else
	{
		return -1;
	}
}

int  peek(Queue q)
{
	if(empty(q) == 0) //NOT empty
	{
		return q -> arr[0];
	}
	else
	{
		return -1;
	}
}
