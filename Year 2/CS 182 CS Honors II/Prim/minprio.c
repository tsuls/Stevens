#include "minprio.h"
#include <stdlib.h>
#include <stdio.h>
MinPrio heapUP(MinPrio qp);
MinPrio heapD(MinPrio qp);
int hasParent(MinPrio qp, int index);
int hasLeftChild(MinPrio qp, int index);
int hasRightChild(MinPrio qp, int index);
void swap(Handle* arr, int i, int j);
/* the queue type */
struct minprio
{
	Handle* items;
	int cursize;
	int max;
	Comparator c;
}; /* to be defined by the implementation */
typedef struct minprio* MinPrio;

MinPrio makeQueue(Comparator comp, int maxsize)
{
	MinPrio qp = (MinPrio) malloc(sizeof(struct minprio));
	qp -> items = malloc(maxsize);
	qp -> cursize = 0;
	qp -> max = maxsize;
	qp -> c = comp;
	return qp;
}

void disposeQueue(MinPrio qp)
{
	while(qp -> cursize > 0)
	{
		dequeueMin(qp);
	}
	free(qp -> items);
	free(qp);
}

Handle enqueue(MinPrio qp, void* item)
{
	Handle h = (Handle) malloc(sizeof(struct handle));
	h -> content = item;
	h -> pos = qp -> cursize;
	qp -> items[qp -> cursize] = item;
	qp -> cursize++;
	qp = heapUP(qp);
	return h;
}

void* dequeueMin(MinPrio qp)
{
	void* element  = qp -> items[0] -> content;

	free(qp -> items[0]);

	qp -> items[0] = qp -> items[qp -> cursize];
	qp-> cursize--;
	heapD(qp);
	return element;
}

MinPrio heapUP(MinPrio qp)
{
	int curDex = qp -> cursize - 1;
	int parentDex = (qp -> items[curDex] -> pos - 2)/2;

	while(hasParent(qp, curDex) > 0 && qp -> c(qp -> items[parentDex], qp -> items[curDex]) > 0)
	{
		//In the array swap the parent and the child
		swap(qp -> items, qp -> items[parentDex] -> pos, qp -> items[curDex] -> pos);
		curDex = parentDex;
	}
	return qp;
}

MinPrio heapD(MinPrio qp)
{
	int curDex = 0; // Starting at the root.
	int lChildDex = (curDex * 2) + 1;
	int rChildDex = (curDex * 2) + 2;
	while(hasLeftChild(qp, curDex) > 0)
	{
		int smallerDex = lChildDex;
		Handle left = qp -> items[lChildDex];
		Handle right = qp -> items[rChildDex];
		if(qp -> c(left, right) > 0)
		{
			smallerDex = rChildDex;
		}

		if(qp -> c(qp -> items[curDex], qp -> items[smallerDex]) < 0)
		{
			return qp;
		}
		else
		{
			swap(qp -> items, curDex, smallerDex);
			curDex = smallerDex;
		}
	}
	return qp;
}

//Return 1 if True 0 if False
int hasLeftChild(MinPrio qp, int index)
{
	int childDex = (index * 2) + 1;

	if(qp -> items[childDex] != NULL)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

//Return 1 if True 0 if False
int hasRightChild(MinPrio qp, int index)
{
	int childDex = (index * 2) + 2;

	if(qp -> items[childDex] != NULL)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

//Return 1 if True 0 if False
int hasParent(MinPrio qp, int index)
{
	int parentDex = (qp -> items[index] -> pos - 2)/2;
	if(qp -> items[parentDex] != NULL || index > 0)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

void swap(Handle* arr, int i, int j)
{
	Handle temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}

int nonempty(MinPrio qp)
{
	if(qp != NULL)
	{
		if(qp -> cursize > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	return -1;
}
