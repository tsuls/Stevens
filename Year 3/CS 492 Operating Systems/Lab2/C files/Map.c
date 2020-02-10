#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/sched.h>
#include <linux/moduleparam.h>
#include <linux/slab.h>
#include <linux/random.h>
#include <linux/list.h>
#define UPPER_BOUND 1000
#define LOWER_BOUND 0
#define TRUE 1
#define FALSE 0
void deconstructMap(void);
void constructLinkedList(void);
void printLinkedList(void);
void freeLinkedList(void);
struct Node * dequeue(void);
void constructQueue(void);
int insertIntoMap(int toAdd);
void displayMap(void);
void initializeMap(void);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Tyler Sulsenti");

struct Node
{
	int data;
	struct Node *next;
};

struct mapNode
{
	int data;
	int valid;
};

struct Map 
{
   struct mapNode *data[UPPER_BOUND];
    int size;
};

struct Node *LLhead;
struct Node *LLcurNode;
struct Node *LLnewNode;

struct Node *Qhead;
struct Node *QcurNode;
struct Node *QnewNode;

struct Map *myMap;

int *listData;
int *queueData;
int *mapData;

static int dstruct_size = 2;
module_param(dstruct_size, int,  S_IRUSR | S_IWUSR);
MODULE_PARM_DESC(dstruct_size,"this is the specified size");

//A//
void constructLinkedList(void)
{
	LLhead = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 	LLhead-> data = listData[0];
 	LLhead -> next = NULL;
 	LLcurNode = LLhead;
 	LLnewNode = NULL;

 	int i = 1;
 	while(i < dstruct_size) 
 	{
 		LLnewNode = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
 		LLnewNode -> data = listData[i];
 		LLcurNode -> next = LLnewNode;
 		LLcurNode = LLnewNode;
 		i++;
 	}
 	LLcurNode -> next = NULL;
}

//B//
void printLinkedList(void)
{
	LLcurNode = LLhead;
 	while(NULL != LLcurNode)
 	{
 		printk("The data is: %i\n", LLcurNode->data);
 		LLcurNode = LLcurNode->next; 
 	}
}

//C//
void freeLinkedList(void)
{
	while(LLhead != NULL)
	{
		LLcurNode = LLhead;
		LLhead = LLhead -> next;
		kfree(LLcurNode);
	}
	printk("The linked list was freed\n");
}

//Add from back of list
void enqueue(struct Node *toAdd)
{
	if(Qhead == NULL)
	{
		Qhead = toAdd;
		toAdd -> next = NULL;
	}
	else
	{
		QcurNode = Qhead;
		
		while(QcurNode->next != NULL)
		{
			QcurNode = QcurNode->next;
		}

		toAdd -> next = QcurNode->next;
		QcurNode -> next = toAdd;
	}
}

//Remove from front. returns NULL if the list is empty
struct Node * dequeue(void)
{
	//printk("QcurNode is %i\n", QcurNode -> data);

	if(Qhead == NULL)
	{
		printk("The Queue is Empty\n");
		return NULL;
	}

	QcurNode = Qhead;

	struct Node *temp;
	struct Node *rtn = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
	rtn -> data = QcurNode -> data;
	rtn -> next = QcurNode -> next;

	temp = QcurNode -> next;
	kfree(QcurNode);

//	printk("QcurNode is NOW %i\n", QcurNode -> data);

	Qhead = temp;

	return rtn;
}

void printQueue(void)
{
	QcurNode = Qhead;
	int i = 0;
 	while(NULL != QcurNode)
 	{
 		printk("The data is: %i at index %i\n", QcurNode->data, i);
 		QcurNode = QcurNode->next; 
 		i++;
 	}
}

//Constructs a Queue of dstruct_size elements of random values
void constructQueue(void)
{
	int i = 0;
	while(i < dstruct_size)
	{
		struct Node *toAdd = (struct Node *)kmalloc(sizeof(struct Node), GFP_KERNEL);
		toAdd -> data = queueData[i];

		enqueue(toAdd);
		i++;
	}
}

void deconstructQueue(void)
{
	int i = 0;
	while (NULL != Qhead)
	{
		printk("Just dequeued %i at index %i", dequeue() -> data, i);
		i++;
	}

	printk("The Queue has been freed\n");
}

/*Hash function cited from 
 *https://stackoverflow.com/questions/664014/what-integer-hash-function-are-good-that-accepts-an-integer-hash-key
 */
int hash(int val) 
{
	unsigned int x = val;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = (x >> 16) ^ x;

    x = x % UPPER_BOUND - LOWER_BOUND;

    return x;
}

//Inserts into map and returns the ID. -1 is returned if it fails.
int insertIntoMap(int toAdd)
{
	int id = hash(toAdd);

	//Nothing in map at location id
	if(myMap->data[id] == NULL)
	{
		//Add
		struct mapNode *nodeToAdd = (struct mapNode *) kmalloc(sizeof(struct mapNode), GFP_KERNEL);
		nodeToAdd-> data = toAdd;
		nodeToAdd-> valid = FALSE;

		myMap->data[id] = nodeToAdd;
		myMap->size++;
		
		return id;
	}//collision
	else
	{
		int i = id;
		while(i <  UPPER_BOUND)
		{
			if(myMap->data[i] == NULL)
			{
				//Add
				struct mapNode *nodeToAdd = (struct mapNode *) kmalloc(sizeof(struct mapNode), GFP_KERNEL);
				nodeToAdd-> data = toAdd;
				nodeToAdd-> valid = FALSE;

				myMap->data[i] = nodeToAdd;
				myMap->size++;
				return i;
			}
			i++;
		}
		//if we leave while it could not be added due to size constraints.
		return -1;
	}

}

void displayMap(void)
{
	int i = 0;
	while(i < UPPER_BOUND)
	{
		if(myMap->data[i] != NULL)
		{
			printk("The data at id: %i is: %i\n",i, myMap->data[i]->data);
		}
		i++;
	}
}

void constructMap(void)
{
	initializeMap();
	int i = 0;
	while(i < dstruct_size)
	{	
		insertIntoMap(mapData[i]);
		i++;
	}
}

void initializeMap(void)
{
	int i = 0;
	while(i < UPPER_BOUND)
	{
		myMap->data[i] = NULL;
		i++;
	}
}

void deconstructMap(void)
{
	int i = 0;
	while(i < UPPER_BOUND)
	{
		kfree(myMap->data[i]);
		i++;
	}

	kfree(myMap->data);
	kfree(myMap);
	printk("The Map has been freed\n");
}


static int lab2_module_init(void) 
{
	//printk("Queue\n----------------\n");

	//printk("dstruct_size: %i\n", dstruct_size);

	//int LLtemp[dstruct_size];
	//int Qtemp[dstruct_size];
	int Mtemp[dstruct_size];
	//listData = LLtemp;
	//queueData = Qtemp;
	mapData = Mtemp;
 	//get_random_bytes(listData,sizeof(LLtemp));
 	//get_random_bytes(queueData, sizeof(Qtemp));
 	get_random_bytes(mapData, sizeof(Mtemp));

 	//printk("----------------\n");

 	/* Linked List
 	constructLinkedList();
 	printLinkedList();
 	freeLinkedList();*/

 	/*Queue
 	constructQueue();
 	printk("The queue is:\n");
 	printQueue();
 	deconstructQueue();*/

 	/*Map*/
 	myMap = (struct Map *) kmalloc(sizeof(struct Map),  GFP_KERNEL);
 	myMap->size = 0;
 	constructMap();
 	printk("The Map is:\n");
 	displayMap();
 	deconstructMap();


 	return 0;
}

 

static void lab2_module_exit(void) 
{
	printk("Module Unloaded\n");
}


module_init (lab2_module_init);
module_exit(lab2_module_exit);